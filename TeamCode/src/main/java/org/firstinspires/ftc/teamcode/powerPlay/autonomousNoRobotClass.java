package org.firstinspires.ftc.teamcode.powerPlay;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="autonomous (No Robot Class)", group = "PowerPlay")
public class autonomousNoRobotClass extends LinearOpMode {

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    DcMotor motorLift;
    Servo   claw;

    double FrontRight;
    double FrontLeft;
    double BackRight;
    double BackLeft;
    double LinearLift;



    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode () {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        motorFrontRight = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackLeft = hardwareMap.get(DcMotor.class, "motor_back_left");
        motorBackRight = hardwareMap.get(DcMotor.class, "motor_back_right");
        motorLift = hardwareMap.get(DcMotor.class, "motor_lift");

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
        public void drive(double speed, int distance, int direction) {

            int target = direction * distance;
            double power = direction * speed;
            moveDriveTrain(target, target, target, target, power, power, power, power);

        }


        public void strafe(double speed, int distance, int direction) {

            int target = direction * distance;
            double power = direction * speed;
            moveDriveTrain(-target, target, target, -target, -power, power, power, -power);

        }

        // TURN FUNCTION BELOW. Radius = 7.7862 in; Circumference = 48.92217 in = 360 degrees
        // 1120 ticks for 1 rotation

        public void turn(double speed, double angle) {

            double circumference = 66; // 70
            double distance = circumference*(angle/360);
            int intDistrot = (int)inchToTics(distance);

            if (angle < 0) {
                moveDriveTrain(intDistrot, -intDistrot, intDistrot, -intDistrot, speed, -speed, speed, -speed);
            } else if (angle >= 0) {
                moveDriveTrain(intDistrot, -intDistrot, intDistrot, -intDistrot, -speed, speed, -speed, speed);
            }

        }

        public void lift(double speed, double height) {
            double ticHeight = inchToTics(height);

            motorLift.setTargetPosition((int) ticHeight);
            motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            motorLift.setPower(speed);

            while (motorLift.isBusy() && opModeIsActive()) {

            }

            motorLift.setPower(0);
        }

        public void moveClaw() {
            //Test this later, we don't know the amount
            claw.setPosition(0.33);
            lift(1, 0);
            claw.setPosition(0);

            //Lifting up to a random position, remember to change
            double liftRandomPosition = Math.random() * 100;
            lift(2, liftRandomPosition);
        }

        public double inchToTics(double inch) {

//            int tics = 1129;
//            int dia = 4;
//            double ratio = 0.64;
            final double COUNTS_PER_REVOLUTION    = 28.0;
            final double GEAR_RATIO               = 20.0;
            final double WHEEL_DIAMETER_IN_INCHES = 1.0;
            final double COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION * GEAR_RATIO) / (WHEEL_DIAMETER_IN_INCHES * Math.PI);

            return COUNTS_PER_INCH;

//            double wheelRot = inch / (Math.PI * dia);
//            double motorRot = wheelRot * ratio;
//            double finalTic = tics * motorRot;
//            return finalTic;

        }

        public void moveDriveTrain(int frontRightTarget, int frontLeftTarget, int backRightTarget, int backLeftTarget, double frontRightSpeed, double frontLeftSpeed, double backRightSpeed, double backLeftSpeed) {

            motorFrontRight.setTargetPosition(frontRightTarget);
            motorFrontLeft.setTargetPosition(frontLeftTarget);
            motorBackRight.setTargetPosition(backRightTarget);
            motorBackLeft.setTargetPosition(backLeftTarget);

            motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            motorFrontRight.setPower(frontRightSpeed);
            motorFrontLeft.setPower(frontLeftSpeed);
            motorBackRight.setPower(backRightSpeed);
            motorBackLeft.setPower(backLeftSpeed);

            while (motorBackRight.isBusy() && opModeIsActive()) {
            }

            motorFrontRight.setPower(0);
            motorFrontLeft.setPower(0);
            motorBackRight.setPower(0);
            motorBackLeft.setPower(0);

            motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

}