package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Practice Autonomous", group="Linear Opmode")

public class ZaleaAuton extends LinearOpMode {

    // Declare OpMode members.
    DcMotor motor;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. 
        motor  = hardwareMap.get(DcMotor.class, "motor_front_left");

        
        motor.setDirection(DcMotor.Direction.FORWARD);

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        
        waitForStart();

        telemetry.addLine("Running autonomous");
        telemetry.update();

        goToPosition(1000);
        goToPosition(5000);
        goToPosition(1300);
        goToPosition(8765);
        goToPosition(1724);

    }

    public void goToPosition(int position){
        motor.setTargetPosition(position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0.5);
        
        while (motor.isBusy()) {

        }

        motor.setPower(0);
        sleep(1000);

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}