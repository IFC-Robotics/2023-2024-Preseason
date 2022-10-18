package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Basic Autonomous Code")
@Disabled
public class basicAutonomous extends LinearOpMode {

    DcMotor motor;

    public void runOpMode() {

        motor = hardwareMap.get(DcMotor.class, "motor");

        waitForStart();

        motor.setPower(1);
        sleep(1000);
        motor.setPower(0);

    }

}