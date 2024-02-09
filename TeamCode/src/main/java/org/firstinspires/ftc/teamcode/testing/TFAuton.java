package org.firstinspires.ftc.teamcode.testing;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.util.HashMap;
import java.util.Map;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.robot.Robot;

import java.util.List;

@Autonomous(name="Tensor Flow Auton", group="Testing")
public class TFAuton extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DistanceSensor sensorDistanceLeft;
    private DistanceSensor sensorDistanceRight;
    double distLeft;
    double distRight;
    String elementPos = "Center";
    double driveSpeed = 0.5;
    int desiredTagId = -1;
    String[] Labels = {"Red Box","Blue Box"};

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);
        Robot.mode = "assist";
        Robot.redBlueModel.initTfod();
        Robot.drivetrain.motorBackLeft.setDirection(DcMotor.Direction.FORWARD);
        Robot.drivetrain.motorFrontRight.setDirection(DcMotor.Direction.FORWARD);
        Robot.drivetrain.motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        Robot.drivetrain.motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);



        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        List<Recognition> objectsDetected = Robot.redBlueModel.currentRecognitions;

        Robot.drivetrain.drive(-12,driveSpeed,false);

        while (opModeIsActive() && runtime.seconds() < 6 && !isStopRequested()) {
            Robot.redBlueModel.telemetryTfod(Labels);
            printRobotData();
        }

        elementPos = findMostCommonPos(Robot.redBlueModel.elementPosList);

        if (elementPos == "Left") {
            desiredTagId = 1;
            Robot.drivetrain.turn(90, driveSpeed);
            quickDeposit("middle");
            Robot.drivetrain.strafe(16, driveSpeed);

        } else if (elementPos == "Right") {
            desiredTagId = 3;

            Robot.drivetrain.turn(-90, driveSpeed);
            quickDeposit("middle");
            Robot.drivetrain.strafe(-16, driveSpeed);
            Robot.drivetrain.turn(180,driveSpeed);
        } else if (elementPos == "Center"){
            desiredTagId = 2;

            Robot.drivetrain.drive(4,driveSpeed);
            Robot.drivetrain.turn(180, driveSpeed);
            quickDeposit("middle");
            Robot.drivetrain.turn(90,driveSpeed);
            Robot.drivetrain.strafe(16, driveSpeed);
        }
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

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");

//        Robot.verticalLift.printData();

//        Robot.motorSweeper.printData();

//        Robot.servoDeposit.printData();

//        Robot.servoLauncher.printData();

        telemetry.update();

    }

    public static String findMostCommonPos(String[] array) {
        // Create a HashMap to store the count of each element
        Map<String, Integer> elementCount = new HashMap<>();

        // Iterate through the array and count occurrences of each element
        for (String element : array) {
            elementCount.put(element, elementCount.getOrDefault(element, 0) + 1);
        }

        // Find the element with the maximum count
        String mostCommonElement = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : elementCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostCommonElement = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return mostCommonElement;
    }

}
