package org.firstinspires.ftc.teamcode.powerPlay;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="teleOp competition", group = "PowerPlay")
public class teleOp extends LinearOpMode {

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    DcMotor motorLift;

    Servo servoClaw;

    double drive;
    double strafe;
    double turn;

    double FrontRight;
    double FrontLeft;
    double BackRight;
    double BackLeft;

    double linearLift;
    boolean waitingForLiftEncoder = false;

    double claw;

    public void runOpMode () {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft  = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackLeft   = hardwareMap.get(DcMotor.class, "motor_back_left");
        motorBackRight  = hardwareMap.get(DcMotor.class, "motor_back_right");

        motorLift = hardwareMap.get(DcMotor.class, "motor_lift");

        servoClaw = hardwareMap.get(Servo.class, "servo_claw");

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (opModeIsActive()) {

            // movement

            drive = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            turn = gamepad1.right_stick_x;

            FrontRight = Range.clip(drive - turn - strafe, -1.0, 1.0);
            FrontLeft = Range.clip(drive + turn + strafe, -1.0, 1.0);
            BackRight = Range.clip(drive - turn + strafe, -1.0, 1.0);
            BackLeft = Range.clip(drive + turn - strafe, -1.0, 1.0);

            motorFrontRight.setPower(FrontRight);
            motorFrontLeft.setPower(FrontLeft);
            motorBackRight.setPower(BackRight);
            motorBackLeft.setPower(BackLeft);

            // linear lift

            linearLift = -gamepad2.left_stick_y;
            motorLift.setPower(linearLift);

            if (gamepad2.a || gamepad2.b || gamepad2.y) {

                double speed = 0.5;
                double inches = 0;

                if (gamepad2.y) inches = 33.5;
                else if (gamepad2.b) inches = 23.5;
                else if (gamepad2.a) inches = 13.5;

                motorLift.setTargetPosition((int)inchToTics(inches));
                motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorLift.setPower(speed);

                waitingForLiftEncoder = true;

            }

            if (waitingForLiftEncoder && !motorLift.isBusy()) {
                motorLift.setPower(0);
                motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                waitingForLiftEncoder = false;
            }

            // claw

            claw = -gamepad2.right_stick_y;
            servoClaw.setPosition(claw);

            if (gamepad2.dpad_up) servoClaw.setPosition(0.33); // Test this later, we don't know the amount
            if (gamepad2.dpad_down) servoClaw.setPosition(0);

        }

    }

    public double inchToTics(double inches) {

        final double COUNTS_PER_REVOLUTION    = 28.0;
        final double GEAR_RATIO               = 20.0;
        final double WHEEL_DIAMETER_IN_INCHES = 1.0;
        final double COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION * GEAR_RATIO) / (WHEEL_DIAMETER_IN_INCHES * Math.PI);

        return inches * COUNTS_PER_INCH;

    }

}