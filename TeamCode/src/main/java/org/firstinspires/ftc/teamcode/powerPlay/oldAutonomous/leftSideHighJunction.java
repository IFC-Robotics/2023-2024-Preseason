package org.firstinspires.ftc.teamcode.powerPlay.oldAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

@Autonomous(name="Left Side High Junction")
@Disabled
public class leftSideHighJunction extends LinearOpMode {

    robotClass robot = new robotClass();

    final double DRIVE_SPEED = 0.5;
    final int SHORT_PAUSE = 200;
    final int LONG_PAUSE = 1000;

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        robot.waitForStart();

        robot.moveHook("extend");
        robot.drive(DRIVE_SPEED, 60, -1);
        sleep(SHORT_PAUSE);
        robot.drive(DRIVE_SPEED, 6, 1);
        sleep(SHORT_PAUSE);
        robot.strafe(DRIVE_SPEED, 12, -1); // for right side program, switch direction to 1

        robot.lift("high");
        sleep(SHORT_PAUSE);
        robot.rotateHook("deposit");
        robot.moveHook("retract");
        sleep(LONG_PAUSE);
        robot.rotateHook("deposit");
        robot.lift("transfer");
        sleep(SHORT_PAUSE);

        robot.strafe(DRIVE_SPEED, 12, 1); // for right side program, switch direction to -1
        sleep(SHORT_PAUSE);
        robot.drive(DRIVE_SPEED, 30, 1);
        sleep(SHORT_PAUSE);

        if (robot.tagOfInterest == null || robot.tagOfInterest.id == 3) {
            robot.strafe(DRIVE_SPEED, 0, 1);
        } else if (robot.tagOfInterest.id == 2) {
            // already there
        } else if (robot.tagOfInterest.id == 1) {
            robot.strafe(DRIVE_SPEED, 0, -1);
        }

    }

}