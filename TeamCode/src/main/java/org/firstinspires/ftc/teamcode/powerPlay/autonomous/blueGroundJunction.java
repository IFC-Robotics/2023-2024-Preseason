package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

@Autonomous(name="Blue Ground Junction")
public class blueGroundJunction extends LinearOpMode {

    robotClass robot = new robotClass();

    final double DRIVE_SPEED = 0.5;
    final int SHORT_PAUSE = 200;

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        robot.waitForStart();

        // code goes here

        robot.moveClaw("close");
        sleep(SHORT_PAUSE);
        robot.strafe(DRIVE_SPEED, 24, -1);
        sleep(SHORT_PAUSE);
        robot.drive(DRIVE_SPEED, 13, 1);
        sleep(SHORT_PAUSE);
        robot.moveClaw("open");
        sleep(SHORT_PAUSE);
        robot.drive(DRIVE_SPEED, 13, -1);
        sleep(SHORT_PAUSE);
        robot.strafe(DRIVE_SPEED, 12, -1);
        sleep(SHORT_PAUSE);
        robot.drive(DRIVE_SPEED, 24, 1);
        sleep(SHORT_PAUSE);

//        robot.drive(DRIVE_SPEED, 2, 1);
//        sleep(SHORT_PAUSE);
//        robot.moveClaw("open");
//        sleep(SHORT_PAUSE);
//        robot.lift("ground junction");
//        sleep(SHORT_PAUSE);
//        robot.strafe(1, 8, 1);

    }

}