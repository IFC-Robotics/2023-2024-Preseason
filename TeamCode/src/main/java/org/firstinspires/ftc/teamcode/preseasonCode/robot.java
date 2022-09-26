package org.firstinspires.ftc.teamcode.preseasonCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import java.lang.Math;

public class robot extends LinearOpMode {

    // public = other classes can use the variable
    // static = the variable belongs to this class only
    // final  = the variable is constant

    public DcMotor motorFrontRight;
    public DcMotor motorFrontLeft;
    public DcMotor motorBackRight;
    public DcMotor motorBackLeft;
    public DcMotor motorLinearLift;
    public DcMotor motorCollectorArm;
    public DcMotor motorCollector;
    public DcMotor motorSpinner;

    public Servo servoLinearLift;
    public Servo servoTransfer;
    public CRServo crServoArm;

    public ModernRoboticsI2cRangeSensor leftRangeSensor;
    public ModernRoboticsI2cRangeSensor rightRangeSensor;
    public ModernRoboticsI2cRangeSensor bottomRangeSensor;

    public final int LIFT_MAX_DIST = 1500; // maxDist
    public final int LIFT_MIN_DIST = 0; // minDist
    public final int ARM_MAX_DIST = 1900; // armMax
    public final int ARM_MIN_DIST = 0; // armMin

    HardwareMap hardwareMap;

    public robot(){}

    public void runOpMode(){}

    public void init(HardwareMap hardwareMap) {
 
        motorFrontRight   = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft    = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackRight    = hardwareMap.get(DcMotor.class, "motor_back_right");
        motorBackLeft     = hardwareMap.get(DcMotor.class, "motor_back_left");

        motorLinearLift   = hardwareMap.get(DcMotor.class, "motor_linear_lift");
        motorCollectorArm = hardwareMap.get(DcMotor.class, "motor_arm");
        motorCollector    = hardwareMap.get(DcMotor.class, "motor_collector");
        motorSpinner      = hardwareMap.get(DcMotor.class, "motor_spinner");

        servoLinearLift   = hardwareMap.get(Servo.class, "servo_linear_lift");
        servoTransfer     = hardwareMap.get(Servo.class, "servo_transfer");
        crServoArm        = hardwareMap.get(CRServo.class, "CRServo_linear_lift");

        leftRangeSensor   = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "left_range_sensor");
        rightRangeSensor  = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "right_range_sensor");
        bottomRangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "bottom_range_sensor");

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void setModeForDrivetrain(HardwareMap hardwareMap) {

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void setModeForOtherMotors(HardwareMap hardwareMap) {

        motorLinearLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLinearLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        motorCollectorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorCollectorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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

    // TURN FUNCTION BELOW. Radius = 7.7862 in; Circumference = 48.92217 in = 360 degrees
    // 1120 ticks for 1 rotation

    public void turn(double speed, double angle) {

        double circumference = 66; // 70
        double distance = circumference*(angle/360);
        int intDistrot = (int)inchToTics(distance);

        if (angle < 0) {
            moveDriveTrain(intDistrot, -intDistrot, intDistrot, -intDistrot, speed, -speed, speed, -speed);
        } else if (angle >= 0) {
            moveDriveTrain(intDistrot, -intDistrot, intDistrot, -intDistrot, -speed, speed, -speed, speed);
        }

    }

    public void moveCollectorArm(double speed, String direction) {

        int targetPosition = 0;
        double power = 1;

        if (direction == "up") {
            power = -1;
        } else if (direction == "down") {
            targetPosition = 1900;
        }

        motorCollectorArm.setTargetPosition(targetPosition);
        motorCollectorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorCollectorArm.setPower(power);

        while (motorCollectorArm.isBusy() && opModeIsActive()) {
        }

        motorCollectorArm.setPower(0);
        motorCollectorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public String bestShippingHubLevel() {

        int middleDistance = leftRangeSensor.rawUltrasonic();
        int rightDistance = rightRangeSensor.rawUltrasonic();
        int threshold = 20;

        telemetry.addData("middle distance: ", middleDistance);
        telemetry.addData("right distance: ", rightDistance);

        if (middleDistance < threshold && rightDistance < threshold) {
            double minDistance = Math.min(middleDistance, rightDistance);
            if (minDistance == middleDistance) { return "middle"; }
            if (minDistance == rightDistance)  { return "top"; }
        }
        if (middleDistance < threshold) { return "middle"; }
        if (rightDistance  < threshold) { return "top"; }
        return "bottom";

    }

    public void deposit(String bestShippingHubLevel) {

        // raise linear lift

        int linearLiftDistance = 0;
        double power = 0.5;

        if (bestShippingHubLevel == "down") { power *= -1; }
        else if (bestShippingHubLevel == "top") { linearLiftDistance = 1500; }
        else if (bestShippingHubLevel == "middle") { linearLiftDistance = 1000; }
        else if (bestShippingHubLevel == "bottom") { linearLiftDistance = 900; }

        motorLinearLift.setTargetPosition(linearLiftDistance);
        motorLinearLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLinearLift.setPower(power);

        while (motorLinearLift.isBusy() && opModeIsActive()) {
        }

        motorLinearLift.setPower(0.01);
        motorLinearLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // deposit

        servoLinearLift.setPosition(0.3);
        sleep(2000);
        servoLinearLift.setPosition(0.63);
        sleep(2000);

        // retract linear lift

        motorLinearLift.setTargetPosition(50);
        motorLinearLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLinearLift.setPower(-0.4);

        while (motorLinearLift.isBusy() && opModeIsActive()) {
        }

        motorLinearLift.setPower(0);
        motorLinearLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void transfer() {

        moveCollectorArm(0.5, "up"); // raise collector arm

        servoTransfer.setPosition(0.3); // open transfer
        sleep(750);

        motorCollector.setPower(0.75); // transfer to linear lift
        sleep(750);
        motorCollector.setPower(0);

        servoTransfer.setPosition(0.63); // close transfer

    }

    public void spinCarousel(double speed, int angle) {

        int distraught = (angle/360) * 2117;
        // 564.5 tics per flywheel spin
        // 3.75 ratio to actual carousel
        // 2116.875 tics per carousel spin

        motorSpinner.setTargetPosition(distraught);
        motorSpinner.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (angle < 0) {
            motorSpinner.setPower(-speed);
        } else if (angle >= 0) {
            motorSpinner.setPower(speed);
        }

        while (motorSpinner.isBusy() && opModeIsActive()) {
        }

        motorSpinner.setPower(0);
        motorSpinner.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorSpinner.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public double inchToTics(double inch) {

        int tics = 1129;
        int dia = 4;
        double ratio = 0.64;

        double wheelRot = inch / (Math.PI * dia);
        double motorRot = wheelRot * ratio;
        double finalTic = tics*motorRot;
        return finalTic;

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

        while (motorBackRight.isBusy() && opModeIsActive()) {
        }

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