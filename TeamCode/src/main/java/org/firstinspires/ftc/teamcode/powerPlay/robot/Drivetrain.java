package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import java.lang.Math;

public class Drivetrain extends LinearOpMode {

    public static DcMotor motorFrontRight;
    public static DcMotor motorFrontLeft;
    public static DcMotor motorBackRight;
    public static DcMotor motorBackLeft;
    public static DcMotor[] motors = { motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft };

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

        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);

        for (DcMotor motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // test what this does
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }

    public void drive(double distance) {

        int target = (int)(distance * COUNTS_PER_INCH);
        double power = Math.signum(distance) * AUTONOMOUS_SPEED;

        int[] targets = { target, target, target, target };
        double[] powers = { power, power, power, power };

        moveDrivetrain(targets, powers);

    }

    public void strafe(double distance) {

        int target = (int)(distance * COUNTS_PER_INCH);
        double power = Math.signum(distance) * AUTONOMOUS_SPEED;

        int[] targets = { -target, target, target, -target };
        double[] powers = { -power, power, power, -power };

        moveDrivetrain(targets, powers);

    }

    public void turn(double angle) { // to test

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

        for (int i = 0; i < 4; i++) {
            motors[i].setTargetPosition(targets[i]);
            motors[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motors[i].setPower(powers[i]);
        }

        while ((motorFrontLeft.isBusy() || motorFrontRight.isBusy() || motorBackLeft.isBusy() || motorBackRight.isBusy()) && opModeIsActive()) {}

        for (int i = 0; i < 4; i++) {
            motors[i].setPower(0);
            motors[i].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motors[i].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }

    public void executeTeleOp() {

        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(turn), 1);

        double frontRightPower = Range.clip((drive - turn - strafe) / denominator, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
        double frontLeftPower  = Range.clip((drive + turn + strafe) / denominator, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
        double backRightPower  = Range.clip((drive - turn + strafe) / denominator, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
        double backLeftPower   = Range.clip((drive + turn - strafe) / denominator, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);

        // if this doesn't work, get rid of the "denominator" variable

        motorFrontRight.setPower(frontRightPower);
        motorFrontLeft.setPower(frontLeftPower);
        motorBackRight.setPower(backRightPower);
        motorBackLeft.setPower(backLeftPower);

    }

}