package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Test AprilTag Detection", group="Test")
public class TestAprilTagDetection extends LinearOpMode {

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Robot.init(hardwareMap);

        Robot.tag = Robot.camera.getTag();
        telemetry.addData("Tag before start", Robot.tag);
        telemetry.addData("Tag after start", Robot.camera.getTag());
        telemetry.update();

        waitForStart();

        Robot.tag = Robot.camera.getTag();
        telemetry.addData("Tag after start", Robot.tag);
        telemetry.addData("Tag after start", Robot.camera.getTag());
        telemetry.update();

        sleep(2000);

    }

}