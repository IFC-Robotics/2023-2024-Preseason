package org.firstinspires.ftc.teamcode.powerPlay;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="autonomous")
public class autonomous extends LinearOpMode {

    robotClass robot = new robotClass();
    private ElapsedTime runtime = new ElapsedTime();
    double DRIVE_SPEED = 0.5;

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);

        waitForStart();
        runtime.reset();

        robot.moveMotorOne(DRIVE_SPEED, 600, -1); // move forward

    }

}