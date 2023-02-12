package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Robot {

    public static Drivetrain drivetrain;
    public static ServoClass servoClaw;
    public static ServoClass servoHook;
    public static ServoClass servoRotateClaw;
    public static ServoClass servoRotateHook;
    public static LiftClass horizontalLift;
    public static LiftClass verticalLift;
    public static SensorClass clawSensor;
    public static SensorClass hookSensor;
    public static Camera camera;

    public static double MAX_LIFT_SPEED = 0.8;
    public static double SERVO_SPEED = 0.0004;
    public static int SERVO_TIME = 500;
    public static int SLEEP_TIME = 50;

    public static String mode = "assist";
    public static String side = "";
    public static int numCycles = 0;
    public static int tag = 0;

    // initialize

    public static void init(LinearOpMode opMode) {

        opMode.telemetry.addLine("initializing robot class...");
        opMode.telemetry.update();

        drivetrain      = new Drivetrain(SLEEP_TIME);
        servoClaw       = new ServoClass("servo_claw", "hold", 0.0, "release", 1.0,  SERVO_SPEED, SERVO_TIME, false);
        servoHook       = new ServoClass("servo_hook", "hold", 0.0, "release", 0.16, SERVO_SPEED, SERVO_TIME, true);
        servoRotateClaw = new ServoClass("servo_rotate_claw", "collect",  0.0, "transfer", 1.0, SERVO_SPEED, SERVO_TIME, false);
        servoRotateHook = new ServoClass("servo_rotate_hook", "transfer", 0.0, "score",    1.0, SERVO_SPEED, SERVO_TIME, false);
        horizontalLift  = new LiftClass("motor_horizontal_lift", MAX_LIFT_SPEED, SLEEP_TIME, true);
        verticalLift    = new LiftClass("motor_vertical_lift",   MAX_LIFT_SPEED, SLEEP_TIME, true);
        clawSensor      = new SensorClass("claw_sensor");
        hookSensor      = new SensorClass("hook_sensor");
        camera          = new Camera();

        drivetrain     .init(opMode);
        servoClaw      .init(opMode);
        servoHook      .init(opMode);
        servoRotateClaw.init(opMode);
        servoRotateHook.init(opMode);
        horizontalLift .init(opMode);
        verticalLift   .init(opMode);
        clawSensor     .init(opMode);
        hookSensor     .init(opMode);
        camera         .init(opMode);

        resetRandomization();

        opMode.telemetry.addLine("done initializing robot class");
        opMode.telemetry.update();

    }

    public static void resetRandomization() {
        mode = "assist";
        side = "";
        numCycles = 0;
        tag = 0;
    }

    public static void resetScoring() {
        servoClaw.runToPosition("hold", false);
        servoHook.runToPosition("release", false);
        servoRotateClaw.runToPosition("transfer", false);
        servoRotateHook.runToPosition("transfer", false);
        horizontalLift.runToPosition("retracted", false);
        verticalLift.runToPosition("zero", false);
    }

    public static void configureAuton(LinearOpMode opMode) {

        while (opMode.opModeInInit()) {

            opMode.telemetry.addLine("Configuring auton...");

            if (opMode.gamepad1.a) side = "left";
            if (opMode.gamepad1.y) side = "right";

            if (opMode.gamepad2.a) numCycles++;
            if (opMode.gamepad2.y) numCycles = 0;

            opMode.telemetry.addData("side", side);
            opMode.telemetry.addData("numCycles", numCycles);
            opMode.telemetry.update();

        }

    }

}