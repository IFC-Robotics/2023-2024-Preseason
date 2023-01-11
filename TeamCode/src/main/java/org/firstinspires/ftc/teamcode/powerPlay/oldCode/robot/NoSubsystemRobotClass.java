package org.firstinspires.ftc.teamcode.powerPlay.oldCode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import com.qualcomm.robotcore.util.Range;
import java.util.ArrayList;
import java.lang.Math;

public class NoSubsystemRobotClass extends LinearOpMode {

    // DcMotor variables

    public final int DC_MOTOR_COUNTS_PER_REV = 28;
    public final int DC_MOTOR_GEAR_RATIO     = 20;
    public final int DC_MOTOR_COUNTS         = (int)((DC_MOTOR_COUNTS_PER_REV * DC_MOTOR_GEAR_RATIO) / Math.PI);

    // drivetrain variables

    public DcMotor motorFrontRight;
    public DcMotor motorFrontLeft;
    public DcMotor motorBackRight;
    public DcMotor motorBackLeft;

    public double AUTONOMOUS_DRIVE_SPEED = 0.3;
    public double AUTONOMOUS_TURN_SPEED = 0.3;
    public final double MAX_TELEOP_DRIVE_SPEED = 0.7;

    public final int DRIVETRAIN_WHEEL_DIAMETER  = 4;
    public final int DRIVETRAIN_COUNTS_PER_INCH = DC_MOTOR_COUNTS / DRIVETRAIN_WHEEL_DIAMETER;

//    // lift variables
//
//    public DcMotor motorLift;
//
//    public final double MAX_LIFT_HEIGHT = 3300.0;
//    public final double MIN_LIFT_HEIGHT = 0.0;
//    public final double LIFT_SPEED = 0.3;
//
//    public final int LIFT_RATIO           = 1;
//    public final int LIFT_COUNTS_PER_INCH = DC_MOTOR_COUNTS / LIFT_RATIO;
//
//    // claw variables
//
//    public Servo servoClaw;
//    public DcMotor motorClaw;
//
//    public final double MIN_CLAW_POSITION = 0;
//    public final double MAX_CLAW_POSITION = 0.4;
//    public final double CLAW_SPEED = 1.0;
//
//    public final int MIN_ROTATION_CLAW_POSITION = 0; // names to be fixed later
//    public final int MAX_ROTATION_CLAW_POSITION = 1000; // to be tested (this value is in tics)
//    public final double ROTATION_CLAW_SPEED = 1.0;
//
//    // hook variables
//
//    public Servo servoHook;
//    public Servo servoRotationHook;
//
//    public final double MIN_HOOK_POSITION = 0;
//    public final double MAX_HOOK_POSITION = 1;
//
//    public final double MIN_ROTATION_HOOK_POSITION = 0.5; // to test later
//    public final double MAX_ROTATION_HOOK_POSITION = 1;
//
//    // camera
//
//    public OpenCvCamera camera;
//    AprilTagDetectionPipeline aprilTagDetectionPipeline;
//
//    double fx = 578.272;
//    double fy = 578.272;
//    double cx = 402.145;
//    double cy = 221.506;
//
//    double tagsize = 0.166; // measured in meters
//
//    int LEFT = 1; // tag ID from the 36h11 family
//    int MIDDLE = 2;
//    int RIGHT = 3;
//
//    public AprilTagDetection tagOfInterest = null;
//
//    // other
//
//    public String mode = "assist";

    public NoSubsystemRobotClass() {}

    @Override
    public void runOpMode() {}

    public void init(HardwareMap hardwareMap) {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // drivetrain

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft  = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackRight  = hardwareMap.get(DcMotor.class, "motor_back_right");
        motorBackLeft   = hardwareMap.get(DcMotor.class, "motor_back_left");

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//        // servos + lifts (unfinished)
//
//        motorLift = hardwareMap.get(DcMotor.class, "motor_horizontal_lift");
//        motorClaw = hardwareMap.get(DcMotor.class, "motor_vertical_lift");
//
//        servoClaw         = hardwareMap.get(Servo.class, "servo_claw"); // consider reversing the directions of these servos
//        servoHook         = hardwareMap.get(Servo.class, "servo_hook");
//        servoRotationHook = hardwareMap.get(Servo.class, "servo_rotation_hook");
//
//        motorLift.setDirection(DcMotor.Direction.REVERSE);
//        servoClaw.setDirection(Servo.Direction.REVERSE);
//         motorClaw.setDirection(DcMotor.Direction.REVERSE);
//         servoHook.setDirection(Servo.Direction.REVERSE);
//         servoRotationHook.setDirection(Servo.Direction.REVERSE);
//
//        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        motorClaw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorClaw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        // camera
//
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
//        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
//
//        camera.setPipeline(aprilTagDetectionPipeline);
//        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
//            @Override
//            public void onOpened() { camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT); }
//            @Override
//            public void onError(int errorCode) {}
//        });
//
//        telemetry.setMsTransmissionInterval(50);

    }

    // driving helper methods

    public void drive(double distance) {

        telemetry.addData("drive distance", distance);

        int target = (int)(distance * DRIVETRAIN_COUNTS_PER_INCH);
        double power = Math.signum(distance) * AUTONOMOUS_DRIVE_SPEED;

        moveDrivetrain(target, target, target, target, power, power, power, power);

    }

