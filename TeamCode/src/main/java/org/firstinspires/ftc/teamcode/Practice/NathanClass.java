package org.firstinspires.ftc.teamcode.Practice;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="Nathan's Code", group = "Practice")

public class NathanClass extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {

        //initialization code
        Robot.init(this);

        waitForStart();

        // start code
        Robot.drivetrain.drive(29,.5);
        Robot.drivetrain.strafe(-29,.5);
        Robot.drivetrain.drive(-29,.5);
        Robot.drivetrain.strafe(29,.5);
        Robot.verticalLift.runToPosition("middle", true);
        Robot.servoDeposit.runToPosition("auton", true );
        Robot.servoDeposit.runToPosition("collect", true);
        Robot.drivetrain.turn(360,.5, false);
        Robot.motorCollector.runToPosition(59,false);
        Robot.verticalLift.runToPosition("zero",true);
        Robot.motorLauncher.runToPosition(7);
        Robot.motorLauncher.runToPosition(-7);
        Robot.motorLauncher.runToPosition(7);
        Robot.motorLauncher.runToPosition(-7);
    }
}
