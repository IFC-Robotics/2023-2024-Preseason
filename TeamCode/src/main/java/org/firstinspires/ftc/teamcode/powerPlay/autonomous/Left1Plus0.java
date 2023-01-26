package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Left 1+0", group="competition")
public class Left1Plus0 extends LinearOpMode {

    double DRIVE_SPEED = 0.5;
    double STRAFE_SPEED = 0.5;
    double TURN_SPEED = 0.5;

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        // read AprilTag

        Robot.tag = Robot.camera.getTag();

        // raise lift w/ preloaded cone

        Robot.servoHook.runToPosition("extend", true);
        Robot.verticalLift.runToPosition("high", false);

        // drive to high junction

        Robot.drivetrain.drive(64, DRIVE_SPEED, true);
        Robot.drivetrain.drive(-8, DRIVE_SPEED, true);
        Robot.drivetrain.turn(45, TURN_SPEED, true);

        // wait for lift to reach max height

        Robot.verticalLift.waitForLift();

        // score on high junction

        double distToJunction = 12;

        Robot.drivetrain.drive(distToJunction, DRIVE_SPEED, true);
        Robot.servoHook.runToPosition("retract", true);
        Robot.drivetrain.drive(-distToJunction, DRIVE_SPEED, true);
        Robot.verticalLift.runToPosition("zero", false);

        // park in correct zone

        Robot.drivetrain.turn(45, TURN_SPEED, true);

        if (Robot.tag == 1) Robot.drivetrain.drive(-24, DRIVE_SPEED, true);
        if (Robot.tag == 3) Robot.drivetrain.drive(24, DRIVE_SPEED, true);

        // wait for lift to lower all the way

        Robot.verticalLift.waitForLift();

    }

}