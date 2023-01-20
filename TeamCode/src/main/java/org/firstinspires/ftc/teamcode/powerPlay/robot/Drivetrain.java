package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.Math;

public class Drivetrain {

    LinearOpMode linearOpMode;
    HardwareMap hardwareMap;
    Telemetry telemetry;

    public static DcMotor motorFrontRight;
    public static DcMotor motorFrontLeft;
    public static DcMotor motorBackRight;
    public static DcMotor motorBackLeft;

    public static double MAX_TELEOP_SPEED = 0.7;
    public static String FORWARD_DIRECTION = "vertical lift";

    public static int COUNTS_PER_INCH;

    public Drivetrain() {}

    public void init(LinearOpMode opModeParam, int countsPerInch) {

        linearOpMode = opModeParam;
        hardwareMap = opModeParam.hardwareMap;
        telemetry = opModeParam.telemetry;

        COUNTS_PER_INCH = countsPerInch;

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft  = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackRight  = hardwareMap.get(DcMotor.class, "motor_back_right");
        motorBackLeft   = hardwareMap.get(DcMotor.class, "motor_back_left");

        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);

        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void drive(double distance, double speed) {

        telemetry.addLine(String.format("\ndriving %s inches", distance));

        double factor = 0.868; // coeff to regulate driving distances
        int target = (int)(distance * COUNTS_PER_INCH * factor);
        double power = Math.signum(distance) * speed;

        moveDrivetrain(target, target, target, target, power, power, power, power);

    }

    public void strafe(double distance, double speed) {

        telemetry.addLine(String.format("\nstrafing %s inches", distance));

        double factor = 1.1423; // once again another constant to try to get the correct distances
        int target = (int)(distance * COUNTS_PER_INCH * factor);
        double power = Math.signum(distance) * speed;

        moveDrivetrain(-target, target, target, -target, -power, power, power, -power);

    }

    public void turn(double angle, double speed) {

        telemetry.addLine(String.format("\nturning %s degrees", angle));

        double factor = 12.85; // no meaning, just what makes the turns work
        double circumference = 2 * Math.PI * factor;
        double distance = circumference * angle / 360;

        int target = (int)(distance * COUNTS_PER_INCH);
        double power = Math.signum(angle) * speed;

        moveDrivetrain(target, -target, target, -target, -power, power, -power, power);

    }

    public void moveDrivetrain(int targetFrontRight, int targetFrontLeft, int targetBackRight, int targetBackLeft, double powerFrontRight, double powerFrontLeft, double powerBackRight, double powerBackLeft) {

        motorFrontRight.setTargetPosition(targetFrontRight);
        motorFrontLeft.setTargetPosition(targetFrontLeft);
        motorBackRight.setTargetPosition(targetBackRight);
        motorBackLeft.setTargetPosition(targetBackLeft);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontRight.setPower(powerFrontRight);
        motorFrontLeft.setPower(powerFrontLeft);
        motorBackRight.setPower(powerBackRight);
        motorBackLeft.setPower(powerBackLeft);

        while (motorBackLeft.isBusy()) {}

        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void teleOp(double drive, double strafe, double turn) {

        double denominator = Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(turn), 1);

        double frontRightPower = Range.clip((drive + turn - strafe) / denominator, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
        double frontLeftPower  = Range.clip((drive - turn + strafe) / denominator, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
        double backRightPower  = Range.clip((drive + turn + strafe) / denominator, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
        double backLeftPower   = Range.clip((drive - turn - strafe) / denominator, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);

        motorFrontRight.setPower(frontRightPower);
        motorFrontLeft.setPower(frontLeftPower);
        motorBackRight.setPower(backRightPower);
        motorBackLeft.setPower(backLeftPower);

    }

}