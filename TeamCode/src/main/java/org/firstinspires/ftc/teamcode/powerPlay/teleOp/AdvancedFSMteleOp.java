package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@TeleOp(name="FSM teleOp", group="competition")
public class AdvancedFSMteleOp extends LinearOpMode {

    // public enum RobotState {
    //     START,
    //     PREPARE_TO_COLLECT,
    //     MOVE_HORIZONTAL_TO_COLLECT,
    //     COLLECT_CONE,
    //     MOVE_HORIZONTAL_TO_TRANSFER,
    //     TRANSFER_CONE,
    //     RELEASE_CONE,
    //     MOVE_VERTICAL_TO_SCORE,
    //     SCORE_CONE,
    //     MOVE_VERTICAL_TO_TRANSFER
    // };

    // RobotState state = RobotState.START;
    // String randomization = Robot.side + " " + Robot.tag;
    // ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);

        waitForStart();

        // timer.reset();
        telemetry.addLine("Executing opMode...");
        telemetry.update();

        while (opModeIsActive()) {

            // Switching modes

            if (gamepad2.back) {
                Robot.reset();
                Robot.mode = "FSM";
            }

            if (gamepad2.guide) Robot.mode = "assist";
            if (gamepad2.start) Robot.mode = "manual";

            telemetry.addData("Robot mode", Robot.mode);

            /*

            // Finite State Machine mode

            if (Robot.mode == "FSM") {

                // driving to FSM position

                if (gamepad1.right_bumper) {

                    telemetry.addLine("driving to FSM position");
                    telemetry.addData("randomization", randomization);

                    Robot.drivetrain.drive(24, 0.5);

                    if (randomization == "left 1") Robot.drivetrain.strafe(60, 0.5);
                    if (randomization == "left 2") Robot.drivetrain.strafe(36, 0.5);
                    if (randomization == "left 3") Robot.drivetrain.strafe(12, 0.5);
                    if (randomization == "right 1") Robot.drivetrain.strafe(-12, 0.5);
                    if (randomization == "right 2") Robot.drivetrain.strafe(-36, 0.5);
                    if (randomization == "right 3") Robot.drivetrain.strafe(-60, 0.5);

                }

                // FSM

                telemetry.addLine(String.format("current FSM state: %s", state));

                switch (state) {

                    case START:
                        if (gamepad1.x) { // start FSM
                            state = RobotState.PREPARE_TO_COLLECT;
                        }
                        break;

                    case PREPARE_TO_COLLECT:
                        Robot.servoRotateClaw.runToPosition("down"); // rotate claw down
                        timer.reset();
                        state = RobotState.MOVE_HORIZONTAL_TO_COLLECT;
                        break;

                    case MOVE_HORIZONTAL_TO_COLLECT:
                        if (gamepad1.a && timer.seconds() >= Robot.servoRotateClaw.TIME) {
                            Robot.horizontalLift.runToPosition("collect"); // move horizontal lift to collect cone
                            state = RobotState.COLLECT_CONE;
                        }
                        break;

                    case COLLECT_CONE:
                        int horizontalLiftMaxPosition = (int) (Robot.horizontalLift.horizontalLiftMaxPosition * Robot.horizontalLift.COUNTS_PER_INCH);
                        if (Robot.horizontalLift.motor.getCurrentPosition() >= horizontalLiftMaxPosition) {
                            Robot.servoClaw.runToPosition("close"); // close claw
                            timer.reset();
                            state = RobotState.MOVE_HORIZONTAL_TO_TRANSFER;
                        }
                        break;

                    case MOVE_HORIZONTAL_TO_TRANSFER:
                        if (timer.seconds() >= Robot.servoClaw.TIME) {
                            Robot.servoRotateClaw.runToPosition("up"); // rotate claw up
                            Robot.horizontalLift.runToPosition("transfer"); // move horizontal lift to transfer cone
                            state = RobotState.TRANSFER_CONE;
                        }
                        break;

                    case TRANSFER_CONE:
                        int horizontalLiftMinPosition = (int) (Robot.horizontalLift.horizontalLiftMinPosition * Robot.horizontalLift.COUNTS_PER_INCH);
                        if (Robot.horizontalLift.motor.getCurrentPosition() >= horizontalLiftMinPosition) {
                            Robot.servoHook.runToPosition("extend"); // extend hook
                            timer.reset();
                            state = RobotState.RELEASE_CONE;
                        }
                        break;

                    case RELEASE_CONE:
                        if (timer.seconds() >= Robot.servoHook.TIME) {
                            Robot.servoClaw.runToPosition("open"); // open claw
                            timer.reset();
                            state = RobotState.MOVE_VERTICAL_TO_SCORE;
                        }
                        break;

                    case MOVE_VERTICAL_TO_SCORE:
                        if (timer.seconds() >= Robot.servoClaw.TIME) {
                            Robot.servoRotateHook.runToPosition("score"); // rotate hook to score cone
                            Robot.verticalLift.runToPosition("high"); // move vertical lift to score cone
                            state = RobotState.SCORE_CONE;
                        }
                        break;

                    case SCORE_CONE:
                        int verticalLiftMaxPosition = (int) (Robot.verticalLift.verticalLiftPosition5 * Robot.verticalLift.COUNTS_PER_INCH);
                        if (Robot.verticalLift.motor.getCurrentPosition() >= verticalLiftMaxPosition) {
                            Robot.servoHook.runToPosition("retract"); // retract hook
                            timer.reset();
                            state = RobotState.MOVE_VERTICAL_TO_TRANSFER;
                        }
                        break;

                    case MOVE_VERTICAL_TO_TRANSFER:
                        if (timer.seconds() >= Robot.servoClaw.TIME) {
                            Robot.servoRotateHook.runToPosition("transfer"); // rotate hook to transfer cone
                            Robot.verticalLift.runToPosition("transfer"); // move vertical lift to transfer cone
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

            } else */ if (Robot.mode == "assist") { // assist mode

                Robot.servoClaw.teleOpAssistMode(gamepad2.dpad_left, gamepad2.dpad_right);
                Robot.servoHook.teleOpAssistMode(gamepad2.y, gamepad2.a);
                Robot.servoRotateClaw.teleOpAssistMode(gamepad2.dpad_up, gamepad2.dpad_down);
                Robot.servoRotateHook.teleOpAssistMode(gamepad2.x, gamepad2.b);

                Robot.horizontalLift.teleOpAssistMode(gamepad2.left_bumper, gamepad2.right_bumper);
                Robot.verticalLift.teleOpAssistMode(gamepad1.a, gamepad1.x, gamepad1.b, gamepad1.y);

            } else if (Robot.mode == "manual") { // manual mode

                Robot.servoClaw.teleOpManualMode(gamepad2.dpad_left, gamepad2.dpad_right);
                Robot.servoHook.teleOpManualMode(gamepad2.y, gamepad2.a);
                Robot.servoRotateClaw.teleOpManualMode(gamepad2.dpad_up, gamepad2.dpad_down);
                Robot.servoRotateHook.teleOpManualMode(gamepad2.x, gamepad2.b);

                Robot.horizontalLift.teleOpManualMode(-gamepad2.left_stick_y);
                Robot.verticalLift.teleOpManualMode(-gamepad2.right_stick_y);

            }

            Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_bumper);

