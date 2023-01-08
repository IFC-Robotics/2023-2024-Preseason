package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Right side High junction", group="competition")
public class rightHighAuton extends OpMode {

    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Robot.init(hardwareMap);
        Robot.tag = Robot.camera.getTag();
        Robot.side = "right";

        telemetry.addData("AprilTag", Robot.tag);
        telemetry.addData("Side", Robot.side);
        telemetry.update();

    }

    @Override
    public void start() {

        // secure preloaded cone

        Robot.servoHook.runToPosition("extend");
        Robot.servoRotateHook.runToPosition("score");
        Robot.verticalLift.runToPosition("ground");

        // drive to high junction

        Robot.drivetrain.drive(-75);
        Robot.drivetrain.turn(45); // left side: -45

        // score on high junction

        Robot.verticalLift.runToPosition("high");
        Robot.drivetrain.drive(-3);
        Robot.servoHook.runToPosition("retract");

        // reset scoring system

        Robot.drivetrain.drive(3);
        Robot.verticalLift.runToPosition("transfer");

        // drive to cone stack

        Robot.drivetrain.turn(-135); // left side: 135
        Robot.drivetrain.drive(-24);

        // pick up cone

        Robot.verticalLift.runToPosition("low"); // height of just above the cone stack
        Robot.drivetrain.drive(-3);
        Robot.verticalLift.runToPosition("ground"); // height of the cone stack
        Robot.servoHook.runToPosition("extend");
        Robot.drivetrain.drive(3);
        Robot.verticalLift.runToPosition("low");

        // drive to high junction (again)

        Robot.drivetrain.drive(24);
        Robot.drivetrain.turn(135); // left side: -135

        // score on high junction (again)

        Robot.verticalLift.runToPosition("high");
        Robot.drivetrain.drive(-3);
        Robot.servoHook.runToPosition("retract");

        // reset scoring system (again)

        Robot.drivetrain.drive(3);
        Robot.verticalLift.runToPosition("transfer");

        // park

        Robot.drivetrain.turn(-45); // left side: 45

        if (Robot.tag == 1) Robot.drivetrain.strafe(-24); // left side: 24
        if (Robot.tag == 3) Robot.drivetrain.strafe(24); // left side: -24

    }

    @Override
    public void loop() {}

}