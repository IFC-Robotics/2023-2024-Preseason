package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Robot: Auto Drive By Encoder")
public class autoWithEncoders2 extends LinearOpMode {

    private DcMotor motorOne;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        motorOne = hardwareMap.get(DcMotor.class, "motor_one");

        motorOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Starting at",  "%7d", motorOne.getCurrentPosition());
        telemetry.update();

        waitForStart();

        int maxTime = 30;
        double speed = 1;
        int target = 100;

        if (opModeIsActive()) {

            int newLeftTarget = motorOne.getCurrentPosition() + target;

            motorOne.setTargetPosition(newLeftTarget);
            motorOne.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            motorOne.setPower(speed);

            while (opModeIsActive() && runtime.seconds() < maxTime && motorOne.isBusy()) {
                telemetry.addData("Running to",  " %7d", newLeftTarget);
                telemetry.addData("Currently at",  " at %7d", motorOne.getCurrentPosition());
                telemetry.update();
            }

            motorOne.setPower(0);
            motorOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);

    }

}