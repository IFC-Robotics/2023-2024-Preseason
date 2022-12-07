package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

@Autonomous(name="red high junction", group = "PowerPlay")
public class redHighJunction extends LinearOpMode {

    robotClass robot = new robotClass();

    final double DRIVE_SPEED = 0.3;
    final double LIFT_SPEED = 0.5;

    public void runOpMode () {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        // code goes here

        int shortPause = 200;
        int longPause = 1000;

        robot.moveClaw("close");
        sleep(longPause);
        robot.lift("low junction");
        sleep(shortPause);
        robot.strafe(DRIVE_SPEED, 37, 1);
        sleep(shortPause);
        robot.drive(DRIVE_SPEED, 24, -1);
        sleep(shortPause);
        robot.lift("high junction");
        sleep(shortPause);
        robot.drive(DRIVE_SPEED, 7, -1);
        sleep(shortPause);
        robot.lift("high junction");
        sleep(shortPause);
        robot.moveClaw("open");
        sleep(longPause);
        robot.moveClaw("close");
        sleep(shortPause);
        robot.drive(DRIVE_SPEED, 5, 1);
        sleep(shortPause);
        robot.lift("ground junction");
        sleep(shortPause);
        robot.parkInCorrectZone(-1);

    }

}
