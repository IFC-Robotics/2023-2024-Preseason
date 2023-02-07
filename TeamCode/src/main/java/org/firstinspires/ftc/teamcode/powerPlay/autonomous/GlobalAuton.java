package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Configurable Auton", group="competition")
public class GlobalAuton extends LinearOpMode {

    double DRIVE_SPEED = 0.5;
    double STRAFE_SPEED = 0.5;
    double TURN_SPEED = 0.5;

    @Override
    public void runOpMode() {

        // initialize opMode

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);
        Robot.configureAuton(this);

        waitForStart();

        // execute opMode

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        double sideMult = (Robot.side == "left") ? 1 : -1;

        // read AprilTag

        Robot.tag = Robot.camera.getTag();

        // raise lift w/ preloaded cone

        Robot.servoHook.runToPosition("hold", true);
        Robot.verticalLift.runToPosition("high", false);

        // drive to high junction

        Robot.drivetrain.drive(64, DRIVE_SPEED, true);
        Robot.drivetrain.drive(-8, DRIVE_SPEED, true);
        Robot.drivetrain.turn(sideMult * 45, TURN_SPEED, true);

        // wait for lift to reach max height

        Robot.verticalLift.waitForLift();

        // score on high junction

        double distToJunction = 12;

        Robot.drivetrain.drive(distToJunction, DRIVE_SPEED, true);
        Robot.servoHook.runToPosition("release", true);
        Robot.drivetrain.drive(-distToJunction, DRIVE_SPEED, true);
        Robot.verticalLift.runToPosition("transfer", false);

        // repeat the process

        for (int i = 0; i < Robot.numCycles; i++) {

            // drive to cone stack

            Robot.drivetrain.turn(-135, DRIVE_SPEED, true);
            Robot.drivetrain.drive(24, DRIVE_SPEED, true);

            // pick up cone from cone stack

            Robot.verticalLift.runToPosition("5th cone", true);
            Robot.servoHook.runToPosition("hold", true);
            Robot.verticalLift.runToPosition("high", false);

            // drive to high junction

            Robot.drivetrain.drive(-28, DRIVE_SPEED, true);
            Robot.drivetrain.turn(135, TURN_SPEED, true);

            // wait for lift to reach max height

            Robot.verticalLift.waitForLift();

            // score on high junction

            Robot.drivetrain.drive(distToJunction, DRIVE_SPEED, true);
            Robot.servoHook.runToPosition("release", true);
            Robot.drivetrain.drive(-distToJunction, DRIVE_SPEED, true);
            Robot.verticalLift.runToPosition("transfer", true);

        }

        // park in correct zone

        Robot.drivetrain.turn(sideMult * 45, TURN_SPEED, true);

        if (Robot.tag == 1) Robot.drivetrain.drive(sideMult * -24, DRIVE_SPEED, true);
        if (Robot.tag == 3) Robot.drivetrain.drive(sideMult * 24, DRIVE_SPEED, true);

        // lower lift

        Robot.verticalLift.runToPosition("zero", true);

    }

}