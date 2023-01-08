package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class LiftClass extends LinearOpMode {

    public DcMotor motor;
    public boolean liftIsMoving = false;

    public String NAME;
    public double MIN_POSITION;
    public double MAX_POSITION;
    public double MAX_SPEED;
    public int COUNTS_PER_INCH;
    public String[] PRESET_POSITION_NAMES;
    public double[] PRESET_POSITIONS;

    public LiftClass() {}

    @Override
    public void runOpMode() {}

    public void init(HardwareMap hardwareMap, String name, int minPosition, int maxPosition, double maxSpeed, boolean reverseDirection, int countsPerInch, String[] presetPositionNames, double[] presetPositions) {

        NAME = name;
        MIN_POSITION = minPosition;
        MAX_POSITION = maxPosition;
        MAX_SPEED = maxSpeed;
        COUNTS_PER_INCH = countsPerInch;
        PRESET_POSITION_NAMES = presetPositionNames;
        PRESET_POSITIONS = presetPositions;

        motor = hardwareMap.get(DcMotor.class, NAME);

        if (reverseDirection) motor.setDirection(DcMotor.Direction.REVERSE);

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    // autonomous

    public void autonomousRunToPosition(String position) {
        runToPosition(position);
        while (motor.isBusy() && opModeIsActive()) {}
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runToPosition(String position) {
        for (int i = 0; i < PRESET_POSITIONS.length; i++) {
            if (position == PRESET_POSITION_NAMES[i]) {
                run(PRESET_POSITIONS[i]);
                break;
            }
        }
    }

    // teleOp

    public void teleOpAssistMode(boolean[] buttons) {

        for (int i = 0; i < PRESET_POSITIONS.length; i++) {
            if (buttons[i]) {
                run(PRESET_POSITIONS[i]);
                liftIsMoving = true;
                break;
            }
        }

        if (liftIsMoving && !motor.isBusy()) {
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

            motor.setPower(liftSpeed);

        }

    }

    // helper methods

    public void run(double target) {
        motor.setTargetPosition((int) target * COUNTS_PER_INCH);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(MAX_SPEED);
    }

}