    public void strafe(double distance) {

        telemetry.addData("strafe distance", distance);

        int target = (int)(distance * DRIVETRAIN_COUNTS_PER_INCH);
        double power = Math.signum(distance) * AUTONOMOUS_DRIVE_SPEED;

        moveDrivetrain(-target, target, target, -target, -power, power, power, -power);

    }

    public void strafe2(double inches) {

        final int DC_MOTOR_COUNTS_PER_REV = 28;
        final int DC_MOTOR_GEAR_RATIO     = 20;
        final int DC_MOTOR_COUNTS         = (int)((DC_MOTOR_COUNTS_PER_REV * DC_MOTOR_GEAR_RATIO) / Math.PI);
        final int DRIVETRAIN_WHEEL_DIAMETER  = 4;
        final int DRIVETRAIN_COUNTS_PER_INCH = DC_MOTOR_COUNTS / DRIVETRAIN_WHEEL_DIAMETER;

        int target = (int) (inches * DRIVETRAIN_COUNTS_PER_INCH);
        double power = Math.signum(inches) * 0.3;

        moveDrivetrain(-target, target, target, -target, -power, power, power, -power);

    }

//    public void turn(double angle) { // to test
//
//        telemetry.addData("turn angle", angle);
//
//        double WHEEL_RADIUS = 2.0;
//        double circumference = 2 * Math.PI * WHEEL_RADIUS;
//        double distance = circumference * angle / 360;
//
//        int target = (int)(distance * DRIVETRAIN_COUNTS_PER_INCH);
//        double power = Math.signum(angle) * AUTONOMOUS_TURN_SPEED;
//
//        moveDrivetrain(target, -target, target, -target, -power, power, -power, power);
//
//    }

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

        while (motorBackLeft.isBusy() && opModeIsActive()) {}

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

    public void drivingTeleOp(double drive, double strafe, double turn) {

        telemetry.addData("driving teleOp", "");

        double frontRightPower = Range.clip(drive - turn - strafe, -MAX_TELEOP_DRIVE_SPEED, MAX_TELEOP_DRIVE_SPEED);
        double frontLeftPower  = Range.clip(drive + turn + strafe, -MAX_TELEOP_DRIVE_SPEED, MAX_TELEOP_DRIVE_SPEED);
        double backRightPower  = Range.clip(drive - turn + strafe, -MAX_TELEOP_DRIVE_SPEED, MAX_TELEOP_DRIVE_SPEED);
        double backLeftPower   = Range.clip(drive + turn - strafe, -MAX_TELEOP_DRIVE_SPEED, MAX_TELEOP_DRIVE_SPEED);

        motorFrontRight.setPower(frontRightPower);
        motorFrontLeft.setPower(frontLeftPower);
        motorBackRight.setPower(backRightPower);
        motorBackLeft.setPower(backLeftPower);

    }

    // scoring helper methods

//    public void moveClaw(String direction) {
//        if (direction == "open")  servoClaw.setPosition(0); // MIN_CLAW_PSITION
//        if (direction == "close") servoClaw.setPosition(0.4); // MAX_CLAW_PSITION
//    }
//
//    public void rotateClaw(String direction) {
//
//        int tics = 0;
//
//        if (direction == "collect")  tics = 0; // MIN_ROTATION_CLAW_POSITION
//        if (direction == "transfer") tics = 1000; // MAX_ROTATION_CLAW_POSITION
//
//        motorClaw.setTargetPosition(tics);
//        motorClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorClaw.setPower(1); // ROTATION_CLAW_SPEED
//
//        while (motorClaw.isBusy() && opModeIsActive()) {}
//
//        motorClaw.setPower(0);
//        motorClaw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//    }
//
//    public void moveHook(String direction) {
//        if (direction == "extend")  servoHook.setPosition(0); // MIN_HOOK_POSITION
//        if (direction == "retract") servoHook.setPosition(1); // MAX_HOOK_POSITION
//    }
//
//    public void rotateHook(String direction) {
//        if (direction == "transfer") servoRotationHook.setPosition(0.5); // MIN_ROTATION_HOOK_POSITION
//        if (direction == "deposit")  servoRotationHook.setPosition(1); // MAX_ROTATION_HOOK_POSITION
//    }
//
//    public void lift(String direction) {
//
//        int tics = 0;
//
//        // values don't correspond w/ LIFT_COUNTS_PER_INCH right now
//
//        if (direction == "high") tics = 3600;
//        if (direction == "middle") tics = 2300; // estimate for middle junction
//        if (direction == "low") tics = 1000; // doesn't actually go to the low junction
//        if (direction == "ground") tics = 500; // estimate for ground junction (the cone should be hovering right above the ground)
//        if (direction == "transfer") tics = 0; // for picking up cones on the ground
//
//        motorLift.setTargetPosition(tics);
//        motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorLift.setPower(LIFT_SPEED);
//
//        while (motorLift.isBusy() && opModeIsActive()) {}
//
//        motorLift.setPower(0);
//        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//    }
//
//     camera
//
//    public int getAprilTag() {
//
//        telemetry.addData("getTag", "");
//
//        while (!isStarted() && !isStopRequested()) {
//
//            telemetry.addData("searching for tag...", "");
//
//            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();
//
//            for(AprilTagDetection tag : currentDetections) {
//                if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT) {
//                    tagOfInterest = tag;
//                    break;
//                }
//            }
//
//            sleep(20);
//
//        }
//
//        if(tagOfInterest != null) {
//            telemetry.addData("Tag ID", tagOfInterest.id);
//            return tagOfInterest.id;
//        } else {
//            telemetry.addData("No tag available", "");
//            return 0;
//        }
//
//    }

}