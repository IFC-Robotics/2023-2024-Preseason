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

    public int positionToDistance(String position) {

        if (this.name == "example_motor") {
            if (position == "collect" || position == "high") return 800;
        }

        return 0;

    }

    // autonomous

    public void runToPosition(String position) { runToPosition(position, false, this.maxSpeed); }
    public void runToPosition(String position, boolean isSynchronous) { runToPosition(position, isSynchronous, this.maxSpeed); }

    public void runToPosition(String position, boolean isSynchronous, double speed) {
        int target = positionToDistance(position);
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

    public void teleOp(float button1) {

        if (button1 > 0) {
            if (motor.isBusy()) {
                motor.setPower(0);
                continuous = false;
            }else {
                motor.setPower(maxSpeed);
                continuous = true;
            }

        }
    }


    public void printData() {
        telemetry.addLine(String.format("\n%1$s Continuous: %2$s", this.name, continuous));
        telemetry.addLine(String.format("\n%1$s position: %2$s", this.name, motor.getCurrentPosition()));
        telemetry.addLine(String.format("%1$s speed: %2$s", this.name, motorCurrentSpeed));
    }

}