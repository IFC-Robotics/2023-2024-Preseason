package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
//robot uses the different classes in this code so if you need specifics look at the class
public class Robot {
    //a log (pretty much console.log)
    public static Telemetry telemetry;
    //
    public static Drivetrain drivetrain;
//

    public static LiftClass verticalLift;
    public static ServoClass servoDeposit;
    public static ServoClass servoLauncher;
    public static MotorClass motorSweeper;
    public static MotorClass motorLauncher;

    public static double MAX_LIFT_SPEED = 0.3;
    public static double MAX_MOTOR_SPEED = 0.7;
    public static double SERVO_SPEED = 0.01;
    public static int SERVO_TIME = 800;
    public static int CR_SERVO_TIME = 1600;
    public static int SLEEP_TIME = 50;

    public static String mode = "assist";
    public static String side = "";
    public static int tag = 0;

    // initialize

    public static void init(LinearOpMode opMode) {

        telemetry = opMode.telemetry;

        telemetry.addLine("initializing robot class...");
        telemetry.update();


        drivetrain = new Drivetrain("ramp", SLEEP_TIME);
        verticalLift = new LiftClass("motor_vertical_lift", MAX_LIFT_SPEED, 0.0005, SLEEP_TIME, true);
        servoDeposit = new ServoClass("servo_deposit", "collect", 0.1, "score",0.65,"auton", 0.80, SERVO_SPEED, SERVO_TIME, false);
        servoLauncher = new ServoClass("servo_launcher", "release", 0.1, "hold",0.5 ,"medium",0.65, SERVO_SPEED, SERVO_TIME, false);
        motorSweeper = new MotorClass("motor_sweeper", MAX_MOTOR_SPEED, SLEEP_TIME, true);
        motorLauncher = new MotorClass("motor_launcher", 0.5*MAX_MOTOR_SPEED, SLEEP_TIME, false);


        drivetrain.init(opMode);
        verticalLift.init(opMode);
        servoDeposit.init(opMode);
        servoLauncher.init(opMode);
        motorSweeper.init(opMode);
        motorLauncher.init(opMode);


    }
}