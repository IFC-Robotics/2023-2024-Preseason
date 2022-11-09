package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.Math;

@Autonomous(name="Autonomous w/ Encoder", group="Examples")
@Disabled
public class autonomousWithEncoder extends LinearOpMode {

    private DcMotor motor;

    static final double COUNTS_PER_REVOLUTION    = 28.0;
    static final double GEAR_RATIO               = 1.0;
    static final double WHEEL_DIAMETER_IN_INCHES = 1.0;
    static final double COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION * GEAR_RATIO) / (WHEEL_DIAMETER_IN_INCHES * Math.PI);

    @Override
    public void runOpMode() {

        motor = hardwareMap.get(DcMotor.class, "motor_one");

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        int inches = 96;
        double power = 0.5;
        double target = inches * COUNTS_PER_INCH;

        motor.setTargetPosition((int)(target));
        
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);

        while (opModeIsActive() && motor.isBusy()) {
            telemetry.addData("Target", target);
            telemetry.addData("Current position", motor.getCurrentPosition());
            telemetry.update();
        }

        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

}
