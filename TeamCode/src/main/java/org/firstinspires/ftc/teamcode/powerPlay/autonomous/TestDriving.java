package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Test Driving", group="Test")
public class TestDriving extends LinearOpMode {

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Robot.init(hardwareMap);

        telemetry.addData("initialized, waiting for start", "");
        telemetry.update();

        waitForStart();

        telemetry.addData("frontRightWheelDrive", "");
        telemetry.update();

        Robot.drivetrain.frontRightWheelDrive(12.0);
        sleep(1000);

        telemetry.addData("drive", "");
        telemetry.update();

        Robot.drivetrain.drive(12.0);
        sleep(1000);

        telemetry.addData("strafe", "");
        telemetry.update();

        Robot.drivetrain.strafe(12.0);
        sleep(1000);

        telemetry.addData("turn", "");
        telemetry.update();

        Robot.drivetrain.turn(90.0);

    }

}