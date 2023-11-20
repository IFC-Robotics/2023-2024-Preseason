package org.firstinspires.ftc.teamcode.meetOne;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name="Motor Test", group="Competition")

public class Motor_test extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Robot.init(this);
        Robot.mode = "assist";

        waitForStart();
        while( opModeIsActive()) {
            Robot.drivetrain.moveWheel(gamepad1.a,gamepad1.b, gamepad1.x,gamepad1.y);
        }

    }


}
