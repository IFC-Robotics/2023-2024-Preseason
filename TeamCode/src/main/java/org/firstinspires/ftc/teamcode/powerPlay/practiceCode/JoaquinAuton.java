package org.firstinspires.ftc.teamcode.powerPlay.practiceCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Joaquin's Autonomous Test", group = "test")
public class JoaquinAuton extends LinearOpMode{

    DcMotor motor;

    public void runOpMode() {
        
        motor = hardwareMap.get(DcMotor.class, "motor_front_left");

//        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        waitForStart();
//
//        motor.setTargetPosition(1000);
//        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motor.setPower(1);
//        while (motor.isBusy()){}
//        motor.setPower(0);
//
//
//        motor.setTargetPosition(1564);
//        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motor.setPower(0.7);
//        while (motor.isBusy()){}
//        motor.setPower(0);
//
//
//        motor.setTargetPosition(800);
//        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motor.setPower(0.5);
//        while (motor.isBusy()){}
//        motor.setPower(0);

        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        motor.setPower(1);
        sleep(2000);
        motor.setPower(0);

    }

}