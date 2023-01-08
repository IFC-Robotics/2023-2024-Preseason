package org.firstinspires.ftc.teamcode.powerPlay.oldCode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.powerPlay.robot.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;
import java.lang.Math;

public class RobotClass extends LinearOpMode {

    // COUNTS_PER_INCH variables

    public static final int DC_MOTOR_COUNTS_PER_REV = 28;
    public static final int DC_MOTOR_GEAR_RATIO     = 20;
    public static final int DC_MOTOR_COUNTS         = (int)((DC_MOTOR_COUNTS_PER_REV * DC_MOTOR_GEAR_RATIO) / Math.PI);

    public static final int DRIVETRAIN_WHEEL_DIAMETER  = 4;
    public static final int DRIVETRAIN_COUNTS_PER_INCH = DC_MOTOR_COUNTS / DRIVETRAIN_WHEEL_DIAMETER;

    public static final int LIFT_RATIO           = 1;
    public static final int LIFT_COUNTS_PER_INCH = DC_MOTOR_COUNTS / LIFT_RATIO;

    // drivetrain variables

    public static DcMotor motorFrontRight;
    public static DcMotor motorFrontLeft;
    public static DcMotor motorBackRight;
    public static DcMotor motorBackLeft;

    public static final double AUTONOMOUS_DRIVE_SPEED = 0.3; // to test
    public static final double MAX_TELEOP_DRIVE_SPEED = 0.7; // to test

    // claw variables

    public static Servo servoClaw;
    public static Servo servoRotateClaw;

    public static final double CLAW_OPEN_POSITION = 0.0; // to test
    public static final double CLAW_CLOSE_POSITION = 0.4; // to test

    public static final double CLAW_UP_POSITION = 0.0; // to test
    public static final double CLAW_DOWN_POSITION = 0.3; // to test

    // horizontal lift variables

    public static DcMotor motorHorizontalLift;

    public static final int HORIZONTAL_LIFT_MIN_DIST = 0; // to test
    public static final int HORIZONTAL_LIFT_MAX_DIST = 1000; // to test
    public static final double HORIZONTAL_LIFT_SPEED = 0.5; // to test

    // hook variables

    public static Servo servoHook;
    public static Servo servoRotateHook;

    public static final double HOOK_EXTEND_POSITION = 0.33; // to test
    public static final double HOOK_RETRACT_POSITION = 0.66; // to test

    public static final double ROTATE_HOOK_TRANSFER_POSITION = 0.33; // to test
    public static final double ROTATE_HOOK_DEPOSIT_POSITION = 0.66; // to test

    // vertical lift variables

    public static DcMotor motorVerticalLift;

    public static final int VERTICAL_LIFT_MIN_DIST = 0; // to test
    public static final int VERTICAL_LIFT_MAX_DIST = 3300; // to test
    public static final double VERTICAL_LIFT_SPEED = 0.3; // to test

    // camera vision variables

    int cameraMonitorViewId;
    public OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    double tagsize = 0.166; // measured in meters

    int LEFT = 1; // tag ID from the 36h11 family
    int MIDDLE = 2;
    int RIGHT = 3;

    AprilTagDetection tagOfInterest = null;

    // other variables

    public static boolean assistMode = true;

    public RobotClass(){}

    @Override
    public void runOpMode(){}

    public void init(HardwareMap hardwareMap) {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // drivetrain

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft  = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackLeft   = hardwareMap.get(DcMotor.class, "motor_back_left");
        motorBackRight  = hardwareMap.get(DcMotor.class, "motor_back_right");

        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);

        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // test what this does
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // test what this does
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // test what this does
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // test what this does

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // intake

        servoClaw           = hardwareMap.get(Servo.class, "servo_claw");
        servoRotateClaw     = hardwareMap.get(Servo.class, "servo_rotate_claw");
        motorHorizontalLift = hardwareMap.get(DcMotor.class, "motor_horizontal_lift");

        servoClaw.setDirection(Servo.Direction.REVERSE); // to test
        servoClaw.scaleRange(CLAW_OPEN_POSITION, CLAW_CLOSE_POSITION);

        servoRotateClaw.setDirection(Servo.Direction.REVERSE); // to test
        servoRotateClaw.scaleRange(CLAW_UP_POSITION, CLAW_DOWN_POSITION);

        // add "Warning: Robot Moves On Initialization" sticker (https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/robot-moves-labels.pdf)

        motorHorizontalLift.setDirection(DcMotor.Direction.REVERSE); // to test
        motorHorizontalLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorHorizontalLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // deposit

        servoHook         = hardwareMap.get(Servo.class, "servo_hook");
        servoRotateHook   = hardwareMap.get(Servo.class, "servo_rotate_hook");
        motorVerticalLift = hardwareMap.get(DcMotor.class, "motor_vertical_lift");

        servoHook.setDirection(Servo.Direction.REVERSE); // to test
//        servoHook.setPosition(HOOK_EXTEND_POSITION); // to test
        servoHook.scaleRange(HOOK_EXTEND_POSITION, HOOK_RETRACT_POSITION);

