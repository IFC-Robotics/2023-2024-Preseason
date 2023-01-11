package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.BPArobot;

@Autonomous(name="BPAwithRobot", group = "PowerPlay")
public class BPAwithRobot extends LinearOpMode {

    BPArobot robot = new BPArobot();

    public void runOpMode () {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);

        waitForStart();

        robot.strafe(10.0);

    }

}