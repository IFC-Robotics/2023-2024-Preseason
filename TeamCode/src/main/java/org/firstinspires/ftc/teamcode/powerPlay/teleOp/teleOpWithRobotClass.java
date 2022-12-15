package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

@TeleOp(name="teleOp with Robot Class", group="powerPlay")
public class teleOpWithRobotClass extends OpMode {

    // drivetrain variables

    double drive;
    double strafe;
    double turn;

    double frontRightSpeed;
    double frontLeftSpeed;
    double backRightSpeed;
    double backLeftSpeed;

    // lift variables

    double linearLift;
    double linearLiftSpeed;
    double liftCurrentPosition;

    boolean waitingForLiftEncoder = false;
    boolean enableLiftLimits = true;

    // claw variables

    double clawActualPosition;
    double claw;

    boolean dpadButtonsPressed = false;

    // other

    robotClass robot = new robotClass();

    @Override
    public void init () { robot.init(hardwareMap); }

    @Override
    public void start() { clawActualPosition = robot.servoClaw.getPosition(); }

    @Override
    public void loop() {

        // movement

        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        frontRightSpeed = Range.clip(drive - turn - strafe, -robot.MAX_DRIVE_SPEED, robot.MAX_DRIVE_SPEED);
        frontLeftSpeed = Range.clip(drive + turn + strafe, -robot.MAX_DRIVE_SPEED, robot.MAX_DRIVE_SPEED);
        backRightSpeed = Range.clip(drive - turn + strafe, -robot.MAX_DRIVE_SPEED, robot.MAX_DRIVE_SPEED);
        backLeftSpeed = Range.clip(drive + turn - strafe, -robot.MAX_DRIVE_SPEED, robot.MAX_DRIVE_SPEED);

        robot.motorFrontRight.setPower(frontRightSpeed);
        robot.motorFrontLeft.setPower(frontLeftSpeed);
        robot.motorBackRight.setPower(backRightSpeed);
        robot.motorBackLeft.setPower(backLeftSpeed);

        // linear lift

        if (!waitingForLiftEncoder) {

            linearLift = -gamepad2.right_stick_y;
            linearLiftSpeed = Range.clip(linearLift, -robot.LIFT_SPEED, robot.LIFT_SPEED);
            liftCurrentPosition = robot.motorLift.getCurrentPosition();

            if (enableLiftLimits && ((liftCurrentPosition > robot.MAX_LIFT_HEIGHT && linearLiftSpeed > 0) || (liftCurrentPosition < robot.MIN_LIFT_HEIGHT && linearLiftSpeed < 0))) {
                linearLiftSpeed = 0;
            }

            robot.motorLift.setPower(linearLiftSpeed);

        }

        if (gamepad2.right_bumper) {
            enableLiftLimits = !enableLiftLimits;
        }

        if (gamepad2.left_bumper) {
            robot.motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        telemetry.addData("enableLiftLimits", enableLiftLimits);
        telemetry.addData("MAX_LIFT_HEIGHT", robot.MAX_LIFT_HEIGHT);
        telemetry.addData("lift position", robot.motorLift.getCurrentPosition());
        telemetry.update();

        if (gamepad2.a || gamepad2.b || gamepad2.y || gamepad2.x) {

            int tics = 0;

            if (gamepad2.y) tics = 3000; // 33.5 inches
            if (gamepad2.b) tics = 2300; // 23.5 inches
            if (gamepad2.a) tics = 1500; // 13.5 inches
            if (gamepad2.x) tics = 0;

            robot.motorLift.setTargetPosition(tics);
            robot.motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorLift.setPower(robot.LIFT_SPEED);

            waitingForLiftEncoder = true;

        }

        if (waitingForLiftEncoder && !robot.motorLift.isBusy()) {
            robot.motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            waitingForLiftEncoder = false;
        }

        // claw

        if (!dpadButtonsPressed) {

            claw = -gamepad2.left_stick_y;

            if (claw < 0 && clawActualPosition < robot.MAX_CLAW_POSITION) {
                clawActualPosition += robot.CLAW_SPEED;
            } else if (claw > 0 && clawActualPosition > robot.MIN_CLAW_POSITION) {
                clawActualPosition -= robot.CLAW_SPEED;
            }

        }

        if (gamepad2.dpad_up || gamepad2.dpad_down) {
            if (gamepad2.dpad_up)   clawActualPosition = robot.MAX_CLAW_POSITION;
            if (gamepad2.dpad_down) clawActualPosition = robot.MIN_CLAW_POSITION;
            dpadButtonsPressed = true;
        }

        if (dpadButtonsPressed && robot.servoClaw.getPosition() == clawActualPosition) {
            dpadButtonsPressed = false;
        }

        robot.servoClaw.setPosition(clawActualPosition);

    }

}