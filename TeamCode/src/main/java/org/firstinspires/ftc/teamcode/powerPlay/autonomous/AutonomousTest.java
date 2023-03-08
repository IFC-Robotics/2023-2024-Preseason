package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.Math;
@Autonomous(name = "Autonomous Test", group = "competition")
public class AutonomousTest extends LinearOpMode{

    double DRIVE_SPEED = 0.3;

    private DcMotor motor;

    static final double COUNTS_PER_REVOLUTION    = 28.0;
    static final double GEAR_RATIO               = 1.0;
    static final double WHEEL_DIAMETER_IN_INCHES = 1.0;
    static final double COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION * GEAR_RATIO) / (WHEEL_DIAMETER_IN_INCHES * Math.PI);


    @Override
    public void runOpMode(){
        motor = hardwareMap.get(DcMotor.class,"motor_front_left");

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double target = 5 * COUNTS_PER_INCH;

        waitForStart();
        motor.setTargetPosition((int) target);

        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1);

        while (motor.isBusy()){

        }

        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        

        motor.setTargetPosition(12345);
        

        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0.345);

        while (motor.isBusy()){

        }

        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        

        motor.setTargetPosition(58743);
       

        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0.9832);

        while (motor.isBusy()){

        }

        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);







    }
}