package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.RobotClassWithSubsystems;

@Autonomous(name="Test Driving", group="Test")
public class TestDriving extends LinearOpMode {

    RobotClassWithSubsystems robot = new RobotClassWithSubsystems();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        robot.drivetrain.drive(24.0);
        sleep(1000);
        robot.drivetrain.strafe(24.0);
        sleep(1000);
        robot.drivetrain.turn(90.0);

    }

}