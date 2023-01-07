package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import java.lang.Math;

public class DrivetrainSubsystem extends LinearOpMode {

    public DcMotor motorFrontRight;
    public DcMotor motorFrontLeft;
    public DcMotor motorBackRight;
    public DcMotor motorBackLeft;
    public DcMotor[] motors = { motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft };

    public double AUTONOMOUS_SPEED = 0.3;
    public double MAX_TELEOP_SPEED = 0.7;
    public int COUNTS_PER_INCH;

    // initialize

    public DrivetrainSubsystem() {}

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

    // teleOp

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

    // autonomous

    public void drive(double speed, int inches, int direction) {

        int target = direction * inches * COUNTS_PER_INCH;
        double power = direction * speed;

        int[] targets = { target, target, target, target };
        double[] powers = { power, power, power, power };

        moveDrivetrain(targets, powers);

    }

    public void strafe(double speed, int inches, int direction) {

        int target = direction * inches * COUNTS_PER_INCH;
        double power = direction * speed;

        int[] targets = { -target, target, target, -target };
        double[] powers = { -power, power, power, -power };

        moveDrivetrain(targets, powers);

    }

    public void turn(double speed, double angle) { // to test

        int circumference = 66; // what does this even mean? circumference of the wheels? must test
        int inches = (int)(circumference * angle / 360);
        int target = inches * COUNTS_PER_INCH;
        double power = Math.signum(angle) * speed;

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

}