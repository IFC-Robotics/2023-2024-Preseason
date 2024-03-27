package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name="servotesting", group="Competition")
public class servoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(this);
        waitForStart();
        Robot.servoClaw.teleOpManualMode(gamepad1.dpad_up,gamepad1.dpad_down);


        }

    }


