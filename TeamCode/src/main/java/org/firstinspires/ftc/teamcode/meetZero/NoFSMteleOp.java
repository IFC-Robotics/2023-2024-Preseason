package org.firstinspires.ftc.teamcode.meetZero;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name="teleOp w/out FSM", group="Competition")
public class NoFSMteleOp extends LinearOpMode {
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
            Robot.verticalLift.teleOp(-gamepad2.right_stick_y, gamepad2.right_bumper, gamepad2.a, gamepad2.x, gamepad2.b, gamepad2.y);

            Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

            Robot.servoDeposit.teleOpAssistMode(gamepad1.left_bumper,gamepad1.right_bumper);

            Robot.sweeper.teleOp(gamepad1.right_trigger);

            printRobotData();
        }
    }

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");

        Robot.verticalLift.printData();

        Robot.sweeper.printData();

        telemetry.update();

    }
}
