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
        LOOK_FOR_CONE,
        COLLECT_CONE,
        MOVE_HORIZONTAL_TO_TRANSFER,
        TRANSFER_CONE,
        RELEASE_CONE,
        MOVE_VERTICAL_TO_SCORE,
        SCORE_CONE,
        MOVE_VERTICAL_TO_TRANSFER
    }

    RobotState state = RobotState.START;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);

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

                telemetry.addLine(String.format("current FSM state: %s", state));

                /*

                    TO DO:

                    - update FSM to include color/range sensor data

                        - extend horizontal lift to "wait to collect"
                        - when the button is pressed, slowly move forward until claw_sensor detects red/blue
                        - then, close the claw (so that it grabs the cone)

                        - bring the cone into the hook
                        - when hook_sensor detects red/blue, meaning the cone is in the cup, extend the hook (so that it grabs the cone)

                */

                switch (state) {

                    case START:
                        if (gamepad1.x) { // start FSM
                            state = RobotState.MOVE_HORIZONTAL_TO_COLLECT;
                        }
                        break;

                    case MOVE_HORIZONTAL_TO_COLLECT:
                        Robot.servoRotateClaw.runToPosition("zero"); // rotate claw down
                        Robot.horizontalLift.runToPosition("wait to collect"); // move horizontal lift to wait to collect cone
                        timer.reset();
                        state = RobotState.LOOK_FOR_CONE;
                        break;

                    case LOOK_FOR_CONE:
                        if (gamepad1.dpad_right && timer.seconds() >= Robot.servoRotateClaw.time && !liftIsMoving(Robot.horizontalLift, "wait to collect")) {
                            
                            // "search" for cone using sensor
                            
                            // if () {
                            //     state = RobotState.COLLECT_CONE;
                            // }

                        }
                        break;

                    case COLLECT_CONE:
                        Robot.servoClaw.runToPosition("hold"); // close claw
                        timer.reset();
                        state = RobotState.MOVE_HORIZONTAL_TO_TRANSFER;
                        break;

                    case MOVE_HORIZONTAL_TO_TRANSFER:
                        if (timer.seconds() >= Robot.servoClaw.time) {
                            Robot.servoRotateClaw.runToPosition("transfer"); // rotate claw up
                            Robot.horizontalLift.runToPosition("transfer"); // move horizontal lift to transfer cone
                            state = RobotState.TRANSFER_CONE;
                        }
                        break;

                    case TRANSFER_CONE:
                        if (liftIsMoving(Robot.horizontalLift, "transfer")) {
                            Robot.servoHook.runToPosition("hold", false); // extend hook
                            timer.reset();
                            state = RobotState.RELEASE_CONE;
                        }
                        break;

                    case RELEASE_CONE:
                        if (timer.seconds() >= Robot.servoHook.time) {
                            Robot.servoClaw.runToPosition("release", false); // open claw
                            timer.reset();
                            state = RobotState.MOVE_VERTICAL_TO_SCORE;
                        }
                        break;

                    case MOVE_VERTICAL_TO_SCORE:
                        if (gamepad1.y && timer.seconds() >= Robot.servoClaw.time) {
                            Robot.servoRotateHook.runToPosition("score", false); // rotate hook to score cone
                            Robot.verticalLift.runToPosition("high", false); // move vertical lift to score cone
                            state = RobotState.SCORE_CONE;
                        }
                        break;

                    case SCORE_CONE:
                        if (liftIsMoving(Robot.verticalLift, "high")) {
                            Robot.servoHook.runToPosition("release", false); // retract hook
                            timer.reset();
                            state = RobotState.MOVE_VERTICAL_TO_TRANSFER;
                        }
                        break;

                    case MOVE_VERTICAL_TO_TRANSFER:
                        if (timer.seconds() >= Robot.servoClaw.time) {
                            Robot.servoRotateHook.runToPosition("transfer", false); // rotate hook to transfer cone
                            Robot.verticalLift.runToPosition("transfer", false); // move vertical lift to transfer cone
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

                Robot.servoClaw.teleOpAssistMode(gamepad2.dpad_left, gamepad2.dpad_right);
                Robot.servoRotateClaw.teleOpAssistMode(gamepad2.dpad_up, gamepad2.dpad_down);

                Robot.servoHook.teleOpAssistMode(gamepad2.y, gamepad2.a);
                Robot.servoRotateHook.teleOpAssistMode(gamepad2.x, gamepad2.b);

//                Robot.horizontalLift.teleOpAssistMode(gamepad1.dpad_down, gamepad1.dpad_left, gamepad1.dpad_right, gamepad1.dpad_up);
//                Robot.verticalLift.teleOpAssistMode(gamepad1.a, gamepad1.x, gamepad1.b, gamepad1.y);

            } else if (Robot.mode == "manual") { // manual mode

                Robot.servoClaw.teleOpManualMode(gamepad2.dpad_left, gamepad2.dpad_right);
                Robot.servoRotateClaw.teleOpManualMode(gamepad2.dpad_up, gamepad2.dpad_down);

                Robot.servoHook.teleOpManualMode(gamepad2.y, gamepad2.a);
                Robot.servoRotateHook.teleOpManualMode(gamepad2.x, gamepad2.b);

//                Robot.horizontalLift.teleOpManualMode(-gamepad2.left_stick_y, gamepad2.left_bumper);
//                Robot.verticalLift.teleOpManualMode(-gamepad2.right_stick_y, gamepad2.right_bumper);

            }

            Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_bumper);

            telemetry.update();

        }

    }

    public boolean liftIsMoving(LiftClass liftClass, String position) {
        int currentPos = liftClass.motor.getCurrentPosition();
        int wantedPos = liftClass.positionToDistance(position);
        return Math.abs(currentPos - wantedPos) < 10;
    }

}

/*

gamepad1.left_stick_x:  strafing
gamepad1.left_stick_y:  driving
gamepad1.right_stick_x: turning
gamepad1.a:             move vertical lift to transfer cone
gamepad1.x:             move vertical lift to low junction
gamepad1.b:             move vertical lift to middle junction
gamepad1.y:             move vertical lift to high junction
gamepad1.x (FSM mode):  start FSM
gamepad1.y (FSM mode):  stop FSM
gamepad1.left_bumper:   toggle max drivetrain speed
gamepad1.dpad_down:     move horizontal lift to zero
gamepad1.dpad_left:     move horizontal lift to wait to collect cone
gamepad1.dpad_right:    move horizontal lift to collect cone
gamepad1.back:          switch to FSM mode
gamepad1.guide:         switch to assist mode
gamepad1.start:         switch to manual mode

gamepad2.left_stick_y:  moving horizontal lift
gamepad2.right_stick_y: moving vertical lift
gamepad2.a:             retract hook to release cone
gamepad2.x:             rotate hook to transfer cone
gamepad2.b:             rotate hook to score cone
gamepad2.y:             extend hook to hold cone
gamepad2.dpad_down:     rotate claw down
gamepad2.dpad_left:     open claw
gamepad2.dpad_right:    close claw
gamepad2.dpad_up:       rotate claw up
gamepad2.left_bumper:   toggle encoder limits for horizontal lift
gamepad2.right_bumper:  toggle encoder limits for vertical lift

*/