package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.powerPlay.robot.LiftClass;
import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@TeleOp(name="FSM teleOp", group="competition")
public class FSMteleOp extends LinearOpMode {

    public enum RobotState {
        START,
        MOVE_HORIZONTAL_TO_COLLECT,
        LOWER_CLAW,
        MOVE_HORIZONTAL_INTO_CONE,
        COLLECT_CONE,
        RAISE_CLAW,
        MOVE_HORIZONTAL_TO_TRANSFER,
        TRANSFER_CONE,
        RELEASE_CONE,
        MOVE_VERTICAL_TO_SCORE,
        ROTATE_HOOK_TO_SCORE,
        SCORE_CONE,
        ROTATE_HOOK_TO_TRANSFER,
        MOVE_VERTICAL_TO_TRANSFER,
        RESTART
    }

    RobotState state = RobotState.START;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);
        Robot.mode = "assist";

        waitForStart();

        timer.reset();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        while (opModeIsActive()) {

            // Switching modes

            telemetry.addLine(String.format("Robot.mode: %s", Robot.mode));

            if (gamepad1.back) {
                Robot.resetScoring();
                Robot.mode = "FSM";
            }

            if (gamepad1.guide) Robot.mode = "assist";
            if (gamepad1.start) Robot.mode = "manual";

            // Finite State Machine mode

            if (Robot.mode == "FSM") {

                telemetry.addData("current FSM state", state);

                switch (state) {

                    case START:
                        if (gamepad1.x) { // start FSM
                            state = RobotState.MOVE_HORIZONTAL_TO_COLLECT;
                        }
                        break;

                    case MOVE_HORIZONTAL_TO_COLLECT:
                        Robot.horizontalLift.runToPosition("wait to collect"); // move horizontal lift to wait to collect cone
                        state = RobotState.LOWER_CLAW;
                        break;

                    case LOWER_CLAW:
                        if (liftIsMoving(Robot.horizontalLift, "wait to collect")) {
                            Robot.servoRotateClaw.runToPosition("zero"); // rotate claw down
                            timer.reset();
                            state = RobotState.MOVE_HORIZONTAL_INTO_CONE;
                        }
                        break;

                    case MOVE_HORIZONTAL_INTO_CONE:
                        if (timer.milliseconds() >= Robot.servoRotateClaw.time) {
                            Robot.horizontalLift.runToPosition("collect"); // move horizontal lift to collect cone
                            state = RobotState.COLLECT_CONE;
                        }
                        break;

                    case COLLECT_CONE:
                        if (liftIsMoving(Robot.horizontalLift, "collect")) {
                            Robot.servoClaw.runToPosition("hold"); // close claw
                            timer.reset();
                            state = RobotState.MOVE_HORIZONTAL_TO_TRANSFER;
                        }
                        break;

                    case RAISE_CLAW:
                        if (timer.milliseconds() >= Robot.servoClaw.time) {
                            Robot.servoRotateClaw.runToPosition("transfer"); // rotate claw up
                            timer.reset();
                            state = RobotState.MOVE_HORIZONTAL_TO_TRANSFER;
                        }
                        break;

                    case MOVE_HORIZONTAL_TO_TRANSFER:
                        if (timer.milliseconds() >= Robot.servoRotateClaw.time) {
                            Robot.horizontalLift.runToPosition("transfer"); // move horizontal lift to transfer cone
                            state = RobotState.TRANSFER_CONE;
                        }
                        break;

                    case TRANSFER_CONE:
                        if (liftIsMoving(Robot.horizontalLift, "transfer")) {
                            Robot.servoHook.runToPosition("hold"); // extend hook
                            timer.reset();
                            state = RobotState.RELEASE_CONE;
                        }
                        break;

                    case RELEASE_CONE:
                        if (timer.milliseconds() >= Robot.servoHook.time) {
                            Robot.servoClaw.runToPosition("release"); // open claw
                            timer.reset();
                            state = RobotState.MOVE_VERTICAL_TO_SCORE;
                        }
                        break;

                    case MOVE_VERTICAL_TO_SCORE:
                        if (timer.milliseconds() >= Robot.servoClaw.time) {
                            Robot.verticalLift.runToPosition("high"); // move vertical lift to score cone
                            state = RobotState.ROTATE_HOOK_TO_SCORE;
                        }
                        break;

                    case ROTATE_HOOK_TO_SCORE:
                        if (liftIsMoving(Robot.verticalLift, "high")) {
                            Robot.servoRotateHook.runToPosition("score"); // rotate hook to score cone
                            timer.reset();
                            state = RobotState.SCORE_CONE;
                        }
                        break;

                    case SCORE_CONE:
                        if (timer.milliseconds() >= Robot.servoRotateHook.time) {
                            Robot.servoHook.runToPosition("release"); // retract hook
                            timer.reset();
                            state = RobotState.ROTATE_HOOK_TO_TRANSFER;
                        }
                        break;

                    case ROTATE_HOOK_TO_TRANSFER:
                        if (timer.milliseconds() >= Robot.servoHook.time) {
                            Robot.servoRotateHook.runToPosition("transfer"); // rotate hook to transfer position
                            timer.reset();
                            state = RobotState.MOVE_VERTICAL_TO_TRANSFER;
                        }
                        break;

                    case MOVE_VERTICAL_TO_TRANSFER:
                        if (timer.milliseconds() >= Robot.servoRotateHook.time) {
                            Robot.verticalLift.runToPosition("transfer"); // move vertical lift to transfer cone
                            state = RobotState.RESTART;
                        }
                        break;

                    case RESTART:
                        if (liftIsMoving(Robot.verticalLift, "transfer")) {
                            timer.reset();
                            state = RobotState.MOVE_HORIZONTAL_TO_COLLECT;
                        }
                        break;

                    default:
                        state = RobotState.START;

                }

                // pause FSM

                if (gamepad1.y && state != RobotState.START) {
                    state = RobotState.START;
                }

            } else if (Robot.mode == "assist") { // assist mode

                Robot.servoClaw.teleOpAssistMode(gamepad1.dpad_left, gamepad1.dpad_right);
                Robot.servoRotateClaw.teleOpAssistMode(gamepad1.dpad_down, gamepad1.dpad_up);

                Robot.servoHook.teleOpAssistMode(gamepad1.y, gamepad1.a);
                Robot.servoRotateHook.teleOpAssistMode(gamepad1.x, gamepad1.b);

                Robot.closeHookUsingColorSensor();

                Robot.horizontalLift.teleOp(-gamepad2.left_stick_y, gamepad2.left_bumper, gamepad2.dpad_down, gamepad2.dpad_left, gamepad2.dpad_right, gamepad2.dpad_up);
                Robot.verticalLift.teleOp(-gamepad2.right_stick_y, gamepad2.right_bumper, gamepad2.a, gamepad2.x, gamepad2.b, gamepad2.y);

            } else if (Robot.mode == "manual") { // manual mode

                Robot.servoClaw.teleOpManualMode(gamepad1.dpad_left, gamepad1.dpad_right);
                Robot.servoRotateClaw.teleOpManualMode(gamepad1.dpad_down, gamepad1.dpad_up);

                Robot.servoHook.teleOpManualMode(gamepad1.y, gamepad1.a);
                Robot.servoRotateHook.teleOpManualMode(gamepad1.x, gamepad1.b);

                Robot.horizontalLift.teleOp(-gamepad2.left_stick_y, gamepad2.left_bumper, gamepad2.dpad_down, gamepad2.dpad_left, gamepad2.dpad_right, gamepad2.dpad_up);
                Robot.verticalLift.teleOp(-gamepad2.right_stick_y, gamepad2.right_bumper, gamepad2.a, gamepad2.x, gamepad2.b, gamepad2.y);

            }

            Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_bumper);

            printRobotData();

        }

    }

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");

        Robot.servoClaw.printData();
        Robot.servoHook.printData();
        Robot.servoRotateClaw.printData();
        Robot.servoRotateHook.printData();

        Robot.horizontalLift.printData();
        Robot.verticalLift.printData();

        Robot.hookSensor.printImportantData();

        telemetry.update();

    }

    public boolean liftIsMoving(LiftClass liftClass, String position) {
        int currentPos = liftClass.motor.getCurrentPosition();
        int wantedPos = liftClass.positionToDistance(position);
        return Math.abs(currentPos - wantedPos) < 10;
    }

}

