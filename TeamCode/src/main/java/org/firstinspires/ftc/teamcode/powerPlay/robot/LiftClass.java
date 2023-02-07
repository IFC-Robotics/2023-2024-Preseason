package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.HashMap;

public class LiftClass {

    public LinearOpMode opMode;
    Telemetry telemetry;

    public DcMotor motor;
    public boolean enableEncoderLimits = true;

    public final String name;
    public final double maxSpeed;
    public final int sleepTime;
    public final boolean reverseDirection;

    public LiftClass(String name, double maxSpeed, int sleepTime, boolean reverseDirection) {
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

        if (this.name == "motor_horizontal_lift") {

            if (position == "transfer" || position == "zero") return 0;
            if (position == "wait to collect") return 1400;
            if (position == "collect" || position == "high") return 1600;

        } else if (this.name == "motor_vertical_lift") {

            if (position == "transfer" || position == "zero") return 0;
            if (position == "cone 2") return 1000;
            if (position == "driving" || position == "cone 3" || position == "ground") return 1500;
            if (position == "cone 4") return 2000;
            if (position == "cone 5") return 2500;
            if (position == "low")    return 3500;
            if (position == "middle") return 4500;
            if (position == "high")   return 6500;

        }

        return 0;

    }

    // autonomous

    public void runToPosition(String position, boolean isSynchronous) {
        int target = positionToDistance(position);
        motor.setTargetPosition(target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(this.maxSpeed);
        if (isSynchronous) waitForLift();
    }

    public void waitForLift() {
        while (motor.isBusy()) {}
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        opMode.sleep(this.sleepTime);
    }

    // teleOp

    public void teleOpAssistMode(boolean button1, boolean button2, boolean button3, boolean button4) {

        // fix

        if (button1 || button2 || button3 || button4) {

            String position = "";

            if (this.name == "motor_horizontal_lift") {

                if (button1) position = "zero";
                if (button2) position = "wait to collect";
                if (button3) position = "collect";

            } else if (this.name == "motor_vertical_lift") {

                if (button1) position = "zero";
                if (button2) position = "low";
                if (button3) position = "middle";
                if (button4) position = "high";

            }

            runToPosition(position, false);

        }

        if (!motor.isBusy()) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }

    public void teleOpManualMode(double joystick, boolean button) {

        // move lift

        double liftSpeed = Range.clip(joystick, -this.maxSpeed, this.maxSpeed);
        int liftCurrentPosition = motor.getCurrentPosition();

        boolean liftIsTooLow  = (liftCurrentPosition <= positionToDistance("zero") && liftSpeed < -0.02);
        boolean liftIsTooHigh = (liftCurrentPosition >= positionToDistance("high") && liftSpeed > 0.02);

        if (enableEncoderLimits && (liftIsTooLow || liftIsTooHigh)) {
            liftSpeed = 0;
        }

        motor.setPower(liftSpeed);

        // change encoder limits

        if (button) {
               
            enableEncoderLimits = !enableEncoderLimits;
           
            if (enableEncoderLimits) {
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

        }

    }

}