package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Practice Autonomous", group="Linear Opmode")

public class PracticeAuton extends LinearOpMode {

    // Declare OpMode members.
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backLeftDrive;
    DcMotor backRightDrive;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. 
        frontLeftDrive  = hardwareMap.get(DcMotor.class, "motor_front_left");
        frontRightDrive = hardwareMap.get(DcMotor.class, "motor_front_right");
        backLeftDrive   = hardwareMap.get(DcMotor.class, "motor_back_left");
        backRightDrive  = hardwareMap.get(DcMotor.class, "motor_back_right");

        
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITH_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITH_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITH_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITH_ENCODER);

        frontLeftDrive.setTarget(2000);
        frontRightDrive.setTarget(2000);
        backLeftDrive.setTarget(2000);
        backRightDrive.setTarget(2000);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        telemetry.addLine("Running autonomous");
        telemetry.update();

        frontLeftDrive.setPower(0.5);
        frontRightDrive.setPower(0.5);
        backLeftDrive.setPower(0.5);
        backRightDrive.setPower(0.5);
        
    }
}