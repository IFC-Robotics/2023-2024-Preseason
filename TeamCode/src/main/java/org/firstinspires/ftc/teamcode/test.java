package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.RobotClass;

@Autonomous(name="test")
public class test extends LinearOpMode {

    RobotClass robot = new RobotClass();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        // add robot.moveHorizontalLift() where necessary

//        robot.moveClaw("close");
//        sleep(1000);
//        robot.rotateClaw("up");
//        sleep(2000);
        robot.moveHook("extend");
        sleep(2000);
//        robot.moveClaw("open");
//        sleep(1000);
        robot.rotateClaw("down");
        sleep(2000);

        robot.moveVerticalLift("high");
        sleep(2000);
        robot.rotateHook("deposit");
//        sleep(1000);
//        robot.moveHook("retract");
//        sleep(1000);
//
//        robot.rotateHook("deposit");
//        sleep(1000);
//        robot.moveVerticalLift("transfer");

    }

}