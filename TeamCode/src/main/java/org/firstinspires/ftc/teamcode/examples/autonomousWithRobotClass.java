package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.RobotClass;

@Autonomous(name="Autonomous With Robot Class", group="powerPlay")
public class autonomousWithRobotClass extends LinearOpMode {

    RobotClass robot = new RobotClass();

    @Override
    public void runOpMode() {

        // initializing

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        // code goes here

        robot.strafe(robot.AUTONOMOUS_DRIVE_SPEED, 28, 1);

    }

}