package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

@Autonomous(name="red ground junction", group = "PowerPlay")
public class redGroundJunction extends LinearOpMode {

    robotClass robot = new robotClass();


    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();


//        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        // code goes here

        final double DRIVE_SPEED = 0.5;

//        moveClaw("close");
        robot.strafe(DRIVE_SPEED, (int) inchToTics(24), 1);
        sleep(100);
        robot.drive(DRIVE_SPEED, (int) inchToTics(13), 1);
        sleep(100);
        robot.moveClaw("open");
        sleep(100);
        robot.drive(DRIVE_SPEED, (int) inchToTics(13), -1);
        sleep(100);
        robot.strafe(DRIVE_SPEED, (int) inchToTics(12), 1);
        sleep(100);
        robot.drive(DRIVE_SPEED, (int) inchToTics(24), 1);
        sleep(100);
//        drive(DRIVE_SPEED, (int)inchToTics(2), 1);
//        moveClaw("open");
//        lift("ground junction");
//        strafe(1, 8, 1);

    }



    public double inchToTics(double inches) {

        final double COUNTS_PER_REVOLUTION = 28.0;
        final double GEAR_RATIO = 20.0;
        final double WHEEL_DIAMETER_IN_INCHES = 4.0;
        final double COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION * GEAR_RATIO) / (WHEEL_DIAMETER_IN_INCHES * Math.PI);

        return inches * COUNTS_PER_INCH;

    }

}