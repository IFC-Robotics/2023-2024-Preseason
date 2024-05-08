package org.firstinspires.ftc.teamcode.Practice;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.robot.MotorClass;

import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="CatVideo", group = "Practice")

public class CatVideo extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {

        //initialization code
        Robot.init(this);

        waitForStart();
        Robot.motorCollector.runToPosition(1000, false, 1);
        Robot.drivetrain.drive(-10,0.7);
        Robot.motorCollector.runToPosition(1000, false, 1);
        Robot.drivetrain.drive(-10,0.7);
        Robot.motorCollector.runToPosition(1000, false, 1);
        Robot.drivetrain.drive(-10,0.7);
        Robot.motorCollector.runToPosition(1000, false, 1);
        Robot.drivetrain.strafe(10,0.7);
        Robot.drivetrain.drive(10,0.7);
        Robot.drivetrain.strafe(-10,0.7);
        Robot.verticalLift.runToPosition("middle",true);
        Robot.servoDeposit.runToPosition("score",true);
        Robot.verticalLift.runToPosition("zero",true);
        Robot.motorCollector.runToPosition(1000, false, 1);
        Robot.verticalLift.runToPosition("middle",true);
        Robot.servoDeposit.runToPosition("score",true);
        Robot.verticalLift.runToPosition("zero",true);
        Robot.drivetrain.turn(540,0.7);
        Robot.drivetrain.strafe(-10,0.7);
        Robot.drivetrain.drive(20,0.7);
        Robot.motorLauncher.runToPosition(50);
        // start code
    }
}