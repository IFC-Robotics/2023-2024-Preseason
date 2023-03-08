package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Configurable Auton", group="competition")
public class NathanAuton extends LinearOpMode {
    public void runOpMode() {
        DcMotor motor;
        motor = hardwareMap.get(DcMotor.class, "motor");

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        motor.setTargetPosition(1000);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0.34);

        while (opModeIsActive() && motor.isBusy()) {}

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPosition(2000);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0.47);

        while (opModeIsActive() && motor.isBusy()) {}

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPosition(3000);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0.804);

        while (opModeIsActive() && motor.isBusy()) {}
        motor.setPower(0);
    }
}