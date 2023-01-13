package org.firstinspires.ftc.teamcode.powerPlay.oldCode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;
import org.firstinspires.ftc.teamcode.powerPlay.robot.ServoClass;

/*

To Do:

- test servo subsystem autonomous
- test lift subsystem autonomous & teleOp
- test drivetrain subsystem autonomous & teleOp
- test camera subsystem autonomous

- fix toggle for assist mode
- fix issue w/ initializing servos
- find out why telemetry doesn't work in the subsystem classes / robot class

- add & test finite state machine

- if necessary, add "Warning: Robot Moves On Initialization" sticker
    - https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/robot-moves-labels.pdf

*/

@TeleOp(name="test teleOp")
@Disabled
public class TestTeleOp extends OpMode {

    Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {

//        if (gamepad2.back) robot.assistMode = !robot.assistMode;
//        telemetry.addData("assistMode", robot.assistMode);
//
//        robot.drivetrain.executeTeleOp();
//
//        robot.servoClaw.executeTeleOp(robot.assistMode, gamepad2.dpad_left, gamepad2.dpad_right);
//        robot.servoRotateClaw.executeTeleOp(robot.assistMode, gamepad2.dpad_up, gamepad2.dpad_down);
//
//        robot.servoHook.executeTeleOp(robot.assistMode, gamepad2.y, gamepad2.a);
//        robot.servoRotateHook.executeTeleOp(robot.assistMode, gamepad2.x, gamepad2.b);
//
//        boolean[] horizontalLiftButtons = { gamepad2.left_bumper, gamepad2.right_bumper };
//        boolean[] verticalLiftButtons = { gamepad1.a, gamepad1.x, gamepad1.b, gamepad1.y, gamepad1.right_bumper };
//
//        robot.horizontalLift.executeTeleOp(robot.assistMode, -gamepad2.left_stick_y, horizontalLiftButtons);
//        robot.verticalLift.executeTeleOp(robot.assistMode, -gamepad2.right_stick_y, verticalLiftButtons);

    }

    public void printServoPosition(ServoClass servoSubsystem) {
        telemetry.addData(String.format("%s position", servoSubsystem.NAME), servoSubsystem.servoPosition);
    }

}

/*

Controls being used:

gamepad1.left_stick_x:  strafing
gamepad1.left_stick_y:  driving
gamepad1.right_stick_x: turning
gamepad1.a:             move vertical lift to transfer
gamepad1.x:             move vertical lift to ground junction
gamepad1.b:             move vertical lift to low junction
gamepad1.y:             move vertical lift to middle junction
gamepad1.right_bumper:  move vertical lift to high junction

gamepad2.left_stick_y:  moving horizontal lift
gamepad2.right_stick_y: moving vertical lift
gamepad2.a:             retract hook
gamepad2.x:             rotate hook to transfer
gamepad2.b:             rotate hook to score
gamepad2.y:             extend hook
gamepad2.dpad_up:       rotate claw up
gamepad2.dpad_right:    close claw
gamepad2.dpad_down:     rotate claw down
gamepad2.dpad_left:     open claw
gamepad2.left_bumper:   move horizontal lift to collect
gamepad2.right_bumper:  move horizontal lift to transfer
gamepad2.back:          turn assist mode on/off

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
- gamepad2.start
- gamepad2.guide

*/