package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.Math;

@Autonomous(name="Iyan's Failure", group="competition")
public class IyansFailure extends LinearOpMode {
    LinearOpMode opMode;
    Telemetry telemetry;

    public static DcMotor motorFrontRight;
    public static DcMotor motorFrontLeft;
    public static DcMotor motorBackRight;
    public static DcMotor motorBackLeft;

    motorFrontLeft  = opMode.hardwareMap.get(DcMotor.class, "motor_front_left");
    motorFrontRight = opMode.hardwareMap.get(DcMotor.class, "motor_front_right");
    motorBackRight  = opMode.hardwareMap.get(DcMotor.class, "motor_back_right");
    motorBackLeft   = opMode.hardwareMap.get(DcMotor.class, "motor_back_left");


    public static void drivePls(int drive, int strafe, int turn, sleepTime) {

        // drive

        double denominator = Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(turn), 1); // test this

        double frontRightPower = Range.clip((drive - turn - strafe) / denominator, -1, 1);
        double frontLeftPower  = Range.clip((drive + turn + strafe) / denominator, -1, 1);
        double backRightPower  = Range.clip((drive - turn + strafe) / denominator, -1, 1);
        double backLeftPower   = Range.clip((drive + turn - strafe) / denominator, -1, 1);

        motorFrontRight.setPower(frontRightPower);
        motorFrontLeft.setPower(frontLeftPower);
        motorBackRight.setPower(backRightPower);
        motorBackLeft.setPower(backLeftPower);

        sleep(sleepTime)

        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);


    }

    public void runOpMode() {
        waitForStart()
        drivePls(1,0,0,1000)
        drivePls(,0.5,0,1000)
        drivePls(0,0,0.5,1000)
        drivePls(1,0,1,1000)
        drivePls(1,1,0,1000)

    }


}