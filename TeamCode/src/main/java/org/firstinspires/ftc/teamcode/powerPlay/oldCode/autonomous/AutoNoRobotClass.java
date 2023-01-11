package org.firstinspires.ftc.teamcode.powerPlay.oldCode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="AutoNoRobotClass")
@Disabled
public class AutoNoRobotClass extends LinearOpMode {

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    public void runOpMode() {
        init(hardwareMap);
        waitForStart();
        drive();
    }

    public void init(HardwareMap hardwareMap) {

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft  = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackRight  = hardwareMap.get(DcMotor.class, "motor_back_right");
        motorBackLeft   = hardwareMap.get(DcMotor.class, "motor_back_left");

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void drive() {

        int target = 500;
        double power = 0.3;

        motorFrontRight.setTargetPosition(target);
        motorFrontLeft.setTargetPosition(target);
        motorBackRight.setTargetPosition(target);
        motorBackLeft.setTargetPosition(target);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        motorBackLeft.setPower(power);

        while (motorBackRight.isBusy() && opModeIsActive()) {}

        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

}