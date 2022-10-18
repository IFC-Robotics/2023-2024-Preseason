package org.firstinspires.ftc.teamcode.powerPlay;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="autonomous")
public class autonomous extends LinearOpMode {

//    robotClass robot = new robotClass();
    private ElapsedTime runtime = new ElapsedTime();
    double DRIVE_SPEED = 0.5;

    public DcMotor motorOne;

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        motorOne = hardwareMap.get(DcMotor.class, "motor_one");

        waitForStart();
        runtime.reset();

//        robot.moveMotorOne(DRIVE_SPEED, 6000, -1); // move forward

        motorOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // resets the encoder
        motorOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // makes it so the encoder can receive a target position

        motorOne.setTargetPosition(6000); // sets the position the motor will move to

        motorOne.setMode(DcMotor.RunMode.RUN_TO_POSITION); // moves the motor
        motorOne.setPower(-0.1);

        while (motorOne.isBusy() && opModeIsActive()) {
            telemetry.addData("v6", "");
            telemetry.addData("robot.motorOne current position", (float)motorOne.getCurrentPosition());
            telemetry.addData("robot.motorOne target position", motorOne.getTargetPosition());
            telemetry.addData("robot.motorOne DRIVE_SPEED", DRIVE_SPEED);
            telemetry.addData("robot.motorOne getPower()", motorOne.getPower());
            telemetry.update();
        }

        telemetry.addData("Done with while loop", "");
        telemetry.update();

        motorOne.setPower(0); // stops the motor
        motorOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // resets the encoder
        motorOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // makes it so the encoder can receive a target position

    }

}