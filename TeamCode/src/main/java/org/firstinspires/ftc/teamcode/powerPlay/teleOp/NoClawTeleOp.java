package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;
import java.util.HashMap;

@TeleOp(name="no claw teleOp", group="competition")
public class NoClawTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        while(opModeIsActive()) {

//            Robot.servoHook.teleOpAssistMode(gamepad2.dpad_down, gamepad2.dpad_up);
//            Robot.servoHook.teleOpManualMode(-gamepad2.left_stick_y);
//
//            Robot.verticalLift.teleOpAssistMode(gamepad2.a, gamepad2.x, gamepad2.b, gamepad2.y, gamepad2.right_bumper);
//            Robot.verticalLift.teleOpManualMode(-gamepad2.right_stick_y, gamepad2.left_bumper);
//
//            Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_bumper);

            telemetry.update();

        }

    }

}

/*

gamepad1.left_stick_x:  strafe
gamepad1.left_stick_y:  drive
gamepad1.right_stick_x: turn

gamepad1.dpad_up:       drive forward 12 inches
gamepad1.dpad_down:     drive backward 12 inches
gamepad1.dpad_left:     strafe left 12 inches
gamepad1.dpad_right:    strafe right 12 inches
gamepad1.x:             turn right 45 degrees
gamepad1.b:             turn left 45 degrees
gamepad1.left_bumper:   change MAX_TELEOP_SPEED

gamepad2.right_stick_y: move vertical lift manually
gamepad2.a:             move vertical lift to zero
gamepad2.x:             move vertical lift to driving height
gamepad2.b:             move vertical lift to low junction
gamepad2.y:             move vertical lift to middle junction
gamepad2.right_bumper:  move vertical lift to high junction

gamepad2.left_stick_y:  move hook manually
gamepad2.dpad_up:       retract hook
gamepad2.dpad_down:     extend hook

*/