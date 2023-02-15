package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Park Auton", group="competition")
public class ParkAuton extends LinearOpMode {

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

        // secure preloaded cone & read AprilTag

        Robot.servoHook.runToPosition("hold", false);
        Robot.tag = Robot.camera.getTag();

        // park

        Robot.drivetrain.drive(36, DRIVE_SPEED, true);
        Robot.drivetrain.drive(-6, DRIVE_SPEED, true);

        if (Robot.tag == 1) Robot.drivetrain.strafe(-24, STRAFE_SPEED, true);
        if (Robot.tag == 3) Robot.drivetrain.strafe(24, STRAFE_SPEED, true);

    }

}