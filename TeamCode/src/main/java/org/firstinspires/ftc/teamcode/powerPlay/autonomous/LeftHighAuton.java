package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Left 1+0", group="competition")
public class LeftHighAuton extends LinearOpMode {

    double DRIVE_SPEED = 0.7;
    double STRAFE_SPEED = 0.7;
    double TURN_SPEED = 0.7;

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);
        Robot.tag = Robot.camera.getTag();

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        // secure preloaded cone

        Robot.servoHook.runToPosition("extend");
        sleep(1000);
        Robot.verticalLift.runToPosition("high");

        // drive to high junction

        Robot.drivetrain.drive(63, DRIVE_SPEED);
        Robot.drivetrain.drive(-8, DRIVE_SPEED);
        Robot.drivetrain.turn(45, TURN_SPEED);

        // score on high junction

        double distToJunction = 8;

        Robot.drivetrain.drive(distToJunction, DRIVE_SPEED);
        sleep(500);
        Robot.servoHook.runToPosition("retract");
        sleep(500);
        Robot.drivetrain.drive(-distToJunction, DRIVE_SPEED);
        sleep(500);
        Robot.verticalLift.runToPosition("transfer");

        // park in correct zone

        Robot.drivetrain.turn(47, TURN_SPEED);

        if (Robot.tag == 1) Robot.drivetrain.drive(21, DRIVE_SPEED);
        if (Robot.tag == 3) Robot.drivetrain.drive(-21, DRIVE_SPEED);

        // reset hook and lift

        Robot.servoHook.runToPosition("retract");
        while (Robot.verticalLift.motor.isBusy()) {}
        Robot.verticalLift.motor.setPower(0);

    }

}