package org.firstinspires.ftc.teamcode.meetZero;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.robot.Robot;


@Autonomous(name="Blue Side Right Park", group="competition")
public class BlueSideRightAuton extends LinearOpMode {

    double DRIVE_SPEED = 0.3;
    double STRAFE_SPEED = 0.3;
    double TURN_SPEED = 0.3;

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        //move forward and wiggle
        Robot.drivetrain.drive(30,0.5);
        Robot.drivetrain.drive(-2,0.5);
        Robot.drivetrain.drive(2,0.5);
        Robot.drivetrain.drive(-30,0.5);
    }

}
