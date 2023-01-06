package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.robot.RobotClass;

@Autonomous(name="Reset Robot")
public class ResetRobot extends LinearOpMode {

    RobotClass robot = new RobotClass();

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();

        robot.moveClaw("open");
        robot.moveHorizontalLift("transfer");
        robot.rotateHook("transfer");
        robot.moveHook("retract");
        robot.moveVerticalLift("transfer");

    }

}