package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.RobotClass;

@Autonomous(name="Test Automatic Scoring Sequence", group="Test")
public class TestAutomaticScoringSequence extends LinearOpMode {

    robotClass robot = new RobotClass();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        robot.moveHorizontalLift("intake");
        robot.moveClaw("close");
        robot.moveHorizontalLift("transfer");
        robot.moveHook("extend");
        robot.rotateHook("deposit");
        robot.moveVerticalLift("deposit");

    }

}