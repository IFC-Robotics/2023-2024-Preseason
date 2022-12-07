package org.firstinspires.ftc.teamcode.powerPlay;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.powerPlay.teleOp.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

/*

- test turn method
- test linear lift method w/ count-to-inch conversion factor
- learn how to make methods asynchronous

*/

public class robotClass extends LinearOpMode {

    // DcMotor variables

    public static final int DC_MOTOR_COUNTS_PER_REV = 28;
    public static final int DC_MOTOR_GEAR_RATIO     = 20;
    public static final int DC_MOTOR_COUNTS         = (int)((DC_MOTOR_COUNTS_PER_REV * DC_MOTOR_GEAR_RATIO) / Math.PI);

    // drivetrain variables

    public static DcMotor motorFrontRight;
    public static DcMotor motorFrontLeft;
    public static DcMotor motorBackRight;
    public static DcMotor motorBackLeft;

    public static final double DRIVE_SPEED = 0.3;
    public static final double MAX_DRIVE_SPEED = 0.7;

    public static final int DRIVETRAIN_WHEEL_DIAMETER  = 4;
    public static final int DRIVETRAIN_COUNTS_PER_INCH = DC_MOTOR_COUNTS / DRIVETRAIN_WHEEL_DIAMETER;

    // lift variables

    public static DcMotor motorLift;

    public static final double MAX_LIFT_HEIGHT = 3300.0;
    public static final double MIN_LIFT_HEIGHT = 0.0;
    public static final double LIFT_SPEED = 0.3;

    public static final int LIFT_RATIO           = 1;
    public static final int LIFT_COUNTS_PER_INCH = DC_MOTOR_COUNTS / LIFT_RATIO;

    // claw variables

    public static Servo servoClaw;

    public static final double MIN_CLAW_POSITION = 0;
    public static final double MAX_CLAW_POSITION = 0.4;
    public static final double CLAW_SPEED = 0.002;

    // camera vision variables

    public OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    //  - Units are pixels
    //  - This calibration is for the C920 webcam at 800x448. You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    //  Tag ID 1, 2, 3 from the 36h11 family
    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;

    AprilTagDetection tagOfInterest = null;

    // other

    HardwareMap hardwareMap;

    public robotClass(){}

    @Override
    public void runOpMode(){}

    public void init(HardwareMap hardwareMap) {

//        hardwareMap = hardwareMap;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft  = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackLeft   = hardwareMap.get(DcMotor.class, "motor_back_left");
        motorBackRight  = hardwareMap.get(DcMotor.class, "motor_back_right");

        motorLift = hardwareMap.get(DcMotor.class, "motor_lift");
        servoClaw = hardwareMap.get(Servo.class, "servo_claw");

        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);

        motorLift.setDirection(DcMotor.Direction.REVERSE);
        servoClaw.setDirection(Servo.Direction.REVERSE);

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

        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // camera vision initialization

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() { camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT); }
            @Override
            public void onError(int errorCode) {}
        });

        telemetry.setMsTransmissionInterval(50);

    }

    // driving helper methods

    public void drive(double speed, int inches, int direction) {
        int target = direction * inches * DRIVETRAIN_COUNTS_PER_INCH;
        double power = direction * speed;
        moveDriveTrain(target, target, target, target, power, power, power, power);
    }

    public void strafe(double speed, int inches, int direction) {
        int target = direction * inches * DRIVETRAIN_COUNTS_PER_INCH;
        double power = direction * speed;
        moveDriveTrain(-target, target, target, -target, -power, power, power, -power);
    }

    public void turn(double speed, double angle) {

        int circumference = 66; // what does this even mean? circumference of the wheels? must test
        int inches = (int)(circumference * angle / 360);
        int intDistRot = inches * DRIVETRAIN_COUNTS_PER_INCH;

        if (angle < 0) {
            moveDriveTrain(intDistRot, -intDistRot, intDistRot, -intDistRot, speed, -speed, speed, -speed);
        } else if (angle >= 0) {
            moveDriveTrain(intDistRot, -intDistRot, intDistRot, -intDistRot, -speed, speed, -speed, speed);
        }

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

    // scoring helper methods

    public void lift(String target) {

        int tics = 0;

        // values don't correspond w/ LIFT_COUNTS_PER_INCH right now

        if (target == "high junction") tics = 3600;
        if (target == "middle junction") tics = 2300; // estimate for middle junction
        if (target == "low junction") tics = 1000; // doesn't actually go to the low junction
        if (target == "ground junction") tics = 0;

        motorLift.setTargetPosition(tics);
        motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLift.setPower(LIFT_SPEED);

        while (motorLift.isBusy() && opModeIsActive()) {}

        motorLift.setPower(0);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void moveClaw(String direction) {
        if (direction == "open")  servoClaw.setPosition(MIN_CLAW_POSITION);
        if (direction == "close") servoClaw.setPosition(MAX_CLAW_POSITION);
    }

    // camera vision helper methods

    public void waitForStart() {

        while (!isStarted() && !isStopRequested()) {
            getAprilTagDetections();
        }

        if(tagOfInterest != null) {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        } else {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }

    }

    public void getAprilTagDetections() {

        ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

        if(currentDetections.size() != 0) {

            boolean tagFound = false;

            for(AprilTagDetection tag : currentDetections) {
                if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT) {
                    tagOfInterest = tag;
                    tagFound = true;
                    break;
                }
            }

            if(tagFound) {
                telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                tagToTelemetry(tagOfInterest);
            } else {

                telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null) {
                    telemetry.addLine("(The tag has never been seen)");
                } else {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

        } else {

            telemetry.addLine("Don't see tag of interest :(");

            if(tagOfInterest == null) {
                telemetry.addLine("(The tag has never been seen)");
            } else {
                telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                tagToTelemetry(tagOfInterest);
            }

        }

        telemetry.update();
        sleep(20);

    }

    public void tagToTelemetry(@NonNull AprilTagDetection detection) {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x * FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y * FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z * FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }

    public void parkInCorrectZone(int direction) {

        if (tagOfInterest == null || tagOfInterest.id == 1) {
            strafe(1, 14, direction);
        } else if (tagOfInterest.id == 2) {
            strafe(1, 28, direction);
        } else if (tagOfInterest.id == 3) {
            strafe(1, 42, direction);
        }

    }

}