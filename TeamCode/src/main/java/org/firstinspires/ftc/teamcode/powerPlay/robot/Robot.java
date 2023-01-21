package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.lang.Math;

public class Robot {

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
    static final double SERVO_TIME = 500;

    public static ServoClass servoHook;

    // lifts

    public static final double LIFT_RATIO = 1.5;
    public static final double LIFT_COUNTS_PER_INCH = DC_MOTOR_COUNTS_PER_INCH / LIFT_RATIO; // 178 / 1.5 = 118.67

    public static final double MAX_LIFT_SPEED = 0.8;

    public static LiftClass verticalLift;

    // camera

    public static int tag = 0;
    public static Camera camera;

    // other variables

    public static LinearOpMode linearOpMode;
    public static HardwareMap hardwareMap;
    public static Telemetry telemetry;

    // initialize

    public Robot() {}

    public static void init(LinearOpMode opModeParam) {

        linearOpMode = opModeParam;
        hardwareMap = opModeParam.hardwareMap;
        telemetry = opModeParam.telemetry;

        // drivetrain

        telemetry.addLine("initializing drivetrain");
        telemetry.update();

        drivetrain = new Drivetrain();
        drivetrain.init(linearOpMode, DRIVETRAIN_COUNTS_PER_INCH);

        // servos

        telemetry.addLine("initializing servos");
        telemetry.update();

        servoHook = new ServoClass();
        servoHook.init(linearOpMode, "servo_hook", 0.05, 0.18, SERVO_SPEED, SERVO_TIME, true);

        // lifts

        telemetry.addLine("initializing lifts");
        telemetry.update();

        verticalLift = new LiftClass();
        verticalLift.init(linearOpMode, "motor_vertical_lift", MAX_LIFT_SPEED, LIFT_COUNTS_PER_INCH);

         // camera

        telemetry.addLine("initializing camera");
        telemetry.update();

        camera = new Camera();
        telemetry.addLine("new Camera() is done");
        telemetry.update();
        camera.init(linearOpMode);

        telemetry.addLine("finished initializing robot class");
        telemetry.update();

    }

    public static void reset() {

        telemetry.addLine("resetting servos + lifts");
        telemetry.update();

        servoHook.runToPosition("retract");
        verticalLift.autonomousRunToPosition("transfer");

    }

}