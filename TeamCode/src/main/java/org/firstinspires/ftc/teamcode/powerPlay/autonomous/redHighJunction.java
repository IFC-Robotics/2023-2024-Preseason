package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

@Autonomous(name="Red High Junction")
public class redHighJunction extends LinearOpMode {

    robotClass robot = new robotClass();

    final double DRIVE_SPEED = 0.3;
    final int SHORT_PAUSE = 200;
    final int LONG_PAUSE = 1000;

    public void runOpMode () {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        robot.waitForStart();

        // code goes here

        robot.moveClaw("close");
        sleep(LONG_PAUSE);
        robot.lift("low junction");
        sleep(SHORT_PAUSE);
        robot.strafe(DRIVE_SPEED, 37, 1);
        sleep(SHORT_PAUSE);
        robot.drive(DRIVE_SPEED, 24, -1);
        sleep(SHORT_PAUSE);
        robot.lift("high junction");
        sleep(SHORT_PAUSE);
        robot.drive(DRIVE_SPEED, 7, -1);
        sleep(SHORT_PAUSE);
        robot.lift("high junction");
        sleep(SHORT_PAUSE);
        robot.moveClaw("open");
        sleep(LONG_PAUSE);
        robot.moveClaw("close");
        sleep(SHORT_PAUSE);
        robot.drive(DRIVE_SPEED, 5, 1);
        sleep(SHORT_PAUSE);
        robot.lift("ground junction");
        sleep(SHORT_PAUSE);
        robot.parkInCorrectZone(-1);

    }

}
