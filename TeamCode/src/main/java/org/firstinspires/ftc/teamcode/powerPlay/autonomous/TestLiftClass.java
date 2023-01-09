package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="Test Lift Class", group="Test")
public class TestLiftClass extends LinearOpMode {

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Robot.init(hardwareMap);
        waitForStart();

        Robot.horizontalLift.autonomousRunToPosition("collect");
        sleep(1000);

//        Robot.verticalLift.autonomousRunToPosition("high");
//        sleep(1000);

//        Robot.verticalLift.autonomousRunToPosition("transfer");
//        sleep(1000);

        Robot.horizontalLift.autonomousRunToPosition("transfer");

    }

}