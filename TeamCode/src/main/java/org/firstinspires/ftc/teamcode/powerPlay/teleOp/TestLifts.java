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

            Robot.horizontalLift.teleOp(-gamepad2.left_stick_y, gamepad2.left_bumper, gamepad1.dpad_down, gamepad1.dpad_left, gamepad1.dpad_right, gamepad1.dpad_up);
            Robot.verticalLift.teleOp(-gamepad2.right_stick_y, gamepad2.right_bumper, gamepad1.a, gamepad1.x, gamepad1.b, gamepad1.y);

            telemetry.addData("horizontal lift position", Robot.horizontalLift.motor.getCurrentPosition());
            telemetry.addData("vertical lift position", Robot.verticalLift.motor.getCurrentPosition());

            telemetry.update();


        }

    }

}