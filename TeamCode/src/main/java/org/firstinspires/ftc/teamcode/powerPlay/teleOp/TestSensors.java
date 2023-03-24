//package org.firstinspires.ftc.teamcode.powerPlay.teleOp;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;
//
//@TeleOp(name="test REV sensors", group="test")
//public class TestSensors extends LinearOpMode {
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
//        while(opModeIsActive()) {
//            Robot.hookSensor.printSensorData(true, true, true);
//            telemetry.update();
//        }
//
//    }
//
//}