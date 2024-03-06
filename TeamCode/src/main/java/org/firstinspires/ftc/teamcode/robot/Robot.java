package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
//robot uses the different classes in this code so if you need specifics look at the class
public class Robot {
    //a log (pretty much console.log)
    public static Telemetry telemetry;

    public static Drivetrain drivetrain;


    public static ServoClass servoClawR;
    public static ServoClass servoClawL;
    public static MotorClass motorArm;
    public static ScissorClass scissorLift;
    public static CameraClass webcam1;
//    public static TFModelClass redBlueModel;


    public static double MAX_LIFT_SPEED = 0.3;
    public static double MAX_MOTOR_SPEED = 0.7;
    public static double SERVO_SPEED = 0.01;
    public static int SERVO_TIME = 800;
    public static int CR_SERVO_TIME = 1600;
    public static int SLEEP_TIME = 50;

    public static String mode = "assist";


    // initialize

    public static void init(LinearOpMode opMode) {

        telemetry = opMode.telemetry;

        telemetry.addLine("initializing robot class...");
        telemetry.update();


        drivetrain     =    new Drivetrain("collector", SLEEP_TIME);
        servoClawR     =    new ServoClass("servo_claw_right", "open", 0.1, "score",0.5,"close", 1, SERVO_SPEED, SERVO_TIME, false);
        servoClawL     =    new ServoClass("servo_claw_left", "open", 0.1, "hold",0.5 ,"close",1, SERVO_SPEED, SERVO_TIME, false);
        motorArm       =    new MotorClass("motor_arm", MAX_MOTOR_SPEED, SLEEP_TIME, true);
        scissorLift    =    new ScissorClass("scissor_lift", 0.5*MAX_LIFT_SPEED, 0.01, SLEEP_TIME, false);
//        webcam1        =    new CameraClass("webcam_1",true);
//        redBlueModel =    new TFModelClass("red_&_blue", new String[]{"Red Box", "Blue Box"},"model_red&blue_low_step.tflite");

        drivetrain.init(opMode);
        servoClawR.init(opMode);
        servoClawL.init(opMode);
        motorArm.init(opMode);
        scissorLift.init(opMode);
//        webcam1.init(opMode);
//        redBlueModel.init(opMode);

    }

}