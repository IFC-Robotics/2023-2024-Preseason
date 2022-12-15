package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

// also works for blue our-side high junction

@Autonomous(name="Right Side High Junction")
public class rightSideHighJunction extends LinearOpMode {

    robotClass robot = new robotClass();

    final double DRIVE_SPEED = 0.5;
    final int SHORT_PAUSE = 200;
    final int LONG_PAUSE = 1000;

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        robot.waitForStart();

//        robot.lift("pickup cone");
//        sleep(SHORT_PAUSE);
//        robot.moveHook("extend");
//        sleep(SHORT_PAUSE);
//        robot.lift("ground junction");
//        sleep(SHORT_PAUSE);
        robot.strafe(DRIVE_SPEED, 36, -1);
        sleep(SHORT_PAUSE);
        robot.drive(DRIVE_SPEED, 24, 1);
        sleep(SHORT_PAUSE);
//        robot.lift("high junction");
//        sleep(SHORT_PAUSE);
        robot.drive(DRIVE_SPEED, 6, 1);
        sleep(SHORT_PAUSE);
//        robot.moveHook("retract");
//        sleep(SHORT_PAUSE);
        robot.drive(DRIVE_SPEED, 6, -1);
        sleep(SHORT_PAUSE);
//        robot.lift("ground junction");
//        sleep(SHORT_PAUSE);

//        if (robot.tagOfInterest == null || robot.tagOfInterest.id == 1) {
//            robot.strafe(1, 12, 1);
//        } else if (robot.tagOfInterest.id == 2) {
//            robot.strafe(1, 36, 1);
//        } else if (robot.tagOfInterest.id == 3) {
//            robot.strafe(1, 60, 1);
//        }

    }

}
