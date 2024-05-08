package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name="teleOp w/out FSM", group="Competition")
public class NoFSMteleOp extends LinearOpMode {

    float pulleyRatio = 0.2f; //test
    float pulleySpeed;
    float launcherSpeed;

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);
        Robot.mode = "assist";

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        boolean LiftisUp = true;

        while (opModeIsActive()) {

            Robot.verticalLift.teleOp(-gamepad2.right_stick_y, gamepad2.right_bumper, gamepad2.a, gamepad2.x, gamepad2.b, gamepad2.y);

            //disabled turbo mode for button mapping
            Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, false/**gamepad1.right_bumper**/);

            Robot.motorCollector.teleOp(gamepad1.right_trigger,-gamepad1.left_trigger);

            Robot.servoLauncher.teleOpAssistMode(gamepad1.left_bumper,false,gamepad1.right_bumper);

            if (LiftisUp) {
                Robot.servoDeposit.teleOpManualMode(gamepad2.left_trigger > 0.2, gamepad2.right_trigger > 0.2);
            } else {
                Robot.servoDeposit.servo.setPosition(0.1);
                Robot.servoDeposit.servoPosition = 0.1;
            }

            if (Robot.verticalLift.motor.getCurrentPosition() > 800) {
                LiftisUp = true;

            } else {
                LiftisUp = false;
            }


            if (gamepad1.dpad_down) {
                pulleySpeed = 0.3F;
                launcherSpeed = 0.7f;
            } else if (gamepad1.dpad_up) {
                pulleySpeed = -0.3F;
                launcherSpeed = -0.7f;
            } else {
                pulleySpeed = 0F;
                launcherSpeed = 0f;
            }

            if (gamepad1.dpad_left) {
                pulleySpeed = 0.4F;
            }else if (gamepad1.dpad_right) {
                pulleySpeed = -0.4F;
            }

            Robot.motorPulley.teleOp(pulleySpeed,0);
            Robot.motorLauncher.teleOp(launcherSpeed,0);

            printRobotData();

            if(gamepad1.y){
                Robot.motorLauncher.runToPosition(20);
                Robot.motorPulley.runToPosition(10);
                sleep(2000);
                while (opModeIsActive()) {
                    Robot.motorPulley.teleOp(0.5f, 0);
                }
            }
        }
    }

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");

        Robot.verticalLift.printData();

        Robot.motorCollector.printData();

        Robot.servoDeposit.printData();

        Robot.servoLauncher.printData();

        Robot.motorLauncher.printData();

        telemetry.update();

    }
}

/**
 * Controls
 * Gamepad1:
 * - left Stick: cardinal drivetrain
 * - right Stick: turning
 * - Left & Right Triggers: collector
 * -
 *
 *
 *
  **/
