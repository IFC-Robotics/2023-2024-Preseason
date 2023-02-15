package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;
import org.firstinspires.ftc.teamcode.powerPlay.robot.LiftClass;
import org.firstinspires.ftc.teamcode.powerPlay.robot.ServoClass;

@TeleOp(name="new FSM teleOp", group="competition")
public class NewFSMteleOp extends LinearOpMode {

    public enum RobotState {
        START,                         // start FSM
        MOVE_HORIZONTAL_TO_LOWER_CLAW, // move horizontal lift to "rotate claw down"
        POSITION_TO_COLLECT,           // when button is pressed, move horizontal lift to "wait to collect" and rotate claw to "collect"
        ATTEMPT_TO_COLLECT,            // slowly start moving lift forward until sensor detects a cone OR until "collect"
        COLLECT,                       // move claw to "close"
        RETRACT_INTAKE,                // rotate claw to "transfer" & move horizontal lift to "transfer"
        TRANSFER_CONE,                 // move claw to "open"
        HOLD_CONE,                     // when sensor detects a cone OR after a certain amount of time, move hook to "hold"
        MOVE_VERTICAL_TO_SCORE,        // raise vertical lift to "high" & rotate hook to "middle"
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

                Robot.closeServoUsingSensor(Robot.clawSensor, Robot.servoClaw, 55, 20);

                switch (state) {

                    // start FSM

                    case START:
                        if (gamepad2.a) {
                            state = RobotState.MOVE_HORIZONTAL_TO_LOWER_CLAW;
                        }
                        break;

                    // move horizontal lift to "rotate claw down"

                    case MOVE_HORIZONTAL_TO_LOWER_CLAW:
                        if (liftIsntMoving(Robot.verticalLift)) {
                            Robot.horizontalLift.runToPosition("rotate claw down");
                            timer.reset();
                            state = RobotState.POSITION_TO_COLLECT;
                        }
                        break;

                    // when button is pressed, move horizontal lift to "wait to collect" and rotate claw to "collect"

                    case POSITION_TO_COLLECT:
                        if (gamepad2.x && liftIsntMoving(Robot.horizontalLift)) {
                            Robot.horizontalLift.runToPosition("wait to collect");
                            Robot.servoRotateClaw.runToPosition("collect");
                            timer.reset();
                            state = RobotState.ATTEMPT_TO_COLLECT;
                        }
                        break;

                    // slowly start moving lift forward until sensor detects a cone OR until "collect"

                    case ATTEMPT_TO_COLLECT:
                        if (liftIsntMoving(Robot.horizontalLift) && servoIsntMoving(Robot.servoRotateClaw)) {
                            Robot.horizontalLift.runToPosition("collect", false, 0.3);
                            timer.reset();
                            state = RobotState.COLLECT;
                        }
                        break;
                    
                    // move claw to "close"

                    case COLLECT:
                        if (Robot.servoClaw.servoPosition == 1 || liftIsntMoving(Robot.horizontalLift)) {
                            if (Robot.servoClaw.servoPosition == 1)   Robot.horizontalLift.stop();
                            if (liftIsntMoving(Robot.horizontalLift)) Robot.servoClaw.runToPosition("hold");
                            timer.reset();
                            state = RobotState.RETRACT_INTAKE;
                        }
                        break;
                    
                    // public enum RobotState {
                    //     RETRACT_INTAKE,                // rotate claw to "transfer" & move horizontal lift to "transfer"
                    //     TRANSFER_CONE,                 // move claw to "open"
                    //     HOLD_CONE,                     // when sensor detects a cone OR after a certain amount of time, move hook to "hold"
                    //     MOVE_VERTICAL_TO_SCORE,        // raise vertical lift to "high" & rotate hook to "middle"
                    //     ROTATE_HOOK_TO_SCORE,          // rotate hook to "score"
                    //     SCORE_CONE,                    // move hook to "release"
                    //     ROTATE_HOOK_TO_MIDDLE,         // rotate hook to "middle"
                    //     RETRACT_DEPOSITOR              // lower vertical lift to "transfer" and rotate hook to "transfer"
                    // }

                    // rotate claw up

                    case RETRACT_INTAKE:
                        if (servoIsntMoving(Robot.servoClaw)) {
                            Robot.servoRotateClaw.runToPosition("transfer");
                            timer.reset();
                            state = RobotState.MOVE_HORIZONTAL_TO_TRANSFER;
                        }
                        break;

                    // move horizontal lift to transfer

                    case MOVE_HORIZONTAL_TO_TRANSFER:
                        if (servoIsntMoving(Robot.servoRotateClaw)) {
                            Robot.horizontalLift.runToPosition("transfer");
                            timer.reset();
                            state = RobotState.RELEASE_CONE;
                        }
                        break;
                    
                    // open claw

                    case RELEASE_CONE:
                        if (liftIsntMoving(Robot.horizontalLift)) {
                            Robot.servoClaw.runToPosition("release");
                            timer.reset();
                            state = RobotState.TRANSFER_CONE;
                        }
                        break;
                    
                    // extend hook

                    case TRANSFER_CONE:
                        if (servoIsntMoving(Robot.servoClaw)) {
                            Robot.servoHook.runToPosition("hold");
                            timer.reset();
                            state = RobotState.MOVE_VERTICAL_TO_SCORE;
                        }
                        break;

                    // raise vertical lift to high junction

                    case MOVE_VERTICAL_TO_SCORE:
                        if (servoIsntMoving(Robot.servoHook)) {
                            Robot.verticalLift.runToPosition("high");
                            timer.reset();
                            state = RobotState.ROTATE_HOOK_TO_SCORE;
                        }
                        break;

                    // rotate hook to score

                    case ROTATE_HOOK_TO_SCORE:
                        if (liftIsntMoving(Robot.verticalLift)) {
                            Robot.servoRotateHook.runToPosition("score");
                            timer.reset();
                            state = RobotState.SCORE_CONE;
                        }
                        break;

                    // release hook

                    case SCORE_CONE:
                        if (servoIsntMoving(Robot.servoRotateHook)) {
                            Robot.servoHook.runToPosition("release");
                            timer.reset();
                            state = RobotState.ROTATE_HOOK_TO_TRANSFER;
                        }
                        break;

                    // rotate hook to transfer

                    case ROTATE_HOOK_TO_TRANSFER:
                        if (servoIsntMoving(Robot.servoHook)) {
                            Robot.servoRotateHook.runToPosition("transfer");
                            timer.reset();
                            state = RobotState.MOVE_VERTICAL_TO_TRANSFER;
                        }
                        break;

                    // lower vertical lift to transfer

                    case MOVE_VERTICAL_TO_TRANSFER:
                        if (servoIsntMoving(Robot.servoRotateHook)) {
                            Robot.verticalLift.runToPosition("transfer");
                            timer.reset();
                            state = RobotState.MOVE_HORIZONTAL_TO_COLLECT;
                        }
                        break;

                    default: state = RobotState.START;

                }

