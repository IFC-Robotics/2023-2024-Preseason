package org.firstinspires.ftc.teamcode.Practice;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.robot.ServoClass;

@Autonomous(name="ayden's Autonomous Code 3", group = "Practice")

public class aydensClass extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {

        //initialization code
        Robot.init(this);
        waitForStart();

        // start code
        Robot.drivetrain.drive(12,0.7);
        Robot.motorCollector.runToPosition(4000);
        Robot.drivetrain.strafe(-60,0.7);
        Robot.verticalLift.runToPosition("middle");
        Robot.servoDeposit.runToPosition("score");
        Robot.verticalLift.runToPosition("zero");
        Robot.drivetrain.drive(36,0.1);
        Robot.drivetrain.turn(-90,0.7);
        Robot.drivetrain.drive(-36,0.7);
        Robot.motorLauncher.runToPosition(12);
        Robot.motorLauncher.runToPosition(0);
        Robot.motorPulley.runToPosition(10);
    }
}
