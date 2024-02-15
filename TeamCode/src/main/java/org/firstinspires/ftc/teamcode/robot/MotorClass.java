package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.Robot;

public class MotorClass {

    public LinearOpMode opMode;
    Telemetry telemetry;

    public DcMotor motor;
    public double  motorCurrentSpeed = 0;
    public boolean continuous = false;

    public final String name;
    public final double maxSpeed;
    public final int sleepTime;
    public final boolean reverseDirection;

    public MotorClass(String name, double maxSpeed, int sleepTime, boolean reverseDirection) {

        this.name = name;
        this.maxSpeed = maxSpeed;
        this.sleepTime = sleepTime;
        this.reverseDirection = reverseDirection;

    }

    public void init(LinearOpMode opModeParam) {

        opMode = opModeParam;
        telemetry = opMode.telemetry;

        motor = opMode.hardwareMap.get(DcMotor.class, this.name);

        if (this.reverseDirection) motor.setDirection(DcMotor.Direction.REVERSE);

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    // helper function


    // autonomous

    public void runToPosition(int position) { runToPosition(position, false, this.maxSpeed); }
    public void runToPosition(int position, boolean isSynchronous) { runToPosition(position, isSynchronous, this.maxSpeed); }

    public void runToPosition(int position, boolean isSynchronous, double speed) {
        int target = position;
        motor.setTargetPosition(target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorCurrentSpeed = speed;
        motor.setPower(motorCurrentSpeed);
        if (isSynchronous) waitForMotor();
    }

    public void waitForMotor() {
        while (motor.isBusy()) {}
        opMode.sleep(this.sleepTime);
    }



    // teleOp

    public void teleOp(float button1,float button2) {

        float speed = 0;
        if (Math.abs(button1) > Math.abs(button2)) {
            speed = button1;
        } else if (Math.abs(button1) < Math.abs(button2)) {
            speed = button2;
        }

        motor.setPower(speed * maxSpeed);

    }


    public void printData() {
        telemetry.addLine(String.format("\n%1$s Running: %2$s", this.name, motor.isBusy()));
        telemetry.addLine(String.format("\n%1$s position: %2$s", this.name, motor.getCurrentPosition()));
        telemetry.addLine(String.format("%1$s speed: %2$s", this.name, motorCurrentSpeed));
    }

}