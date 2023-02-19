package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;
import org.firstinspires.ftc.teamcode.powerPlay.robot.LiftClass;
import org.firstinspires.ftc.teamcode.powerPlay.robot.ServoClass;
import org.firstinspires.ftc.teamcode.powerPlay.robot.CRServoClass;

@TeleOp(name="new FSM teleOp", group="competition")
public class NewFSMteleOp extends LinearOpMode {

    public enum RobotState {
        START,                         // start FSM
        POSITION_TO_COLLECT,           // when button is pressed, move horizontal lift to "wait to collect" and rotate claw to "collect"
        ATTEMPT_TO_COLLECT,            // slowly start moving lift forward until sensor detects a cone OR until "collect"
        COLLECT,                       // move claw to "close"
        RETRACT_INTAKE,                // rotate claw to "transfer" & move horizontal lift to "transfer"
        TRANSFER_CONE,                 // move claw to "open"
        HOLD_CONE,                     // move hook to "hold"
        MOVE_VERTICAL_TO_SCORE,        // raise vertical lift to "high", rotate hook to "middle", move horizontal lift to "wait to collect", and rotate claw to "collect"
        ROTATE_HOOK_TO_SCORE,          // rotate hook to "score"
        SCORE_CONE,                    // move hook to "release"
        ROTATE_HOOK_TO_MIDDLE,         // rotate hook to "middle"
        RETRACT_DEPOSITOR              // lower vertical lift to "transfer" and rotate hook to "transfer"
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

                    // start FSM

                    case START:
                        if (gamepad2.a) {
                            state = RobotState.POSITION_TO_COLLECT;
                        }
                        break;

                    // when button is pressed, move horizontal lift to "wait to collect" and rotate claw to "collect"

                    case POSITION_TO_COLLECT:
                        if (gamepad2.x && liftIsntMoving(Robot.verticalLift) && servoIsntMoving(Robot.servoRotateHook) && liftIsntMoving(Robot.horizontalLift) && crServoIsntMoving(Robot.crServoRotateClaw)) { // keep button here
                            Robot.horizontalLift.runToPosition("wait to collect");
                            Robot.crServoRotateClaw.runToPosition("collect");
                            timer.reset();
                            state = RobotState.ATTEMPT_TO_COLLECT;
                        }
                        break;

                    // slowly start moving lift forward until sensor detects a cone OR until "collect"

                    case ATTEMPT_TO_COLLECT:
                        if (gamepad2.x && liftIsntMoving(Robot.horizontalLift) && crServoIsntMoving(Robot.crServoRotateClaw)) {
                            Robot.horizontalLift.runToPosition("collect", false, 0.3);
                            timer.reset();
                            state = RobotState.COLLECT;
                        }
                        break;

                    // move claw to "close"

                    case COLLECT:
                        if (gamepad2.x && Robot.checkForConeInClaw() || liftIsntMoving(Robot.horizontalLift)) {
                            if (Robot.checkForConeInClaw()) Robot.horizontalLift.stop();
                            Robot.servoClaw.runToPosition("hold");
                            timer.reset();
                            state = RobotState.RETRACT_INTAKE;
                        }
                        break;

                    // rotate claw to "transfer" & move horizontal lift to "transfer"

                    case RETRACT_INTAKE:
                        if (gamepad2.x && servoIsntMoving(Robot.servoClaw)) {
                            Robot.crServoRotateClaw.runToPosition("transfer");
                            Robot.horizontalLift.runToPosition("transfer");
                            timer.reset();
                            state = RobotState.TRANSFER_CONE;
                        }
                        break;

                    // move claw to "open"

                    case TRANSFER_CONE:
                        if (gamepad2.x && crServoIsntMoving(Robot.crServoRotateClaw) && liftIsntMoving(Robot.horizontalLift)) {
                            Robot.servoClaw.runToPosition("open");
                            timer.reset();
                            state = RobotState.HOLD_CONE;
                        }
                        break;

                    // move hook to "hold"

                    case HOLD_CONE:
                        if (gamepad2.x && servoIsntMoving(Robot.servoClaw)) {
                            Robot.servoHook.runToPosition("hold");
                            timer.reset();
                            state = RobotState.MOVE_VERTICAL_TO_SCORE;
                        }
                        break;

                    // raise vertical lift to "high", rotate hook to "middle", move horizontal lift to "wait to collect", and rotate claw to "collect"

                    case MOVE_VERTICAL_TO_SCORE:
                        if (gamepad2.x && servoIsntMoving(Robot.servoHook)) {
                            Robot.verticalLift.runToPosition("high");
                            Robot.servoRotateHook.runToPosition("middle");
                            Robot.horizontalLift.runToPosition("wait to collect");
                            Robot.crServoRotateClaw.runToPosition("collect");
                            timer.reset();
                            state = RobotState.ROTATE_HOOK_TO_SCORE;
                        }
                        break;

                    // rotate hook to "score"

                    case ROTATE_HOOK_TO_SCORE:
                        if (gamepad2.x && liftIsntMoving(Robot.verticalLift) && servoIsntMoving(Robot.servoHook)) {
                            Robot.servoRotateHook.runToPosition("score");
                            timer.reset();
                            state = RobotState.SCORE_CONE;
                        }
                        break;
 
                    // move hook to "release"

