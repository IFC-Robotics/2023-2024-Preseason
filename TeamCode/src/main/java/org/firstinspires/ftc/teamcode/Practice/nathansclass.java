package org.firstinspires.ftc.teamcode.Practice;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="nathan's autonomous", group = "Practice")

public class nathansclass extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {

        //initialization code
        Robot.init(this);

        waitForStart();
        Robot.drivetrain.drive(20,0.6);
        Robot.drivetrain.turn(90, 0.4) ;
        Robot.motorCollector.runToPosition(1000);
        Robot.motorCollector.runToPosition(-1000);
        Robot.motorCollector.runToPosition(1000);
        Robot.drivetrain.drive(20,0.6);
        Robot.drivetrain.turn(90,0.5);
        Robot.drivetrain.drive(20,0.6);
        Robot.verticalLift.runToPosition("middle");
        Robot.verticalLift.runToPosition("score");
        Robot.verticalLift.runToPosition("zero");
        Robot.drivetrain.strafe(20,0.6);
        Robot.drivetrain.strafe(-20,0.6);


        // start code
    }
}
