package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@TeleOp(name="test range sensor", group="test")
public class TestRangeSensor extends LinearOpMode {

    ColorRangeSensor colorRange;

    @Override
    public void runOpMode() {

        colorRange = hardwareMap.get(ColorRangeSensor.class, "color_range_sensor");

        telemetry.addLine("Initializing opMode...");
        telemetry.update();
        Robot.init(this);

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        while(opModeIsActive()) {
            telemetry.addData("Red", colorRange.red());
            telemetry.addData("Green", colorRange.green());
            telemetry.addData("Blue", colorRange.blue());
            telemetry.addData("Distance", colorRange.getDistance());
            telemetry.update();
        }

    }

}