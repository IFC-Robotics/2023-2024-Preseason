package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@TeleOp(name="Test Servos and Lifts", group="test")
public class TestServosAndLifts extends LinearOpMode {

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        String mode = "assist";

        while(opModeIsActive()) {

//             // claw servo
//
//             Robot.servoClaw.teleOpAssistMode(gamepad1.dpad_up, gamepad1.dpad_down);
//             telemetry.addData("servo_claw position", Robot.servoClaw.servo.getPosition());
//
//             // hook servo
//
//             Robot.servoHook.teleOpAssistMode(gamepad1.a, gamepad1.y);
//             telemetry.addData("servo_hook position", Robot.servoHook.servo.getPosition());
//
//             Robot.closeHookUsingSensor();
//
//             // rotate hook servo
//
             Robot.servoRotateHook.teleOpAssistMode(gamepad1.x, gamepad1.b);
             telemetry.addData("servo_rotate_hook position", Robot.servoRotateHook.servo.getPosition());

            // rotate claw servo

            if (mode == "manual") Robot.crServoRotateClaw.teleOpManualMode(gamepad2.left_trigger, gamepad2.right_trigger);
            if (mode == "assist") Robot.crServoRotateClaw.teleOpAssistMode(gamepad2.left_trigger, gamepad2.right_trigger);

            if (mode == "auton") {
                if (gamepad2.a) Robot.crServoRotateClaw.runToPosition("collect");
                if (gamepad2.y) Robot.crServoRotateClaw.runToPosition("transfer");
                Robot.crServoRotateClaw.checkForStop();
            }

            if (gamepad2.dpad_down)  mode = "manual";
            if (gamepad2.dpad_left)  mode = "assist";
            if (gamepad2.dpad_right) mode = "auton";

            Robot.crServoRotateClaw.printData();
            telemetry.addData("mode", mode);

            // horizontal lift

//            Robot.horizontalLift.teleOp(-gamepad2.left_stick_y, gamepad2.left_bumper, gamepad2.dpad_down, gamepad2.dpad_left, gamepad2.dpad_right, gamepad2.dpad_up);
//            telemetry.addData("horizontal lift position", Robot.horizontalLift.motor.getCurrentPosition());

//            // vertical lift
//
//             Robot.verticalLift.teleOp(-gamepad2.right_stick_y, gamepad2.right_bumper, gamepad2.a, gamepad2.x, gamepad2.b, gamepad2.y);
//             telemetry.addData("vertical lift position", Robot.verticalLift.motor.getCurrentPosition());

            telemetry.update();

        }

    }

}