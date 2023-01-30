package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LiftClass {

    LinearOpMode opMode;
    Telemetry telemetry;

    public DcMotor motor;
    public boolean enableEncoderLimits = true;

    public String NAME;
    public double MAX_SPEED;
    public int SLEEP_TIME;

    int zeroDist    =    0; // zero & cone 1 from cone stack
    int drivingDist = 1550; // driving & ground junction
    int lowDist     = 3550; // low junction
    int middleDist  = 3750; // middle junction
    int highDist    = 7100; // high junction

    int cone2Dist = 1000; // cone 2 from cone stack
    int cone3Dist = 1500; // cone 3 from cone stack
    int cone4Dist = 2000; // cone 4 from cone stack
    int cone5Dist = 2500; // cone 5 from cone stack

    // idea: have a sensor/camera in the hook, so that it knows when it can pick up a cone and will do it automatically

    public LiftClass() {}

    public void init(LinearOpMode opModeParam, String name, double maxSpeed, int sleepTime, boolean reverseDirection) {

        opMode = opModeParam;
        telemetry = opModeParam.telemetry;

        NAME = name;
        MAX_SPEED = maxSpeed;
        SLEEP_TIME = sleepTime;

        motor = opMode.hardwareMap.get(DcMotor.class, NAME);

        if (reverseDirection) motor.setDirection(DcMotor.Direction.REVERSE);

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    // autonomous

    public void runToPosition(String position, boolean isSynchronous) {

        int target = 0;

        if (position == "zero")    target = zeroDist;
        if (position == "driving") target = drivingDist;
        if (position == "low")     target = lowDist;
        if (position == "middle")  target = middleDist;
        if (position == "high")    target = highDist;

        motor.setTargetPosition(target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(MAX_SPEED);

        if (isSynchronous) waitForLift();

    }

    public void waitForLift() {
        while (motor.isBusy()) {}
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        opMode.sleep(SLEEP_TIME);
    }

    // teleOp

    public void teleOpAssistMode(boolean button1, boolean button2, boolean button3, boolean button4, boolean button5) {

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

        double liftSpeed = Range.clip(joystick, -MAX_SPEED, MAX_SPEED);
        int liftCurrentPosition = motor.getCurrentPosition();

        if (enableEncoderLimits && ((liftCurrentPosition >= highDist && liftSpeed > 0.02) || (liftCurrentPosition <= zeroDist && liftSpeed < -0.02))) {
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