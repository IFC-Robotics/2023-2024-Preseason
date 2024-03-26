package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.Math;

public class Drivetrain {
//
    LinearOpMode opMode;
    Telemetry telemetry;
//DcMotor is a FTC based class that
    public static DcMotor motorFront;
    public static DcMotor motorLeft;
    public static DcMotor motorRight;
    public static DcMotor motorBack;

    public static double DRIVE_FACTOR = 40.92;
    public static double STRAFE_FACTOR = 50.83;
    public static double TURN_FACTOR = 296.0;

    public static double MAX_TELEOP_SPEED = 0.7;

    public final String forwardDirection;
    public final int sleepTime;

    public Drivetrain(String forwardDirection, int sleepTime) {
        this.forwardDirection = forwardDirection;
        this.sleepTime = sleepTime;
    }

    public void init(LinearOpMode opModeParam) {

        opMode = opModeParam;
        telemetry = opMode.telemetry;

        motorLeft  = opMode.hardwareMap.get(DcMotor.class, "motor_left");
        motorFront = opMode.hardwareMap.get(DcMotor.class, "motor_front");
        motorRight  = opMode.hardwareMap.get(DcMotor.class, "motor_right");
        motorBack   = opMode.hardwareMap.get(DcMotor.class, "motor_back");

        motorFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorFront.setDirection(DcMotor.Direction.REVERSE);
        motorRight.setDirection(DcMotor.Direction.REVERSE);
    }

    // autonomous

    public void drive (double distance, double speed) { drive(distance, speed, true); }
    public void strafe(double distance, double speed) { strafe(distance, speed, true); }
    public void turn  (double angle,    double speed) { turn(angle,    speed, true); }

    public void drive(double distance, double speed, boolean isSynchronous) {

        telemetry.addLine(String.format("\ndriving %s inches", distance));

        int target = (int)(distance * DRIVE_FACTOR);
        double power = Math.signum(distance) * speed;

        moveDrivetrain(target, target, target, target, 0, power, power, 0, isSynchronous);

    }

    public void strafe(double distance, double speed, boolean isSynchronous) {

        telemetry.addLine(String.format("\nstrafing %s inches", distance));

        int target = (int)(distance * DRIVE_FACTOR);
        double power = Math.signum(distance) * speed;

        moveDrivetrain(-target, target, target, -target, power, 0, 0, power, isSynchronous);

    }

    public void turn(double angle, double speed, boolean isSynchronous) {

        telemetry.addLine(String.format("\nturning %s degrees", angle));

        double radius = 2;
        double circumference = 2 * Math.PI * radius;
        double distance = circumference * angle / 360;
        int target = (int)(distance * TURN_FACTOR);

        double power = Math.signum(angle) * speed;

        moveDrivetrain(0, -target, target, 0, 0, -power, power, 0, isSynchronous);

    }

    public void moveDrivetrain(int targetFront, int targetLeft, int targetRight, int targetBack, double powerFront, double powerLeft, double powerRight, double powerBack, boolean isSynchronous) {

        motorFront.setTargetPosition(targetFront);
        motorLeft.setTargetPosition(targetLeft);
        motorRight.setTargetPosition(targetRight);
        motorBack.setTargetPosition(targetBack);

        motorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFront.setPower(powerFront);
        motorLeft.setPower(powerLeft);
        motorRight.setPower(powerRight);
        motorBack.setPower(powerBack);

        if (isSynchronous) waitForDrivetrain();

    }

    public void moveRobot(double x, double y, double yaw) {
        // Calculate wheel powers.
        double leftFrontPower    =  x -y +yaw;
        double rightFrontPower   =  x +y -yaw;
        double leftBackPower     =  x +y +yaw;
        double rightBackPower    =  x -y -yaw;

        // Normalize wheel powers to be less than 1.0
        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        // Send powers to the wheels.
        motorLeft.setPower(leftFrontPower);
        motorFront.setPower(rightFrontPower);
        motorBack.setPower(leftBackPower);
        motorRight.setPower(rightBackPower);
    }

    public void moveWheel(boolean frontRightTest,boolean leftTest,boolean rightTest,boolean backTest) {
        if (frontRightTest) {
            motorFront.setPower(1);
        }else {
            motorFront.setPower(0);
        }
        if (leftTest) {
            motorLeft.setPower(1);
        }else {
            motorLeft.setPower(0);
        }
        if (rightTest) {
            motorRight.setPower(1);
        }else {
            motorRight.setPower(0);
        }
        if (backTest) {
            motorBack.setPower(1);
        }else {
            motorBack.setPower(0);
        }
        telemetry.addData("motorFront",motorFront.getPower());
        telemetry.addData("motorFrontLeft",motorLeft.getPower());
        telemetry.addData("motorBackRight",motorRight.getPower());
        telemetry.addData("motorBack",motorBack.getPower());
    }

    public void waitForDrivetrain() {

        while (drivetrainIsBusy()) {

            telemetry.addData("motorFront position", motorFront.getCurrentPosition());
            telemetry.addData("motorFrontLeft position", motorLeft.getCurrentPosition());
            telemetry.addData("motorBackRight position", motorRight.getCurrentPosition());
            telemetry.addData("motorBack position", motorBack.getCurrentPosition());

            telemetry.addData("motorFront power", motorFront.getPower());
            telemetry.addData("motorFrontLeft power", motorLeft.getPower());
            telemetry.addData("motorBackRight power", motorRight.getPower());
            telemetry.addData("motorBack power", motorBack.getPower());

            telemetry.update();

        }

        resetDrivetrain();
        opMode.sleep(this.sleepTime);

    }

    // teleOp

    public void teleOp(double drive, double strafe, double turn, boolean turboModeToggle) {

        double frontPower;
        double rightPower;
        double leftPower;
        double backPower;

        double denominator = (Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(turn), 1))/(turboModeToggle ? 0.7 : MAX_TELEOP_SPEED);
        leftPower = (drive + turn) / denominator ;
        backPower = (strafe + turn) / denominator;
        frontPower = (strafe - turn) / denominator;
        rightPower = (drive - turn) / denominator;

        //old charlie code that locks out diagonal movement
//        if (Math.abs(drive) >= Math.abs(strafe)) {
//            frontRightPower = Range.clip(drive - turn, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
//            frontLeftPower  = Range.clip(drive + turn, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
//            backRightPower  = Range.clip(drive - turn, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
//            backLeftPower   = Range.clip(drive + turn, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
//        } else {
//            frontRightPower = Range.clip(-turn - strafe, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
//            frontLeftPower  = Range.clip( turn + strafe, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
//            backRightPower  = Range.clip(-turn + strafe, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
//            backLeftPower   = Range.clip( turn - strafe, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
//        }

        motorFront.setPower(frontPower);
        motorRight.setPower(rightPower);
        motorBack.setPower(backPower);
        motorLeft.setPower(leftPower);

    }

    // helper methods

    public boolean drivetrainIsBusy() {
        return (motorFront.isBusy() || motorLeft.isBusy() || motorRight.isBusy() || motorBack.isBusy());
    }

    public void resetDrivetrain() {

        motorFront.setPower(0);
        motorRight.setPower(0);
        motorLeft.setPower(0);
        motorBack.setPower(0);

        motorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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