        // add "Warning: Robot Moves On Initialization" sticker (https://www.firstinspires.org/sites/default/files/uploads/resource_library/ftc/robot-moves-labels.pdf)

        servoRotateHook.setDirection(Servo.Direction.REVERSE); // to test
        servoRotateHook.scaleRange(ROTATE_HOOK_TRANSFER_POSITION, ROTATE_HOOK_DEPOSIT_POSITION);

        motorVerticalLift.setDirection(DcMotor.Direction.REVERSE); // to test
        motorVerticalLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorVerticalLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // camera vision initialization

        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() { camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT); }
            @Override
            public void onError(int errorCode) {}
        });

        telemetry.setMsTransmissionInterval(50);

    }

    // driving helper methods

    public void drive(double speed, int inches, int direction) {
        int target = direction * inches * DRIVETRAIN_COUNTS_PER_INCH;
        double power = direction * speed;
        moveDrivetrain(target, target, target, target, power, power, power, power);
    }

    public void strafe(double speed, int inches, int direction) {
        int target = direction * inches * DRIVETRAIN_COUNTS_PER_INCH;
        double power = direction * speed;
        moveDrivetrain(-target, target, target, -target, -power, power, power, -power);
    }

    public void turn(double speed, double angle) { // to test
        int circumference = 66; // what does this even mean? circumference of the wheels? must test
        int inches = (int)(circumference * angle / 360);
        int distance = inches * DRIVETRAIN_COUNTS_PER_INCH;
        double power = Math.signum(angle) * speed;
        moveDrivetrain(distance, -distance, distance, -distance, -power, power, -power, power);
    }

    public void moveDrivetrain(int frontRightTarget, int frontLeftTarget, int backRightTarget, int backLeftTarget, double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower) {

        motorFrontRight.setTargetPosition(frontRightTarget);
        motorFrontLeft.setTargetPosition(frontLeftTarget);
        motorBackRight.setTargetPosition(backRightTarget);
        motorBackLeft.setTargetPosition(backLeftTarget);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontRight.setPower(frontRightPower);
        motorFrontLeft.setPower(frontLeftPower);
        motorBackRight.setPower(backRightPower);
        motorBackLeft.setPower(backLeftPower);

        while ((motorFrontLeft.isBusy() || motorFrontRight.isBusy() || motorBackLeft.isBusy() || motorBackRight.isBusy()) && opModeIsActive()) {}

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

    public void moveClaw(String direction) {
        if (direction == "open")  servoClaw.setPosition(0);
        if (direction == "close") servoClaw.setPosition(1);
    }

    public void rotateClaw(String direction) {
        if (direction == "up")   servoRotateClaw.setPosition(0);
        if (direction == "down") servoRotateClaw.setPosition(1);
    }

    public void moveHorizontalLift(String direction) {

        if (direction == "intake") {
            motorVerticalLift.setTargetPosition(HORIZONTAL_LIFT_MAX_DIST);
        } else if (direction == "transfer") {
            motorVerticalLift.setTargetPosition(HORIZONTAL_LIFT_MIN_DIST);
        }

        motorVerticalLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorVerticalLift.setPower(VERTICAL_LIFT_SPEED);

        while (motorVerticalLift.isBusy() && opModeIsActive()) {}

        motorVerticalLift.setPower(0);
        motorVerticalLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void moveHook(String direction) {
        if (direction == "extend")  servoHook.setPosition(0);
        if (direction == "retract") servoHook.setPosition(1);
    }

    public void rotateHook(String direction) {
        if (direction == "transfer") servoRotateHook.setPosition(0);
        if (direction == "deposit")  servoRotateHook.setPosition(1);
    }

    public void moveVerticalLift(String direction) {

        int tics = 0;

        // values don't correspond w/ LIFT_COUNTS_PER_INCH right now

        if (direction == "high") tics = 3600;
        if (direction == "middle") tics = 2300; // estimate for middle junction
        if (direction == "low") tics = 1000; // doesn't actually go to the low junction
        if (direction == "ground") tics = 500; // estimate for ground junction (the cone should be hovering right above the ground)
        if (direction == "transfer") tics = 0;

        motorVerticalLift.setTargetPosition(tics);
        motorVerticalLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorVerticalLift.setPower(VERTICAL_LIFT_SPEED);

        while (motorVerticalLift.isBusy() && opModeIsActive()) {}

        motorVerticalLift.setPower(0);
        motorVerticalLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    // AprilTag helper methods

    public int getTag() {

        while (!isStarted() && !isStopRequested()) {
            
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            for(AprilTagDetection tag : currentDetections) {
                if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT) {
                    tagOfInterest = tag;
                    break;
                }
            }

            sleep(20);

        }

        if(tagOfInterest != null) {
            telemetry.addData("Tag of interest is in sight! Tag ID", tagOfInterest.id);
            telemetry.update();
            return tagOfInterest.id;
        } else {
            telemetry.addLine("No tag available, it was never sighted during the init loop :(");
            telemetry.update();
            return 0;
        }

    }

}