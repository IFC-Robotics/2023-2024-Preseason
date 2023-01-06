package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.RobotClass;

@Autonomous(name="Test Driving", group="Test")
public class TestDriving extends LinearOpMode {

    RobotClass robot = new RobotClass();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        robot.drive(robot.AUTONOMOUS_DRIVE_SPEED, 24, 1);
        sleep(1000);
        robot.strafe(robot.AUTONOMOUS_DRIVE_SPEED, 24, 1);
        sleep(1000);
        robot.turn(robot.AUTONOMOUS_DRIVE_SPEED, 90);

    }

}