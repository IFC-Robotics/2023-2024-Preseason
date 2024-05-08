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
        Robot.drivetrain.drive(14,0.7);
        Robot.motorCollector.runToPosition(4000);
        Robot.drivetrain.strafe(-60,0.7);
        Robot.drivetrain.drive(3,0.76);
        Robot.drivetrain.drive(-3,0.76);
        Robot.verticalLift.runToPosition("middle",true);
        Robot.servoDeposit.runToPosition("score");
        Robot.verticalLift.runToPosition("zero",true);
        Robot.servoDeposit.runToPosition("zero");
        Robot.drivetrain.drive(42,0.1);
        Robot.drivetrain.turn(-90,0.7);
        Robot.drivetrain.drive(-36,0.7);
        Robot.motorLauncher.runToPosition(-127,true);
        Robot.motorLauncher.runToPosition(0,true);
    }
}
