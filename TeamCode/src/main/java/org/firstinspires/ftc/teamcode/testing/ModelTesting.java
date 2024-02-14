/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.testing;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@Autonomous(name = "Object Detection: Red & Blue", group = "Testing")

public class ModelTesting extends LinearOpMode {

    String elementPos = "Center";
    double driveSpeed = 0.5;
    int desiredTagId = -1;

    private ElapsedTime runtime = new ElapsedTime();

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "model_red&blue_low_step.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.
//    private static final String TFOD_MODEL_FILE = "/C:\\Users\\IFCro\\StudioProjects\\Center-Stage_2023-2024\\TeamCode\\src\\main\\res\\raw\\model_blue_box.tflite";
    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
            "Blue Box", "Red Box"
    };

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {

        initTfod();

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();

        Robot.drivetrain.drive(32,0.3);

        runtime.reset();

        if (opModeIsActive()) {
            while (opModeIsActive() && runtime.seconds() < 6) {

                telemetryTfod();

                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                // Share the CPU.
                sleep(20);
            }


        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

//        elementPos = findMostCommonPos(Robot.redBlueModel.elementPosList);

        telemetry.addData("Detected Position",elementPos);
        telemetry.update();

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
        Robot.motorCollector.runToPosition(300, true);
        // detect april tag
        runtime.reset();
        Robot.webcam1.driveToTag(desiredTagId,5,"");
        Robot.drivetrain.drive(-20, driveSpeed);
        telemetry.addLine("Done moving to aprilTag");

        quickDeposit("high");
    }

    public void printRobotData() {

        telemetry.addLine("\nRobot data:\n");

        telemetry.addData("Element Position",elementPos);

        Robot.verticalLift.printData();

        Robot.motorCollector.printData();

//        Robot.servoDeposit.printData();

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


    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // With the following lines commented out, the default TfodProcessor Builder
                // will load the default model for the season. To define a custom model to load,
                // choose one of the following:
                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(TFOD_MODEL_ASSET)
                //.setModelFileName(TFOD_MODEL_FILE)

                // The following default settings are available to un-comment and edit as needed to
//                 set parameters for custom models.
                .setModelLabels(LABELS)
                .setIsModelTensorFlow2(true)
                .setIsModelQuantized(true)
                .setModelInputSize(320)
                .setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        builder.setCameraResolution(new Size(1280, 720));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        tfod.setMinResultConfidence(0.55f);

        // Disable or re-enable the TFOD processor at any time.
        visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            if (x < 440) {
                elementPos = "Left";
            } else if (x > 840) {
                elementPos = "Right";
            } else {
                elementPos = "Center";
            }


            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("Guess",elementPos);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }   // end for() loop

    }   // end method telemetryTfod()

}   // end class
