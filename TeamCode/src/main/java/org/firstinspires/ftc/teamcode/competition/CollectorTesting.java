package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.robot.Robot;


@Autonomous(name="Collector Testing", group="Competition")
public class CollectorTesting extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    // end aprilTag detection config

    @Override
    public void runOpMode() {

        Robot.init(this);


        waitForStart();
        runtime.reset();
//        while (runtime.seconds() < 60 && opModeIsActive()) {
//            Robot.motorCollector.teleOp(1,0);
//        }
        }

}
