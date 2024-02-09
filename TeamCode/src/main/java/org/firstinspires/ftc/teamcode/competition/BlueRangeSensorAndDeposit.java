package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.robot.Robot;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.teamcode.testing.RobotAutoDriveToAprilTagOmni.*;

import java.util.List;
import java.util.concurrent.TimeUnit;


// DISTANCES ARE TUNED
// SERVO WON'T ROTATE BACK FOR SOME REASON IDK



@Autonomous(name = "Blue Range Auton (Run this if on BlueLeft)", group = "Competition")

public class BlueRangeSensorAndDeposit extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DistanceSensor sensorDistanceLeft;
    private DistanceSensor sensorDistanceRight;
    double distLeft;
    double distRight;
    String pixelPos = "center";
    double driveSpeed = 0.8;

    int desiredTagId = -1;

    @Override
    public void runOpMode() {

        // you can use this as a regular DistanceSensor.
        sensorDistanceLeft = hardwareMap.get(DistanceSensor.class, "sensor_range_left");
        sensorDistanceRight = hardwareMap.get(DistanceSensor.class, "sensor_range_right");



        Robot.init(this);

        waitForStart();
        Robot.drivetrain.drive(32,0.7);
        runtime.reset();
        while (runtime.seconds() < 1.2 && opModeIsActive()){ //maybe shorten this for more time
            distLeft = sensorDistanceLeft.getDistance(DistanceUnit.CM);
            distRight = sensorDistanceRight.getDistance(DistanceUnit.CM);

            if (distLeft <= 100) {
                pixelPos = "Left";
            } else if (distRight <= 100 && opModeIsActive()) {
                pixelPos = "Right";
            }

            // generic DistanceSensor methods.
            telemetry.addData("sensorLeft", sensorDistanceLeft.getDeviceName());
            telemetry.addData("range", String.format("%.01f cm", distLeft));

            telemetry.addData("sensorRight", sensorDistanceRight.getDeviceName());
            telemetry.addData("range", String.format("%.01f cm", distRight));

            telemetry.addData("runtime",runtime.seconds());
            telemetry.addData("Pixel Pos:", pixelPos);
            telemetry.update();

        }
        telemetry.addData("direction",pixelPos);
        telemetry.update();


        if (pixelPos == "Left") {
            desiredTagId = 1;
            Robot.drivetrain.turn(-90, driveSpeed);
            quickDeposit("middle");
            Robot.drivetrain.strafe(16, driveSpeed);

        } else if (pixelPos == "Right") {
            desiredTagId = 3;

            Robot.drivetrain.turn(90, driveSpeed);
            quickDeposit("middle");
            Robot.drivetrain.strafe(-16, driveSpeed);
            Robot.drivetrain.turn(180,driveSpeed);
        } else {
            desiredTagId = 2;

            Robot.drivetrain.drive(-4,driveSpeed);
            Robot.drivetrain.turn(180, driveSpeed);
            quickDeposit("middle");
            Robot.drivetrain.turn(90,driveSpeed);
            Robot.drivetrain.strafe(16, driveSpeed);

        }

        goToBackDrop();
    }

    private void quickDeposit(String position) {
        Robot.verticalLift.runToPosition(position, true);
        Robot.servoDeposit.runToPosition("auton",true);
        sleep(1000);
        Robot.servoDeposit.runToPosition("collect",true);
        Robot.verticalLift.runToPosition("zero", true);
    }

    private void goToBackDrop() {
        Robot.drivetrain.drive(-25,1.2*driveSpeed);
        Robot.drivetrain.strafe(-13, 0.8*driveSpeed);
        Robot.motorSweeper.runToPosition(300, true);
        // detect april tag
        runtime.reset();
        Robot.webcam1.driveToTag(desiredTagId,5,"paurghas");
        Robot.drivetrain.drive(-20, driveSpeed);
        telemetry.addLine("Done moving to aprilTag");

        quickDeposit("high");
    }



}
