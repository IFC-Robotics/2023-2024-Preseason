package org.firstinspires.ftc.teamcode.powerPlay.practiceCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ZaleaDrivetrain extends LinearOpMode{

    public static DcMotor frontLeft;
    public static DcMotor frontRight;
    public static DcMotor backLeft;
    public static DcMotor backRight;

    public static double frontLeftPower;
    public static double frontRightPower;
    public static double backLeftPower;
    public static double backRightPower;

    LinearOpMode opMode;


    public void init(LinearOpMode opModeParam) {

        opMode = opModeParam;
        telemetry = opMode.telemetry;

        frontRight = opMode.hardwareMap.get(DcMotor.class, "front_right");
        frontLeft  = opMode.hardwareMap.get(DcMotor.class, "front_left");
        backLeft   = opMode.hardwareMap.get(DcMotor.class, "back_left");
        backRight  = opMode.hardwareMap.get(DcMotor.class, "back_right");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
    }

    public void drive(double power) {
        frontLeftPower  = power;
        frontRightPower = power;
        backLeftPower   = power;
        backRightPower  = power;

        move();
    }

    public void turn(double left, double right) {
        frontLeftPower  = left;
        frontRightPower = right;
        backLeftPower   = left; 
        backRightPower  = right;

        move();
    }

    public void strafe(String dir, double power) {
        if (dir == "right"){
            frontLeftPower  = -power;
            frontRightPower = power;
            backLeftPower   = power;
            backRightPower  = -power;
        }
        else if (dir == "left") {
            frontLeftPower  = power;
            frontRightPower = -power;
            backLeftPower   = -power;
            backRightPower  = power;
        }
        move();
    }

    public void move() {
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}