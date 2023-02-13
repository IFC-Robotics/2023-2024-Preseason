package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@TeleOp(name="Test Lifts", group="test")
public class TestLifts extends LinearOpMode {

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        while (opModeIsActive()) {

            Robot.horizontalLift.teleOp(-gamepad1.left_stick_y, gamepad1.left_bumper, gamepad1.dpad_down, gamepad1.dpad_left, gamepad1.dpad_right, gamepad1.dpad_up);
            telemetry.addData("horizontal lift position", Robot.horizontalLift.motor.getCurrentPosition());

            telemetry.update();


        }

    }

}