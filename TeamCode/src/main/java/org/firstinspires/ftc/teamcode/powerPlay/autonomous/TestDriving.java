package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

@Autonomous(name="Test Driving", group="Test")
public class TestDriving extends LinearOpMode {

    robotClass robot = new robotClass();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        robot.drive(AUTONOMOUS_DRIVE_SPEED, 24, 1);
        sleep(1000);
        robot.strafe(AUTONOMOUS_DRIVE_SPEED, 24, 1);
        sleep(1000);
        robot.turn(AUTONOMOUS_DRIVE_SPEED, 90);

    }

}