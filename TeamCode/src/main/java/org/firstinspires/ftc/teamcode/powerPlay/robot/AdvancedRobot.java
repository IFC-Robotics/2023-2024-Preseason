package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.lang.Math;

public class AdvancedRobot {

    // conversions

    static final int DC_MOTOR_COUNTS_PER_REV  = 28;
    static final int DC_MOTOR_GEAR_RATIO      = 20;
    static final int DC_MOTOR_COUNTS_PER_INCH = (int)((DC_MOTOR_COUNTS_PER_REV * DC_MOTOR_GEAR_RATIO) / Math.PI); // (int)(28 * 20 / pi) = (int)(178.253536) = 178

    // drivetrain

    static final int DRIVETRAIN_WHEEL_DIAMETER  = 4;
    static final int DRIVETRAIN_COUNTS_PER_INCH = DC_MOTOR_COUNTS_PER_INCH / DRIVETRAIN_WHEEL_DIAMETER;

    public static Drivetrain drivetrain;

    // servos

    static final double SERVO_SPEED = 0.002;
    static final double SERVO_TIME = 0.5;

    public static AdvancedServoClass servoClaw;
    public static AdvancedServoClass servoRotateClaw;
    public static AdvancedServoClass servoHook;
    public static AdvancedServoClass servoRotateHook;

    // lifts

    public static final double LIFT_RATIO = 1.5;
    public static final double LIFT_COUNTS_PER_INCH = DC_MOTOR_COUNTS_PER_INCH / LIFT_RATIO; // 178 / 1.5 = 118.67

    public static final double MAX_LIFT_SPEED = 0.7;

    public static AdvancedLiftClass horizontalLift;
    public static AdvancedLiftClass verticalLift;

    // camera

    public static int tag = 0;
    public static Camera camera;

    // other variables

    public static Telemetry telemetry;
    public static String side = "";
    public static String mode = "assist";

    // initialize

    public AdvancedRobot() {}

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
        servoClaw = new AdvancedServoClass();
        servoRotateClaw = new AdvancedServoClass();
        servoHook = new AdvancedServoClass();
        servoRotateHook = new AdvancedServoClass();

        servoClaw.init(hardwareMap, telemetry, "servo_claw", 0.47, 0.67, SERVO_SPEED, SERVO_TIME, false);
        servoRotateClaw.init(hardwareMap, telemetry, "servo_rotate_claw", 0.0, 1.0, SERVO_SPEED + 0.001, SERVO_TIME, true);
        servoHook.init(hardwareMap, telemetry, "servo_hook", 0.06, 0.16, SERVO_SPEED, SERVO_TIME, true);
        servoRotateHook.init(hardwareMap, telemetry, "servo_rotate_hook", 0.05, 0.05, SERVO_SPEED, SERVO_TIME, true);

        // lifts

        telemetry.addLine("initializing lifts");
        telemetry.update();

        horizontalLift = new AdvancedLiftClass();
        verticalLift = new AdvancedLiftClass();

        horizontalLift.init(hardwareMap, telemetry, "motor_horizontal_lift", MAX_LIFT_SPEED, LIFT_COUNTS_PER_INCH);
        verticalLift.init(hardwareMap, telemetry, "motor_vertical_lift", MAX_LIFT_SPEED, LIFT_COUNTS_PER_INCH);

        // camera

        telemetry.addLine("initializing camera");
        telemetry.update();

        camera = new Camera();
//        camera.init(hardwareMap, telemetry);
//
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