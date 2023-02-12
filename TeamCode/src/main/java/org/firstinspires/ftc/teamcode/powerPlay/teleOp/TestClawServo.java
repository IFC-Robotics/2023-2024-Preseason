package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@TeleOp(name="Test Claw Servo", group="test")
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

            Robot.servoRotateClaw.teleOpManualMode(gamepad1.dpad_left, gamepad1.dpad_right);

            telemetry.addData("servo_rotate_claw position", Robot.servoRotateClaw.servo.getPosition());
            telemetry.update();

        }

    }

}