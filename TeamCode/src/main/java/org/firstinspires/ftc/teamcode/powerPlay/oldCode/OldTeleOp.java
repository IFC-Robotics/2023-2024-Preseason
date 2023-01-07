package org.firstinspires.ftc.teamcode.powerPlay.oldCode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="old teleOp")
@Disabled
public class OldTeleOp extends OpMode {

    // drivetrain variables

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    double drive;
    double strafe;
    double turn;

    double frontRightSpeed;
    double frontLeftSpeed;
    double backRightSpeed;
    double backLeftSpeed;

    final double MAX_DRIVE_SPEED = 0.7;

    // lift variables

    DcMotor motorLift;

    double linearLift;
    double linearLiftSpeed;
    double liftCurrentPosition;

    boolean waitingForLiftEncoder = false;
    boolean enableLiftLimits = true;

    final double MAX_LIFT_HEIGHT = 3300.0;
    final double MIN_LIFT_HEIGHT = 0.0;
    final double LIFT_SPEED = 0.3;

    // claw variables

    Servo servoClaw;

    double clawActualPosition;
    double claw;

    boolean dpadButtonsPressed = false;

    final double MAX_SERVO_CLAW_POSITION = 0.40; // increase this
    final double CLAW_SPEED = 0.002;

    @Override
    public void init () {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft  = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackLeft   = hardwareMap.get(DcMotor.class, "motor_back_left");
        motorBackRight  = hardwareMap.get(DcMotor.class, "motor_back_right");

        motorLift = hardwareMap.get(DcMotor.class, "motor_lift");
        servoClaw = hardwareMap.get(Servo.class, "servo_claw");

        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);

        motorLift.setDirection(DcMotor.Direction.REVERSE);
        servoClaw.setDirection(Servo.Direction.REVERSE);

        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    @Override
    public void start() { clawActualPosition = servoClaw.getPosition(); }

    @Override
    public void loop() {

        // movement

        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        frontRightSpeed = Range.clip(drive - turn - strafe, -MAX_DRIVE_SPEED, MAX_DRIVE_SPEED);
        frontLeftSpeed = Range.clip(drive + turn + strafe, -MAX_DRIVE_SPEED, MAX_DRIVE_SPEED);
        backRightSpeed = Range.clip(drive - turn + strafe, -MAX_DRIVE_SPEED, MAX_DRIVE_SPEED);
        backLeftSpeed = Range.clip(drive + turn - strafe, -MAX_DRIVE_SPEED, MAX_DRIVE_SPEED);

        motorFrontRight.setPower(frontRightSpeed);
        motorFrontLeft.setPower(frontLeftSpeed);
        motorBackRight.setPower(backRightSpeed);
        motorBackLeft.setPower(backLeftSpeed);

        // linear lift

        if (!waitingForLiftEncoder) {

            linearLift = -gamepad2.right_stick_y;
            linearLiftSpeed = Range.clip(linearLift, -LIFT_SPEED, LIFT_SPEED);
            liftCurrentPosition = motorLift.getCurrentPosition();

            if (enableLiftLimits && ((liftCurrentPosition > MAX_LIFT_HEIGHT && linearLiftSpeed > 0) || (liftCurrentPosition < MIN_LIFT_HEIGHT && linearLiftSpeed < 0))) {
                linearLiftSpeed = 0;
            }

            motorLift.setPower(linearLiftSpeed);

        }

        if (gamepad2.right_bumper) {
            enableLiftLimits = !enableLiftLimits;
        }

        if (gamepad2.left_bumper) {
            motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        telemetry.addData("enableLiftLimits", enableLiftLimits);
        telemetry.addData("MAX_LIFT_HEIGHT", MAX_LIFT_HEIGHT);
        telemetry.addData("lift position", motorLift.getCurrentPosition());
        telemetry.update();

        if (gamepad2.a || gamepad2.b || gamepad2.y || gamepad2.x) {

            int tics = 0;

            if (gamepad2.y) tics = 3000; // 33.5 inches
            if (gamepad2.b) tics = 2300; // 23.5 inches
            if (gamepad2.a) tics = 1500; // 13.5 inches
            if (gamepad2.x) tics = 0;

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

        if (!dpadButtonsPressed) {

            claw = -gamepad2.left_stick_y;

            if (claw < 0 && clawActualPosition < MAX_SERVO_CLAW_POSITION) {
                clawActualPosition += CLAW_SPEED;
            } else if (claw > 0 && clawActualPosition > 0) {
                clawActualPosition -= CLAW_SPEED;
            }

        }

        if (gamepad2.dpad_up || gamepad2.dpad_down) {

            if (gamepad2.dpad_up)   clawActualPosition = MAX_SERVO_CLAW_POSITION;
            if (gamepad2.dpad_down) clawActualPosition = 0;

            dpadButtonsPressed = true;

        }

        if (dpadButtonsPressed && servoClaw.getPosition() == clawActualPosition) {
            dpadButtonsPressed = false;
        }

        servoClaw.setPosition(clawActualPosition);

    }

}