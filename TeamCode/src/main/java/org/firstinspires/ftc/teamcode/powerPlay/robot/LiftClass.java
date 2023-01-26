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
    public double SLEEP_TIME;

    // zero
    // driving & ground junction
    // cone 1 from cone stack
    // cone 2 from cone stack
    // cone 3 from cone stack
    // cone 4 from cone stack
    // cone 5 from cone stack
    // low junction
    // middle junction
    // high junction

    // idea: have a sensor/camera in the hook, so that it knows when it can pick up a cone and will do it automatically

    double zeroDist = 0;
    double drivingDist = 1550;
    double lowDist = 3550;
    double middleDist = 3750;
    double highDist = 7100;

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

        if (enableEncoderLimits && ((liftCurrentPosition >= verticalLiftPosition5 && liftSpeed > 0.02) || (liftCurrentPosition <= verticalLiftPosition1 && liftSpeed < -0.02))) {
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