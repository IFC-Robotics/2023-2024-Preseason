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

    public static double MAX_LIFT_SPEED = 0.8;
    public static double SERVO_SPEED = 0.002;
    public static int SERVO_TIME = 500;
    public static int SLEEP_TIME = 50;

    public static String mode = "assist";
    public static String side = "";
    public static int tag = 0;

    // initialize

    public Robot() {}

    public static void init(LinearOpMode opMode) {

        opMode.telemetry.addLine("initializing robot class...");
        opMode.telemetry.update();

        drivetrain      = new Drivetrain(SLEEP_TIME);
        servoClaw       = new ServoClass("servo_claw", "hold", 0.0, "release", 0.2,  SERVO_SPEED, SERVO_TIME, false);
        servoHook       = new ServoClass("servo_hook", "hold", 0.0, "release", 0.16, SERVO_SPEED, SERVO_TIME, true);
        servoRotateClaw = new ServoClass("servo_rotate_claw", "collect",  0, "transfer", 1, SERVO_SPEED, SERVO_TIME, false);
        servoRotateHook = new ServoClass("servo_rotate_hook", "transfer", 0, "score",    1, SERVO_SPEED, SERVO_TIME, false);
        horizontalLift  = new LiftClass("motor_horizontal_lift", MAX_LIFT_SPEED, SLEEP_TIME, true);
        verticalLift    = new LiftClass(  "motor_vertical_lift", MAX_LIFT_SPEED, SLEEP_TIME, true);
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

    public static void resetRandomization() {
        side = "";
        tag = 0;
    }

    public static void resetScoring() {
        servoClaw.runToPosition("hold", false);
        servoHook.runToPosition("release", false);
        servoRotateClaw.runToPosition("transfer", false);
        servoRotateHook.runToPosition("transfer", false);
        horizontalLift.runToPosition("zero", false);
        verticalLift.runToPosition("zero", false);
    }

}