package org.firstinspires.ftc.teamcode.meetZero;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name="Driving Practice", group="Competition")
public class DrivingPractice extends LinearOpMode {
    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);
        Robot.mode = "assist";

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        while (opModeIsActive()) {

            Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

            printRobotData();
        }
    }

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");


        telemetry.update();

    }
}
