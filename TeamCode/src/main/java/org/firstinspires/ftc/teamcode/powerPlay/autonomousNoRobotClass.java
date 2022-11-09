package org.firstinspires.ftc.teamcode.powerPlay;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="autonomous competition", group = "PowerPlay")
public class autonomousNoRobotClass extends LinearOpMode {

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    DcMotor motorLift;

    Servo claw;

    double FrontRight;
    double FrontLeft;
    double BackRight;
    double BackLeft;

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode () {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft  = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackLeft   = hardwareMap.get(DcMotor.class, "motor_back_left");
        motorBackRight  = hardwareMap.get(DcMotor.class, "motor_back_right");

        motorLift = hardwareMap.get(DcMotor.class, "motor_lift");

        claw = hardwareMap.get(Servo.class, "servo_claw");

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // code goes here
        strafe(1, 37, -1 );
        drive(1,44,1);
        lift("high junction");
        moveClaw("open");
        lift("ground junction");
        strafe(1,8,1);

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

    public void lift(String target) {

        double speed = 0.5;
        double inches = 0;

        if (target == "high junction") inches = 33.5;
        else if (target == "middle junction") inches = 23.5;
        else if (target == "low junction") inches = 13.5;
        else if (target == "ground junction") inches = 0;

        motorLift.setTargetPosition((int)inchToTics(inches));
        motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLift.setPower(speed);

        while (motorLift.isBusy() && opModeIsActive()) {}

        motorLift.setPower(0);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void moveClaw(String direction) {
        if (direction == "open") claw.setPosition(0.33); // Test this later, we don't know the amount
        if (direction == "close") claw.setPosition(0);
    }

    public double inchToTics(double inches) {

        final double COUNTS_PER_REVOLUTION    = 28.0;
        final double GEAR_RATIO               = 20.0;
        final double WHEEL_DIAMETER_IN_INCHES = 1.0;
        final double COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION * GEAR_RATIO) / (WHEEL_DIAMETER_IN_INCHES * Math.PI);

        return inches * COUNTS_PER_INCH;

//        int tics = 1129;
//        int dia = 4;
//        double ratio = 0.64;
//
//        double wheelRot = inch / (Math.PI * dia);
//        double motorRot = wheelRot * ratio;
//        double finalTic = tics * motorRot;
//        return finalTic;

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