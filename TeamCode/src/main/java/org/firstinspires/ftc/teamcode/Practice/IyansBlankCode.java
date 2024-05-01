package org.firstinspires.ftc.teamcode.Practice;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="Basic Autonomous Code", group = "Practice")

public class IyansBlankCode extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {

        //initialization code
        Robot.init(this);

        waitForStart();

        // start code
    }
}
