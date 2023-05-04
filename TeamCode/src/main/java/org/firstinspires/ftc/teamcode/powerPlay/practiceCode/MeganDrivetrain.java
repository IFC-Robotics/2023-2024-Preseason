//package org.firstinspires.ftc.teamcode.powerPlay.practiceCode;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//
//public class Drivetrain{
//    public static DcMotor motorFrontLeft;
//    public static DcMotor motorFrontRight;
//    public static DcMotor motorBackLeft;
//    public static DcMotor motorBackRight;
//
//    public static double frontLeftPower;
//    public static double frontRightPower;
//    public static double backLeftPower;
//    public static double backRightPower;
//
//
//    public void init(LinearOpMode opModeParam){
//        opMode = opModeParam;
//        telemetry = opMode.telemetry;
//         motorFrontLeft = opMode.hardwareMap.get(DcMotor.class,"motor_front_left");
//         motorFrontRight = opMode.hardwareMap.get(DcMotor.class,"motor_front_right");
//         motorBackLeft = opMode.hardwareMap.get(DcMotor.class,"motor_back_left");
//         motorBackRight = opMode.hardwareMap.get(DcMotor.class,"motor_back_right");
//
//         motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
//         motorFrontRight.setDirection(DcMotor.Direction.FORWARD);
//         motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
//         motorBackRight.setDirection(DcMotor.Direction.FORWARD);
//
//    public void drive(double power){
//        frontLeftPower = power;
//        frontRightPower = power;
//        backLeftPower = power;
//        backRightPower = power;
//
//    }
//
//    public void turn(String dir, double power){
//        if (dir == "right"){
//        frontLeftPower = power;
//        frontRightPower = -power;
//        backLeftPower = power;
//        backRightPower = -power;
//        }else if (dir == "left"){
//        frontLeftPower = -power;
//        frontRightPower = power;
//        backLeftPower = -power;
//        backRightPower = power;
//        }
//
//    }
//
//    public void strafe(String dir, double power){
//        if (dir == "right"){
//        frontLeftPower = -power;
//        frontRightPower = power;
//        backLeftPower = power;
//        backRightPower = -power;
//
//        }else if (dir == "left"){
//        frontLeftPower = power;
//        frontRightPower = -power;
//        backLeftPower = -power;
//        backRightPower = power;
//
//        }
//
//    }
//
//    motorFrontLeft.setPower(frontLeftPower);
//    motorFrontRight.setPower(frontRightPower);
//    motorBackLeft.setPower(backLeftPower);
//    motorBackRight.setPower(backRightPower);
//
//
//}
//}