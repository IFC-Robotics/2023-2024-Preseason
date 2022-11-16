package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="blue ground junction", group = "PowerPlay")
public class blueGroundJunction extends LinearOpMode {

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    Servo servoClaw;

    public void runOpMode () {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft  = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackLeft   = hardwareMap.get(DcMotor.class, "motor_back_left");
        motorBackRight  = hardwareMap.get(DcMotor.class, "motor_back_right");

        servoClaw = hardwareMap.get(Servo.class, "servo_claw");

        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);

        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // code goes here

        final double DRIVE_SPEED = 0.5;

//        moveClaw("close");
        strafe(DRIVE_SPEED, (int)inchToTics(24), -1);
        sleep(100);
        drive(DRIVE_SPEED, (int)inchToTics(13), 1);
        sleep(100);
        moveClaw("open");
        sleep(100);
        drive(DRIVE_SPEED, (int)inchToTics(13), -1);
        sleep(100);
        strafe(DRIVE_SPEED, (int)inchToTics(12), -1);
        sleep(100);
        drive(DRIVE_SPEED, (int)inchToTics(24), 1);
        sleep(100);
//        drive(DRIVE_SPEED, (int)inchToTics(2), 1);
//        moveClaw("open");
//        lift("ground junction");
//        strafe(1, 8, 1);

    }

    public void drive(double speed, int distance, int direction) {
        int target = direction * distance;
        double power = direction * speed;
        moveDriveTrain(target, target, target, target, power, power, power, power);
    }

    public void strafe(double speed, int distance, int direction) {
        int target = direction * distance;
        double power = direction * speed;
        moveDriveTrain(-target, target, target, -target, -power, power, power, -power);
    }

    public void turn(double speed, double angle) {

        double circumference = 66; // what does this even mean? circumference of the wheels? must test
        double distance = circumference*(angle/360);
        int intDistRot = (int)inchToTics(distance);

        if (angle < 0) {
            moveDriveTrain(intDistRot, -intDistRot, intDistRot, -intDistRot, speed, -speed, speed, -speed);
        } else if (angle >= 0) {
            moveDriveTrain(intDistRot, -intDistRot, intDistRot, -intDistRot, -speed, speed, -speed, speed);
        }

    }


    public void moveClaw(String direction) {
        if (direction == "open") servoClaw.setPosition(0.33); // Test this later, we don't know the amount
        if (direction == "close") servoClaw.setPosition(0);
    }

    public double inchToTics(double inches) {

        final double COUNTS_PER_REVOLUTION    = 28.0;
        final double GEAR_RATIO               = 20.0;
        final double WHEEL_DIAMETER_IN_INCHES = 4.0;
        final double COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION * GEAR_RATIO) / (WHEEL_DIAMETER_IN_INCHES * Math.PI);

        return inches * COUNTS_PER_INCH;

    }

    public void moveDriveTrain(int frontRightTarget, int frontLeftTarget, int backRightTarget, int backLeftTarget, double frontRightSpeed, double frontLeftSpeed, double backRightSpeed, double backLeftSpeed) {

        motorFrontRight.setTargetPosition(frontRightTarget);
        motorFrontLeft.setTargetPosition(frontLeftTarget);
        motorBackRight.setTargetPosition(backRightTarget);
        motorBackLeft.setTargetPosition(backLeftTarget);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontRight.setPower(frontRightSpeed);
        motorFrontLeft.setPower(frontLeftSpeed);
        motorBackRight.setPower(backRightSpeed);
        motorBackLeft.setPower(backLeftSpeed);

        while (motorBackRight.isBusy() && opModeIsActive()) {}

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

}