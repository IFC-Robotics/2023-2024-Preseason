package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Storage;
import org.firstinspires.ftc.teamcode.powerPlay.robot.RobotClassWithSubsystems;

@Autonomous(name="Test AprilTag Detection", group="Test")
public class TestAprilTagDetection extends LinearOpMode {

    RobotClassWithSubsystems robot = new RobotClassWithSubsystems();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        robot.cameraSubsystem.getTag();

        telemetry.addData("Tag before start", robot.cameraSubsystem.tag);
        telemetry.update();

        waitForStart();

        telemetry.addData("Tag after start", robot.cameraSubsystem.tag);
        telemetry.update();

        sleep(2000);

        Storage.tag = robot.cameraSubsystem.tag;

    }

}