//package org.firstinspires.ftc.teamcode.powerPlay.oldCode.autonomous;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;
//
//@Autonomous(name="Preload Cone", group="competition")
//@Disabled
//public class PreloadCone extends LinearOpMode {
//
//    @Override
//    public void runOpMode() {
//
//        telemetry.addLine("Initializing opMode...");
//        telemetry.update();
//
//        Robot.init(this);
//
//        waitForStart();
//
//        telemetry.addLine("Executing opMode...");
//        telemetry.update();
//
//        Robot.servoHook.runToPosition("hold", true);
//
//    }
//
//}