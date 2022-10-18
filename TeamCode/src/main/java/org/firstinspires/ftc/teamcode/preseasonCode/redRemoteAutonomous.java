package org.firstinspires.ftc.teamcode.preseasonCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;

@Autonomous(name="Red Remote Autonomous")
@Disabled
public class redRemoteAutonomous extends LinearOpMode {

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    DcMotor motorLinearLift;
    DcMotor motorCollectorArm;
    DcMotor motorCollector;
    DcMotor motorSpinner;

    Servo servoLinearLift;
    Servo servoTransfer;
    CRServo crServoArm;

    ModernRoboticsI2cRangeSensor leftRangeSensor;
    ModernRoboticsI2cRangeSensor rightRangeSensor;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        motorFrontRight   = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft    = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackLeft     = hardwareMap.get(DcMotor.class, "motor_back_left");
        motorBackRight    = hardwareMap.get(DcMotor.class, "motor_back_right");
        motorLinearLift   = hardwareMap.get(DcMotor.class, "motor_linear_lift");
        motorCollector    = hardwareMap.get(DcMotor.class, "motor_collector");
        motorCollectorArm = hardwareMap.get(DcMotor.class, "motor_arm");
        motorSpinner      = hardwareMap.get(DcMotor.class, "motor_spinner");
        
        servoLinearLift   = hardwareMap.get(Servo.class, "servo_linear_lift");
        servoTransfer     = hardwareMap.get(Servo.class, "servo_transfer");
        crServoArm        = hardwareMap.get(CRServo.class, "CRServo_linear_lift");

        leftRangeSensor   = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "left_range_sensor");
        rightRangeSensor  = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "right_range_sensor");

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        runtime.reset();

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLinearLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLinearLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorLinearLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLinearLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorCollectorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorCollectorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double DRIVE_SPEED = 0.5;
        double TURN_SPEED = 0.55;

        drive(DRIVE_SPEED, 600, -1); // move forward

        String bestShippingHubLevel = bestShippingHubLevel(); // measure correct barcode
        telemetry.addData("Best Shipping Hub Level: ", bestShippingHubLevel);
        telemetry.update();

        strafe(DRIVE_SPEED, 900, -1); // strafe right to be in front of shipping hub

        if (bestShippingHubLevel == "bottom") {
            drive(DRIVE_SPEED, 150, 1); // drive away from shipping hub
        }

        deposit(bestShippingHubLevel); // deposit freight in shipping hub

        if (bestShippingHubLevel == "bottom") {
            drive(DRIVE_SPEED, 150, 1); // drive away from shipping hub
        } else {
            drive(DRIVE_SPEED, 300, 1); // drive away from shipping hub
        }

        int turnToDuckDist = 0;
        int driveToDuckDist = 0;
        double curveFactor = 1;
        int turnToShippingHubDist = 0;
        int driveToShippingHubDist = 0;
        int driveToShippingHubPower = 0;
        int turnToWarehouseDist = 0;
        int strafeToWarehouseDist = 0;

        if (bestShippingHubLevel == "top") {
            turnToDuckDist = 120;
            driveToDuckDist = 900;
            curveFactor = 1.5;
            turnToShippingHubDist = 50;
            driveToShippingHubDist = 100;
            driveToShippingHubPower = -1;
            turnToWarehouseDist = 41;
            strafeToWarehouseDist = 500;
        } else if (bestShippingHubLevel == "middle") {
            turnToDuckDist = 120;
            driveToDuckDist = 800;
            curveFactor = 1.4;
            turnToShippingHubDist = 60;
            turnToWarehouseDist = 43;
            strafeToWarehouseDist = 400;
        } else if (bestShippingHubLevel == "bottom") {
            turnToDuckDist = 120;
            driveToDuckDist = 540;
            curveFactor = 1.1;
            turnToShippingHubDist = 90;
            driveToShippingHubDist = 60;
            driveToShippingHubPower = 1;
            turnToWarehouseDist = 57;
            strafeToWarehouseDist = 250;
        }

        turn(TURN_SPEED, turnToDuckDist); // turn to align with other TSE (duck)

        moveCollectorArm(0.5, 2050, 1); // lower collector arm

        motorCollector.setPower(0.4); // start collector
        curve(DRIVE_SPEED, DRIVE_SPEED * curveFactor, driveToDuckDist, 1); // drive toward duck
        sleep(1000); // wait while we collect duck
        motorCollector.setPower(0); // stop collector

