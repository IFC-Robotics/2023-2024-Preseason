package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.NoSubsystemRobotClass;

@Autonomous(name="thisauto", group="Test")
public class thisauto extends LinearOpMode {

    NoSubsystemRobotClass robot = new NoSubsystemRobotClass();

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);

        waitForStart();

        robot.strafe2(10.0);

//        telemetry.addData("drive", "");
//        telemetry.update();

//        robot.drive(12.0);
//        sleep(1000);
//
//        telemetry.addData("strafe", "");
//        telemetry.update();
//
//        robot.strafe(12.0);
//        sleep(1000);

//        telemetry.addData("turn", "");
//        telemetry.update();
//
//        robot.turn(90.0);

    }

}