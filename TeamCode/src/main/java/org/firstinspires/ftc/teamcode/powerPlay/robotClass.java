package org.firstinspires.ftc.teamcode.powerPlay;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

// learn how to make program asynchronous (add second motor, run it after first motor, and remove/change while loop in moveMotorOne())

public class robotClass extends LinearOpMode {

    public DcMotor motorOne;

    static final double DC_MOTOR_COUNTS_PER_REVOLUTION = 28.0;
    static final double MOTOR_ONE_GEAR_RATIO           = 1.0; // varies by motor
    static final double WHEEL_DIAMETER_IN_INCHES       = 1.0; // I think this is always 1.0
    static final double MOTOR_ONE_COUNTS_PER_INCH      = (DC_MOTOR_COUNTS_PER_REVOLUTION * MOTOR_ONE_GEAR_RATIO) / (WHEEL_DIAMETER_IN_INCHES * Math.PI);

    HardwareMap hardwareMap;

    public robotClass(){}

    @Override
    public void runOpMode(){}

    public void init(HardwareMap hardwareMapParameter) {

        hardwareMap = hardwareMapParameter;

        motorOne = hardwareMap.get(DcMotor.class, "motor_one");
        motorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

//    public void resetMotor(DcMotor motor) {
//
//        motor.setPower(0); // stops the motor
//        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // resets the encoder
//        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // makes it so the encoder can receive a target position
//
//    }

    public void moveMotorOne(double speed, int inches) {

        motorOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorOne.setTargetPosition((int)(inches * MOTOR_ONE_COUNTS_PER_INCH));
        motorOne.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorOne.setPower(speed);

        while (opModeIsActive() && motorOne.isBusy()) {}

        motorOne.setPower(0);
        motorOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

}