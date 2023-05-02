package org.firstinspires.ftc.teamcode.powerPlay.practiceCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Zalea's Practice Autonomous", group="Linear Opmode")

public class ZaleaAuton extends LinearOpMode {

    // Declare OpMode members.
    DcMotor motor;

    static final double COUNTS_PER_REVOLUTION    = 28.0;
    static final double GEAR_RATIO               = 20.0;
    static final double WHEEL_DIAMETER_IN_INCHES = 4.0;
    static double TICKS_PER_INCH;
    static int TICKS;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. 
        motor  = hardwareMap.get(DcMotor.class, "motor");

        
        motor.setDirection(DcMotor.Direction.FORWARD);

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        
        waitForStart();

        telemetry.addLine("Running autonomous");
        telemetry.update();

        goToPosition(inchesToTicks(24));
        goToPosition(5000);
        goToPosition(1300);
        goToPosition(8765);
        goToPosition(1724);

    }

    public void goToPosition(int position){
        motor.setTargetPosition(position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0.5);
        
        while (motor.isBusy()) {

        }

        motor.setPower(0);
        sleep(1000);

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int inchesToTicks(int inches){
        TICKS_PER_INCH = (COUNTS_PER_REVOLUTION*GEAR_RATIO)/(Math.PI*WHEEL_DIAMETER_IN_INCHES);
        TICKS = (int)(inches*TICKS_PER_INCH);

        return TICKS;
    }

}