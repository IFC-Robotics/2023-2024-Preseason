package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import java.lang.Math;

@TeleOp(name = "Drivetrain")
@Disabled
public class Drivetrain extends LinearOpMode {

    public static DcMotor motorFrontRight;
    public static DcMotor motorFrontLeft;
    public static DcMotor motorBackRight;
    public static DcMotor motorBackLeft;

    public static double AUTONOMOUS_SPEED = 0.3;
    public static double AUTONOMOUS_TURN_SPEED = 0.3;
    public static double MAX_TELEOP_SPEED = 0.7;
    public static int COUNTS_PER_INCH;

    public Drivetrain() {}

    @Override
    public void runOpMode() {}

    public void init(HardwareMap hardwareMap, int countsPerInch) {

        COUNTS_PER_INCH = countsPerInch;

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft  = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackRight  = hardwareMap.get(DcMotor.class, "motor_back_right");
        motorBackLeft   = hardwareMap.get(DcMotor.class, "motor_back_left");

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

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

    public void frontRightWheelDrive(double distance) {

        telemetry.addData("frontRightWheelDrive distance", distance);

        int target = (int)(distance * COUNTS_PER_INCH);
        double power = Math.signum(distance) * AUTONOMOUS_SPEED;

        motorFrontRight.setTargetPosition(target);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setPower(power);

        while (motorFrontRight.isBusy() && opModeIsActive()) {}

        motorFrontRight.setPower(0);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void drive(double distance) {

        telemetry.addData("drive distance", distance);

        int target = (int)(distance * COUNTS_PER_INCH);
        double power = Math.signum(distance) * AUTONOMOUS_SPEED;

        int[] targets = { target, target, target, target };
        double[] powers = { power, power, power, power };

        moveDrivetrain(targets, powers);

    }

    public void strafe(double distance) {

        telemetry.addData("strafe distance", distance);

        int target = (int)(distance * COUNTS_PER_INCH);
        double power = Math.signum(distance) * AUTONOMOUS_SPEED;

        int[] targets = { -target, target, target, -target };
        double[] powers = { -power, power, power, -power };

        moveDrivetrain(targets, powers);

    }

    public void turn(double angle) { // to test

        telemetry.addData("turn angle", angle);

        double WHEEL_RADIUS = 2.0;
        double circumference = 2 * Math.PI * WHEEL_RADIUS;
        double distance = circumference * angle / 360;

        int target = (int)(distance * COUNTS_PER_INCH);
        double power = Math.signum(angle) * AUTONOMOUS_TURN_SPEED;

        int[] targets = { target, -target, target, -target };
        double[] powers = { -power, power, -power, power };

        moveDrivetrain(targets, powers);

    }

    public void moveDrivetrain(int[] targets, double[] powers) {

        telemetry.addData("moveDrivetrain", "");

        motorFrontRight.setTargetPosition(targets[0]);
        motorFrontLeft.setTargetPosition(targets[1]);
        motorBackRight.setTargetPosition(targets[2]);
        motorBackLeft.setTargetPosition(targets[3]);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontRight.setPower(powers[0]);
        motorFrontLeft.setPower(powers[1]);
        motorBackRight.setPower(powers[2]);
        motorBackLeft.setPower(powers[3]);

        while ((motorFrontRight.isBusy() || motorFrontLeft.isBusy() || motorBackRight.isBusy() || motorBackLeft.isBusy()) && opModeIsActive()) {}

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

        telemetry.addData("teleOp", "");

        double denominator = Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(turn), 1);

        double frontRightPower = Range.clip((drive - turn - strafe) / denominator, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
        double frontLeftPower  = Range.clip((drive + turn + strafe) / denominator, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
        double backRightPower  = Range.clip((drive - turn + strafe) / denominator, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
        double backLeftPower   = Range.clip((drive + turn - strafe) / denominator, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);

        motorFrontRight.setPower(frontRightPower);
        motorFrontLeft.setPower(frontLeftPower);
        motorBackRight.setPower(backRightPower);
        motorBackLeft.setPower(backLeftPower);

    }

}