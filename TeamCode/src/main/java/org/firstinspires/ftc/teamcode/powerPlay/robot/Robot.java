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

    public static final int LIFT_RATIO = 1;
    public static final int LIFT_COUNTS_PER_INCH = DC_MOTOR_COUNTS_PER_INCH / LIFT_RATIO;

    public static final double MAX_LIFT_SPEED = 0.5;

    static String[] horizontalLiftPresetPositionNames = { "transfer", "collect" };
    static double[] horizontalLiftPresetPositions = { 0.0, 18.0 }; // change values (18 -> distance to auto collect)

    static String[] verticalLiftPresetPositionNames = { "transfer", "ground", "low", "middle", "high" };
    static double[] verticalLiftPresetPositions = {0.0, 2.0, 15.0, 25.0, 35.0 };  // change values (2, 15, 25, 35 -> height to score on ground, low, medium, and high junctions)

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

        drivetrain = new Drivetrain();
        drivetrain.init(hardwareMap, telemetry, DRIVETRAIN_COUNTS_PER_INCH);

        // servos

        servoClaw = new ServoClass();
        servoRotateClaw = new ServoClass();
        servoHook = new ServoClass();
        servoRotateHook = new ServoClass();

        servoClaw.init(hardwareMap, telemetry, "servo_claw", 0.0, 0.2, SERVO_SPEED, SERVO_TIME, false);
        servoRotateClaw.init(hardwareMap, telemetry, "servo_rotate_claw", 0.0, 0.3, SERVO_SPEED, SERVO_TIME, true);
        servoHook.init(hardwareMap, telemetry, "servo_hook", 0.0, 1, SERVO_SPEED, SERVO_TIME, false);
        servoRotateHook.init(hardwareMap, telemetry, "servo_rotate_hook", 0.0, 1, SERVO_SPEED, SERVO_TIME, false);

        // lifts

        horizontalLift = new LiftClass();
        verticalLift = new LiftClass();

        horizontalLift.init(hardwareMap, telemetry, "motor_horizontal_lift", 0, 1000, MAX_LIFT_SPEED, false, LIFT_COUNTS_PER_INCH, horizontalLiftPresetPositionNames, horizontalLiftPresetPositions);
        verticalLift.init(hardwareMap, telemetry, "motor_vertical_lift", 0, 3300, MAX_LIFT_SPEED, true, LIFT_COUNTS_PER_INCH, verticalLiftPresetPositionNames, verticalLiftPresetPositions);

        // camera

        camera = new Camera();
        camera.init(hardwareMap, telemetry);

    }

    public static void reset() {

        telemetry.addLine("resetting the robot's servos and lift");
        telemetry.update();

        servoClaw.runToPosition("open");
        servoRotateClaw.runToPosition("up");
        servoRotateHook.runToPosition("transfer");
        servoHook.runToPosition("retract");

//        verticalLift.runToPosition("transfer");
        horizontalLift.runToPosition("transfer");

    }

}