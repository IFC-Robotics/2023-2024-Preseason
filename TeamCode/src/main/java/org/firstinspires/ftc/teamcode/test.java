package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

@Autonomous(name="test")
public class test extends LinearOpMode {

    robotClass robot = new robotClass();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        robot.moveClaw("close");
        sleep(1000);
        robot.rotateClaw("transfer");
        sleep(1000);
        robot.moveHook("extend");
        sleep(1000);
        robot.moveClaw("open");
        sleep(1000);
        robot.rotateClaw("collect");
        sleep(1000);

        robot.lift("high");
        sleep(1000);
        robot.rotateHook("deposit");
        sleep(1000);
        robot.moveHook("retract");
        sleep(1000);

        robot.rotateHook("deposit");
        sleep(1000);
        robot.lift("transfer");

//        robot.transferCone();
//        robot.liftTransfer("high");

    }

}