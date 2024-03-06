//package org.firstinspires.ftc.teamcode.competition;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DistanceSensor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.util.Range;
//
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.teamcode.robot.Robot;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//
//// DISTANCES ARE TUNED
//// SERVO WON'T ROTATE BACK FOR SOME REASON IDK
//
//
//
//@Autonomous(name = "Red Range Auton (Run this if on RedRight)", group = "Competition")
//
//public class RedRangeSensorAndDeposit extends LinearOpMode {
//    private ElapsedTime runtime = new ElapsedTime();
//    private DistanceSensor sensorDistanceLeft;
//    private DistanceSensor sensorDistanceRight;
//    double distLeft;
//    double distRight;
//    String pixelPos = "center";
//    double driveSpeed = 0.8;
//
//    // aprilTag detection config
//
//    private static final boolean USE_WEBCAM = false;
//    private VisionPortal visionPortal;               // Used to manage the video source.
//    private AprilTagProcessor aprilTag;              // Used for managing the AprilTag detection process.
//    private AprilTagDetection desiredTag = null;
//    private int desiredTagId;
//
//    final double DESIRED_DISTANCE = 0.0; //  this is how close the camera should get to the target (inches)
//
//    //  Set the GAIN constants to control the relationship between the measured position error, and how much power is
//    //  applied to the drive motors to correct the error.
//    //  Drive = Error * Gain    Make these values smaller for smoother control, or larger for a more aggressive response.
//    final double SPEED_GAIN  =  0.02  ;   //  Forward Speed Control "Gain". eg: Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
//    final double STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  eg: Ramp up to 25% power at a 25 degree Yaw error.   (0.25 / 25.0)
//    final double TURN_GAIN   =  0.01  ;   //  Turn Control "Gain".  eg: Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)
//
//    final double MAX_AUTO_SPEED = 0.7;   //  Clip the approach speed to this max value (adjust for your robot)
//    final double MAX_AUTO_STRAFE= 0.7;   //  Clip the approach speed to this max value (adjust for your robot)
//    final double MAX_AUTO_TURN  = 0.3;
//
//    boolean targetFound     = false;    // Set to true when an AprilTag target is detected
//    double  drive           = 0;        // Desired forward power/speed (-1 to +1)
//    double  strafe          = 0;        // Desired strafe power/speed (-1 to +1)
//    double  turn            = 0;
//
//    // end aprilTag detection config
//
//    @Override
//    public void runOpMode() {
//
//        // you can use this as a regular DistanceSensor.
//        sensorDistanceLeft = hardwareMap.get(DistanceSensor.class, "sensor_range_left");
//        sensorDistanceRight = hardwareMap.get(DistanceSensor.class, "sensor_range_right");
//
//        //initAprilTag();
//
//        Robot.init(this);
//
//        if (USE_WEBCAM)
//            setManualExposure(6, 250);
//
//        waitForStart();
//        Robot.drivetrain.drive(32,0.7);
//        runtime.reset();
//        while (runtime.seconds() < 1.2 && opModeIsActive()){ //maybe shorten this for more time
//            distLeft = sensorDistanceLeft.getDistance(DistanceUnit.CM);
//            distRight = sensorDistanceRight.getDistance(DistanceUnit.CM);
//
//            if (distLeft <= 100) {
//                pixelPos = "Left";
//            } else if (distRight <= 100 && opModeIsActive()) {
//                pixelPos = "Right";
//            }
//
//            // generic DistanceSensor methods.
//            telemetry.addData("sensorLeft", sensorDistanceLeft.getDeviceName());
//            telemetry.addData("range", String.format("%.01f cm", distLeft));
//
//            telemetry.addData("sensorRight", sensorDistanceRight.getDeviceName());
//            telemetry.addData("range", String.format("%.01f cm", distRight));
//
//            telemetry.addData("runtime",runtime.seconds());
//            telemetry.addData("Pixel Pos:", pixelPos);
//            telemetry.update();
//
//        }
//        telemetry.addData("direction",pixelPos);
//        telemetry.update();
//
//
//        if (pixelPos == "Left") {
//            desiredTagId = 4;
//
//            Robot.drivetrain.turn(-90, driveSpeed);
//            Robot.motorCollector.runToPosition(-300, true);
//            Robot.drivetrain.turn(180, driveSpeed);
//
//            Robot.drivetrain.strafe(16, driveSpeed);
//
//        } else if (pixelPos == "Right") {
//            desiredTagId = 6;
//
//            Robot.drivetrain.turn(90, driveSpeed);
//
//            Robot.motorCollector.runToPosition(-300, true);
//            Robot.drivetrain.strafe(16, driveSpeed);
//            Robot.drivetrain.turn(180, driveSpeed);
//        } else {
//            desiredTagId = 5;
//
//            Robot.drivetrain.drive(4, driveSpeed);
//
//            Robot.drivetrain.turn(180, driveSpeed);
//            Robot.motorCollector.runToPosition(-300, true);
//            Robot.drivetrain.turn(90, driveSpeed);
//
//            Robot.drivetrain.strafe(-14, driveSpeed);
//
//        }
//
//        goToBackDrop();
//    }
//
//    private void quickDeposit(String position) {
//        Robot.verticalLift.runToPosition(position, true);
//        Robot.servoDeposit.runToPosition("auton",true);
//        sleep(1000);
//        Robot.servoDeposit.runToPosition("collect",true);
//        Robot.verticalLift.runToPosition("zero", true);
//    }
//
//    private void goToBackDrop() {
////        Robot.drivetrain.strafe(10, 1.2*driveSpeed);
//        Robot.drivetrain.drive(-16,1.2*driveSpeed);
//        Robot.drivetrain.strafe(6, driveSpeed);
//        Robot.motorCollector.runToPosition(500, true);
//        // detect april tag
//        runtime.reset();
////        moveToAprilTag();
//        Robot.drivetrain.drive(-20, driveSpeed);
////        telemetry.addLine("Done moving to aprilTag");
//
////        Robot.drivetrain.strafe(-8, driveSpeed);
////        Robot.drivetrain.drive(12, driveSpeed);
//        quickDeposit("high");
//    }
//
//
//    private void moveToAprilTag() {
//        telemetry.addData("Desired Tag", "Desired tag is %d", desiredTagId);
//        while (opModeIsActive() && runtime.seconds() < 6.5) {
//            targetFound = false;
//            desiredTag = null;
//
//            // Step through the list of detected tags and look for a matching tag
//            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
//            for (AprilTagDetection detection : currentDetections) {
//                // Look to see if we have size info on this tag.
//                if (detection.metadata != null) {
//                    //  Check to see if we want to track towards this tag.
//                    if ((desiredTagId < 0) || (detection.id == desiredTagId)) {
//                        // Yes, we want to use this tag.
//                        targetFound = true;
//                        desiredTag = detection;
//                        break;  // don't look any further.
//                    } else {
//                        // This tag is in the library, but we do not want to track it right now.
//                        telemetry.addData("Skipping", "Tag ID %d is not desired", detection.id);
//                    }
//                } else {
//                    // This tag is NOT in the library, so we don't have enough information to track to it.
//                    telemetry.addData("Unknown", "Tag ID %d is not in TagLibrary", detection.id);
//                }
//            }
//            if (targetFound) {
//
//                // Determine heading, range and Yaw (tag image rotation) error so we can use them to control the robot automatically.
//                double rangeError = (desiredTag.ftcPose.range - DESIRED_DISTANCE);
//                double headingError = desiredTag.ftcPose.bearing;
//                double yawError = desiredTag.ftcPose.yaw;
//
//                // Use the speed and turn "gains" to calculate how we want the robot to move.
//                drive = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
//                turn = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN);
//                strafe = Range.clip(-yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);
//
//                telemetry.addData("Auto", "Drive %5.2f, Strafe %5.2f, Turn %5.2f ", drive, strafe, turn);
//            } else {
//
//                // drive using manual POV Joystick mode.  Slow things down to make the robot more controlable.
////                drive = -gamepad1.left_stick_y / 2.0;  // Reduce drive rate to 50%.
////                strafe = -gamepad1.left_stick_x / 2.0;  // Reduce strafe rate to 50%.
////                turn = -gamepad1.right_stick_x / 3.0;  // Reduce turn rate to 33%.
////                telemetry.addData("Manual", "Drive %5.2f, Strafe %5.2f, Turn %5.2f ", drive, strafe, turn);
//                drive = 0.0;
//                strafe = 0.0;
//                turn = 0.0;
//                telemetry.addLine("couldn't find right aprilTag");
//            }
//            telemetry.update();
//
//            // Apply desired axes motions to the drivetrain.
//            Robot.drivetrain.moveRobot(-drive, strafe, turn);
//            sleep(10);
//        }
//    }
//
//    private void initAprilTag() {
//        // Create the AprilTag processor by using a builder.
//        aprilTag = new AprilTagProcessor.Builder().build();
//
//        // Adjust Image Decimation to trade-off detection-range for detection-rate.
//        // eg: Some typical detection data using a Logitech C920 WebCam
//        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
//        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
//        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second
//        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second
//        // Note: Decimation can be changed on-the-fly to adapt during a match.
//        aprilTag.setDecimation(2);
//
//        // Create the vision portal by using a builder.
//        if (USE_WEBCAM) {
//            visionPortal = new VisionPortal.Builder()
//                    .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
//                    .addProcessor(aprilTag)
//                    .build();
//        } else {
//            visionPortal = new VisionPortal.Builder()
//                    .setCamera(BuiltinCameraDirection.BACK)
//                    .addProcessor(aprilTag)
//                    .build();
//        }
//    }
//
//    private void setManualExposure(int exposureMS, int gain) {
//        // Wait for the camera to be open, then use the controls
//
//        if (visionPortal == null) {
//            return;
//        }
//
//        // Make sure camera is streaming before we try to set the exposure controls
//        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
//            telemetry.addData("Camera", "Waiting");
//            telemetry.update();
//            while (!isStopRequested() && (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
//                sleep(20);
//            }
//            telemetry.addData("Camera", "Ready");
//            telemetry.update();
//        }
//
//        // Set camera controls unless we are stopping.
//        if (!isStopRequested())
//        {
//            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
//            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
//                exposureControl.setMode(ExposureControl.Mode.Manual);
//                sleep(50);
//            }
//            exposureControl.setExposure((long)exposureMS, TimeUnit.MILLISECONDS);
//            sleep(20);
//            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
//            gainControl.setGain(gain);
//            sleep(20);
//        }
//    }
//
//}
