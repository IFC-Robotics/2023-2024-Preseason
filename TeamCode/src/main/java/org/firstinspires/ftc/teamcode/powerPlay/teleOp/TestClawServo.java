package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@TeleOp(name="Test Claw Servo", group="competition")
public class TestClawServo extends LinearOpMode {

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        while(opModeIsActive()) {

            Robot.servoClaw.teleOpManualMode(gamepad1.dpad_down, gamepad1.dpad_up);
            Robot.servoRotateClaw.teleOpManualMode(gamepad1.a, gamepad1.y);

            telemetry.addData("servo_claw position", Robot.servoClaw.servo.getPosition());
            telemetry.addData("servo_rotate_claw position", Robot.servoRotateClaw.servo.getPosition());
            telemetry.update();

        }

    }

}