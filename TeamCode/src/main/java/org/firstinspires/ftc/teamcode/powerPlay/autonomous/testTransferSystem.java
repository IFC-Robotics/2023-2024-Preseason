package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robotClass;

@Autonomous(name="Test Transfer System")
public class testTransferSystem extends LinearOpMode {

    robotClass robot = new robotClass();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        //robot.waitForStart();
        waitForStart();
        robot.moveHook("extend");
        sleep(500);
        robot.lift("high");
        sleep(500);
        robot.rotateHook("deposit");
        sleep(500);
        robot.rotateHook("transfer");
        sleep(500);
        robot.lift("transfer");
        sleep(500);
        robot.moveHook("retract");

    }

}