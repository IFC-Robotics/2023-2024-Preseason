package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.powerPlay.robot.RobotClassWithSubsystems;
import org.firstinspires.ftc.teamcode.powerPlay.robot.Storage;

@Autonomous(name="Right side High junction", group="competition")
public class rightHighAuton extends OpMode {

    RobotClassWithSubsystems robot = new RobotClassWithSubsystems();

    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        robot.cameraSubsystem.getTag();

        telemetry.addData("Tag before start", robot.cameraSubsystem.tag);
        telemetry.update();

        Storage.side = "right";
        Storage.tag = robot.cameraSubsystem.tag;

    }

    @Override
    public void start() {

        // secure preloaded cone

        robot.servoHook.runToPosition("extend");
        robot.servoRotateHook.runToPosition("score");
        robot.verticalLift.runToPosition("ground");

        // drive to high junction

        robot.drivetrain.drive(-75);
        robot.drivetrain.turn(45);

        // score on high junction

        robot.verticalLift.runToPosition("high");
        robot.drivetrain.drive(-3);
        robot.servoHook.runToPosition("retract");

        // reset scoring system

        robot.drivetrain.drive(3);
        robot.verticalLift.runToPosition("transfer");

        // drive to cone stack

        robot.drivetrain.turn(-135);
        robot.drivetrain.drive(-24);

        // pick up cone

        robot.verticalLift.runToPosition("low"); // height of just above the cone stack
        robot.drivetrain.drive(-3);
        robot.verticalLift.runToPosition("ground"); // height of the cone stack
        robot.servoHook.runToPosition("extend");
        robot.drivetrain.drive(3);
        robot.verticalLift.runToPosition("low");

        // drive to high junction (again)

        robot.drivetrain.drive(24);
        robot.drivetrain.turn(135);

        // score on high junction (again)

        robot.verticalLift.runToPosition("high");
        robot.drivetrain.drive(-3);
        robot.servoHook.runToPosition("retract");

        // reset scoring system (again)

        robot.drivetrain.drive(3);
        robot.verticalLift.runToPosition("transfer");

        // park

        robot.drivetrain.turn(-45);

        if (robot.cameraSubsystem.tag == 1) robot.drivetrain.strafe(-24);
        if (robot.cameraSubsystem.tag == 3) robot.drivetrain.strafe(24);

    }

    @Override
    public void loop() {}

}