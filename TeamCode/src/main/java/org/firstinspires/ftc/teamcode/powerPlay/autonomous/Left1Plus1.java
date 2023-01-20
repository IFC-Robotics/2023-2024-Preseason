package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Left 1+1", group="competition")
public class Left1Plus1 extends LinearOpMode {

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

        // drive to high junction

        Robot.servoHook.runToPosition("extend");
        Robot.drivetrain.drive(56, DRIVE_SPEED);
        Robot.drivetrain.turn(45, TURN_SPEED);

        // score on high junction

        Robot.verticalLift.autonomousRunToPosition("high");
        Robot.drivetrain.drive(15, DRIVE_SPEED);
        sleep(500);
        Robot.servoHook.runToPosition("retract");
        sleep(1000);
        Robot.drivetrain.drive(-15, DRIVE_SPEED);
        Robot.verticalLift.autonomousRunToPosition("low");

        // drive to cone stack

        Robot.drivetrain.turn(-135, TURN_SPEED);
        Robot.drivetrain.drive(30, DRIVE_SPEED);

        // pick up cone from cone stack

        Robot.servoHook.runToPosition("extend");
        Robot.verticalLift.autonomousRunToPosition("6th cone");
        Robot.servoHook.runToPosition("extend");
        Robot.verticalLift.autonomousRunToPosition("high");

        // drive to high junction

        Robot.drivetrain.drive(-30, DRIVE_SPEED);
        Robot.drivetrain.turn(135, TURN_SPEED);

        // score on high junction

        Robot.drivetrain.drive(15, DRIVE_SPEED);
        sleep(500);
        Robot.servoHook.runToPosition("retract");
        sleep(1000);
        Robot.drivetrain.drive(-15, DRIVE_SPEED);
        Robot.verticalLift.autonomousRunToPosition("transfer");

        // park

        Robot.drivetrain.turn(135, TURN_SPEED);
        Robot.drivetrain.drive(22, DRIVE_SPEED);

        if (Robot.tag == 1) Robot.drivetrain.strafe(24, STRAFE_SPEED);
        if (Robot.tag == 3) Robot.drivetrain.strafe(-24, STRAFE_SPEED);

    }

}