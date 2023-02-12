package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LiftClass {

    public LinearOpMode opMode;
    Telemetry telemetry;

    public DcMotor motor;
    public double  motorCurrentSpeed = 0;
    public boolean motorIsMoving = false;
    public boolean enableEncoderLimits = true;

    public final String name;
    public final double maxSpeed;
    public final double idleSpeed;
    public final int sleepTime;
    public final boolean reverseDirection;

    public LiftClass(String name, double maxSpeed, double idleSpeed, int sleepTime, boolean reverseDirection) {
        this.name = name;
        this.maxSpeed = maxSpeed;
        this.idleSpeed = idleSpeed;
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

        if (this.name == "servo_horizontal_lift") {

            if (position == "transfer" || position == "zero") return 0;
            if (position == "wait to collect") return 1400;
            if (position == "collect" || position == "high") return 1600;

        } else if (this.name == "motor_vertical_lift") {

            if (position == "transfer" || position == "zero")   return 0;
            if (position == "low")    return 950;
            if (position == "middle") return 1700;
            if (position == "high")   return 2550;

        }

        return 0;

    }

    // autonomous

    public void runToPosition(String position) {
        runToPosition(position, false, this.maxSpeed);
    }
    public void runToPosition(String position, boolean isSynchronous) { runToPosition(position, isSynchronous, this.maxSpeed); }

    public void runToPosition(String position, boolean isSynchronous, double speed) {
        int target = positionToDistance(position);
        motor.setTargetPosition(target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorCurrentSpeed = speed;
        motor.setPower(motorCurrentSpeed);
        if (isSynchronous) waitForLift();
    }

    public void waitForLift() {
        while (motor.isBusy()) {}
        motorCurrentSpeed = this.idleSpeed;
        motor.setPower(motorCurrentSpeed);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        opMode.sleep(this.sleepTime);
    }

    // teleOp

    public void teleOp(double joystick, boolean encoderButton, boolean button1, boolean button2, boolean button3, boolean button4) {

        // buttons

        if (button1 || button2 || button3 || button4) {

            String position = "";

            if (this.name == "servo_horizontal_lift") {

                if (button1) position = "zero";
                if (button2) position = "wait to collect";
                if (button3) position = "collect";

            } else if (this.name == "motor_vertical_lift") {

                if (button1) position = "zero";
                if (button2) position = "low";
                if (button3) position = "middle";
                if (button4) position = "high";

            }

            motorIsMoving = true;
            runToPosition(position, false);

        } else {

            if (motorIsMoving && !motor.isBusy()) {
                motorIsMoving = false;
                motorCurrentSpeed = this.idleSpeed;
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            // joystick

            if (!motorIsMoving) {

                double liftSpeed = Range.clip(joystick, -this.maxSpeed, this.maxSpeed);
                int liftCurrentPosition = motor.getCurrentPosition();

                boolean liftIsTooLow  = (liftCurrentPosition < positionToDistance("zero") && liftSpeed < 0);
                boolean liftIsTooHigh = (liftCurrentPosition > positionToDistance("high") && liftSpeed > 0);

                if (liftSpeed == 0 || (enableEncoderLimits && (liftIsTooLow || liftIsTooHigh))) {
                    motorCurrentSpeed = this.idleSpeed;
                } else {
                    motorCurrentSpeed = liftSpeed;
                }

            }

            telemetry.addData("motorCurrentSpeed", motorCurrentSpeed);
            motor.setPower(motorCurrentSpeed);

        }

        // change encoder limits

        if (encoderButton) {

            enableEncoderLimits = !enableEncoderLimits;

            if (enableEncoderLimits) {
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

        }

        telemetry.addData("enableEncoderLimits", enableEncoderLimits);


    }

}