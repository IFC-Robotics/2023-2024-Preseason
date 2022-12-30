package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

@TeleOp(name="teleOp")
public class TeleOp extends OpMode {

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

    // claw servo variables

    double servoClawActualPosition;
    double servoClawDesiredPosition;
    boolean clawIsMovingHorizontally = false;

    // claw motor variables

    boolean waitingforClawEncoder = false;

    // hook variables

    double servoHookActualPosition;
    double servoHookDesiredPosition;
    boolean hookisMoving = false;

    // rotate hook variables

    double servoRotationHookActualPosition;
    double servoRotationHookDesiredPosition;
    boolean rotationHookIsMoving = false;

    // other

    robotClass robot = new robotClass();

    @Override
    public void init () {
        robot.init(hardwareMap);
    }

    @Override
    public void start() {
        servoClawActualPosition = robot.servoClaw.getPosition();
        motorClawActualPosition = robot.motorClaw.getCurrentPosition();
    }

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

        // opening/closing claw

        if (!clawIsMovingHorizontally) {

            servoClawDesiredPosition = -gamepad2.left_stick_y;

            if (servoClawDesiredPosition < 0 && servoClawActualPosition < robot.MAX_CLAW_POSITION) {
                servoClawActualPosition += robot.CLAW_SPEED;
            } else if (servoClawDesiredPosition > 0 && servoClawActualPosition > robot.MIN_CLAW_POSITION) {
                servoClawActualPosition -= robot.CLAW_SPEED;
            }

        }

        if (gamepad2.dpad_up || gamepad2.dpad_down) {
            if (gamepad2.dpad_up)   servoClawActualPosition = robot.MAX_CLAW_POSITION;
            if (gamepad2.dpad_down) servoClawActualPosition = robot.MIN_CLAW_POSITION;
            clawIsMovingHorizontally = true;
        }

        if (clawIsMovingHorizontally && robot.servoClaw.getPosition() == servoClawActualPosition) {
            clawIsMovingHorizontally = false;
        }

        robot.servoClaw.setPosition(servoClawActualPosition);

        // raising/lowering the claw

        if (gamepad2.dpad_up || gamepad2.dpad_down) {

            int tics = 0;

            if (gamepad2.dpad_up)   tics = 0;
            if (gamepad2.dpad_down) tics = 1000;

            robot.motorClaw.setTargetPosition(tics);
            robot.motorClaw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorClaw.setPower(0.5);

            waitingForClawEncoder = true;

        }

        if (waitingForClawEncoder && !robot.motorClaw.isBusy()) {
            robot.motorClaw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            waitingForClawEncoder = false;
        }

        // extending/retracting the hook

        // double servoHookActualPosition;
        // double servoHookDesiredPosition;
        // boolean hookisMoving = false;

        // if (!hookisMoving) {

        //     servoHookDesiredPosition = -gamepad2.left_stick_y;

        //     _ MAX_HOOK_POSITION = _;
        //     _ MAX_HOOK_POSITION = _;
        //     _ MAX_HOOK_POSITION = _;

        //     if (servoHookDesiredPosition < 0 && servoHookActualPosition < MAX_HOOK_POSITION) {
        //         servoHookActualPosition += HOOK_SPEED;
        //     } else if (servoHookDesiredPosition > 0 && servoHookActualPosition > MIN_HOOK_POSITION) {
        //         servoHookActualPosition -= HOOK_SPEED;
        //     }

        // }

        // if (gamepad2.dpad_up || gamepad2.dpad_down) {
        //     if (gamepad2.dpad_up)   servoHookActualPosition = MAX_HOOK_POSITION;
        //     if (gamepad2.dpad_down) servoHookActualPosition = MIN_HOOK_POSITION;
        //     hookisMoving = true;
        // }

        // if (hookisMoving && robot.servoHook.getPosition() == servoHookActualPosition) {
        //     hookisMoving = false;
        // }

        // robot.servoHook.setPosition(servoHookActualPosition);

        // if (direction == "extend")  servoHook.setPosition(0); // MIN_HOOK_POSITION
        // if (direction == "retract") servoHook.setPosition(1); // MAX_HOOK_POSITION

        // if (direction == "transfer") servoRotationHook.setPosition(0.5); // MIN_ROTATION_HOOK_POSITION
        // if (direction == "deposit")  servoRotationHook.setPosition(1); // MAX_ROTATION_HOOK_POSITION

        // rotating the hook

        // double servoRotationHookActualPosition;
        // double servoRotationHookDesiredPosition;
        // boolean rotationHookIsMoving = false;

    }

//     public void transferCone() {

//         lift("transfer");
//         robot.rotateHook("transfer");
//         robot.moveHook("retract");

//         rotateClaw("transfer");
//         robot.moveHook("extend");
//         robot.moveClaw("open");
//         rotateClaw("intake");

//     }

//     public void liftTransfer(String direction) {

//         lift(direction);
//         robot.rotateHook("deposit");
//         robot.moveHook("retract");

// //        sleep(500);

//         robot.rotateHook("deposit");
//         lift("transfer");

//     }

}