package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.RobotClassWithSubsystems;

@Autonomous(name="Test Automatic Scoring Sequence", group="Test")
public class TestAutomaticScoringSequence extends LinearOpMode {

    RobotClassWithSubsystems robot = new RobotClassWithSubsystems();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        robot.servoRotateClaw.runToPosition("down");
        robot.horizontalLift.autonomousRunToPosition("collect");
        robot.servoClaw.runToPosition("close");

        robot.horizontalLift.autonomousRunToPosition("transfer");
        robot.servoRotateClaw.runToPosition("up");
        robot.servoHook.runToPosition("extend");

        robot.servoRotateHook.runToPosition("score");
        robot.verticalLift.autonomousRunToPosition("score");
        robot.servoHook.runToPosition("retract");

    }

}