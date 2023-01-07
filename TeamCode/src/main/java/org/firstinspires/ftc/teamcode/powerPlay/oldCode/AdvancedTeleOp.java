package org.firstinspires.ftc.teamcode.powerPlay.oldCode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Advanced TeleOp Code")
@Disabled
public class AdvancedTeleOp extends LinearOpMode {

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    DcMotor motorClaw;
    Servo servoClaw;

    DcMotor motorLift;
    Servo servoHook;

    double FrontRight;
    double FrontLeft;
    double BackRight;
    double BackLeft;

    double drive;
    double strafe;
    double turn;

    double MAX_SPEED = 0.5;

    public void runOpMode (){

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackLeft = hardwareMap.get(DcMotor.class, "motor_back_left");
        motorBackRight = hardwareMap.get(DcMotor.class, "motor_back_right");

        motorClaw = hardwareMap.get(DcMotor.class, "motor_rotation_claw");
        servoClaw = hardwareMap.get(Servo.class, "servo_claw");
        servoHook = hardwareMap.get(Servo.class, "servo_hook");
        motorLift = hardwareMap.get(DcMotor.class, "motor_lift");

        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

        motorLift.setDirection(DcMotor.Direction.REVERSE);
        servoHook.setDirection(Servo.Direction.REVERSE);

        waitForStart();

        double hookInitialPosition = servoHook.getPosition();

        while(opModeIsActive()) {

            drive = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            turn = gamepad1.right_stick_x;

            FrontRight = Range.clip(drive - turn - strafe, -MAX_SPEED, MAX_SPEED);
            FrontLeft = Range.clip(drive + turn + strafe, -MAX_SPEED, MAX_SPEED);
            BackRight = Range.clip(drive - turn + strafe, -MAX_SPEED, MAX_SPEED);
            BackLeft = Range.clip(drive + turn - strafe, -MAX_SPEED, MAX_SPEED);

            motorFrontRight.setPower(FrontRight);
            motorFrontLeft.setPower(FrontLeft);
            motorBackRight.setPower(BackRight);
            motorBackLeft.setPower(BackLeft);

            // opening/closing hook

            if (gamepad2.b) {
                servoHook.setPosition(hookInitialPosition);
            } else if(gamepad2.x) {
                servoHook.setPosition(hookInitialPosition + 30);
            }

            // raising/lowering linear lift

            double lift = -gamepad2.right_stick_y;
            double liftSpeed = Range.clip(lift, -0.5, 0.5);
            motorLift.setPower(liftSpeed);

        }

    }

}