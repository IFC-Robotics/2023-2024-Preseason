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
    public static MotorClass sweeper;

    public static double MAX_LIFT_SPEED = 0.3;
    public static double MAX_MOTOR_SPEED = 0.7;
    public static double SERVO_SPEED = 0.01;
    public static int SERVO_TIME = 500;
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


        drivetrain        = new Drivetrain("ramp", SLEEP_TIME);
        verticalLift      = new LiftClass("motor_vertical_lift",   MAX_LIFT_SPEED, 0.0005, SLEEP_TIME, true);
        servoDeposit      = new ServoClass("servo_deposit","release",0.0,"hold",0.5,SERVO_SPEED,SERVO_TIME, false);
        sweeper           = new MotorClass("motor_sweeper",MAX_MOTOR_SPEED, SLEEP_TIME, false);


        drivetrain.init(opMode);
        verticalLift.init(opMode);
        servoDeposit.init(opMode);
        sweeper.init(opMode);

//specific for 2022 ftc season
        resetRandomization();

        telemetry.addLine("done initializing robot class");
        telemetry.update();

    }


    // resetting robot
//qr code dependent storing thing for 2022 season can be used for any qr code related thing
    public static void resetRandomization() {
        mode = "assist";
        side = "";
        tag = 0;
    }
//it would reset robot to original positions as if it was just turned on
    public static void resetScoring() {
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