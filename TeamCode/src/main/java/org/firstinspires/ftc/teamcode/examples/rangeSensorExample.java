package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Range Sensor Example", group = "Examples")
@Disabled
public class rangeSensorExample extends LinearOpMode {

    ModernRoboticsI2cRangeSensor rangeSensor;

    @Override
    public void runOpMode() {

        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range_sensor");

        waitForStart();

        while (opModeIsActive()) {
        
            telemetry.addData("raw ultrasonic", rangeSensor.rawUltrasonic());
            telemetry.addData("raw optical", rangeSensor.rawOptical());
            telemetry.addData("cm ultrasonic", rangeSensor.cmUltrasonic());
            telemetry.addData("cm optical", rangeSensor.cmOptical());
            telemetry.addData("cm best", rangeSensor.getDistance(DistanceUnit.CM));
            telemetry.update();
            
        }
    }
}
