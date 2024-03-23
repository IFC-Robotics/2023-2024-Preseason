package org.firstinspires.ftc.teamcode.Event;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name="STEAM Night teleOp", group="Event")
public class STEAMnight extends LinearOpMode {

    float pulleyRatio = 0.2f; //test
    float pulleySpeed;
    float launcherSpeed;
    boolean LiftisUp = false;

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

            //disabled encoder toggle for children
            Robot.verticalLift.teleOp(-gamepad2.right_stick_y, false /**gamepad2.right_bumper **/, gamepad2.a, gamepad2.x, gamepad2.b, gamepad2.y);

            Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, false/**gamepad1.right_bumper**/);

            Robot.servoDeposit.teleOpAssistMode(gamepad2.dpad_down,(gamepad2.dpad_left||gamepad2.dpad_right),gamepad2.dpad_up);

            Robot.motorCollector.teleOp(gamepad1.right_trigger,-gamepad1.left_trigger);

            Robot.servoLauncher.teleOpAssistMode(gamepad1.left_bumper,false,gamepad1.right_bumper);

            if (LiftisUp) {
                Robot.servoDeposit.teleOpManualMode(gamepad2.left_trigger > 0.2, gamepad2.right_trigger > 0.2);
            } else {
                Robot.servoDeposit.servo.setPosition(0.1);
                Robot.servoDeposit.servoPosition = 0.1;
            }

            if (Robot.verticalLift.motor.getCurrentPosition() > 600) {
                LiftisUp = true;

            } else {
                LiftisUp = false;
            }



            printRobotData();
        }
    }

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");

        Robot.verticalLift.printData();

        Robot.motorCollector.printData();

        Robot.servoDeposit.printData();

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
