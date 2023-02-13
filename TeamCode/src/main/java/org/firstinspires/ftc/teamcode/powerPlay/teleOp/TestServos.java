package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@TeleOp(name="Test Servos", group="test")
public class TestServos extends LinearOpMode {

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        while(opModeIsActive()) {

            Robot.servoClaw.teleOpManualMode(gamepad1.dpad_up, gamepad1.dpad_down);
            telemetry.addData("servo_claw position", Robot.servoClaw.servo.getPosition());

            Robot.servoRotateClaw.teleOpManualMode(gamepad1.dpad_left, gamepad1.dpad_right);
            telemetry.addData("servo_rotate_claw position", Robot.servoRotateClaw.servo.getPosition());

//            Robot.servoRotateHook.teleOpManualMode(gamepad1.x, gamepad1.b);
//            telemetry.addData("servo_rotate_hook position", Robot.servoRotateHook.servo.getPosition());

            telemetry.update();

        }

    }

}