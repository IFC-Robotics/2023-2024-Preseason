package org.firstinspires.ftc.teamcode.clubRush;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Basic TeleOp Code", group = "clubRush")
public class zalea extends LinearOpMode{
    public int multiply(){
        int a = 45;
        int b = 28;

        return a*b
    }

    public void runOpMode() {

        int i = 0;

        public static DcMotor motor;

        motor = hardwareMap.get(DcMotor.class, "motor_1");

        waitForStart();

        while(opModeIsActive()) {
            System.out.println(i);
            i++;
        }

    }
}
