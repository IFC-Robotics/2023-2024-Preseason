package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.robot.Robot;


@Autonomous(name="Blue Side Left Park", group="Competition")
public class BlueSideLeftAuton extends LinearOpMode {

    double DRIVE_SPEED = 0.3;
    double STRAFE_SPEED = 0.3;
    double TURN_SPEED = 0.3;

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        //strafe left to park
        Robot.drivetrain.drive(30,0.5);
        Robot.drivetrain.drive(-2,0.5);
        Robot.drivetrain.drive(2,0.5);
        Robot.drivetrain.drive(-27,0.5);
        Robot.drivetrain.strafe(-30,0.5);


        telemetry.update();
    }

}
