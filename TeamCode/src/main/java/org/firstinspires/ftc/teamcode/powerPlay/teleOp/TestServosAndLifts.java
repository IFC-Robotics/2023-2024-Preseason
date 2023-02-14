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

        while(opModeIsActive()) {

            // claw servo

            Robot.servoClaw.teleOpAssistMode(gamepad2.dpad_up, gamepad2.dpad_down);
            telemetry.addData("servo_claw position", Robot.servoClaw.servo.getPosition());

            Robot.closeClawUsingTouchSensor();

            // rotate claw servo

            Robot.servoRotateClaw.teleOpManualMode(gamepad2.dpad_left, gamepad2.dpad_right);
            telemetry.addData("servo_rotate_claw position", Robot.servoRotateClaw.servo.getPosition());

            // hook servo

            Robot.servoHook.teleOpAssistMode(gamepad2.a, gamepad2.y);
            telemetry.addData("servo_hook position", Robot.servoHook.servo.getPosition());

            Robot.closeHookUsingColorSensor();

            // rotate hook servo

            Robot.servoRotateHook.teleOpAssistMode(gamepad2.x, gamepad2.b);
            telemetry.addData("servo_rotate_hook position", Robot.servoRotateHook.servo.getPosition());

            // horizontal lift

            Robot.horizontalLift.teleOpAssistMode(gamepad1.dpad_down, gamepad1.dpad_left, gamepad1.dpad_right, gamepad1.dpad_up);
            Robot.horizontalLift.teleOpManualMode(-gamepad2.left_stick_y, gamepad2.left_bumper);
            telemetry.addData("horizontal lift position", Robot.horizontalLift.motor.getCurrentPosition());

            // vertical lift

            Robot.verticalLift.teleOpAssistMode(gamepad1.a, gamepad1.x, gamepad1.b, gamepad1.y);
            Robot.verticalLift.teleOpManualMode(-gamepad2.right_stick_y, gamepad2.right_bumper);
            telemetry.addData("vertical lift position", Robot.verticalLift.motor.getCurrentPosition());

            telemetry.update();

        }

    }

}