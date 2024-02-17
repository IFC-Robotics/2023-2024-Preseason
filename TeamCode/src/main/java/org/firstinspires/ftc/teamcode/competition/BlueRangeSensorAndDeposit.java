package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.robot.Robot;


@Autonomous(name = "Blue Range Auton (Run this if on BlueLeft)", group = "Competition")

public class BlueRangeSensorAndDeposit extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DistanceSensor sensorDistanceLeft;
    private DistanceSensor sensorDistanceRight;
    double distLeft;
    double distRight;
    String pixelPos = "center";
    double driveSpeed = 0.8;

    int searchTime = 2;

    int desiredTagId = 2;

    @Override
    public void runOpMode() {

        // you can use this as a regular DistanceSensor.
//        sensorDistanceLeft = hardwareMap.get(DistanceSensor.class, "sensor_range_left");
//        sensorDistanceRight = hardwareMap.get(DistanceSensor.class, "sensor_range_right");



        Robot.init(this);
        Robot.webcam1.init(this);

        waitForStart();
        Robot.drivetrain.drive(-32,0.7);
        runtime.reset();
        while (runtime.seconds() < 1.2 && opModeIsActive()){ //maybe shorten this for more time
//            distLeft = sensorDistanceLeft.getDistance(DistanceUnit.CM);
//            distRight = sensorDistanceRight.getDistance(DistanceUnit.CM);
            distLeft = 0;

            if (distLeft <= 100) {
                pixelPos = "Left";
            } else if (distRight <= 100 && opModeIsActive()) {
                pixelPos = "Right";
            }

            // generic DistanceSensor methods.
//            telemetry.addData("sensorLeft", sensorDistanceLeft.getDeviceName());
//            telemetry.addData("range", String.format("%.01f cm", distLeft));
//
//            telemetry.addData("sensorRight", sensorDistanceRight.getDeviceName());
//            telemetry.addData("range", String.format("%.01f cm", distRight));
//
//            telemetry.addData("runtime",runtime.seconds());
//            telemetry.addData("Pixel Pos:", pixelPos);
//            telemetry.update();

        }
        telemetry.addData("direction",pixelPos);
        telemetry.update();


        if (pixelPos == "Left") {
            desiredTagId = 1;
            Robot.drivetrain.turn(90, driveSpeed);
//            quickDeposit("middle");
            Robot.drivetrain.strafe(16, driveSpeed);

        } else if (pixelPos == "Right") {
            desiredTagId = 3;

            Robot.drivetrain.turn(-90, driveSpeed);
//            quickDeposit("middle");
            Robot.drivetrain.strafe(-16, driveSpeed);
            Robot.drivetrain.turn(180,driveSpeed);
        } else {
            desiredTagId = 2;

            Robot.drivetrain.drive(-4,driveSpeed);
            Robot.drivetrain.turn(180, driveSpeed);
//            quickDeposit("middle");
            Robot.drivetrain.turn(90,driveSpeed);
            Robot.drivetrain.strafe(16, driveSpeed);

        }

        goToBackDrop();
    }

    private void quickDeposit(String position) {
//        Robot.verticalLift.runToPosition(position, true);
//        Robot.servoDeposit.runToPosition("auton",true);
        sleep(1000);
//        Robot.servoDeposit.runToPosition("collect",true);
//        Robot.verticalLift.runToPosition("zero", true);
    }

    private void goToBackDrop() {
        Robot.drivetrain.drive(-30,1*driveSpeed);
        Robot.drivetrain.strafe(-13, 0.8*driveSpeed);
        Robot.motorCollector.runToPosition(300, true);
        // detect april tag
        runtime.reset();
        Robot.webcam1.driveToTag(desiredTagId,searchTime,"");
        sleep(searchTime*1000);
//        if (Robot.webcam1.targetFound) {
//            Robot.drivetrain.drive(5, driveSpeed);
//        }
//        else {
//            Robot.drivetrain.drive(15, driveSpeed);
//        }
        telemetry.addLine("Done moving to aprilTag");

//        quickDeposit("high");
    }



}
