package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.lang.Math;

public class RobotClassWithSubsystems extends LinearOpMode {

    // conversions

    static final int DC_MOTOR_COUNTS_PER_REV  = 28;
    static final int DC_MOTOR_GEAR_RATIO      = 20;
    static final int DC_MOTOR_COUNTS_PER_INCH = (int)((DC_MOTOR_COUNTS_PER_REV * DC_MOTOR_GEAR_RATIO) / Math.PI);

    // drivetrain subsystem

    static final int DRIVETRAIN_WHEEL_DIAMETER  = 4;
    static final int DRIVETRAIN_COUNTS_PER_INCH = DC_MOTOR_COUNTS_PER_INCH / DRIVETRAIN_WHEEL_DIAMETER;

    public static DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();

    // servo subsystems

    static final double SERVO_SPEED = 0.002;
    static final double SERVO_TIME = 0.5;

    public static ServoSubsystem servoClaw = new ServoSubsystem();
    public static ServoSubsystem servoRotateClaw = new ServoSubsystem();
    public static ServoSubsystem servoHook = new ServoSubsystem();
    public static ServoSubsystem servoRotateHook = new ServoSubsystem();

    // lift subsystems

    public static final int LIFT_RATIO = 1;
    public static final int LIFT_COUNTS_PER_INCH = DC_MOTOR_COUNTS_PER_INCH / LIFT_RATIO;

    public static final double MAX_LIFT_SPEED = 0.5;

    static String[] horizontalLiftPresetPositionNames = { "transfer", "collect" };
    static double[] horizontalLiftPresetPositions = { 0.0, 18.0 }; // change values (18 -> distance to auto collect)

    static String[] verticalLiftPresetPositionNames = { "transfer", "ground", "low", "middle", "high" };
    static double[] verticalLiftPresetPositions = {0.0, 2.0, 15.0, 25.0, 35.0 };  // change values (2, 15, 25, 35 -> height to score on ground, low, medium, and high junctions)

    public static LiftSubsystem horizontalLift = new LiftSubsystem();
    public static LiftSubsystem verticalLift = new LiftSubsystem();

    // camera subsystem

    public static CameraSubsystem cameraSubsystem = new CameraSubsystem();

    // other variables


    public static String mode = "assist";

    // initialize

    public RobotClassWithSubsystems() {}

    @Override
    public void runOpMode() {}

    public void init(HardwareMap hardwareMap) {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        drivetrain.init(hardwareMap, DRIVETRAIN_COUNTS_PER_INCH);

        servoClaw.init(hardwareMap, "servo_claw", 0.0, 0.2, SERVO_SPEED, SERVO_TIME, false);
        servoRotateClaw.init(hardwareMap, "servo_rotate_claw", 0.0, 0.3, SERVO_SPEED, SERVO_TIME, true);
        servoHook.init(hardwareMap, "servo_hook", 0.0, 1, SERVO_SPEED, SERVO_TIME, false);
        servoRotateHook.init(hardwareMap, "servo_rotate_hook", 0.0, 1, SERVO_SPEED, SERVO_TIME, false);

        horizontalLift.init(hardwareMap, "motor_horizontal_lift", 0, 1000, MAX_LIFT_SPEED, true, LIFT_COUNTS_PER_INCH, horizontalLiftPresetPositionNames, horizontalLiftPresetPositions);
        verticalLift.init(hardwareMap, "motor_vertical_lift", 0, 3300, MAX_LIFT_SPEED, true, LIFT_COUNTS_PER_INCH, verticalLiftPresetPositionNames, verticalLiftPresetPositions);

        cameraSubsystem.init(hardwareMap);

    }

}