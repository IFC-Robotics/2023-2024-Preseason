package org.firstinspires.ftc.teamcode.Practice;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.robot.MotorClass;

import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="Basic Autonomous Code", group = "Practice")

public class CatVideo extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {

        //initialization code

        waitForStart();
        Robot.motorCollector.runToPosition(100);
        Robot.drivetrain.drive(-40,0.7);
        Robot.drivetrain.strafe(-20,0.7);
        Robot.drivetrain.drive(-20,0.7);
        Robot.drivetrain.strafe(20,0.7);
        Robot.drivetrain.drive(-20,0.7);
//        Robot.motorPulley.runToPosition(50);
        // start code
    }
}