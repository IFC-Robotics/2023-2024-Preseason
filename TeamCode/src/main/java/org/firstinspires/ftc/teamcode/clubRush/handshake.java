package org.firstinspires.ftc.teamcode.clubRush;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Handshake", group="Linear Opmode")

public class handshake extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor armMotor;

    @Override

    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables
        armMotor  = hardwareMap.get(DcMotor.class, "arm_motor");

        // Wait for the turn to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run for 1 seconds
        while (runtime.seconds() < 0.55 && opModeIsActive()) {

            // Send calculated power to wheels
            armMotor.setPower(-0.32);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Status", "Run Time: " + new String(String.valueOf(armMotor.isBusy())));
            telemetry.update();
        }
        while (runtime.seconds() < 1.5 && runtime.seconds() > 0.55  && opModeIsActive()) {

            // Send calculated power to wheels
            armMotor.setPower(0.1);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}
