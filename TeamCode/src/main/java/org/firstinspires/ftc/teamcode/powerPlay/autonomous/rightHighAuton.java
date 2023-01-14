package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Right side High junction", group="competition")
public class rightHighAuton extends LinearOpMode {

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(hardwareMap, telemetry);

        Robot.tag = Robot.camera.getTag();
        Robot.side = "right";

        telemetry.addData("AprilTag", Robot.tag);
        telemetry.addData("Side", Robot.side);
        telemetry.update();

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.addData("AprilTag", Robot.tag);
        telemetry.addData("Side", Robot.side);
        telemetry.update();

        // secure preloaded cone (could be done before init, in a separate file)

        Robot.servoHook.runToPosition("extend");
        sleep(1000);
        Robot.verticalLift.autonomousRunToPosition("ground");

        // drive to high junction

        Robot.drivetrain.drive(50);
        Robot.drivetrain.turn(-135);

        // score on high junction

        Robot.verticalLift.autonomousRunToPosition("high");
        Robot.drivetrain.drive(-6);
        Robot.servoHook.runToPosition("retract");
        sleep(1000);

        // reset scoring system

        Robot.drivetrain.drive(6);
        Robot.verticalLift.autonomousRunToPosition("transfer");

        // park

        Robot.drivetrain.turn(135);
        Robot.drivetrain.drive(-24);

        if (Robot.tag == 1) Robot.drivetrain.strafe(-24);
        if (Robot.tag == 3) Robot.drivetrain.strafe(24);

    }

}