package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.Math;

public class Drivetrain {

    LinearOpMode opMode;
    Telemetry telemetry;

    public static DcMotor motorFrontRight;
    public static DcMotor motorFrontLeft;
    public static DcMotor motorBackRight;
    public static DcMotor motorBackLeft;

    public static double DRIVE_FACTOR = 38.63;
    public static double STRAFE_FACTOR = 50.83;
    public static double TURN_FACTOR = 285.91;

    public static double MAX_TELEOP_SPEED = 0.7;
    public static boolean limitSpeed = true;

    public final String forwardDirection;
    public final int sleepTime;

    public Drivetrain(String forwardDirection, int sleepTime) {
        this.forwardDirection = forwardDirection;
        this.sleepTime = sleepTime;
    }

    public void init(LinearOpMode opModeParam) {

        opMode = opModeParam;
        telemetry = opMode.telemetry;

        motorFrontLeft  = opMode.hardwareMap.get(DcMotor.class, "motor_front_left");
        motorFrontRight = opMode.hardwareMap.get(DcMotor.class, "motor_front_right");
        motorBackRight  = opMode.hardwareMap.get(DcMotor.class, "motor_back_right");
        motorBackLeft   = opMode.hardwareMap.get(DcMotor.class, "motor_back_left");

        if (this.forwardDirection == "hook") {
            motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
            motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
            motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        } else {
            motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        }

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

    // autonomous

    public void drive (double distance, double speed) { drive(distance, speed, true); }
    public void strafe(double distance, double speed) { drive(distance, speed, true); }
    public void turn  (double angle,    double speed) { drive(angle,    speed, true); }

    public void drive(double distance, double speed, boolean isSynchronous) {

        telemetry.addLine(String.format("\ndriving %s inches", distance));

        int target = inchesToTicks(distance);
        // int target = (int)(distance * DRIVE_FACTOR);

        double power = Math.signum(distance) * speed;

        moveDrivetrain(target, target, target, target, power, power, power, power, isSynchronous);

    }

    public void strafe(double distance, double speed, boolean isSynchronous) {

        telemetry.addLine(String.format("\nstrafing %s inches", distance));

        int target = inchesToTicks(distance);
        // int target = (int)(distance * STRAFE_FACTOR);

        double power = Math.signum(distance) * speed;

        moveDrivetrain(-target, target, target, -target, -power, power, power, -power, isSynchronous);
        
    }

    public void turn(double angle, double speed, boolean isSynchronous) {

        telemetry.addLine(String.format("\nturning %s degrees", angle));

        // double radius = 2;
        // double circumference = 2 * Math.PI * radius;
        // double distance = circumference * angle / 360;
        // int target = (int)(distance * TURN_FACTOR);

        int target = degreesToTicks(angle);

        double power = Math.signum(angle) * speed;

        moveDrivetrain(target, -target, target, -target, -power, power, -power, power, isSynchronous);

    }

    public void moveDrivetrain(int targetFrontRight, int targetFrontLeft, int targetBackRight, int targetBackLeft, double powerFrontRight, double powerFrontLeft, double powerBackRight, double powerBackLeft, boolean isSynchronous) {

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

        if (isSynchronous) waitForDrivetrain();

    }

    public void waitForDrivetrain() {

        while (drivetrainIsBusy()) {

            telemetry.addData("\nmotorFrontRight position", motorFrontRight.getCurrentPosition());
            telemetry.addData("motorFrontLeft position", motorFrontLeft.getCurrentPosition());
            telemetry.addData("motorBackRight position", motorBackRight.getCurrentPosition());
            telemetry.addData("motorBackLeft position", motorBackLeft.getCurrentPosition());

            telemetry.addData("\nmotorFrontRight power", motorFrontRight.getPower());
            telemetry.addData("motorFrontLeft power", motorFrontLeft.getPower());
            telemetry.addData("motorBackRight power", motorBackRight.getPower());
            telemetry.addData("motorBackLeft power", motorBackLeft.getPower());

            telemetry.update();

        }

        resetDrivetrain();
        opMode.sleep(this.sleepTime);

    }

    // teleOp

    public void teleOp(double drive, double strafe, double turn, boolean button) {

        // drive

        double denominator = Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(turn), 1); // test this
        double speed = limitSpeed ? MAX_TELEOP_SPEED : 1;

        double frontRightPower = Range.clip((drive + turn - strafe) / denominator, -speed, speed);
        double frontLeftPower  = Range.clip((drive - turn + strafe) / denominator, -speed, speed);
        double backRightPower  = Range.clip((drive + turn + strafe) / denominator, -speed, speed);
        double backLeftPower   = Range.clip((drive - turn - strafe) / denominator, -speed, speed);

        motorFrontRight.setPower(frontRightPower);
        motorFrontLeft.setPower(frontLeftPower);
        motorBackRight.setPower(backRightPower);
        motorBackLeft.setPower(backLeftPower);

        // change speed limits

        if (button) limitSpeed = !limitSpeed;

    }

    // helper methods

    public boolean drivetrainIsBusy() {
        return (motorFrontRight.isBusy() || motorFrontLeft.isBusy() || motorBackRight.isBusy() || motorBackLeft.isBusy());
    }

    public void resetDrivetrain() {

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

    // inches & degrees to ticks

    public static int inchesToTicks(double inches) {

        double TICKS_PER_REV = 1120; // 28, 560, 1120
        double WHEEL_RADIUS = 2; // measure
        double GEAR_RATIO = 20; // 1, 20, 40

        double wheelCircumference = 2 * Math.PI * WHEEL_RADIUS;
        double ticksPerInch = TICKS_PER_REV / (wheelCircumference * GEAR_RATIO);

        return (int)(inches * ticksPerInch);

    }

    public static int degreesToTicks(double degrees) {

        double ROBOT_RADIUS = 9; // measure/test

        double robotCircumference = 2 * Math.PI * ROBOT_RADIUS;
        double arc = robotCircumference * degrees / 360;

        return inchesToTicks(arc);

    }

}