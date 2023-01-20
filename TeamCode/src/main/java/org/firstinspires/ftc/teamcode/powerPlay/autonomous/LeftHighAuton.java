package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

/*

To Do:

- right now, lift raises before servo hook closes
- fix camera issue. Instead of running for 10 sec, run while waiting for the program to start
- get rid of hardwareMap and telemetry inputs in Robot.init() throughout code
- adjust values of leftHighAuton.java until it works
- transfer to rightHighAuton.java

*/

@Autonomous(name="Left side High junction", group="competition")
public class LeftHighAuton extends LinearOpMode {

    double DRIVE_SPEED = 0.5;
    double STRAFE_SPEED = 0.5;
    double TURN_SPEED = 0.5;

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this, hardwareMap, telemetry);
        Robot.tag = Robot.camera.getTag();

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        // secure preloaded cone

        Robot.servoHook.runToPosition("extend");
        sleep(500);
        Robot.verticalLift.autonomousRunToPosition("ground");

        // drive to high junction

        Robot.drivetrain.drive(64, DRIVE_SPEED);
        Robot.drivetrain.drive(-8, DRIVE_SPEED);
        Robot.drivetrain.turn(45, TURN_SPEED);

        // score on high junction

        Robot.verticalLift.autonomousRunToPosition("high");
        Robot.drivetrain.drive(10, DRIVE_SPEED);
        sleep(2000);
        Robot.servoHook.runToPosition("retract");
        sleep(500);
        Robot.drivetrain.drive(-10, DRIVE_SPEED);
        Robot.verticalLift.autonomousRunToPosition("transfer");

        // park

        Robot.drivetrain.turn(135, TURN_SPEED);
        Robot.drivetrain.drive(20, DRIVE_SPEED);

        if (Robot.tag == 1) Robot.drivetrain.strafe(21, STRAFE_SPEED);
        if (Robot.tag == 3) Robot.drivetrain.strafe(-21, STRAFE_SPEED);

    }

}