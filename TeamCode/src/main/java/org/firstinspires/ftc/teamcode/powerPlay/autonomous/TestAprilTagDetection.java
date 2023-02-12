 package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

 import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
 import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

 import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

 @Autonomous(name="Test AprilTag Detection", group="Test")
 public class TestAprilTagDetection extends LinearOpMode {

     public void runOpMode() {

         telemetry.addLine("Initializing opMode...");
         telemetry.update();

         Robot.init(this);

         waitForStart();

         telemetry.addLine("Executing opMode...");

         Robot.tag = Robot.camera.getTag();
         telemetry.addData("Robot.tag", Robot.tag);
         telemetry.update();

         sleep(2000);

     }

 }