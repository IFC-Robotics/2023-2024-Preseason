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
    public final HashMap<String, Integer> distMap;
    public final double maxSpeed;
    public final int sleepTime;
    public final boolean reverseDirection;

    public LiftClass(String name, HashMap<String, Integer> distMap, double maxSpeed, int sleepTime, boolean reverseDirection) {
        this.name = name;
        this.distMap = distMap;
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

    // autonomous

    public void runToPosition(String position, boolean isSynchronous) {
        int target = this.distMap.get(position);
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

    public void teleOpAssistMode(boolean button1, boolean button2, boolean button3, boolean button4, boolean button5) {

        // fix

        if (button1 || button2 || button3 || button4 || button5) {
            if (button1) runToPosition("zero", false);
            if (button2) runToPosition("driving", false);
            if (button3) runToPosition("low", false);
            if (button4) runToPosition("middle", false);
            if (button5) runToPosition("high", false);
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

        boolean liftIsTooLow  = (liftCurrentPosition <= this.distMap.get("zero") && liftSpeed < -0.02);
        boolean liftIsTooHigh = (liftCurrentPosition >= this.distMap.get("high") && liftSpeed > 0.02);

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