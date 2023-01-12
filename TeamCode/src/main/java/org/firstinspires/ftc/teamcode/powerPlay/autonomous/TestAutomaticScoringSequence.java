package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Test Automatic Scoring Sequence", group="Test")
public class TestAutomaticScoringSequence extends LinearOpMode {

    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(hardwareMap, telemetry);
        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        Robot.servoRotateClaw.runToPosition("down");
        Robot.horizontalLift.autonomousRunToPosition("collect");
        Robot.servoClaw.runToPosition("close");

        Robot.horizontalLift.autonomousRunToPosition("transfer");
        Robot.servoRotateClaw.runToPosition("up");
        Robot.servoHook.runToPosition("extend");

        Robot.servoRotateHook.runToPosition("score");
        Robot.verticalLift.autonomousRunToPosition("score");
        Robot.servoHook.runToPosition("retract");

    }

}