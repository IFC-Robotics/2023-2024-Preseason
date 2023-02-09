package org.firstinspires.ftc.teamcode.powerPlay.teleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@TeleOp(name="test REV color/range sensor", group="test")
public class TestRangeSensor extends LinearOpMode {

    ColorRangeSensor colorRangeSensor;

    @Override
    public void runOpMode() {

        colorRangeSensor = hardwareMap.get(ColorRangeSensor.class, "color_range_sensor");

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        while(opModeIsActive()) {
            telemetry.addData("Red", colorRangeSensor.red());
            telemetry.addData("Green", colorRangeSensor.green());
            telemetry.addData("Blue", colorRangeSensor.blue());
            telemetry.addData("Alpha", colorRangeSensor.alpha());
            telemetry.addData("MM Distance", colorRangeSensor.getDistance(DistanceUnit.MM));
            telemetry.addData("Meter Distance", colorRangeSensor.getDistance(DistanceUnit.METER));
            telemetry.update();
        }

    }

}