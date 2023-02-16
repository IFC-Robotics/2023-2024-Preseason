package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Configurable Auton", group="competition")
public class ConfigurableAuton extends LinearOpMode {

    double DRIVE_SPEED = 0.3;
    double STRAFE_SPEED = 0.3;
    double TURN_SPEED = 0.3;

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);
        Robot.configureAuton(this);

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        double sideMult = (Robot.side == "left") ? 1 : -1;

        // secure preloaded cone & read AprilTag

        Robot.servoHook.runToPosition("hold", true);
        Robot.tag = Robot.camera.getTag();

        // drive to high junction

        Robot.drivetrain.drive(-64, DRIVE_SPEED, true);
        Robot.drivetrain.drive(8, DRIVE_SPEED, true);
        Robot.drivetrain.turn(sideMult * 45, TURN_SPEED, true);

        // raise lift

        Robot.verticalLift.runToPosition("high", true);
        Robot.servoRotateHook.runToPosition("score", true);

        // score on high junction

        double distToJunction = -12;

        Robot.drivetrain.drive(distToJunction, DRIVE_SPEED, true);
        Robot.servoHook.runToPosition("release", true);
        Robot.drivetrain.drive(-distToJunction, DRIVE_SPEED, true);

        // lower lift

        Robot.servoRotateHook.runToPosition("transfer", true);
        Robot.verticalLift.runToPosition("zero", true);

        // park in correct zone

        Robot.drivetrain.turn(sideMult * -45, TURN_SPEED, true);
        Robot.drivetrain.drive(24, DRIVE_SPEED, true);

        if (Robot.tag == 1) Robot.drivetrain.strafe(-24, STRAFE_SPEED, true);
        if (Robot.tag == 3) Robot.drivetrain.strafe(24, STRAFE_SPEED, true);

    }

}