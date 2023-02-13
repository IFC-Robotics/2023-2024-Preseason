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
    public static double SERVO_SPEED = 0.001;
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

        /*

            TO DO:

                - make horizontal_lift come back smoothly (fabricators)

                - fix horizontal_lift teleOp method not working properly
                    - doesn't work when enableEncoderLimits is true
                    - buttons don't do anything

                - test the horizontal_lift predetermined distances
                - make sure servo_rotate_claw aligns with servo_rotate_hook
                - test inchesToTicks() and degreesToTicks()

                - make servo_rotate_hook rotate the other way (above, not below)

        */

        drivetrain      = new Drivetrain(SLEEP_TIME);
        servoClaw       = new ServoClass("servo_claw", "hold", 0.30, "release", 0.64, SERVO_SPEED, SERVO_TIME, false);
        servoHook       = new ServoClass("servo_hook", "hold", 0.51, "release", 0.57, SERVO_SPEED, SERVO_TIME, false);
        servoRotateClaw = new ServoClass("servo_rotate_claw", "collect",  0.00, "transfer", 1.00, SERVO_SPEED, SERVO_TIME, false);
        servoRotateHook = new ServoClass("servo_rotate_hook", "transfer", 0.10, "score",    0.84, SERVO_SPEED, SERVO_TIME, false);
        horizontalLift  = new LiftClass("motor_horizontal_lift", MAX_LIFT_SPEED, 0, SLEEP_TIME, false);
        verticalLift    = new LiftClass("motor_vertical_lift",   MAX_LIFT_SPEED, 0.0005, SLEEP_TIME, false);
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

    public static double inchesToTicks(double inches) {

        double TICKS_PER_REV = 1120;
        double WHEEL_RADIUS = 2;
        double GEAR_RATIO = 20;

        double wheelCircumference = 2 * Math.PI * WHEEL_RADIUS;
        double ticksPerInch = TICKS_PER_REV / (wheelCircumference * GEAR_RATIO);
        return inches * ticksPerInch;

    }

    public static double degreesToTicks(double degrees) {

        double ROBOT_RADIUS = 9;

        double robotCircumference = 2 * Math.PI * ROBOT_RADIUS;
        double arc = robotCircumference * degrees / 360;
        return inchesToTicks(arc);

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