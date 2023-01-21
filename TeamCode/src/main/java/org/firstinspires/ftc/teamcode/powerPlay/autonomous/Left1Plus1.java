package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Left 1+1", group="competition")
@Disabled
public class Left1Plus1 extends LinearOpMode {

    double DRIVE_SPEED = 0.7;
    double STRAFE_SPEED = 0.7;
    double TURN_SPEED = 0.7;

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
        Robot.verticalLift.runToPosition("high");

        // drive to high junction

        Robot.drivetrain.drive(63, DRIVE_SPEED);
        Robot.drivetrain.drive(-8, DRIVE_SPEED);
        Robot.drivetrain.turn(45, TURN_SPEED);

        // wait for lift to be raised

        while (Robot.verticalLift.motor.isBusy()) {}

        // score on high junction

        double distToJunction = 9;

        Robot.drivetrain.drive(distToJunction, DRIVE_SPEED);
        sleep(500);
        Robot.servoHook.runToPosition("retract");
        sleep(500);
        Robot.drivetrain.drive(-distToJunction, DRIVE_SPEED);
        sleep(500);
        Robot.verticalLift.autonomousRunToPosition("low");

        // drive to cone stack

        Robot.drivetrain.turn(-136, 0.5);
        Robot.drivetrain.drive(28, 0.5);

        // pick up cone from cone stack

        Robot.verticalLift.autonomousRunToPosition("5th cone");
        sleep(500);
        Robot.servoHook.runToPosition("extend");
        sleep(500);
        Robot.verticalLift.autonomousRunToPosition("high");

        // drive to high junction

        Robot.drivetrain.drive(-28, DRIVE_SPEED);
        Robot.drivetrain.turn(135, TURN_SPEED);

        // score on high junction

        Robot.drivetrain.drive(distToJunction, DRIVE_SPEED);
        sleep(500);
        Robot.servoHook.runToPosition("retract");
        sleep(500);
        Robot.drivetrain.drive(-distToJunction, DRIVE_SPEED);
        Robot.verticalLift.autonomousRunToPosition("transfer");

        // park in correct zone

        Robot.drivetrain.turn(47, TURN_SPEED);

        if (Robot.tag == 1) Robot.drivetrain.drive(20, DRIVE_SPEED);
        if (Robot.tag == 3) Robot.drivetrain.drive(-20, DRIVE_SPEED);

        // reset hook and lift

        Robot.servoHook.runToPosition("retract");
        while (Robot.verticalLift.motor.isBusy()) {}
        Robot.verticalLift.motor.setPower(0);

    }

}