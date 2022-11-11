package org.firstinspires.ftc.teamcode.powerPlay;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="teleOp competition", group = "PowerPlay")
public class teleOp extends OpMode {

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    DcMotor motorLift;

    Servo servoClaw;

    double drive;
    double strafe;
    double turn;

    double frontRightSpeed;
    double frontLeftSpeed;
    double backRightSpeed;
    double backLeftSpeed;

    double linearLiftSpeed;
    double servoClawPosition;

    double linearLift;
    boolean waitingForLiftEncoder = false;

    double claw;

    final double MAX_DRIVETRAIN_SPEED = 0.7;

    final double LIFT_SPEED = 0.3;

    final double MAX_LIFT_HEIGHT = 3300.0;
    final double MIN_LIFT_HEIGHT = 0.0;

    @Override
    public void init () {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackLeft = hardwareMap.get(DcMotor.class, "motor_back_left");
        motorBackRight = hardwareMap.get(DcMotor.class, "motor_back_right");

        motorLift = hardwareMap.get(DcMotor.class, "motor_lift");

        servoClaw = hardwareMap.get(Servo.class, "servo_claw");

        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorLift.setDirection(DcMotor.Direction.REVERSE);

        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

//    @Override
//    public void start() {
//        servoClaw.setPosition(0);
//    }

    @Override
    public void loop() {

        // movement

        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        frontRightSpeed = Range.clip(drive - turn - strafe, -MAX_DRIVETRAIN_SPEED, MAX_DRIVETRAIN_SPEED);
        frontLeftSpeed = Range.clip(drive + turn + strafe, -MAX_DRIVETRAIN_SPEED, MAX_DRIVETRAIN_SPEED);
        backRightSpeed = Range.clip(drive - turn + strafe, -MAX_DRIVETRAIN_SPEED, MAX_DRIVETRAIN_SPEED);
        backLeftSpeed = Range.clip(drive + turn - strafe, -MAX_DRIVETRAIN_SPEED, MAX_DRIVETRAIN_SPEED);

        motorFrontRight.setPower(frontRightSpeed);
        motorFrontLeft.setPower(frontLeftSpeed);
        motorBackRight.setPower(backRightSpeed);
        motorBackLeft.setPower(backLeftSpeed);

        // linear lift joystick

        if (!waitingForLiftEncoder) {

            linearLift = gamepad2.left_stick_y;
            linearLiftSpeed = Range.clip(linearLift, -LIFT_SPEED, LIFT_SPEED);
            motorLift.setPower(linearLiftSpeed);

//            if ((motorLift.getCurrentPosition() >= MAX_LIFT_HEIGHT && linearLift < 0) || (motorLift.getCurrentPosition() <= MIN_LIFT_HEIGHT && linearLift > 0)) {
//                linearLiftSpeed = Range.clip(linearLift, -LIFT_SPEED, LIFT_SPEED);
//                motorLift.setPower(linearLiftSpeed);
//            }

        }

        // linear lift buttons

        if (gamepad2.a || gamepad2.b || gamepad2.y || gamepad2.x) {

            int tics = 0;

            if (gamepad2.y) {
                tics = 3000; // 33.5 inches
            } else if (gamepad2.b) {
                tics = 2300; // 23.5 inches
            } else if (gamepad2.a) {
                tics = 1500; // 13.5 inches
            } else if (gamepad2.x) {
                tics = 0;
            }

            motorLift.setTargetPosition(tics);
            motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorLift.setPower(LIFT_SPEED);

            waitingForLiftEncoder = true;

        }

        if (waitingForLiftEncoder && !motorLift.isBusy()) {
            motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            waitingForLiftEncoder = false;
        }

        // claw

//        claw = -gamepad2.right_stick_y;
//        servoClawPosition = Range.clip(claw, 0, 1);
//        servoClaw.setPosition(servoClawPosition);

        telemetry.addData("claw joystick position", servoClawPosition);
        telemetry.addData("claw position", servoClaw.getPosition());
        telemetry.update();

//        if (gamepad2.dpad_up) {
//            servoClaw.setPosition(1);
//        } else if (gamepad2.dpad_right) {
//            servoClaw.setPosition(0.67);
//        } else if (gamepad2.dpad_down) {
//            servoClaw.setPosition(0.33);
//        } else if (gamepad2.dpad_left) {
//            servoClaw.setPosition(0);
//        }

    }

//    @Override
//    public void stop() {}

    public double inchToTics(double inches) {

        final double COUNTS_PER_REVOLUTION    = 28.0;
        final double GEAR_RATIO               = 20.0;
        final double WHEEL_DIAMETER_IN_INCHES = 4.0;
        final double COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION * GEAR_RATIO) / (WHEEL_DIAMETER_IN_INCHES * Math.PI); // 44.5633841

        return inches * COUNTS_PER_INCH;

    }

}