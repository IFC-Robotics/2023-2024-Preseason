package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SensorClass {

    LinearOpMode opMode;
    Telemetry telemetry;

    public ColorRangeSensor sensor;
    public final String name;

    public SensorClass(String name) {
        this.name = name;
    }

    public void init(LinearOpMode opModeParam) {
        opMode = opModeParam;
        telemetry = opMode.telemetry;
        sensor = opMode.hardwareMap.get(ColorRangeSensor.class, this.name);
    }

    /*

        Built-in methods:

        r = sensor.red();
        g = sensor.green();
        b = sensor.blue();
        a = sensor.alpha();

     */

    public String getDominantColor() {

        int r = sensor.red();
        int g = sensor.green();
        int b = sensor.blue();

        if (isMuchBiggerThan(r, b, 500) && isMuchBiggerThan(r, g, 500)) return "red";
        if (isMuchBiggerThan(g, r, 500) && isMuchBiggerThan(g, b, 500)) return "green";
        if (isMuchBiggerThan(b, r, 500) && isMuchBiggerThan(b, g, 500)) return "blue";

        return "no dominant color";

    }

    public boolean isMuchBiggerThan(int color1, int color2, int reqDif) {
        return (color1 > color2 + reqDif);
    }

    public double getDistance(String unit) {
        if (unit == "mm" || unit == "millimeters") return sensor.getDistance(DistanceUnit.MM);
        if (unit == "cm" || unit == "centimeters") return sensor.getDistance(DistanceUnit.CM);
        else return sensor.getDistance(DistanceUnit.METER);
    }

    public void printSensorData(boolean colors, boolean distances, boolean dominantColor) {

        if (colors) {
            telemetry.addData("Red", sensor.red());
            telemetry.addData("Green", sensor.green());
            telemetry.addData("Blue", sensor.blue());
            telemetry.addData("Alpha", sensor.alpha());
        }

        if (distances) {
            telemetry.addData("MM Distance", sensor.getDistance(DistanceUnit.MM));
            telemetry.addData("CM Distance", sensor.getDistance(DistanceUnit.CM));
            telemetry.addData("Meter Distance", sensor.getDistance(DistanceUnit.METER));
        }

        if (dominantColor) {
            telemetry.addData("Dominant Color", getDominantColor());
        }

        telemetry.update();

    }

}