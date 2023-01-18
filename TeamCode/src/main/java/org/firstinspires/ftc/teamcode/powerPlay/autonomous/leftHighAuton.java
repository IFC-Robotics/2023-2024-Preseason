package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Left side High junction", group="competition")
public class leftHighAuton extends LinearOpMode {

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(hardwareMap, telemetry);

        Robot.tag = Robot.camera.getTag();
//        Robot.side = "left";

        telemetry.addData("AprilTag", Robot.tag);
        telemetry.update();

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        // drive to high junction

        Robot.servoHook.runToPosition("extend");
        Robot.drivetrain.drive(56);
        Robot.drivetrain.turn(45);

        // score on high junction

        Robot.verticalLift.autonomousRunToPosition("high");
        Robot.drivetrain.drive(11);
        sleep(1000);
        Robot.servoHook.runToPosition("retract");
        Robot.drivetrain.drive(-11);
        Robot.verticalLift.autonomousRunToPosition("transfer");

        // park

        Robot.drivetrain.turn(135);
        Robot.drivetrain.drive(26);

        if (Robot.tag == 1) Robot.drivetrain.strafe(24);
        if (Robot.tag == 3) Robot.drivetrain.strafe(-24);

    }

}