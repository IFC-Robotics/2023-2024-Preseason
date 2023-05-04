package org.firstinspires.ftc.teamcode.powerPlay.practiceCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Zalea's Practice Autonomous", group="Linear Opmode")

public class ZaleaAuton extends LinearOpMode {

    // Declare OpMode members.
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;

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
        frontRight  = hardwareMap.get(DcMotor.class, "motor_front_right");
        frontLeft  = hardwareMap.get(DcMotor.class, "motor_front_left");
        backRight  = hardwareMap.get(DcMotor.class, "motor_back_right");
        backLeft  = hardwareMap.get(DcMotor.class, "motor_back_left");

        
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        
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
        frontRight.setTargetPosition(position);
        frontLeft.setTargetPosition(position);
        backRight.setTargetPosition(position);
        backLeft.setTargetPosition(position);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setPower(0.5);
        frontLeft.setPower(0.5);
        backRight.setPower(0.5);
        backLeft.setPower(0.5);
        
        while (frontRight.isBusy() || frontLeft.isBusy() || backRight.isBusy() || backLeft.isBusy()) {

        }

        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
        sleep(1000);

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int inchesToTicks(int inches){
        TICKS_PER_INCH = (COUNTS_PER_REVOLUTION*GEAR_RATIO)/(Math.PI*WHEEL_DIAMETER_IN_INCHES);
        TICKS = (int)(inches*TICKS_PER_INCH);

        return TICKS;
    }

}