package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Left side High junction", group="competition")
public class leftHighAuton extends OpMode {

    @Override
    public void init() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(hardwareMap, telemetry);

        Robot.tag = Robot.camera.getTag();
        Robot.side = "left";

        telemetry.addData("AprilTag", Robot.tag);
        telemetry.addData("Side", Robot.side);
        telemetry.update();

    }

    @Override
    public void start() {

        telemetry.addLine("Executing opMode...");
        telemetry.addData("AprilTag", Robot.tag);
        telemetry.addData("Side", Robot.side);
        telemetry.update();

        // secure preloaded cone

//        Robot.servoHook.runToPosition("extend");
//        Robot.servoRotateHook.runToPosition("score");

        // drive to high junction

        Robot.drivetrain.drive(50);
        Robot.drivetrain.turn(135);

        // score on high junction

        Robot.verticalLift.autonomousRunToPosition("high");
        Robot.drivetrain.drive(-6);
//        Robot.servoHook.runToPosition("retract");
        Robot.drivetrain.drive(6);
        Robot.verticalLift.autonomousRunToPosition("transfer");

        // park

        Robot.drivetrain.turn(-135);
        Robot.drivetrain.drive(-24);

        if (Robot.tag == 1) Robot.drivetrain.strafe(24);
        if (Robot.tag == 3) Robot.drivetrain.strafe(-24);

    }

    @Override
    public void loop() {}

}