package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="park autonomous", group="competition")
public class ParkAuton extends LinearOpMode {

    double DRIVE_SPEED = 0.7;
    double STRAFE_SPEED = 0.7;
    double TURN_SPEED = 0.7;

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this, hardwareMap, telemetry);
        Robot.tag = Robot.camera.getTag();

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        Robot.drivetrain.drive(30, DRIVE_SPEED);

        if (Robot.tag == 1) Robot.drivetrain.strafe(-24, STRAFE_SPEED);
        if (Robot.tag == 3) Robot.drivetrain.strafe(24, STRAFE_SPEED);

    }

}