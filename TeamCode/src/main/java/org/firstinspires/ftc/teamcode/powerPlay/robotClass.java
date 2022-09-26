package org.firstinspires.ftc.teamcode.powerPlay;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

// after the autonomous and teleOp programs work correctly, learn how to make program asynchronous
// add second motor, run it after first motor, and remove/change while loop in moveMotorOne()

public class robotClass extends LinearOpMode {

    public DcMotor motorOne;

    HardwareMap hardwareMap;

    public robotClass(){}

    public void runOpMode(){}

    public void init(HardwareMap hardwareMapParameter) {

        hardwareMap = hardwareMapParameter;

        motorOne = hardwareMap.get(DcMotor.class, "motor_one");
        motorOne.setDirection(DcMotor.Direction.FORWARD);
        motorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        resetMotor(motorOne);

    }

    public void resetMotor(DcMotor motor) {

        motor.setPower(0); // stops the motor
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // resets the encoder
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // makes it so the encoder can receive a target position

    }

    public void moveMotorOne(double speed, int distance, int direction) {

        resetMotor(motorOne); // resets the motor

        motorOne.setTargetPosition(direction * distance); // sets the position the motor will move to

        motorOne.setMode(DcMotor.RunMode.RUN_TO_POSITION); // moves the motor
        motorOne.setPower(direction * speed);
        while (motorOne.isBusy() && opModeIsActive()) {}

        resetMotor(motorOne); // resets the motor

    }

}