                    case SCORE_CONE:
                        if (gamepad2.x && servoIsntMoving(Robot.servoRotateHook)) {
                            Robot.servoHook.runToPosition("release");
                            timer.reset();
                            state = RobotState.ROTATE_HOOK_TO_MIDDLE;
                        }
                        break;

                    // rotate hook to "middle"

                    case ROTATE_HOOK_TO_MIDDLE:
                        if (gamepad2.x && servoIsntMoving(Robot.servoHook)) {
                            Robot.servoRotateHook.runToPosition("middle");
                            timer.reset();
                            state = RobotState.RETRACT_DEPOSITOR;
                        }
                        break;

                    // lower vertical lift to "transfer" and rotate hook to "transfer"

                    case RETRACT_DEPOSITOR:
                        if (gamepad2.x && servoIsntMoving(Robot.servoRotateHook)) {
                            Robot.verticalLift.runToPosition("transfer");
                            Robot.servoRotateHook.runToPosition("transfer");
                            timer.reset();
                            state = RobotState.POSITION_TO_COLLECT;
                        }
                        break;

                    default: state = RobotState.START;

                }

                Robot.crServoRotateClaw.checkForStop();

                // pause FSM

                if (gamepad2.y && state != RobotState.START) {
                    state = RobotState.START;
                }

            } else {

                if (Robot.mode == "assist") { // assist mode

                    Robot.servoClaw.teleOpAssistMode(gamepad1.dpad_left, gamepad1.dpad_right);
                    Robot.servoHook.teleOpAssistMode(gamepad1.y, gamepad1.a);
                    Robot.servoRotateHook.teleOpAssistMode(gamepad1.x, gamepad1.b);

//                    Robot.crServoRotateClaw.teleOpAssistMode(gamepad2.left_trigger, gamepad2.right_trigger);
                    Robot.crServoRotateClaw.teleOpManualMode(gamepad2.left_trigger, gamepad2.right_trigger); // teleOpAssistMode doesnt work rn

//                    Robot.closeClawUsingSensor();
                    Robot.closeHookUsingSensor();

                } else if (Robot.mode == "manual") { // manual mode

                    Robot.servoClaw.teleOpManualMode(gamepad1.dpad_left, gamepad1.dpad_right); 
                    Robot.servoHook.teleOpManualMode(gamepad1.y, gamepad1.a);
                    Robot.servoRotateHook.teleOpManualMode(gamepad1.x, gamepad1.b);

                    Robot.crServoRotateClaw.teleOpManualMode(gamepad2.left_trigger, gamepad2.right_trigger);

                }

                // both assist and manual

                Robot.horizontalLift.teleOp(-gamepad2.left_stick_y, gamepad2.left_bumper, gamepad2.dpad_down, gamepad2.dpad_left, gamepad2.dpad_right, gamepad2.dpad_up);
                Robot.verticalLift.teleOp(-gamepad2.right_stick_y, gamepad2.right_bumper, gamepad2.a, gamepad2.x, gamepad2.b, gamepad2.y);

            }

            Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_bumper);

            printRobotData();

        }

    }

    public boolean liftIsntMoving(LiftClass liftClass) {
        int currentPos = liftClass.motor.getCurrentPosition();
        int targetPos = liftClass.motor.getTargetPosition();
        return Math.abs(currentPos - targetPos) < 10;
    }

    public boolean servoIsntMoving(ServoClass servoClass) {
        return timer.milliseconds() > servoClass.time;
    }

    public boolean crServoIsntMoving(CRServoClass crServoClass) {
        return crServoClass.crServo.getPower() == 0;
    }

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");

        Robot.servoClaw.printData();
        Robot.servoHook.printData();
        Robot.servoRotateHook.printData();

        Robot.crServoRotateClaw.printData();

        Robot.horizontalLift.printData();
        Robot.verticalLift.printData();

        Robot.clawSensor.printImportantData();
        Robot.hookSensor.printImportantData();

        telemetry.update();

    }

}

/*

Gamepad 1 - driving, servos, and modes

gamepad1.left_stick_x:  strafing
gamepad1.left_stick_y:  driving
gamepad1.right_stick_x: turning

gamepad1.a:             retract hook (to release cone)
gamepad1.x:             rotate hook up
gamepad1.b:             rotate hook down
gamepad1.y:             extend hook (to hold cone)

gamepad1.dpad_down:     rotate claw down
gamepad1.dpad_left:     open claw
gamepad1.dpad_right:    close claw
gamepad1.dpad_up:       rotate claw upe

gamepad1.left_bumper:   toggle max drivetrain speed

gamepad1.back:          switch to FSM mode
gamepad1.guide:         switch to assist mode
gamepad1.start:         switch to manual mode

Gamepad 2 - lifts & FSM

gamepad2.left_stick_y:  moving horizontal lift
gamepad2.right_stick_y: moving vertical lift

gamepad2.a:             move vertical lift to transfer
gamepad2.x:             move vertical lift to low
gamepad2.b:             move vertical lift to middle
gamepad2.y:             move vertical lift to high

gamepad2.a (FSM mode):  start FSM
gamepad2.x (FSM mode):  collect another cone
gamepad2.y (FSM mode):  stop FSM

gamepad2.dpad_down:     move horizontal lift to zero
gamepad2.dpad_left:     move horizontal lift to wait to collect cone
gamepad2.dpad_right:    move horizontal lift to collect cone

gamepad2.left_bumper:   toggle encoder limits for horizontal lift
gamepad2.right_bumper:  toggle encoder limits for vertical lift

*/