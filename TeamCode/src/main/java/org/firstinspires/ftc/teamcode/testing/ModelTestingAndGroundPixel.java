///* Copyright (c) 2019 FIRST. All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without modification,
// * are permitted (subject to the limitations in the disclaimer below) provided that
// * the following conditions are met:
// *
// * Redistributions of source code must retain the above copyright notice, this list
// * of conditions and the following disclaimer.
// *
// * Redistributions in binary form must reproduce the above copyright notice, this
// * list of conditions and the following disclaimer in the documentation and/or
// * other materials provided with the distribution.
// *
// * Neither the name of FIRST nor the names of its contributors may be used to endorse or
// * promote products derived from this software without specific prior written permission.
// *
// * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
// * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
// * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
// * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package org.firstinspires.ftc.teamcode.testing;
//
//import android.util.Size;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
//import org.firstinspires.ftc.teamcode.robot.Robot;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.tfod.TfodProcessor;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///*
// * This OpMode illustrates the basics of TensorFlow Object Detection,
// * including Java Builder structures for specifying Vision parameters.
// *
// * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
// * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
// */
//@Autonomous(name = "Object Detection: Blue, with Ground Pixel Deposit", group = "Testing")
//
//public class ModelTestingAndGroundPixel extends LinearOpMode {
//
//    String elementPos = "Center";
//    String[] elementList = { "Center" };
//    double driveSpeed = 0.5;
//    int desiredTagId = -1;
//    String desiredLabel = "Blue Box";
//    int searchTime = 2;
//
//    private ElapsedTime runtime = new ElapsedTime();
//    private VisionPortal visionPortal;
//    private TfodProcessor tfod;
//
//    @Override
//    public void runOpMode() {
//
//        Robot.init(this);
//        Robot.webcam1.init(this);
//
//        visionPortal = Robot.webcam1.visionPortal;
//        tfod = Robot.webcam1.tfod;
//
//        // visionPortal.setProcessorEnabled(Robot.webcam1.aprilTag, false); // we don't
//        // need AprilTag detection yet
//
//        // Wait for the DS start button to be touched.
//        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
//        telemetry.addData(">", "Touch Play to start OpMode");
//        telemetry.update();
//        waitForStart();
//
//        desiredTagId = 2;
//
//        runtime.reset();
//
//        if (opModeIsActive()) {
//            while (opModeIsActive() && runtime.seconds() < 1) {
//
//                telemetryTfod();
//
//                // Push telemetry to the Driver Station.
//                telemetry.update();
//
//                // Save CPU resources; can resume streaming when needed.
//                if (gamepad1.dpad_down) {
//                    visionPortal.stopStreaming();
//                } else if (gamepad1.dpad_up) {
//                    visionPortal.resumeStreaming();
//                }
//
//                // Share the CPU.
//                sleep(20);
//            }
//
//            mainPath();
//
//        }
//
//        // Save more CPU resources when camera is no longer needed.
//
//    }
//
//    private void mainPath() {
//        elementPos = findMostCommonPos(elementList);
//
//        telemetry.addData("Detected Position", elementPos);
//        telemetry.update();
//        sleep(2000);
//
//
//        Robot.drivetrain.drive(-30, 0.7);
//        if (elementPos == "Left") {
//            desiredTagId = 1;
//
//            Robot.drivetrain.turn(-90, driveSpeed);
//
//            Robot.motorCollector.runToPosition(-300, true);
//            Robot.drivetrain.strafe(-16, driveSpeed);
//            Robot.drivetrain.turn(180, driveSpeed);
//
//        } else if (elementPos == "Right") {
//            desiredTagId = 3;
//
//            Robot.drivetrain.turn(90, driveSpeed);
//            Robot.motorCollector.runToPosition(-300, true);
//            Robot.drivetrain.strafe(16, driveSpeed);
//        } else {
//            desiredTagId = 2;
//
//            Robot.drivetrain.drive(4, driveSpeed);
//
//            Robot.drivetrain.turn(180, driveSpeed);
//            Robot.motorCollector.runToPosition(-300, true);
//            Robot.drivetrain.turn(-90, driveSpeed);
//            Robot.drivetrain.strafe(14, driveSpeed);
//
//        }
//        // visionPortal.setProcessorEnabled(Robot.webcam1.tfod, false);
//        // visionPortal.setProcessorEnabled(Robot.webcam1.aprilTag, true); // now we do
//        // need AT detection
//        telemetry.addData("Searching for", desiredTagId);
//        goToBackDrop();
//    }
//
//    private void quickDeposit(String position) {
//        Robot.verticalLift.runToPosition(position, true);
//        Robot.servoDeposit.runToPosition("auton", true);
//        sleep(1000);
//        Robot.servoDeposit.runToPosition("collect", true);
//        Robot.verticalLift.runToPosition("zero", true);
//    }
//
//    private void goToBackDrop() {
//        Robot.drivetrain.drive(-30, 1 * driveSpeed);
//        Robot.drivetrain.strafe(-14, 0.8 * driveSpeed);
//        // detect april tag
//        telemetry.addData("Searching for", desiredTagId);
//        telemetry.update();
//        runtime.reset();
//        Robot.webcam1.driveToTag(desiredTagId, searchTime, "clockwise");
//        sleep(searchTime * 1000);
//        if (Robot.webcam1.targetFound) {
//            Robot.drivetrain.drive(-10, driveSpeed);
//        } else {
//            Robot.drivetrain.drive(-15, driveSpeed);
//        }
//        telemetry.addLine("Done moving to aprilTag");
//
//         quickDeposit("high");
//    }
//
//    public void printRobotData() {
//
//        telemetry.addLine("\nRobot data:\n");
//
//        telemetry.addData("Element Position", elementPos);
//
//        Robot.verticalLift.printData();
//
//        Robot.motorCollector.printData();
//
//        // Robot.servoDeposit.printData();
//
//        telemetry.update();
//
//    }
//
//    public static String findMostCommonPos(String[] array) {
//        // Create a HashMap to store the count of each element
//        Map<String, Integer> elementCount = new HashMap<>();
//
//        // Iterate through the array and count occurrences of each element
//        for (String element : array) {
//            elementCount.put(element, elementCount.getOrDefault(element, 0) + 1);
//        }
//
//        // Find the element with the maximum count
//        String mostCommonElement = null;
//        int maxCount = 0;
//
//        for (Map.Entry<String, Integer> entry : elementCount.entrySet()) {
//            if (entry.getValue() > maxCount) {
//                mostCommonElement = entry.getKey();
//                maxCount = entry.getValue();
//            }
//        }
//
//        return mostCommonElement;
//    }
//
//    /**
//     * Initialize the TensorFlow Object Detection processor.
//     */
//
//    /**
//     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
//     */
//    private void telemetryTfod() {
//
//        List<Recognition> currentRecognitions = tfod.getRecognitions();
//        telemetry.addData("# Objects Detected", currentRecognitions.size());
//
//        int leftCutoff = 190;
//        int rightCutoff = 300;
//
//        // Step through the list of recognitions and display info for each one.
//        for (Recognition recognition : currentRecognitions) {
//            double x = (recognition.getLeft() + recognition.getRight()) / 2;
//            double y = (recognition.getTop() + recognition.getBottom()) / 2;
//            String appendElement = "None";
//            if (recognition.getLabel() == desiredLabel && recognition.getWidth() < 400) {
//                if (x < leftCutoff) {
//                    appendElement = "Left";
//                } else if (x > rightCutoff) {
//                    appendElement = "Right";
//                } else {
//                    appendElement = "Center";
//                }
//                if (appendElement != "None") {
//                    elementList = Arrays.copyOf(elementList, elementList.length + 1);
//                    elementList[elementList.length - 1] = appendElement;
//                }
//            }
//            telemetry.addData("", " ");
//            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
//            telemetry.addData("- Position", "%.0f / %.0f", x, y);
//            telemetry.addData("Guess", appendElement);
//            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
//        } // end for() loop
//
//    } // end method telemetryTfod()
//
//} // end class
