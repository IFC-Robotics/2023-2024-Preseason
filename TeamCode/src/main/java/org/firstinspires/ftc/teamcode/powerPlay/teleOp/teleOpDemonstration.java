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

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        while (opModeIsActive()) {

            Robot.servoHook.teleOpAssistMode(gamepad1.y, gamepad1.a);
            Robot.servoRotateHook.teleOpAssistMode(gamepad1.x, gamepad1.b);
            Robot.verticalLift.teleOpAssistMode(gamepad1.dpad_down, gamepad1.dpad_left, gamepad1.dpad_right, gamepad1.dpad_up);
            Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

            printRobotData();

        }

    }

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");

        Robot.servoHook.printData();
        Robot.servoRotateHook.printData();
        Robot.verticalLift.printData();

        telemetry.update();

    }

}