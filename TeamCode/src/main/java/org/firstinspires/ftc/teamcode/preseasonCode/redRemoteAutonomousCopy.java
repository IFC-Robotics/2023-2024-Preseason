package org.firstinspires.ftc.teamcode.preseasonCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Hardware Test - Red Remote Autonomous Copy", group="Preseason Code")
@Disabled
public class redRemoteAutonomousCopy extends LinearOpMode {

    robot robot = new robot();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        robot.setModeForDrivetrain(hardwareMap);
        robot.setModeForOtherMotors(hardwareMap);

        waitForStart();
        runtime.reset();

        // What this code does:
        // red side
        // starts in front of carousel-side barcode
        // measure correct barcode w/ range sensors
        // deposit preloaded freight
        // collects & deposits other TSE (duck)
        // does 1 cycle
        // parks in warehouse

        double DRIVE_SPEED = 0.5;
        double TURN_SPEED = 0.5;

        robot.drive(DRIVE_SPEED, 600, -1); // move forward

        String bestShippingHubLevel = robot.bestShippingHubLevel(); // measure correct barcode
        telemetry.addData("Best Shipping Hub Level: ", bestShippingHubLevel);
        telemetry.update();

        robot.strafe(DRIVE_SPEED, 900, -1); // strafe right to be in front of shipping hub
        robot.drive(DRIVE_SPEED, 100, -1); // drive to shipping hub

        robot.deposit(bestShippingHubLevel); // deposit freight in shipping hub

        robot.drive(DRIVE_SPEED, 300, 1); // drive away from shipping hub

        int turnToDuckDist = 0;
        int driveToDuckDist = 0;
        int turnToShippingHubDist = 0;
        int driveToShippingHubDist = 0;
        int turnToWarehouseDist = 0;
        int strafeToWarehouseDist = 0;

        if (bestShippingHubLevel == "top") {
            turnToDuckDist = 100;
            driveToDuckDist = 900;
            turnToShippingHubDist = 65;
            driveToShippingHubDist = 250;
            turnToWarehouseDist = 40;
            strafeToWarehouseDist = 300;
        } else if (bestShippingHubLevel == "middle") {
            turnToDuckDist = 110;
            driveToDuckDist = 800;
            turnToShippingHubDist = 75;
            driveToShippingHubDist = 100;
            turnToWarehouseDist = 45;
            strafeToWarehouseDist = 100;
        } else if (bestShippingHubLevel == "bottom") {
            turnToDuckDist = 125;
            driveToDuckDist = 400;
            turnToShippingHubDist = 80;
            driveToShippingHubDist = 0;
            turnToWarehouseDist = 60;
            strafeToWarehouseDist = 0;
        }

        robot.turn(TURN_SPEED, turnToDuckDist); // turn to align with other TSE (duck)

        robot.moveCollectorArm(0.5, "down"); // lower collector arm
        robot.motorCollector.setPower(0.4); // start collector
        robot.drive(DRIVE_SPEED, driveToDuckDist, 1); // drive toward duck
        sleep(1000); // wait while we collect duck
        robot.motorCollector.setPower(0); // stop collector

        robot.transfer(); // transfer duck to linear lift

        robot.turn(TURN_SPEED, -turnToShippingHubDist); // turn to face shipping hub
        robot.drive(DRIVE_SPEED, driveToShippingHubDist, -1); // drive to shipping hub
        robot.deposit("top"); // deposit freight in top level of shipping hub

        robot.drive(DRIVE_SPEED, 100, 1); // back up
        robot.turn(TURN_SPEED, turnToWarehouseDist); // turn towards warehouse
        robot.strafe(DRIVE_SPEED, strafeToWarehouseDist, -1); // strafe to center of warehouse
        robot.drive(DRIVE_SPEED, 1200, 1); // drive over barriers into warehouse

        robot.turn(TURN_SPEED, -45); // turn to face freight
        robot.moveCollectorArm(0.5, "down"); // lower collector arm
        robot.motorCollector.setPower(1); // start collector
        robot.drive(DRIVE_SPEED, 400, 1); // drive toward freight
        sleep(1000); // wait while we collect freight
        robot.motorCollector.setPower(0); // stop collector

        robot.transfer(); // transfer freight to linear lift

        robot.drive(DRIVE_SPEED, 400, -1); // drive back
        robot.turn(TURN_SPEED, 45); // align with barriers
        robot.drive(DRIVE_SPEED, 1200, 1); // exit warehouse

        robot.turn(TURN_SPEED, -45); // turn to face shipping hub
        robot.drive(DRIVE_SPEED, 200, -1); // drive to shipping hub
        robot.deposit("top"); // deposit freight

        robot.drive(DRIVE_SPEED, 100, 1); // back up
        robot.turn(TURN_SPEED, 60); // turn towards warehouse
        robot.drive(DRIVE_SPEED, 1200, 1); // drive over barriers into warehouse

    }

}
