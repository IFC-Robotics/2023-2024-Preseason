package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name="teleOp w/out FSM", group="Competition")
public class NoFSMteleOp extends LinearOpMode {

    float pulleyRatio = 1; //test
    float pulleySpeed;

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

            Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_bumper);

//            Robot.servoDeposit.teleOpAssistMode(gamepad2.left_trigger > 0.2,(gamepad2.dpad_left || gamepad2.dpad_right),gamepad2.right_trigger > 0.2);

            Robot.motorCollector.teleOp(gamepad1.right_trigger,gamepad1.left_trigger);

            Robot.motorLauncher.teleOp(gamepad2.left_stick_y,-gamepad2.left_stick_y);

//            Robot.servoLauncher.teleOpAssistMode(gamepad2.left_bumper,false, false);

            if (gamepad2.dpad_down) {
                pulleySpeed = -0.5F;
            } else if (gamepad2.dpad_up) {
                pulleySpeed = 0.5F;
            } else{
                pulleySpeed = -gamepad2.left_stick_y * pulleyRatio;
            }

            Robot.motorPulley.teleOp(pulleySpeed,-pulleySpeed);

            printRobotData();
        }
    }

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");

        Robot.verticalLift.printData();

        Robot.motorCollector.printData();

//        Robot.servoDeposit.printData();

//        Robot.servoLauncher.printData();

        telemetry.update();

    }
}
