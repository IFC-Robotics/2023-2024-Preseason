package org.firstinspires.ftc.teamcode.practice.zalea;

import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

@Autonomous
public class colorSensor extends LinearOpMode {
    // Define a variable for our color sensor
    NormalizedColorSensor colorSensor;

    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "color_sensor_1");

        float gain = 2;
        float[] hsvValues = new float[3];
        // Wait for the Play button to be pressed
        waitForStart();

        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {
//            colorSensor.setGain(gain);

            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            Color.colorToHSV(colors.toColor(), hsvValues);

            telemetry.addData("Hue", hsvValues[0]);
            telemetry.addData("Saturation", hsvValues[1]);
            telemetry.addData("Value", hsvValues[2]);
            telemetry.addData("It's the duck!", false);
            telemetry.update();

            if (hsvValues[0] < 70 && hsvValues[0] > 50) {
                telemetry.addData("It's the duck!", true);
                telemetry.update();
            }
        }
    }
}