package org.firstinspires.ftc.teamcode.powerPlay.oldCode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerPlay.oldCode.robot.RobotHardwareClass;

@Autonomous(name="AutoWithRobotClass")
@Disabled
public class AutoWithRobotClass extends LinearOpMode {

    RobotHardwareClass robot = new RobotHardwareClass();

    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        robot.drive();
    }

}