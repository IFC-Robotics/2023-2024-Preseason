package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

@Autonomous(name="Test AprilTag Detection", group="Test")
public class TestAprilTagDetection extends LinearOpMode {

    robotClass robot = new robotClass();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        int tag = robot.getTag();

        telemetry.addData("Tag", tag);
        telemetry.update();

        sleep(5000);

    }

}