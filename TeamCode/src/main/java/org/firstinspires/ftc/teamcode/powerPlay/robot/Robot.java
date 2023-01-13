package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import static java.lang.Thread.sleep;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.Math;

public class Robot {

    // conversions

    static final int DC_MOTOR_COUNTS_PER_REV  = 28;
    static final int DC_MOTOR_GEAR_RATIO      = 20;
    static final int DC_MOTOR_COUNTS_PER_INCH = (int)((DC_MOTOR_COUNTS_PER_REV * DC_MOTOR_GEAR_RATIO) / Math.PI);

    // drivetrain

    static final int DRIVETRAIN_WHEEL_DIAMETER  = 4;
    static final int DRIVETRAIN_COUNTS_PER_INCH = DC_MOTOR_COUNTS_PER_INCH / DRIVETRAIN_WHEEL_DIAMETER;

    public static Drivetrain drivetrain;

    // servos

    static final double SERVO_SPEED = 0.002;
    static final double SERVO_TIME = 0.5;

    public static ServoClass servoClaw;
    public static ServoClass servoRotateClaw;
    public static ServoClass servoHook;
    public static ServoClass servoRotateHook;

    // lifts

    public static final double LIFT_RATIO = 1.5;
    public static final double LIFT_COUNTS_PER_INCH = DC_MOTOR_COUNTS_PER_INCH / LIFT_RATIO;

    public static final double MAX_LIFT_SPEED = 0.5; // 0.8

    public static LiftClass horizontalLift;
    public static LiftClass verticalLift;

    // camera

    public static int tag = 0;
    public static Camera camera;

    // other variables

    public static Telemetry telemetry;
    public static String side = "";
    public static String mode = "assist";

    // initialize

    public Robot() {}

    public static void init(HardwareMap hardwareMap, Telemetry telemetryParameter) {

        telemetry = telemetryParameter;

        // drivetrain

        telemetry.addLine("initializing drivetrain");
        telemetry.update();

        drivetrain = new Drivetrain();
        drivetrain.init(hardwareMap, telemetry, DRIVETRAIN_COUNTS_PER_INCH);

        // servos

        telemetry.addLine("initializing servos");
        telemetry.update();

        servoClaw = new ServoClass();
        servoRotateClaw = new ServoClass();
        servoHook = new ServoClass();
        servoRotateHook = new ServoClass();

        servoClaw.init(hardwareMap, telemetry, "servo_claw", 0.33, 0.67, SERVO_SPEED, SERVO_TIME, false);
        servoRotateClaw.init(hardwareMap, telemetry, "servo_rotate_claw", 0.0, 0.3, SERVO_SPEED, SERVO_TIME, true);
        servoHook.init(hardwareMap, telemetry, "servo_hook", 0.0, 0.1, SERVO_SPEED, SERVO_TIME, false);
        servoRotateHook.init(hardwareMap, telemetry, "servo_rotate_hook", 0, 1, SERVO_SPEED, SERVO_TIME, true);

        // lifts

        telemetry.addLine("initializing lifts");
        telemetry.update();

        horizontalLift = new LiftClass();
        verticalLift = new LiftClass();

        horizontalLift.init(hardwareMap, telemetry, "motor_horizontal_lift", MAX_LIFT_SPEED, LIFT_COUNTS_PER_INCH);
        verticalLift.init(hardwareMap, telemetry, "motor_vertical_lift", MAX_LIFT_SPEED, LIFT_COUNTS_PER_INCH);

        // camera

        telemetry.addLine("initializing camera");
        telemetry.update();

        camera = new Camera();
        camera.init(hardwareMap, telemetry);

        telemetry.addLine("finished initializing robot class");
        telemetry.update();

    }

    public static void reset() {

        telemetry.addLine("resetting servos + lifts");
        telemetry.update();

        servoClaw.runToPosition("open");
        servoRotateClaw.runToPosition("up");
        servoHook.runToPosition("retract");
        servoRotateHook.runToPosition("transfer");

        verticalLift.autonomousRunToPosition("transfer");
        horizontalLift.autonomousRunToPosition("transfer");

    }

}