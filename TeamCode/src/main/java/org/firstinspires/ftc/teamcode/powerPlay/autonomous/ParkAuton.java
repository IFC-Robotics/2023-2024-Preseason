package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="park autonomous", group="competition")
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

        // read AprilTag

        Robot.tag = Robot.camera.getTag();

        // secure preloaded cone

        Robot.servoHook.runToPosition("extend");
        sleep(1000);

        // drive to starting

        Robot.drivetrain.drive(34, DRIVE_SPEED);
        sleep(100);
        Robot.drivetrain.drive(-6, DRIVE_SPEED);
        sleep(100);

        if (Robot.tag == 1) Robot.drivetrain.strafe(-26, STRAFE_SPEED);
        if (Robot.tag == 3) Robot.drivetrain.strafe(26, STRAFE_SPEED);

    }

}