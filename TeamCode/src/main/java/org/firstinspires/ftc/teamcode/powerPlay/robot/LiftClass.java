package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LiftClass {

    LinearOpMode linearOpMode;
    HardwareMap hardwareMap;
    Telemetry telemetry;

    public DcMotor motor;
    public boolean enableEncoderLimits = true;

    public String NAME;
    public double MAX_SPEED;

    public int verticalLiftPosition1 = 0; // starting position
    public int verticalLiftPosition2 = 1550; // ground junction
    public int verticalLiftPosition3 = 3550; // low junction
    public int verticalLiftPosition4 = 4750; // middle junction
    public int verticalLiftPosition5 = 7100; // high junction

    public boolean verticalLiftReverseDirection = true;

    public LiftClass() {}

    public void init(LinearOpMode opModeParam, String name, double maxSpeed) {

        linearOpMode = opModeParam;
        hardwareMap = opModeParam.hardwareMap;
        telemetry = opModeParam.telemetry;

        NAME = name;
        MAX_SPEED = maxSpeed;

        motor = hardwareMap.get(DcMotor.class, NAME);

        if (verticalLiftReverseDirection) motor.setDirection(DcMotor.Direction.REVERSE);

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    // autonomous

    public void runToPosition(String position) {
        if (position == "transfer") run(verticalLiftPosition1);
        if (position == "ground")   run(verticalLiftPosition2);
        if (position == "low")      run(verticalLiftPosition3);
        if (position == "middle")   run(verticalLiftPosition4);
        if (position == "high")     run(verticalLiftPosition5);
        if (position == "5th cone") run(12);
    }

    public void autonomousRunToPosition(String position) {

        runToPosition(position);

        while (motor.isBusy()) {
            telemetry.addLine(String.format("%1$s is at position %2$s", NAME, motor.getCurrentPosition()));
        }

        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    // teleOp

    public void teleOpAssistMode(boolean button1, boolean button2, boolean button3, boolean button4, boolean button5) {

        if (button1 || button2 || button3 || button4 || button5) {
            if (button1) run(verticalLiftPosition1);
            if (button2) run(verticalLiftPosition2);
            if (button3) run(verticalLiftPosition3);
            if (button4) run(verticalLiftPosition4);
            if (button5) run(verticalLiftPosition5);
        }

        if (!motor.isBusy()) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }

    public void teleOpManualMode(double joystick) {

        double liftSpeed = Range.clip(joystick, -MAX_SPEED, MAX_SPEED);
        int liftCurrentPosition = motor.getCurrentPosition();

        if (enableEncoderLimits && ((liftCurrentPosition >= verticalLiftPosition5 && liftSpeed > 0.02) || (liftCurrentPosition <= verticalLiftPosition1 && liftSpeed < -0.02))) {
            liftSpeed = 0;
        }

        telemetry.addLine(String.format("running %1$s with speed %2$s at (current) position %3$s", NAME, liftSpeed, liftCurrentPosition));

        motor.setPower(liftSpeed);

    }

    public void teleOpProgramEncoder(boolean button) {

//        if (button) {
//            enableLowerLiftLimit = !enableLowerLiftLimit;
//            if (enableLowerLiftLimit) {
//                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            }
//        }

    }

    // helper methods

    public void run(int target) {
        motor.setTargetPosition(target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(MAX_SPEED);
    }

}