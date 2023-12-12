package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.robot.Robot;



// DISTANCES ARE TUNED
// SERVO WON'T ROTATE BACK FOR SOME REASON IDK



@Autonomous(name = "Blue Range Auton", group = "Competition")

public class BlueRangeSensorAndDeposit extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DistanceSensor sensorDistanceLeft;
    private DistanceSensor sensorDistanceRight;
    double distLeft;
    double distRight;
    String pixelPos = "center";
    double driveSpeed = 0.8;


    @Override
    public void runOpMode() {
        // you can use this as a regular DistanceSensor.
        sensorDistanceLeft = hardwareMap.get(DistanceSensor.class, "sensor_range_left");
        sensorDistanceRight = hardwareMap.get(DistanceSensor.class, "sensor_range_right");

        Robot.init(this);

        waitForStart();
        Robot.drivetrain.drive(32,0.7);
        runtime.reset();
        while (runtime.seconds() < 2 && opModeIsActive()){ //maybe shorten this for more time
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

            Robot.drivetrain.turn(-90, driveSpeed);
            quickDeposit();
            Robot.drivetrain.strafe(4, driveSpeed);

        } else if (pixelPos == "Right") {

            Robot.drivetrain.turn(90, driveSpeed);
            quickDeposit();
            Robot.drivetrain.turn(180,driveSpeed);
            Robot.drivetrain.strafe(4, driveSpeed);

        } else {
            Robot.drivetrain.drive(-4,driveSpeed);
            Robot.drivetrain.turn(180, driveSpeed);
            quickDeposit();
            Robot.drivetrain.turn(90,driveSpeed);

        }

        goToBackDrop();
    }

    private void quickDeposit() {
        Robot.verticalLift.runToPosition("middle", true);

//        Robot.servoDeposit.servo.setPosition(0.65);
//            sleep(2000);
//            Robot.servoDeposit.servo.setPosition(0.1);
        Robot.servoDeposit.runToPosition("auton");
        Robot.servoDeposit.runToPosition("collect");

        Robot.verticalLift.runToPosition("zero", true);
    }

    private void goToBackDrop() {
        Robot.drivetrain.strafe(10, driveSpeed);
        Robot.drivetrain.drive(-38,driveSpeed);
        Robot.drivetrain.strafe(-10, driveSpeed);
        Robot.motorSweeper.runToPosition(1000, true);
        Robot.verticalLift.runToPosition("high", true);
//        Robot.servoDeposit.servo.setPosition(0.65);
//        sleep(2000);
//        Robot.servoDeposit.servo.setPosition(0.1);
        Robot.servoDeposit.runToPosition("score");
        Robot.servoDeposit.runToPosition("collect");
        Robot.verticalLift.runToPosition("zero", true);
    }

}
