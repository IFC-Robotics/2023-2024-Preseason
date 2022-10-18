package org.firstinspires.ftc.teamcode.powerPlay;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="teleOp")
public class teleOp extends LinearOpMode {

    robotClass robot = new robotClass();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.init(hardwareMap);
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            robot.motorOne.setPower(-gamepad1.left_stick_y);
        }

    }

}