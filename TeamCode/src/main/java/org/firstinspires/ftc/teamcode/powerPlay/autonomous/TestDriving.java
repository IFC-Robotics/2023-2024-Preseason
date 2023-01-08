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
        waitForStart();

        Robot.drivetrain.drive(24.0);
        sleep(1000);
        Robot.drivetrain.strafe(24.0);
        sleep(1000);
        Robot.drivetrain.turn(90.0);

    }

}