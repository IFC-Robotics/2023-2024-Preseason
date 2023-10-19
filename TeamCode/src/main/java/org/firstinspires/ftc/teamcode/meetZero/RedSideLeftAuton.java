package org.firstinspires.ftc.teamcode.meetZero;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.robot.Robot;


@Autonomous(name="Red Side Right Park", group="competition")
public class RedSideLeftAuton extends LinearOpMode {

    double DRIVE_SPEED = 0.3;
    double STRAFE_SPEED = 0.3;
    double TURN_SPEED = 0.3;

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        //strafe right to park
        Robot.drivetrain.strafe(200,0.5);

        //atemmpt to deposit
        Robot.drivetrain.turn(90,0.5);
        Robot.servoDeposit.runToPosition("release",true);
    }

}
