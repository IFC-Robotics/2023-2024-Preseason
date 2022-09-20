package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Basic TeleOp Code")
public class basicTeleOpCode extends LinearOpMode {

    public void runOpMode() {

        DcMotor motor = hardwareMap.get(DcMotor.class, "motor");

        waitForStart();

        while(opModeIsActive()) {
            motor.setPower(gamepad1.left_stick_y);
        }

    }

}