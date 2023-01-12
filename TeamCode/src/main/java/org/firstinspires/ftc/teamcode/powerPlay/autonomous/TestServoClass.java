package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Test Servo Class", group="Test")
public class TestServoClass extends LinearOpMode {

    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(hardwareMap, telemetry);
        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

//        Robot.servoClaw.runToPosition("open");
//        sleep(2000);
//        Robot.servoClaw.runToPosition("close");
//        sleep(2000);

        Robot.servoRotateHook.runToPosition("score");
        telemetry.addLine("attempting to rotate hook");
        telemetry.update();
        sleep(2000);
        Robot.servoRotateHook.runToPosition("transfer");
    }

}