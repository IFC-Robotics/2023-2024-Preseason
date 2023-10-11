package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import android.graphics.Color;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

@Autonomous(name="Megan code", group = "Practice")
public class megan extends LinearOpMode {
    public int multiply() {
        int a = 99;
        int b = 20;
        int c = a * b;
        return c;
    }

    NormalizedColorSensor colorSensor1;

    public void runOpMode() {
        int i = 0;
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("i: ", i);
            telemetry.update();
            i += 1;
            DcMotor motor;
            motor = hardwareMap.get(DcMotor.class, "motor_1");


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

                if (colors1.red + colors1.green > 4 * colors1.blue) {
                    telemetry.addData("Found the duck!", "");
                } else if (colors1.red > colors1.green + colors1.blue) {
                    telemetry.addData("It's the cone!", "");
                } else if (colors1.red == colors1.green && colors1.green == colors1.blue) {
                    telemetry.addData("The ball!", "");
                } else {
                    telemetry.addData("Keep looking", "");
                }

                telemetry.update();
            }
        }
    }
}
