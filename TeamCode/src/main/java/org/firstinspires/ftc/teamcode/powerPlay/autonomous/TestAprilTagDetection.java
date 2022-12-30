package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.RobotClass;

@Autonomous(name="Test AprilTag Detection", group="Test")
public class TestAprilTagDetection extends LinearOpMode {

    robotClass robot = new RobotClass();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        int tag = robot.getTag();

        telemetry.addData("Tag before start", tag);
        telemetry.update();

        waitForStart();

        telemetry.addData("Tag after start", tag);
        telemetry.update();

        sleep(2000);

    }

}