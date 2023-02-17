package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {

    public static Telemetry telemetry;

    public static Drivetrain drivetrain;
    public static ServoClass servoClaw;
    public static ServoClass servoHook;
    public static CRServoClass servoRotateClaw;
    public static ServoClass servoRotateHook;
    public static LiftClass horizontalLift;
    public static LiftClass verticalLift;
    public static SensorClass clawSensor;
    public static SensorClass hookSensor;
    public static Camera camera;

    public static double MAX_LIFT_SPEED = 0.8;
    public static double SERVO_SPEED = 0.01;
    public static int SERVO_TIME = 1000;
    public static int SLEEP_TIME = 50;

    public static String mode = "assist";
    public static String side = "";
    public static int tag = 0;

    // initialize

    public static void init(LinearOpMode opMode) {

        telemetry = opMode.telemetry;

        telemetry.addLine("initializing robot class...");
        telemetry.update();

        /*

            TO-DO:

                1. test cr_servo_rtate_claw
                2. test that FSM doesn't break
                3. test the horizontal_lift predetermined distances
                4. make sure servo_claw actually holds onto the cone
                5. make sure servo_rotate_claw aligns with servo_rotate_hook
                6. finalize FSM
                6. test ConfigurableAuton.java
                7. test beacon (assist mode and FSM mode)
                8. test inchesToTicks() and degreesToTicks()
                9. test overall teleOp
                10. driver practice!!!!!!

        */

        drivetrain        = new Drivetrain("cone", SLEEP_TIME);
        servoClaw         = new ServoClass("servo_claw", "release", 0.30, "hold", 0.64, SERVO_SPEED, SERVO_TIME, false);
        servoHook         = new ServoClass("servo_hook", "release", 0.51, "hold", 0.57, SERVO_SPEED, SERVO_TIME, false);
        servoRotateHook   = new ServoClass("servo_rotate_hook", "transfer", 0.10, "score", 0.84, SERVO_SPEED, SERVO_TIME, false);
        crServoRotateClaw = new CRServoClass("cr_servo_rotate_claw", "collect", "transfer", SERVO_TIME, false);
        horizontalLift    = new LiftClass("motor_horizontal_lift", MAX_LIFT_SPEED, 0,      SLEEP_TIME, false);
        verticalLift      = new LiftClass("motor_vertical_lift",   MAX_LIFT_SPEED, 0.0005, SLEEP_TIME, false);
        clawSensor        = new SensorClass("claw_sensor");
        hookSensor        = new SensorClass("hook_sensor");
        camera            = new Camera();

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

        telemetry.addLine("done initializing robot class");
        telemetry.update();

    }

    // inches & degrees to ticks

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

    // cone detection algorithm & automatic closing

    public static boolean checkForConeInClaw() { return checkForCone(Robot.clawSensor, 60, 7); }
    public static boolean checkForConeInHook() { return checkForCone(Robot.hookSensor, 55, 20); }

    public static void closeClawUsingSensor() { closeServoUsingSensor(Robot.servoHook, checkForConeInClaw()); }
    public static void closeHookUsingSensor() { closeServoUsingSensor(Robot.servoHook, checkForConeInHook()); }

    public static boolean checkForCone(SensorClass sensorClass, double desiredDistance, double error) {

        String currentColor = sensorClass.getDominantColor();
        double currentDistance = sensorClass.getDistance("mm");

        boolean correctColor = (currentColor == "red" || currentColor == "blue");
        boolean correctDistance = (desiredDistance - error < currentDistance && currentDistance < desiredDistance + error);

        return correctColor && correctDistance;

    }

    public static void closeServoUsingSensor(ServoClass servoClass, boolean thereIsACone) {
        if (thereIsACone && servoClass.servo.getPosition() == 0) {
            servoClass.runToPosition("hold");
        }
    }

    // resetting robot

    public static void resetRandomization() {
        mode = "assist";
        side = "";
        tag = 0;
    }

    public static void resetScoring() {
        servoClaw.runToPosition("release");
        servoHook.runToPosition("release");
        servoRotateClaw.runToPosition("transfer");
        servoRotateHook.runToPosition("transfer");
        horizontalLift.runToPosition("zero");
        verticalLift.runToPosition("zero");
    }

    // configure autonomous (left/right side)

    public static void configureAuton(LinearOpMode opMode) {

        while (opMode.opModeInInit()) {

            telemetry.addLine("Configuring auton...");

            if (opMode.gamepad1.x) side = "left";
            if (opMode.gamepad1.b) side = "right";

            telemetry.addData("side", side);
            telemetry.update();

        }

    }

}