                // close claw if sensor

                Robot.closeServoUsingSensor(Robot.clawSensor, Robot.servoClaw, 25, 10);

                // pause FSM

                if (gamepad2.y && state != RobotState.START) {
                    state = RobotState.START;
                }

            } else {
                
                if (Robot.mode == "assist") { // assist mode

                    Robot.servoClaw.teleOpAssistMode(gamepad1.dpad_left, gamepad1.dpad_right);
                    Robot.servoRotateClaw.teleOpAssistMode(gamepad1.dpad_down, gamepad1.dpad_up);

                    Robot.servoHook.teleOpAssistMode(gamepad1.y, gamepad1.a);
                    Robot.servoRotateHook.teleOpAssistMode(gamepad1.x, gamepad1.b);

                    Robot.closeServoUsingSensor(Robot.hookSensor, Robot.servoHook, 55, 20);

                } else if (Robot.mode == "manual") { // manual mode

                    Robot.servoClaw.teleOpManualMode(gamepad1.dpad_left, gamepad1.dpad_right);
                    Robot.servoRotateClaw.teleOpManualMode(gamepad1.dpad_down, gamepad1.dpad_up);

                    Robot.servoHook.teleOpManualMode(gamepad1.y, gamepad1.a);
                    Robot.servoRotateHook.teleOpManualMode(gamepad1.x, gamepad1.b);

                }

                // both assist and manual

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

    // public void liftToLift(LiftClass liftClass, LiftClass nextLiftClass, String position, RobotState nextState) {
    //     if (liftIsntMoving(liftClass)) {
    //         nextLiftClass.runToPosition(position);
    //         timer.reset();
    //         state = nextState;
    //     }
    // }

    // public void liftToServo(LiftClass liftClass, ServoClass servoClass, String position, RobotState nextState) {
    //     if (liftIsntMoving(liftClass)) {
    //         servoClass.runToPosition(position);
    //         timer.reset();
    //         state = nextState;
    //     }
    // }

    // public void servoToLift(ServoClass servoClass, LiftClass liftClass, String position, RobotState nextState) {
    //     if (servoIsntMoving(servoClass)) {
    //         liftClass.runToPosition(position);
    //         timer.reset();
    //         state = nextState;
    //     }
    // }

    // public void servoToServo(ServoClass servoClass, ServoClass newServoClass, String position, RobotState nextState) {
    //     if (servoIsntMoving(servoClass)) {
    //         newServoClass.runToPosition(position);
    //         timer.reset();
    //         state = nextState;
    //     }
    // }

    public boolean liftIsntMoving(LiftClass liftClass) {
        int currentPos = liftClass.motor.getCurrentPosition();
        int targetPos = liftClass.motor.getTargetPosition();
        return Math.abs(currentPos - targetPos) < 10;
    }

    public boolean servoIsntMoving(ServoClass servoClass) {
        return timer.milliseconds() > servoClass.time;
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