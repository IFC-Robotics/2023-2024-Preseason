package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="preload cone", group="competition")
public class PreloadCone extends LinearOpMode {

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this, hardwareMap, telemetry);

        telemetry.addData("AprilTag", Robot.tag);
        telemetry.update();

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        Robot.servoHook.runToPosition("extend");
        sleep(2000);

    }

}