            telemetry.update();

        }

    }

}

/*

Controls being used:

gamepad1.left_stick_x:  strafing
gamepad1.left_stick_y:  driving
gamepad1.right_stick_x: turning
gamepad1.a (assist):    move vertical lift to transfer cone
gamepad1.x (assist):    move vertical lift to low junction
gamepad1.b (assist):    move vertical lift to middle junction
gamepad1.y (assist):    move vertical lift to high junction
gamepad1.a (FSM):       move horizontal lift to collect cone
gamepad1.x (FSM):       start FSM
gamepad1.y (FSM):       stop FSM

gamepad2.left_stick_y:  moving horizontal lift
gamepad2.right_stick_y: moving vertical lift
gamepad2.a:             retract hook
gamepad2.x:             rotate hook to transfer cone
gamepad2.b:             rotate hook to score cone
gamepad2.y:             extend hook
gamepad2.dpad_up:       rotate claw up
gamepad2.dpad_right:    close claw
gamepad2.dpad_down:     rotate claw down
gamepad2.dpad_left:     open claw
gamepad2.left_bumper:   move horizontal lift to collect cone
gamepad2.right_bumper:  move horizontal lift to transfer cone
gamepad2.back:          switch to FSM mode
gamepad2.guide:         switch to assist mode
gamepad2.start:         switch to manual mode
gamepad2.right_bumper:  strafe to FSM position

Controls not being used:

- gamepad1.left_stick_button
- gamepad1.right_stick_button
- gamepad1.right_stick_y
- gamepad1.dpad_up
- gamepad1.dpad_right
- gamepad1.dpad_down
- gamepad1.dpad_left
- gamepad1.left_bumper
- gamepad1.left_trigger
- gamepad1.right_trigger
- gamepad1.back
- gamepad1.start
- gamepad1.guide

- gamepad2.left_stick_button
- gamepad2.right_stick_button
- gamepad2.left_stick_x
- gamepad2.right_stick_x
- gamepad2.left_bumper
- gamepad2.left_trigger
- gamepad2.right_bumper
- gamepad2.right_trigger

*/