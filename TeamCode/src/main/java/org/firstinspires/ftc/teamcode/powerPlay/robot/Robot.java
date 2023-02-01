package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.lang.Math;
import java.util.HashMap;

public class Robot {

    public static Drivetrain drivetrain;
    public static ServoClass servoClaw;
    public static ServoClass servoHook;
    public static ServoClass servoRotateClaw;
    public static ServoClass servoRotateHook;
    public static LiftClass horizontalLift;
    public static LiftClass verticalLift;
    public static Camera camera;

    HashMap<String, Double> servoClawDistMap = new HashMap<String, Double>() {{
        put("hold", 0.0);
        put("release", 0.2);
    }};

    HashMap<String, Double> servoHookDistMap = new HashMap<String, Double>() {{
        put("hold", 0.0);
        put("release", 0.16);
    }};

    HashMap<String, Double> servoRotateClawDistMap = new HashMap<String, Double>() {{
        put("collect", 0.0);
        put("transfer", 1.0);
    }};

    HashMap<String, Double> servoRotateHookDistMap = new HashMap<String, Double>() {{
        put("transfer", 0.0);
        put("score", 1.0);
    }};

    HashMap<String, Integer> horizontalLiftDistMap = new HashMap<String, Integer>() {{
        put("zero", 0);
        put("wait to collect", 1400);
        put("collect", 1600);
    }};

    HashMap<String, Integer> verticalLiftDistMap = new HashMap<String, Integer>() {{
        put("zero",    0);
        put("cone 2",  1000);
        put("driving", 1500);
        put("cone 3",  1500);
        put("ground",  1500);
        put("cone 4",  2000);
        put("cone 5",  2500);
        put("low",     3500);
        put("middle",  4500);
        put("high",    6500);
    }};

    public static double MAX_LIFT_SPEED = 0.8;
    public static double SERVO_SPEED = 0.002;
    public static double SERVO_TIME = 500;
    public static int SLEEP_TIME = 50;
    public static int tag = 0;

    // initialize

    public Robot() {}

    public static void init(LinearOpMode opMode) {

        opMode.telemetry.addLine("initializing robot class...");
        opMode.telemetry.update();

        drivetrain      = new Drivetrain(SLEEP_TIME);
        servoClaw       = new ServoClass("servo_claw", servoClawDistMap, SERVO_SPEED, SERVO_TIME, false);
        servoHook       = new ServoClass("servo_hook", servoHookDistMap, SERVO_SPEED, SERVO_TIME, true);
        servoRotateClaw = new ServoClass("servo_rotate_claw", servoRotateClawDistMap, SERVO_SPEED, SERVO_TIME, false);
        servoRotateHook = new ServoClass("servo_rotate_hook", servoRotateHookDistMap, SERVO_SPEED, SERVO_TIME, false);
        horizontalLift  = new LiftClass("motor_horizontal_lift", horizontalLiftDistMap, MAX_LIFT_SPEED, SLEEP_TIME, true);
        verticalLift    = new LiftClass(  "motor_vertical_lift",   verticalLiftDistMap, MAX_LIFT_SPEED, SLEEP_TIME, true);
        camera          = new Camera();

        drivetrain     .init(opMode);
        servoClaw      .init(opMode);
        servoHook      .init(opMode);
        servoRotateClaw.init(opMode);
        servoRotateHook.init(opMode);
        horizontalLift .init(opMode);
        verticalLift   .init(opMode);
        camera         .init(opMode);

        opMode.telemetry.addLine("done initializing robot class");
        opMode.telemetry.update();

    }

}