package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.lang.Math;

@TeleOp(name="test teleOp")
public class TestTeleOp extends LinearOpMode {

    public static Servo servoClaw;
    double servoClawPosition = 0;
    boolean servoClawIsMoving = false;
    public static final double CLAW_OPEN_POSITION = 0.0; // to test
    public static final double CLAW_CLOSE_POSITION = 0.2; // to test

    public static Servo servoRotateClaw;
    double servoRotateClawPosition = 0;
    boolean servoRotateClawIsMoving = false;
    public static final double CLAW_UP_POSITION = 0.0; // to test
    public static final double CLAW_DOWN_POSITION = 0.3; // to test

//    public static DcMotor motorHorizontalLift;
//    boolean motorHorizontalLiftIsMoving = false;
//    public static final int HORIZONTAL_LIFT_MIN_DIST = 0; // to test
//    public static final int HORIZONTAL_LIFT_MAX_DIST = 1000; // to test
//    public static final double HORIZONTAL_LIFT_SPEED = 0.5; // to test

//    boolean assistMode = false;

    int test = 2;

    @Override
    public void runOpMode() {

        if (test == 1) {

            servoClaw = hardwareMap.get(Servo.class, "servo_claw");
//            servoClaw.setDirection(Servo.Direction.REVERSE); // to test

            telemetry.addData("servoClaw.getPosition()", servoClaw.getPosition());
            telemetry.update();
            sleep(1000);

        } else if (test == 2) {

            servoRotateClaw = hardwareMap.get(Servo.class, "servo_rotate_claw");
            servoRotateClaw.setDirection(Servo.Direction.REVERSE); // to test

            telemetry.addData("servoRotateClaw.getPosition()", servoRotateClaw.getPosition());
            telemetry.update();
            sleep(1000);

        }

//        } else if (test == 3) {
//
//            motorHorizontalLift = hardwareMap.get(DcMotor.class, "motor_horizontal_lift");
//            motorHorizontalLift.setDirection(DcMotor.Direction.REVERSE); // to test
//            motorHorizontalLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            motorHorizontalLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        }

        waitForStart();

        while (opModeIsActive()) {

            if (test == 1) {

                if (gamepad2.dpad_left  && servoClawPosition > CLAW_OPEN_POSITION)  servoClawPosition -= 0.01; // test this value
                if (gamepad2.dpad_right && servoClawPosition < CLAW_CLOSE_POSITION) servoClawPosition += 0.01; // test this value

                servoClaw.setPosition(servoClawPosition);

                telemetry.addData("servoClawPosition", servoClawPosition);
                telemetry.addData("gamepad2.dpad_left", gamepad2.dpad_left);
                telemetry.addData("gamepad2.dpad_right", gamepad2.dpad_right);
                telemetry.addData("servoClaw.getPosition()", servoClaw.getPosition());
                telemetry.update();

            } else if (test == 2) {

                if (gamepad2.dpad_up && servoRotateClawPosition > CLAW_UP_POSITION) {
                    servoRotateClawPosition -= 0.001; // test this value
                    servoRotateClaw.setPosition(servoRotateClawPosition);
                } else if (gamepad2.dpad_down && servoRotateClawPosition < CLAW_DOWN_POSITION) {
                    servoRotateClawPosition += 0.001; // test this value
                    servoRotateClaw.setPosition(servoRotateClawPosition);
                }

                telemetry.addData("servoRotateClawPosition", servoRotateClawPosition);
                telemetry.addData("gamepad2.dpad_up", gamepad2.dpad_up);
                telemetry.addData("gamepad2.dpad_down", gamepad2.dpad_down);
                telemetry.addData("servoRotateClaw.getPosition()", servoRotateClaw.getPosition());
                telemetry.update();

            }

//            } else if (test == 3) {
//
//                double motorSpeed = Range.clip(-gamepad1.left_stick_y, -HORIZONTAL_LIFT_SPEED, HORIZONTAL_LIFT_SPEED);
//                int motorCurrentPosition = motorHorizontalLift.getCurrentPosition();
//
//                telemetry.addData("-gamepad1.left_stick_y", -gamepad1.left_stick_y);
//                telemetry.addData("motorSpeed", motorSpeed);
//                telemetry.addData("motorCurrentPosition", motorCurrentPosition);
//
//                if ((motorCurrentPosition > HORIZONTAL_LIFT_MAX_DIST && motorSpeed > 0) ||
//                    (motorCurrentPosition < HORIZONTAL_LIFT_MIN_DIST && motorSpeed < 0)) {
//                    motorSpeed = 0;
//                }
//
//                telemetry.addData("motorCurrentPosition > HORIZONTAL_LIFT_MAX_DIST && motorSpeed > 0", motorCurrentPosition > HORIZONTAL_LIFT_MAX_DIST && motorSpeed > 0);
//                telemetry.addData("motorCurrentPosition < HORIZONTAL_LIFT_MIN_DIST && motorSpeed < 0", motorCurrentPosition < HORIZONTAL_LIFT_MIN_DIST && motorSpeed < 0);
//                telemetry.addData("motorSpeed", motorSpeed);
//
//                motorHorizontalLift.setPower(motorSpeed);
//
//                telemetry.addData("motorHorizontalLift.isBusy()", motorHorizontalLift.isBusy());
//                telemetry.addData("motorHorizontalLift.getCurrentPosition()", motorHorizontalLift.getCurrentPosition());
//                telemetry.update();
//
//            }

//            controlServo(servoClaw, servoClawPosition, servoClawIsMoving, gamepad2.dpad_left, gamepad2.dpad_right);
//            controlServo(robot.servoRotateClaw, servoRotateClawPosition, servoRotateClawIsMoving, gamepad2.dpad_up, gamepad2.dpad_down);
//            controlMotor(robot.motorHorizontalLift, motorHorizontalLiftIsMoving, -gamepad2.left_stick_y, robot.HORIZONTAL_LIFT_MIN_DIST, robot.HORIZONTAL_LIFT_MAX_DIST, robot.HORIZONTAL_LIFT_SPEED, gamepad2.left_bumper, gamepad2.right_bumper, false, false, false, 0.0, 18.0, 0.0, 0.0, 0.0); // change values (18 -> distance to auto collect)

        }

    }

//    public void controlServo(Servo servo, double servoPosition, boolean servoIsMoving, boolean button1, boolean button2) {
//
//        if (assistMode) {
//
//            if (button1 || button2) {
//                if (button1) servo.setPosition(0);
//                if (button2) servo.setPosition(1);
//                servoIsMoving = true;
//            }
//
//        } else {
//
//            if (!servoIsMoving) {
//                if (button1 && servoPosition > 0) servoPosition -= 0.001; // test this value
//                if (button2 && servoPosition < 1) servoPosition += 0.001; // test this value
//            }
//
//            servo.setPosition(servoPosition);
//
//        }
//
//        // remember that servo.getPosition() returns the position that the servo is currently moving to (target position)
//
//        if (servoIsMoving && servo.getPosition() == servoPosition) {
//            servoIsMoving = false;
//        }
//
//    }
//
//    public void controlMotor(DcMotor motor, boolean motorIsMoving, double joystick, int motorMinDist, int motorMaxDist, double motorMaxSpeed, boolean button1, boolean button2, boolean button3, boolean button4, boolean button5, double button1Dist, double button2Dist, double button3Dist, double button4Dist, double button5Dist) {
//
//        if (!motorIsMoving) {
//
//            double motorSpeed = Range.clip(joystick, -motorMaxSpeed, motorMaxSpeed);
//            int motorCurrentPosition = motor.getCurrentPosition();
//
//            if ((motorCurrentPosition > motorMaxDist && motorSpeed > 0) || (motorCurrentPosition < motorMinDist && motorSpeed < 0)) {
//                motorSpeed = 0;
//            }
//
//            motor.setPower(motorSpeed);
//
//        }
//
//        if (button1 || button2 || button3 || button4 || button5) {
//
//            if (button1) motor.setTargetPosition((int) button1Dist * robot.LIFT_COUNTS_PER_INCH);
//            if (button2) motor.setTargetPosition((int) button2Dist * robot.LIFT_COUNTS_PER_INCH);
//            if (button3) motor.setTargetPosition((int) button3Dist * robot.LIFT_COUNTS_PER_INCH);
//            if (button4) motor.setTargetPosition((int) button4Dist * robot.LIFT_COUNTS_PER_INCH);
//            if (button5) motor.setTargetPosition((int) button5Dist * robot.LIFT_COUNTS_PER_INCH);
//
//            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            motor.setPower(motorMaxSpeed);
//            motorIsMoving = true;
//
//        }
//
//        if (motorIsMoving && !motor.isBusy()) {
//            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            motorIsMoving = false;
//        }
//
//    }

}