 package org.firstinspires.ftc.teamcode.powerPlay.oldCode.autonomous;

 import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
 import com.qualcomm.robotcore.eventloop.opmode.Disabled;
 import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
 import com.qualcomm.robotcore.hardware.DcMotor;

 import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

 @Autonomous(name="Right 1+0", group="competition")
 @Disabled
 public class RightHighAuton extends LinearOpMode {

     double DRIVE_SPEED = 0.3;
     double STRAFE_SPEED = 0.3;
     double TURN_SPEED = 0.3;

     @Override
     public void runOpMode() {

         telemetry.addLine("Initializing opMode...");
         telemetry.update();

         Robot.init(this);

         waitForStart();

         telemetry.addLine("Executing opMode...");
         telemetry.update();

         // read AprilTag

         Robot.tag = Robot.camera.getTag();

         // secure preloaded cone

         Robot.servoHook.runToPosition("extend");
         sleep(1000);
//         Robot.verticalLift.autonomousRunToPosition("ground");
         sleep(100);

         // drive to high junction

         Robot.drivetrain.drive(64, DRIVE_SPEED);
         sleep(100);
         Robot.drivetrain.drive(-8, DRIVE_SPEED);
         sleep(100);
         Robot.drivetrain.turn(-45, TURN_SPEED);
         sleep(100);

         // raise lift

//         Robot.verticalLift.autonomousRunToPosition("high");
         sleep(100);

         // score on high junction

         double distToJunction = 12;

         Robot.drivetrain.drive(distToJunction, DRIVE_SPEED);
         sleep(100);
         Robot.servoHook.runToPosition("retract");
         sleep(500);
         Robot.drivetrain.drive(-distToJunction, DRIVE_SPEED);
         sleep(100);
//         Robot.verticalLift.autonomousRunToPosition("transfer");
         sleep(100);

         // park in correct zone

         Robot.drivetrain.turn(-46, TURN_SPEED);
         sleep(100);
         Robot.drivetrain.strafe(1, STRAFE_SPEED);
         sleep(100);

         if (Robot.tag == 1) Robot.drivetrain.drive(23, DRIVE_SPEED);
         if (Robot.tag == 3) Robot.drivetrain.drive(-24, DRIVE_SPEED);

     }

 }