package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

        motor = hardwareMap.get(DcMotor.class, "motor");

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        // Movement is relative, b/c encoders are not reset

        int target = motor.getCurrentPosition() + (inches * COUNTS_PER_INCH);

        motor.setTargetPosition(target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0.5);

        while (opModeIsActive() && motor.isBusy()) {
            telemetry.addData("Target", target);
            telemetry.addData("Current position", motor.getCurrentPosition());
            telemetry.update();
        }

        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

}