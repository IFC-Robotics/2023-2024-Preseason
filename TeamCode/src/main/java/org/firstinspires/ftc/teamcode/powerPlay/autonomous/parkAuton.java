package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="park autonomous", group="competition")
public class parkAuton extends LinearOpMode {

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(hardwareMap, telemetry);
        Robot.tag = Robot.camera.getTag();

        telemetry.addData("AprilTag", Robot.tag);
        telemetry.update();

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        Robot.drivetrain.drive(30);

        if (Robot.tag == 1) Robot.drivetrain.strafe(-24);
        if (Robot.tag == 3) Robot.drivetrain.strafe(24);

    }

}