package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Storage;
import org.firstinspires.ftc.teamcode.powerPlay.robot.RobotClassWithSubsystems;
import org.firstinspires.ftc.teamcode.powerPlay.robot.ServoSubsystem;

/*

To Do:

- test servo subsystem autonomous
- test lift subsystem autonomous & teleOp
- test drivetrain subsystem autonomous & teleOp
- test camera subsystem autonomous

- fix issue w/ initializing servos
- find out why telemetry doesn't work in the subsystem classes / robot class

- test finite state machine
    - test switch statement
    - test switching between robot modes (FSM, assist, manual)
    - test initial strafe to FSM position

- think about what the controls should be (joysticks, buttons, etc.)

- fix existing autonomous opModes, based on new robot/subsystem classes
- create new competition autonomous opModes, based on new path

- move storage to RobotClass
- make RobotClass static, meaning each file will reference the class itself (instead of an instance, like what happens rn)
    - make DrivetrainSubsystem static
    - make CameraSubsystem static

- if necessary, add "Warning: Robot Moves On Initialization" sticker
    - https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/robot-moves-labels.pdf

*/

@TeleOp(name="finite state machine")
public class FSM extends OpMode {

    public enum RobotState {
        START,
        PREPARE_TO_COLLECT,
        MOVE_HORIZONTAL_TO_COLLECT,
        COLLECT_CONE,
        MOVE_HORIZONTAL_TO_TRANSFER,
        TRANSFER_CONE,
        RELEASE_CONE,
        MOVE_VERTICAL_TO_SCORE,
        SCORE_CONE,
        MOVE_VERTICAL_TO_TRANSFER
    };

    RobotClassWithSubsystems robot = new RobotClassWithSubsystems();
    RobotState state = RobotState.START;

    String randomization = Storage.side + " " + Storage.tag;
    double INIT_DRIVE_DIST = 24;

    ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void start() {

        resetRobot();
        robot.drivetrain.drive(INIT_DRIVE_DIST);

        switch (randomization) {
            case "left 1":  robot.drivetrain.strafe(60);
            case "left 2":  robot.drivetrain.strafe(36);
            case "left 3":  robot.drivetrain.strafe(12);
            case "right 1": robot.drivetrain.strafe(-12);
            case "right 2": robot.drivetrain.strafe(-36);
            case "right 3": robot.drivetrain.strafe(-60);
        }

        timer.reset();

    }