/*

Gamepad 1 - driving, servos, FSM, and modes

gamepad1.left_stick_x:  strafing
gamepad1.left_stick_y:  driving
gamepad1.right_stick_x: turning

gamepad1.a:             retract hook (to release cone)
gamepad1.x:             rotate hook up
gamepad1.b:             rotate hook down
gamepad1.y:             extend hook (to hold cone)

gamepad1.x (FSM mode):  start FSM
gamepad1.y (FSM mode):  stop FSM

gamepad1.dpad_down:     rotate claw down
gamepad1.dpad_left:     open claw
gamepad1.dpad_right:    close claw
gamepad1.dpad_up:       rotate claw upe

gamepad1.left_bumper:   toggle max drivetrain speed

gamepad1.back:          switch to FSM mode
gamepad1.guide:         switch to assist mode
gamepad1.start:         switch to manual mode

Gamepad 2 - lifts

gamepad2.left_stick_y:  moving horizontal lift
gamepad2.right_stick_y: moving vertical lift

gamepad2.a:             move vertical lift to transfer
gamepad2.x:             move vertical lift to low
gamepad2.b:             move vertical lift to middle
gamepad2.y:             move vertical lift to high

gamepad2.dpad_down:     move horizontal lift to zero
gamepad2.dpad_left:     move horizontal lift to wait to collect cone
gamepad2.dpad_right:    move horizontal lift to collect cone

gamepad2.left_bumper:   toggle encoder limits for horizontal lift
gamepad2.right_bumper:  toggle encoder limits for vertical lift

*/