//        motorCollector.setTargetPosition(something);
//        motorCollector.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorCollector.setPower(0.4);
//        curve(DRIVE_SPEED, DRIVE_SPEED * curveFactor, driveToDuckDist, 1); // drive toward duck
//        while (motorCollector.isBusy() && opModeIsActive()) {
//        }
//        motorCollector.setPower(0);
//        motorCollector.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        transfer(); // transfer duck to linear lift

        turn(TURN_SPEED, -turnToShippingHubDist); // turn to face shipping hub

        if (bestShippingHubLevel != "middle") {
            drive(DRIVE_SPEED, driveToShippingHubDist, driveToShippingHubPower); // drive to shipping hub
        }
        deposit("top"); // deposit duck in top level of shipping hub

        drive(DRIVE_SPEED, 100, 1); // back up
        turn(TURN_SPEED, turnToWarehouseDist); // turn towards warehouse
        strafe(DRIVE_SPEED, strafeToWarehouseDist, 1); // strafe to center of warehouse
        drive(1, 1800, 1); // drive over barriers into warehouse

//        turn(TURN_SPEED, -45); // turn to face freight
//        moveCollectorArm(0.5, 1900, 1); // lower collector arm
//        motorCollector.setPower(1); // start collector
//        drive(DRIVE_SPEED, 400, 1); // drive toward freight
//        sleep(1000); // wait while we collect freight
//        motorCollector.setPower(0); // stop collector
//
//        transfer(); // transfer freight to linear lift
//
//        drive(DRIVE_SPEED, 400, -1); // drive back
//        turn(TURN_SPEED, 45); // align with barriers
//        drive(DRIVE_SPEED, 1200, 1); // exit warehouse
//
//        turn(TURN_SPEED, -45); // turn to face shipping hub
//        drive(DRIVE_SPEED, 200, -1); // drive to shipping hub
//        deposit("top"); // deposit freight
//
//        drive(DRIVE_SPEED, 100, 1); // back up
//        turn(TURN_SPEED, 60); // turn towards warehouse
//        drive(DRIVE_SPEED, 1200, 1); // drive over barriers into warehouse

    }

    public void drive(double speed, int distance, int direction) {

        int target = direction * distance;
        double power = direction * speed;
        moveDriveTrain(target, target, target, target, power, power, power, power);

    }

    public void curve(double speed1, double speed2, int distance, int direction) {

        int target = direction * distance;
        double power1 = direction * speed1;
        double power2 = direction * speed2;
        moveDriveTrain(target, (int)(target*power2/power1), target, (int)(target*power2/power1), power1, power2, power1, power2);

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

    public void moveCollectorArm(double speed, int distance, int direction) {

        motorCollectorArm.setTargetPosition(distance * direction);
        motorCollectorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorCollectorArm.setPower(speed * direction);

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
        double power = 0.75;

        if (bestShippingHubLevel == "down") { power *= -1; }
        else if (bestShippingHubLevel == "top") { linearLiftDistance = 1500; }
        else if (bestShippingHubLevel == "middle") { linearLiftDistance = 1000; }
        else if (bestShippingHubLevel == "bottom") { linearLiftDistance = 1000; }

        motorLinearLift.setTargetPosition(linearLiftDistance);
        motorLinearLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLinearLift.setPower(power);

        while (motorLinearLift.isBusy() && opModeIsActive()) {
        }

        motorLinearLift.setPower(0.01);
        motorLinearLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // deposit

        servoLinearLift.setPosition(0.2);
        sleep(1900);
        servoLinearLift.setPosition(0.63);
        sleep(1000);

        // retract linear lift

        motorLinearLift.setTargetPosition(50);
        motorLinearLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLinearLift.setPower(-0.3);

        while (motorLinearLift.isBusy() && opModeIsActive()) {
        }

        motorLinearLift.setPower(0);
        motorLinearLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void transfer() {

        moveCollectorArm(0.5, 0, -1); // raise collector arm

        servoTransfer.setPosition(0.3); // open transfer
        sleep(750);

        motorCollector.setPower(0.75); // transfer to linear lift
        sleep(750);
        motorCollector.setPower(0);

        servoTransfer.setPosition(0.63); // close transfer


    }

    public void spinCarousel(double speed, int angle) {

        int distraught = (angle/360) * 2117;
        // 564.5 tics per compliant wheel spin
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