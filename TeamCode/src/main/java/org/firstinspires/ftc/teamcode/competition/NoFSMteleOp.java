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

        while (opModeIsActive()) {

            Robot.verticalLift.teleOp(-gamepad2.right_stick_y, gamepad2.right_bumper, gamepad2.a, gamepad2.x, gamepad2.b, gamepad2.y);

            Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_bumper);

            Robot.servoClaw.teleOpManualMode(gamepad2.left_trigger > 0.2,gamepad2.right_trigger > 0.2);
            Robot.servoClaw.teleOpAssistMode(gamepad2.dpad_down,(gamepad2.dpad_left||gamepad2.dpad_right),gamepad2.dpad_up);


//            Robot.servoLauncher.teleOpAssistMode(gamepad2.left_bumper,false, false);

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

            printRobotData();

            if(gamepad1.y){

            }
        }
    }

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");

        Robot.verticalLift.printData();

        Robot.servoClaw.printData();

//        Robot.servoLauncher.printData();

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
