package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
@Autonomous(name="IyanExamples",group="Practice")
public class IyanExamples extends LinearOpMode{
    public void runOpMode(){

        DcMotor motor;
        int i = 0;


        motor = hardwareMap.get(DcMotor.class,"motor_1");

        waitForStart();

        //counter that stops at 5000 loops
        while (opModeIsActive() && i <= 5000) {
            i += 1;
            telemetry.addData("counter: ", i);
            telemetry.update();
        }

        //run motor for 5 seconds
        motor.setPower(0.5);
        sleep(5000);
        motor.setPower(0);

        // encoder, runs to 5000 ticks
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // finish


    }

}
