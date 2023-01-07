package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class LiftSubsystem extends LinearOpMode {

    public static DcMotor motor;
    public static boolean liftIsMoving = false;

    public static String NAME;
    public static double MIN_POSITION;
    public static double MAX_POSITION;
    public static double MAX_SPEED;
    public static int COUNTS_PER_INCH;
    public static String[] PRESET_POSITION_NAMES;
    public static double[] PRESET_POSITIONS;

    // initialize

    public LiftSubsystem() {}

    @Override
    public void runOpMode() {}

    public void init(HardwareMap hardwareMap, String name, int minPosition, int maxPosition, double maxSpeed, boolean reverseDirection, int countsPerInch, String initialMode, String[] presetPositionNames, double[] presetPositions) {

        NAME = name;
        MIN_POSITION = minPosition;
        MAX_POSITION = maxPosition;
        MAX_SPEED = maxSpeed;
        COUNTS_PER_INCH = countsPerInch;
        PRESET_POSITION_NAMES = presetPositionNames;
        PRESET_POSITIONS = presetPositions;

        motor = hardwareMap.get(DcMotor.class, NAME);

        if (reverseDirection) motor.setDirection(DcMotor.Direction.REVERSE);

        if (initialMode == "stop & reset, run") {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }

    // teleOp

    public void executeTeleOp(boolean assistMode, double joystick, boolean[] buttons) {

        if (assistMode) {

            for (int i = 0; i < PRESET_POSITIONS.length; i++) {
                if (buttons[i]) {

                    motor.setTargetPosition((int) PRESET_POSITIONS[i] * COUNTS_PER_INCH);
                    motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motor.setPower(MAX_SPEED);
                    liftIsMoving = true;

                    break;

                }
            }

            if (liftIsMoving && !motor.isBusy()) {
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftIsMoving = false;
            }

        } else if (!liftIsMoving) {

            double liftSpeed = Range.clip(joystick, -MAX_SPEED, MAX_SPEED);
            int liftCurrentPosition = motor.getCurrentPosition();

            if ((liftCurrentPosition > MAX_POSITION && liftSpeed > 0) || (liftCurrentPosition < MIN_POSITION && liftSpeed < 0)) {
                liftSpeed = 0;
            }

            motor.setPower(liftSpeed);

        }

    }

    // autonomous

    public void moveLift(String direction) {

        for (int i = 0; i < PRESET_POSITIONS.length; i++) {
            if (direction == PRESET_POSITION_NAMES[i]) {

                motor.setTargetPosition((int) PRESET_POSITIONS[i] * COUNTS_PER_INCH);
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor.setPower(MAX_SPEED);
                liftIsMoving = true;

                while (motor.isBusy() && opModeIsActive()) {}

                motor.setPower(0);
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                break;

            }
        }

    }

}