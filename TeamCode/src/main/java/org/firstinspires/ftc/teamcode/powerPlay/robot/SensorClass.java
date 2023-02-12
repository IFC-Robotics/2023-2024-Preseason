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

        if (isMuchBiggerThan(r, b, 1.4) && isMuchBiggerThan(r, g, 1.4)) return "red";
        if (isMuchBiggerThan(b, r, 1.4) && isMuchBiggerThan(b, g, 1.4)) return "blue";

        return "no dominant color";

    }

    public boolean isMuchBiggerThan(int color1, int color2, double reqRatio) {
        return (color1 > color2 * reqRatio);
    }

    public double getDistance(String unit) {
        double rawDistance = 0.0;
        double factor = 0.55;
        if (unit == "mm" || unit == "millimeters") rawDistance = sensor.getDistance(DistanceUnit.MM);
        if (unit == "cm" || unit == "centimeters") rawDistance = sensor.getDistance(DistanceUnit.CM);
        if (unit == "m"  || unit == "meters")      rawDistance = sensor.getDistance(DistanceUnit.METER);
        return round(rawDistance, 3) * factor;
    }

    public void printSensorData(boolean colors, boolean distances, boolean dominantColor) {

        if (colors) {
            telemetry.addData("Red", sensor.red());
            telemetry.addData("Green", sensor.green());
            telemetry.addData("Blue", sensor.blue());
            telemetry.addData("Alpha", sensor.alpha());
        }

        if (distances) {
            telemetry.addData("MM Distance", getDistance("mm"));
            telemetry.addData("CM Distance", getDistance("cm"));
            telemetry.addData("Meter Distance", getDistance("meters"));
        }

        if (dominantColor) {
            telemetry.addData("Dominant Color", getDominantColor());
        }

        telemetry.update();

    }

    public double round(double value, double numDecimalPlaces) {
        double exponent = Math.pow(10, numDecimalPlaces);
        return Math.round(value * exponent) / exponent;
    }

}