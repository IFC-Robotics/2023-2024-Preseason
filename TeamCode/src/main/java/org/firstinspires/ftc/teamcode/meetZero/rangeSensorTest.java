package org.firstinspires.ftc.teamcode.meetZero;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import org.firstinspires.ftc.teamcode.robot.Robot;


@Autonomous(name = "REV color/range sensor test", group = "Sensor")

public class rangeSensorTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DistanceSensor sensorDistanceLeft;
    private DistanceSensor sensorDistanceRight;
    double distLeft;
    double distRight;
    int i = 0;
    String pixelPos = "center";

    @Override
    public void runOpMode() {
        // you can use this as a regular DistanceSensor.
        sensorDistanceLeft = hardwareMap.get(DistanceSensor.class, "sensor_range_left");
        sensorDistanceRight = hardwareMap.get(DistanceSensor.class, "sensor_range_right");

        Robot.init(this);

        waitForStart();

//        Robot.drivetrain.drive(30,0.5);
        runtime.reset();
        while (runtime.seconds() < 30){
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

            telemetry.addData("Pixel Pos:", pixelPos);

            telemetry.update();

        }
        telemetry.addData("direction",pixelPos);
        telemetry.update();
        if (pixelPos == "Left") {
            Robot.drivetrain.turn(-90, 0.5);
            Robot.sweeper.runToPosition(20);
            Robot.drivetrain.turn(90, 0.5);
        }
        else if (pixelPos == "Right") {
            Robot.drivetrain.turn(90, 0.5);
            Robot.sweeper.runToPosition(20);
            Robot.drivetrain.turn(-90, 0.5);
        }
        else if (pixelPos == "Center") {
            Robot.drivetrain.drive(5, 0.5);
            Robot.sweeper.runToPosition(20);
        }
//
//        Robot.drivetrain.drive(-27,0.5);
//        Robot.drivetrain.strafe(-30,0.5);
    }
}
