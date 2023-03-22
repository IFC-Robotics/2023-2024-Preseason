package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@TeleOp(name="Demonstration code")
public class teleOpDemonstration extends LinearOpMode {

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);
        Robot.mode = "assist";

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        while (opModeIsActive()) {

            Robot.servoClaw.teleOpAssistMode(gamepad1.dpad_left, gamepad1.dpad_right);
            Robot.servoHook.teleOpAssistMode(gamepad1.y, gamepad1.a);
            Robot.servoRotateHook.teleOpAssistMode(gamepad1.x, gamepad1.b);

            Robot.crServoRotateClaw.teleOpAssistMode(gamepad2.left_trigger, gamepad2.right_trigger);
            Robot.crServoRotateClaw.teleOpManualMode(gamepad2.left_trigger, gamepad2.right_trigger);

            Robot.horizontalLift.teleOp(-gamepad2.left_stick_y, gamepad2.left_bumper, gamepad2.dpad_down, gamepad2.dpad_left, gamepad2.dpad_right, gamepad2.dpad_up);
            Robot.verticalLift.teleOp(-gamepad2.right_stick_y, gamepad2.right_bumper, gamepad2.a, gamepad2.x, gamepad2.b, gamepad2.y);

             Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_bumper);

            printRobotData();

        }

    }

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");

        Robot.servoClaw.printData();
        Robot.servoHook.printData();
        Robot.servoRotateHook.printData();

        Robot.crServoRotateClaw.printData();

        Robot.horizontalLift.printData();
        Robot.verticalLift.printData();

        telemetry.update();

    }

}

/*

Gamepad 1 - driving, servos, and modes

gamepad1.left_stick_x:  strafing
gamepad1.left_stick_y:  driving
gamepad1.right_stick_x: turning

gamepad1.a:             retract hook (to release cone)
gamepad1.x:             rotate hook up
gamepad1.b:             rotate hook down
gamepad1.y:             extend hook (to hold cone)

gamepad1.dpad_down:     rotate claw down
gamepad1.dpad_left:     open claw
gamepad1.dpad_right:    close claw
gamepad1.dpad_up:       rotate claw upe

gamepad1.left_bumper:   toggle max drivetrain speed

gamepad1.back:          switch to FSM mode
gamepad1.guide:         switch to assist mode
gamepad1.start:         switch to manual mode

Gamepad 2 - lifts

gamepad2.left_stick_y:  moving horizontal lift
gamepad2.right_stick_y: moving vertical lift

gamepad2.a:             move vertical lift to transfer
gamepad2.x:             move vertical lift to low
gamepad2.b:             move vertical lift to middle
gamepad2.y:             move vertical lift to high

gamepad2.dpad_down:     move horizontal lift to zero
gamepad2.dpad_left:     move horizontal lift to wait to collect cone
gamepad2.dpad_right:    move horizontal lift to collect cone

gamepad2.left_bumper:   toggle encoder limits for horizontal lift
gamepad2.right_bumper:  toggle encoder limits for vertical lift

*/