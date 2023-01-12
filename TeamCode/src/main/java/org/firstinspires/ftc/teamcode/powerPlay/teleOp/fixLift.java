package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@TeleOp(name="fix Lift")
public class fixLift extends OpMode {

    @Override
    public void init() {
        telemetry.addLine("Initializing opMode...");
        telemetry.update();
        Robot.init(hardwareMap, telemetry);
    }

    @Override
    public void start() {
        telemetry.addLine("Executing opMode...");
        telemetry.update();
    }

    @Override
    public void loop() {
        Robot.horizontalLift.teleOpNoEncoderLimits(-gamepad2.left_stick_y);
        Robot.verticalLift.teleOpNoEncoderLimits(-gamepad2.right_stick_y);
    }

}