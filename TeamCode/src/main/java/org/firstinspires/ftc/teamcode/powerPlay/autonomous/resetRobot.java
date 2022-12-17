package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

@Autonomous(name="Reset Robot")
public class resetRobot extends LinearOpMode {

    robotClass robot = new robotClass();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        robot.moveClaw("open");
        robot.rotateClaw("collect");
        robot.rotateHook("transfer");
        robot.moveHook("retract");
        robot.lift("transfer");

    }

}