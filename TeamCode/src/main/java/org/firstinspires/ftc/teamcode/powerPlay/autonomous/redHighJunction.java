package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

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
        int actualZone;

        robot.moveClaw("close");
        sleep(longPause);
        robot.lift("low junction");
        sleep(shortPause);
        actualZone = computerVision();
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
        waterOfDog(actualZone);

    }





    public int computerVision() {
        int zone = 1;

        return zone;
    }

    public void waterOfDog(int zone) {
        if (zone == 1) {
            robot.strafe(1, 14, -1);
        }
        else if (zone == 2) {
            robot.strafe(1, 28, -1);
        }
        else if (zone == 3) {
            robot.strafe(1, 42, -1);
        }
    }

    public double inchToTics(double inches) {

        final double COUNTS_PER_REVOLUTION    = 28.0;
        final double GEAR_RATIO               = 20.0;
        final double WHEEL_DIAMETER_IN_INCHES = 4.0;
        final double COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION * GEAR_RATIO) / (WHEEL_DIAMETER_IN_INCHES * Math.PI);

        return inches * COUNTS_PER_INCH;

    }


}
