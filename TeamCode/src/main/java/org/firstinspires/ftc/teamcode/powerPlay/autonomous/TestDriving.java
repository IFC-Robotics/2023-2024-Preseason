// package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

// import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

// @Autonomous(name="Test Driving", group="Test")
// public class TestDriving extends LinearOpMode {

//     public void runOpMode() {

//         telemetry.addLine("Initializing opMode...");
//         telemetry.update();

//         Robot.init(this);

//         waitForStart();

//         telemetry.addLine("Executing opMode...");
//         telemetry.update();

//         Robot.drivetrain.drive(12.0, 0.5);
//         sleep(3000);
//         Robot.drivetrain.strafe(12.0, 0.5);
//         sleep(3000);
//         Robot.drivetrain.turn(90.0, 0.5);
//         sleep(1000);
//         Robot.drivetrain.turn(-180.0, 0.5);

//     }

// }