package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.lang.Math;

public class Robot {

    public static LinearOpMode opMode;
    public static Telemetry telemetry;

    public static Drivetrain drivetrain;
    public static ServoClass servoClaw;
    public static ServoClass servoHook;
    public static LiftClass verticalLift;
    public static Camera camera;

    public static int SLEEP_TIME = 50;
    public static int tag = 0;

    // initialize

    public Robot() {}

    public static void init(LinearOpMode opModeParam) {

        opMode = opModeParam;
        telemetry = opModeParam.telemetry;

        // drivetrain

        telemetry.addLine("initializing drivetrain...");
        telemetry.update();

        drivetrain = new Drivetrain();
        drivetrain.init(opMode, SLEEP_TIME);

        // servos

        telemetry.addLine("initializing servos...");
        telemetry.update();

        // try using

        servoClaw = new ServoClass();
        servoClaw.init(opMode, "servo_claw", 0, "hold", 0.2, "release", 0.002, 500, false);

        servoHook = new ServoClass();
        servoHook.init(opMode, "servo_hook", 0.05, "hold", 0.18, "release", 0.002, 500, true);

        // look into using servo.scaleRange();

        // lifts

        telemetry.addLine("initializing lifts...");
        telemetry.update();

        verticalLift = new LiftClass();
        verticalLift.init(opMode, "motor_vertical_lift", 0.8, SLEEP_TIME, true);

        // camera

        telemetry.addLine("initializing camera...");
        telemetry.update();

        camera = new Camera();
        camera.init(opMode);

        telemetry.addLine("finished initializing robot class");
        telemetry.update();

    }

}