    @Override
    public void loop() {

        if (gamepad2.back)  {
            resetRobot();
            robot.mode = "FSM";
        }

        if (gamepad2.guide) robot.mode = "assist";
        if (gamepad2.start) robot.mode = "manual";

        telemetry.addData("mode", robot.mode);

        if (robot.mode == "FSM") {

            telemetry.addData("state", state);

            switch (state) {

                case START:
                    if (gamepad1.x) { // start FSM
                        state = RobotState.PREPARE_TO_COLLECT;
                    }
                    break;

                case PREPARE_TO_COLLECT:
                    if (gamepad1.x) {
                        robot.servoRotateClaw.runToPosition("down"); // rotate claw down
                        timer.reset();
                        state = RobotState.MOVE_HORIZONTAL_TO_COLLECT;
                    }
                    break;

                case MOVE_HORIZONTAL_TO_COLLECT:
                    if (gamepad1.a && timer.seconds() >= robot.servoRotateClaw.TIME) {
                        robot.horizontalLift.runToPosition("collect"); // move horizontal lift to collect cone
                        state = RobotState.COLLECT_CONE;
                    }
                    break;

                case COLLECT_CONE:
                    if (robot.horizontalLift.motor.getCurrentPosition() == robot.horizontalLift.PRESET_POSITIONS[2]) {
                        robot.servoClaw.runToPosition("close"); // close claw
                        timer.reset();
                        state = RobotState.MOVE_HORIZONTAL_TO_TRANSFER;
                    }
                    break;

                case MOVE_HORIZONTAL_TO_TRANSFER:
                    if (timer.seconds() >= robot.servoClaw.TIME) {
                        robot.servoRotateClaw.runToPosition("up"); // rotate claw up
                        robot.horizontalLift.runToPosition("transfer"); // move horizontal lift to transfer cone
                        state = RobotState.TRANSFER_CONE;
                    }
                    break;

                case TRANSFER_CONE:
                    if (robot.horizontalLift.motor.getCurrentPosition() == robot.horizontalLift.PRESET_POSITIONS[0]) {
                        robot.servoHook.runToPosition("extend"); // extend hook
                        timer.reset();
                        state = RobotState.RELEASE_CONE;
                    }
                    break;

                case RELEASE_CONE:
                    if (timer.seconds() >= robot.servoHook.TIME) {
                        robot.servoClaw.runToPosition("open"); // open claw
                        timer.reset();
                        state = RobotState.MOVE_VERTICAL_TO_SCORE;
                    }
                    break;

                case MOVE_VERTICAL_TO_SCORE:
                    if (timer.seconds() >= robot.servoClaw.TIME) {
                        robot.servoRotateHook.runToPosition("score"); // rotate hook to score cone
                        robot.verticalLift.runToPosition("high"); // move vertical lift to score cone
                        state = RobotState.SCORE_CONE;
                    }
                    break;

                case SCORE_CONE:
                    if (robot.verticalLift.motor.getCurrentPosition() == robot.verticalLift.PRESET_POSITIONS[0]) {
                        robot.servoHook.runToPosition("retract"); // retract hook
                        timer.reset();
                        state = RobotState.MOVE_VERTICAL_TO_TRANSFER;
                    }
                    break;

                case MOVE_VERTICAL_TO_TRANSFER:
                    if (timer.seconds() >= robot.servoClaw.TIME) {
                        robot.servoRotateHook.runToPosition("transfer"); // rotate hook to transfer cone
                        robot.verticalLift.runToPosition("transfer"); // move vertical lift to transfer cone
                        state = RobotState.MOVE_HORIZONTAL_TO_COLLECT;
                    }
                    break;

                default:
                    state = RobotState.START;

            }

            if (gamepad1.y && state != RobotState.START) {
                state = RobotState.START; // stop FSM
            }

        } else if (robot.mode == "assist") {

            robot.servoClaw.teleOpAssistMode(gamepad2.dpad_left, gamepad2.dpad_right);
            robot.servoRotateClaw.teleOpAssistMode(gamepad2.dpad_up, gamepad2.dpad_down);

            robot.servoHook.teleOpAssistMode(gamepad2.y, gamepad2.a);
            robot.servoRotateHook.teleOpAssistMode(gamepad2.x, gamepad2.b);

            boolean[] horizontalLiftButtons = { gamepad2.left_bumper, gamepad2.right_bumper };
            boolean[] verticalLiftButtons = { gamepad1.a, gamepad1.x, gamepad1.b, gamepad1.y, gamepad1.right_bumper };

            robot.horizontalLift.teleOpAssistMode(horizontalLiftButtons);
            robot.verticalLift.teleOpAssistMode(verticalLiftButtons);

        } else if (robot.mode == "manual") {

            robot.servoClaw.teleOpManualMode(gamepad2.dpad_left, gamepad2.dpad_right);
            robot.servoRotateClaw.teleOpManualMode(gamepad2.dpad_up, gamepad2.dpad_down);

            robot.servoHook.teleOpManualMode(gamepad2.y, gamepad2.a);
            robot.servoRotateHook.teleOpManualMode(gamepad2.x, gamepad2.b);

            robot.horizontalLift.teleOpManualMode(-gamepad2.left_stick_y);
            robot.verticalLift.teleOpManualMode(-gamepad2.right_stick_y);

        }

        robot.drivetrain.executeTeleOp();

        // maybe: right here, if the horizontal lift isn't at the 0 (transfer) position, give it 0.01 power, so that gravity doesn't pull it down

    }

    public void resetRobot() {

        robot.servoClaw.runToPosition("open");
        robot.servoRotateClaw.runToPosition("up");
        robot.servoRotateHook.runToPosition("transfer");
        robot.servoHook.runToPosition("retract");

        robot.verticalLift.runToPosition("transfer");
        robot.horizontalLift.runToPosition("transfer");

    }

    public void printServoPosition(ServoSubsystem servoSubsystem) {
        telemetry.addData(String.format("%s position", servoSubsystem.NAME), servoSubsystem.servoPosition);
    }

}

/*

Note: the controls below are probably correct, but should be checked (or just redone)

Controls being used:

gamepad1.left_stick_x:  strafing
gamepad1.left_stick_y:  driving
gamepad1.right_stick_x: turning
gamepad1.a:             move vertical lift to transfer cone
gamepad1.x:             move vertical lift to ground junction
gamepad1.b:             move vertical lift to low junction
gamepad1.y:             move vertical lift to medium junction
gamepad1.a (FSM):       move horizontal lift to collect cone
gamepad1.x (FSM):       start FSM
gamepad1.y (FSM):       stop FSM
gamepad1.right_bumper:  move vertical lift to high junction

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
gamepad2.start:         switch to assist mode
gamepad2.guide:         switch to manual mode

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