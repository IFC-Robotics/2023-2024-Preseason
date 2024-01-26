package org.firstinspires.ftc.teamcode.testing;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name="Cinamatography", group="testing")
public class CinemaTesting extends LinearOpMode {

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

            Robot.drivetrain.teleOp(gamepad1.left_stick_y, -gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_bumper);

            Robot.servoDeposit.teleOpAssistMode(gamepad2.left_trigger > 0.2 || gamepad2.dpad_left,(gamepad2.dpad_down || gamepad2.dpad_up),gamepad2.right_trigger > 0.2 || gamepad2.dpad_right);

            Robot.motorSweeper.teleOp(gamepad1.right_trigger,gamepad1.left_trigger);

            Robot.motorLauncher.teleOp(gamepad2.left_stick_y,-gamepad2.left_stick_y);

            Robot.servoLauncher.teleOpAssistMode(gamepad2.left_bumper,false, false);



            printRobotData();
        }
    }

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");

        Robot.verticalLift.printData();

        Robot.motorSweeper.printData();

        Robot.servoDeposit.printData();

        Robot.servoLauncher.printData();

        telemetry.update();

    }
}
