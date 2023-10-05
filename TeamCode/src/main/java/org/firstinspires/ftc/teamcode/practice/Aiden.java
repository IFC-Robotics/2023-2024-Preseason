package org.firstinspires.ftc.teamcode.practice;


import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;


//This is my (very much untested) code for finding my poor lost duck
//It uses a color sensor to to determine if an object is yellow and displays all the other attributes for clarity
// You might have to fiddle with my conditional statement to get a good yellow detector

@Autonomous(name="Aiden",group="Practice")
public class Aiden extends LinearOpMode{

    NormalizedColorSensor colorSensor1;

    @Override public void runOpMode() {

        // This increases the raw value, must be > 1
        float gain = 2;

        final float[] hsvValues = new float[3];

        colorSensor1 = hardwareMap.get(NormalizedColorSensor.class, "color_sensor_1");

        // If possible, turn the light on in the beginning
        if (colorSensor1 instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor1).enableLight(true);
        }

        waitForStart();

        while (opModeIsActive()) {
            colorSensor1.setGain(gain);

            NormalizedRGBA colors1 = colorSensor1.getNormalizedColors();

            Color.colorToHSV(colors1.toColor(), hsvValues);

            telemetry.addLine()
                    .addData("Red", "%.3f", colors1.red)
                    .addData("Green", "%.3f", colors1.green)
                    .addData("Blue", "%.3f", colors1.blue);
            telemetry.addLine()
                    .addData("Hue", "%.3f", hsvValues[0])
                    .addData("Saturation", "%.3f", hsvValues[1])
                    .addData("Value", "%.3f", hsvValues[2]);
            telemetry.addData("Alpha", "%.3f", colors1.alpha);

            if (colors1.red+colors1.green>4*colors1.blue){
                telemetry.addData('Duck.')
            }
            else if (colors1.red>colors1.green+colors1.blue){
                telemetry.addData('Cone.')
            }
            else if (colors1.red+colors1.green>2*colors1.blue){
                telemetry.addData('Ring.')
            }
            else if (colors1.red==colors1.blue && colors1.blue==colors1.green && colors1.green>175){
                telemetry.addData('Ball.')
            }
            else {
                telemetry.addData('No friends.')
            }

            telemetry.update();
        }
    }
}
