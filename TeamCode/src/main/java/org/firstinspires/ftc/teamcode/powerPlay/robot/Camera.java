package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public class Camera extends LinearOpMode {

    static int cameraMonitorViewId;
    public static OpenCvCamera camera;
    static AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static double fx = 578.272;
    static double fy = 578.272;
    static double cx = 402.145;
    static double cy = 221.506;

    static double tagsize = 0.166; // measured in meters

    static int LEFT = 1; // tag ID from the 36h11 family
    static int MIDDLE = 2;
    static int RIGHT = 3;

    static AprilTagDetection tagOfInterest = null;

    public Camera() {}

    @Override
    public void runOpMode() {}

    public void init(HardwareMap hardwareMap) {

        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() { camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT); }
            @Override
            public void onError(int errorCode) {}
        });

        telemetry.setMsTransmissionInterval(50);

    }

    public int getTag() {

        while (!isStarted() && !isStopRequested()) {

            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            for(AprilTagDetection tag : currentDetections) {
                if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT) {
                    tagOfInterest = tag;
                    break;
                }
            }

            sleep(20);

        }

        if(tagOfInterest != null) {
            telemetry.addData("Tag ID", tagOfInterest.id);
            return tagOfInterest.id;
        } else {
            telemetry.addLine("No tag available");
            return 0;
        }

    }

}