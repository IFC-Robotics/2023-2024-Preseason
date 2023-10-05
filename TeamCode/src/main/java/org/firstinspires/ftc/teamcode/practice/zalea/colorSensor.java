package org.firstinspires.ftc.teamcode.clubRush;
 
import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
 
@Autonomous
public class colorSensor extends LinearOpMode {
    // Define a variable for our color sensor
    ColorSensor color;
    
    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        color = hardwareMap.get(ColorSensor.class, "color_sensor_1");
        
        // Wait for the Play button to be pressed
        waitForStart();
 
        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {
            final float[] hsvValues = new float[3];

            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            Color.colorToHSV(colors.toColor(), hsvValues);

            telemetry.addData("Hue", hsvValues[0]);
            telemetry.addData("Saturation", hsvValues[1]);
            telemetry.addData("Value", hsvValues[2]);

            if (hsvValues[0] < 70 && hsvValues[0] > 50) {
                telemetry.addData("It's the duck!");
                telemetry.update();
            }
            
            telemetry.update();
        }
    }
}