package org.firstinspires.ftc.teamcode.powerPlay.oldCode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
 import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

 import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

 @Autonomous(name="Test Servo Class", group="Test")
 @Disabled
 public class TestServoClass extends LinearOpMode {

     public void runOpMode() {

         telemetry.addLine("Initializing opMode...");
         telemetry.update();

         Robot.init(this);
         waitForStart();

         telemetry.addLine("Executing opMode...");
         telemetry.update();

         Robot.servoClaw.runToPosition("open");
         telemetry.update();
         sleep(3000);
//         Robot.servoRotateClaw.runToPosition("down");
         telemetry.update();
         sleep(3000);
         Robot.servoClaw.runToPosition("close");
         telemetry.update();
         sleep(3000);
//         Robot.servoRotateClaw.runToPosition("up");
         telemetry.update();
         sleep(3000);

         Robot.servoHook.runToPosition("extend");
         telemetry.update();
         sleep(1000);

         Robot.servoRotateHook.runToPosition("score");
         telemetry.update();
         sleep(3000);

         Robot.servoRotateHook.runToPosition("transfer");
         telemetry.update();
         sleep(3000);

         Robot.servoHook.runToPosition("retract");
         telemetry.update();
         sleep(1000);

     }

 }