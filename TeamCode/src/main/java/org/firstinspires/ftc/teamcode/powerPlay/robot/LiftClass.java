package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

import static java.lang.Thread.sleep;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LiftClass {

    Telemetry telemetry;

    public DcMotor motor;
    public boolean liftIsMoving = false;

    public String NAME;
    public double MIN_POSITION;
    public double MAX_POSITION;
    public double MAX_SPEED;
    public double COUNTS_PER_INCH;

    public double horizontalLiftMinPosition = 0; // starting position
    public double horizontalLiftMaxPosition = 16.0; // collecting position
    public boolean horizontalLiftReverseDirection = false;

    public double verticalLiftPosition1 = 0.0; // starting position
    public double verticalLiftPosition2 = 12.0; // ground junction (if this is the same as starting position, then ignore this value and let Charlie know.)
    public double verticalLiftPosition3 = 20.0; // low junction
    public double verticalLiftPosition4 = 35.0; // middle junction
    public double verticalLiftPosition5 = 50.0; // high junction
    public boolean verticalLiftReverseDirection = true;

    public LiftClass() {}

    public void init(HardwareMap hardwareMap, Telemetry telemetryParameter, String name, double maxSpeed,  double countsPerInch) {

        NAME = name;
        MAX_SPEED = maxSpeed;
        COUNTS_PER_INCH = countsPerInch;

        motor = hardwareMap.get(DcMotor.class, NAME);

        if (NAME == "motor_horizontal_lift" && horizontalLiftReverseDirection) motor.setDirection(DcMotor.Direction.REVERSE);
        if (NAME == "motor_vertical_lift"   && verticalLiftReverseDirection)   motor.setDirection(DcMotor.Direction.REVERSE);

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry = telemetryParameter;

    }

    // autonomous

    public void autonomousRunToPosition(String position) {

        if (NAME == "motor_horizontal_lift") {

            if(position == "transfer") run(horizontalLiftMinPosition);
            if(position == "collect")  run(horizontalLiftMaxPosition);

        } else if (NAME == "motor_vertical_lift") {

            if(position == "transfer") run(verticalLiftPosition1);
            if(position == "ground")   run(verticalLiftPosition2);
            if(position == "low")      run(verticalLiftPosition3);
            if(position == "middle")   run(verticalLiftPosition4);
            if(position == "high")     run(verticalLiftPosition5);

        }

        while (motor.isBusy()) {
            telemetry.addLine(String.format("%1$s is at position %2$s", NAME, motor.getCurrentPosition()));
            telemetry.update();
        }

        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    // teleOp

    public void teleOpHorizontalLiftAssistMode(boolean button1, boolean button2) {
        if(button1) teleOpAssistMode(horizontalLiftMinPosition);
        if(button2) teleOpAssistMode(horizontalLiftMaxPosition);
    }

    public void teleOpVerticalLiftAssistMode(boolean button1, boolean button2, boolean button3, boolean button4, boolean button5) {
        if(button1) teleOpAssistMode(verticalLiftPosition1);
        if(button2) teleOpAssistMode(verticalLiftPosition2);
        if(button3) teleOpAssistMode(verticalLiftPosition3);
        if(button4) teleOpAssistMode(verticalLiftPosition4);
        if(button5) teleOpAssistMode(verticalLiftPosition5);
    }

    public void teleOpAssistMode(double target) {

        telemetry.addLine(String.format("running %1$s to target %2$s", NAME, target));

        run(target);
        liftIsMoving = true;

        if (liftIsMoving && !motor.isBusy()) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftIsMoving = false;
        }

    }

    public void teleOpManualMode(double joystick) {

        if (!liftIsMoving) {

            double liftSpeed = Range.clip(joystick, -MAX_SPEED, MAX_SPEED);
            int liftCurrentPosition = motor.getCurrentPosition();

            if ((liftCurrentPosition > MAX_POSITION && liftSpeed > 0) || (liftCurrentPosition < MIN_POSITION && liftSpeed < 0)) {
                liftSpeed = 0;
            }

            telemetry.addLine(String.format("running %1$s with speed %2$s", NAME, liftSpeed));

            motor.setPower(liftSpeed);

        }

    }

    public void teleOpNoEncoderLimits(double joystick) {
        if (!liftIsMoving) {
            motor.setPower(Range.clip(joystick, -MAX_SPEED, MAX_SPEED));
        }
    }

    // helper methods

    public void run(double target) {
        motor.setTargetPosition((int)(target * COUNTS_PER_INCH));
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(MAX_SPEED);
    }

}