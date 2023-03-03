package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Joaquin's Autonomous Test", group = "test")
public class JoaquinAuton extends LinearOpMode{

    DcMotor motor;

    public void runOpMode() {
        
        motor = hardwareMap.get(DcMotor.class, "motor");

        waitForStart();

        motor.setPower(1);
        sleep(1000);
        motor.setPower(0);

    }

}