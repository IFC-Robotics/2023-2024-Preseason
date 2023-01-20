package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Test Automatic Scoring Sequence", group="Test")
public class TestAutomaticScoringSequence extends LinearOpMode {

    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);
        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

//        Robot.servoRotateClaw.runToPosition("down");
//        Robot.horizontalLift.autonomousRunToPosition("collect");
//        Robot.servoClaw.runToPosition("close");
//
//        Robot.horizontalLift.autonomousRunToPosition("transfer");
//        Robot.servoRotateClaw.runToPosition("up");

        Robot.servoHook.runToPosition("extend");
        sleep(1000);

        Robot.verticalLift.autonomousRunToPosition("ground");
//        Robot.servoRotateHook.runToPosition("score");
//        sleep(1000);

        Robot.verticalLift.autonomousRunToPosition("high");

        Robot.drivetrain.drive(-6, 0.5);
        Robot.servoHook.runToPosition("retract");
        sleep(1000);
        Robot.drivetrain.drive(6, 0.5);

//        Robot.servoRotateHook.runToPosition("transfer");
//        sleep(1000);
        Robot.verticalLift.autonomousRunToPosition("transfer");

    }

}