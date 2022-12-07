package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

@Autonomous(name="Autonomous With Robot Class", group="powerPlay")
public class autonomousWithRobotClass extends LinearOpMode {

    robotClass robot = new robotClass();

    @Override
    public void runOpMode() {

        // initializing

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        // code goes here

        robot.strafe(robot.DRIVE_SPEED, 28, 1);

    }

}