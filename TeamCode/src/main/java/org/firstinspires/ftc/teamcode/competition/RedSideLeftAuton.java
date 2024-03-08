//package org.firstinspires.ftc.teamcode.competition;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.hardware.DistanceSensor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.teamcode.robot.Robot;
//
//
//@Autonomous(name="Red Side Left Park", group="Competition")
//public class RedSideLeftAuton extends LinearOpMode {
//
//    private ElapsedTime runtime = new ElapsedTime();
//    private DistanceSensor sensorDistanceLeft;
//    private DistanceSensor sensorDistanceRight;
//    double distLeft;
//    double distRight;
//    String pixelPos = "center";
//    double driveSpeed = 0.8;
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
//            Robot.drivetrain.turn(-90, driveSpeed);
//            quickDeposit("middle");
//            Robot.drivetrain.turn(90, driveSpeed);
//
//        } else if (pixelPos == "Right") {
//            Robot.drivetrain.turn(90, driveSpeed);
//            quickDeposit("middle");
//            Robot.drivetrain.turn(-90,driveSpeed);
//        } else {
//            Robot.drivetrain.drive(-4,driveSpeed);
//            Robot.drivetrain.turn(180, driveSpeed);
//            quickDeposit("middle");
//            Robot.drivetrain.turn(180,driveSpeed);
//
//        }
//
//        park();
//
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
//    private void park() {
//        Robot.drivetrain.drive(-27,0.5);
//        Robot.drivetrain.strafe(30, 0.5);
//    }
//
//}
