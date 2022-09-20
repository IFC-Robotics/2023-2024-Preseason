package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Driving TeleOp Code")
public class drivingTeleOpCode extends LinearOpMode {

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;

    double FrontRight;
    double FrontLeft;
    double BackRight;
    double BackLeft;

    double drive;
    double strafe;
    double turn;

    public void runOpMode (){

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackLeft = hardwareMap.get(DcMotor.class, "motor_back_left");
        motorBackRight = hardwareMap.get(DcMotor.class, "motor_back_right");

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()){

            drive = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            turn = gamepad1.right_stick_x;

            FrontRight = Range.clip(drive - turn - strafe, -1.0, 1.0);
            FrontLeft = Range.clip(drive + turn + strafe, -1.0, 1.0);
            BackRight = Range.clip(drive - turn + strafe, -1.0, 1.0);
            BackLeft = Range.clip(drive + turn - strafe, -1.0, 1.0);

            motorFrontRight.setPower(FrontRight);
            motorFrontLeft.setPower(FrontLeft);
            motorBackRight.setPower(BackRight);
            motorBackLeft.setPower(BackLeft);

        }
    }
}

