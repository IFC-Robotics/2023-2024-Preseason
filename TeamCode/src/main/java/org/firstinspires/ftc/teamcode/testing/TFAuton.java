package org.firstinspires.ftc.teamcode.testing;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="Tensor Flow Auton", group="Testing")
public class TFAuton extends LinearOpMode {

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);
        Robot.mode = "assist";
        Robot.redBlueModel.initTfod();

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        Robot.drivetrain.drive(12,0.1,true);

        while (opModeIsActive()) {

            printRobotData();
        }
    }

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");

//        Robot.verticalLift.printData();

//        Robot.motorSweeper.printData();

//        Robot.servoDeposit.printData();

//        Robot.servoLauncher.printData();

        Robot.redBlueModel.telemetryTfod();

        telemetry.update();

    }

}
