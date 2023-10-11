package org.firstinspires.ftc.teamcode.practice.zalea;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Zalea code", group = "Practice")
public class zalea extends LinearOpMode{
    public int multiply(){
        int a = 45;
        int b = 28;

        return a*b;
    }

    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        int i = 0;

        DcMotor motor;

        motor = hardwareMap.get(DcMotor.class, "motor_1");

        waitForStart();

        while(opModeIsActive()) {
//            System.out.println(i);
            telemetry.addData("i: ", i);
            telemetry.update();
            i++;
        }

    }
}
