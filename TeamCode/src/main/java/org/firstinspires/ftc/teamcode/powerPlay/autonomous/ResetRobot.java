package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.RobotClassWithSubsystems;

@Autonomous(name="Reset Robot")
public class ResetRobot extends LinearOpMode {

    RobotClassWithSubsystems robot = new RobotClassWithSubsystems();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        robot.servoClaw.runToPosition("open");
        robot.servoRotateClaw.runToPosition("up");
        robot.horizontalLift.autonomousRunToPosition("transfer");
        robot.servoRotateHook.runToPosition("transfer");
        robot.servoHook.runToPosition("retract");
        robot.verticalLift.autonomousRunToPosition("transfer");

    }

}