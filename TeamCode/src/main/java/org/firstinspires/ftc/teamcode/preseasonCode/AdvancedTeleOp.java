package org.firstinspires.ftc.teamcode.preseasonCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;

@TeleOp(name="Advanced TeleOp", group="Preseason Code")
@Disabled
public class AdvancedTeleOp extends LinearOpMode {

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    DcMotor motorLinearLift;
    DcMotor motorCollector;
    DcMotor motorCollectorArm;
    DcMotor motorSpinner;

    Servo servoLinearLift;
    Servo servoTransfer;
    CRServo servoArm;

    ModernRoboticsI2cRangeSensor leftRangeSensor;
    ModernRoboticsI2cRangeSensor rightRangeSensor;
    ModernRoboticsI2cRangeSensor bottomRangeSensor;

    int leftDistance;
    int rightDistance;
    int lowerDistance;
    int bottomDistance;

    int bottomSensorDistFromGround = 6;
    double bottomSensorDistError = 1;

    double drive;
    double strafe;
    double turn;

    double FrontRight;
    double FrontLeft;
    double BackRight;
    double BackLeft;
    
    boolean collecting = false;
    boolean depositing = false;
    boolean bumperRelease = true;
    boolean assistMode = true;
    boolean assistRelease = true;
    boolean dpadPress = false;
    boolean autoDrop = true;
    boolean dropRelease = true;

    int armDown = 0;
    int transferOpen = 0;
    int liftOut = 0;
    int droppingFreight = 0;
    
    double linearLiftDepositorHeight = 0.63;
    double transferServoPosition = 0.63;
    
    double linearLiftDist;
    double linearLift;
    double armDist;
    double armPower;

    // linear lift limits
    double maxDist = 1500;
    int minDist = 0;

    // arm limits
    int armMax = 2000;
    int armMin = 0;

    // angling servo
    double currLength = 10;
    double moveTime = 0;
    int anglePower = 1;

    double targetX = 45;
    double targetY = 45;

    double spinPower = 0;

    double pause = 0;
    private ElapsedTime runtime   = new ElapsedTime();
    public  ElapsedTime angleTime = new ElapsedTime();

    public void runOpMode() {

        motorFrontRight   = hardwareMap.get(DcMotor.class, "motor_front_right");
        motorFrontLeft    = hardwareMap.get(DcMotor.class, "motor_front_left");
        motorBackLeft     = hardwareMap.get(DcMotor.class, "motor_back_left");
        motorBackRight    = hardwareMap.get(DcMotor.class, "motor_back_right");

        motorCollector    = hardwareMap.get(DcMotor.class, "motor_collector");
        motorCollectorArm = hardwareMap.get(DcMotor.class, "motor_arm");
        motorSpinner      = hardwareMap.get(DcMotor.class, "motor_spinner");
        motorLinearLift   = hardwareMap.get(DcMotor.class, "motor_linear_lift");

        servoLinearLift   = hardwareMap.get(Servo.class, "servo_linear_lift");
        servoTransfer     = hardwareMap.get(Servo.class, "servo_transfer");
        servoArm          = hardwareMap.get(CRServo.class, "CRServo_linear_lift");

        leftRangeSensor   = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "left_range_sensor");
        rightRangeSensor  = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "right_range_sensor");
        bottomRangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "bottom_range_sensor");
        
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);

        motorLinearLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLinearLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorCollectorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorCollectorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        runtime.reset();
        angleTime.reset();

        while (opModeIsActive()) {

            leftDistance = leftRangeSensor.rawUltrasonic();
            rightDistance = rightRangeSensor.rawUltrasonic();
            lowerDistance = Math.min(leftDistance, rightDistance);

            telemetry.addData("move time: ", moveTime);
            telemetry.addData("angle power: ", anglePower);
            telemetry.addData("current length: ", currLength);
            telemetry.addData("max distance: ", maxDist);
            telemetry.addData("target x: ", targetX);
            telemetry.addData("target y: ", targetY);
            telemetry.addData("left distance: ", leftDistance);
            telemetry.addData("right distance: ", rightDistance);
            telemetry.addData("Lower Distance: ", lowerDistance);

            // movement

            drive = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            turn = gamepad1.right_stick_x;

            FrontRight = Range.clip(drive - turn - strafe, -1.0, 1.0);
            FrontLeft = Range.clip(drive + turn + strafe, -1.0, 1.0);
            BackRight = Range.clip(drive - turn + strafe, -1.0, 1.0);
            BackLeft = Range.clip(drive + turn - strafe, -1.0, 1.0);

            // driving

            motorFrontRight.setPower(FrontRight);
            motorFrontLeft.setPower(FrontLeft);
            motorBackRight.setPower(BackRight);
            motorBackLeft.setPower(BackLeft);

            if (gamepad2.dpad_left){
                targetX = targetX - 0.1;
            } else if (gamepad2.dpad_right){
                targetX = targetX + 0.1;
            }

            if (gamepad2.dpad_down){
                targetY = targetY - 0.1;
            } else if (gamepad2.dpad_up){
                targetY = targetY + 0.1;
            }

            // collecting

            if (gamepad1.right_bumper) {
                if(bumperRelease) {
                    if (collecting) {
                        motorCollector.setTargetPosition(motorCollector.getCurrentPosition() - (motorCollector.getCurrentPosition() % 210) + 210);
                        motorCollector.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        motorCollector.setPower(1);
                        collecting = false;
                    } else {
                        motorCollector.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        motorCollector.setPower(1);
                        collecting = true;
                    }
                }
                bumperRelease = false;
            }

            // depositing

            else if (gamepad1.left_trigger > 0) {
                if(bumperRelease) {
                    if (depositing) {
                        motorCollector.setPower(0);
                        depositing = false;
                    } else {
                        motorCollector.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        motorCollector.setPower(-1);
                        depositing = true;
                    }
                }
                bumperRelease = false;
            }

            else{
                bumperRelease = true;
            }

            // Carousel spinner

            spinPower = 0;
            if (gamepad2.left_bumper) {
                spinPower -= 0.45;
            }
            if (gamepad2.right_bumper) {
                spinPower += 0.45;
            }
            motorSpinner.setPower(-spinPower);

            // arm variables
            armDist = motorCollectorArm.getCurrentPosition();
            armPower = -gamepad2.left_stick_y;
            telemetry.addData("arm dist: ", armDist);

            if(gamepad2.back) {
                if(assistRelease) {
                    assistMode = !assistMode;
                }
                assistRelease = false;
            } else {
                assistRelease = true;
            }

            if(gamepad2.start) {
                if(dropRelease) {
                    autoDrop = !autoDrop;
                }
                dropRelease = false;
            } else {
                dropRelease = true;
            }

//            if(gamepad2.dpad_up){
//                servoArm.setPower(1);
//            } else if(gamepad2.dpad_down){
//                servoArm.setPower(-1);
//            } else {
//                servoArm.setPower(0);
//            }

            // assist mode specific utility

            if(assistMode) {

                bottomDistance = bottomRangeSensor.rawUltrasonic();
                telemetry.addData("bottomDistance: ", bottomDistance);

                if (bottomDistance - bottomSensorDistError < bottomSensorDistFromGround && bottomSensorDistFromGround < bottomDistance + bottomSensorDistError) {
                    telemetry.addData("On bump? ", "No");
                } else {
                    telemetry.addData("On bump? ", "Yes");
                    telemetry.addData("Auto Drop? ", autoDrop);
                    if (FrontLeft > 0 && FrontRight > 0 && BackLeft > 0 && BackRight > 0 && bottomDistance < 20 && autoDrop) {
                        armDown = 5;
                    }
                }

                if(moveTime > angleTime.milliseconds()){
                    servoArm.setPower(anglePower);
                } else {
                    servoArm.setPower(0);
                    if(dpadPress == false) {
                        if (gamepad2.dpad_left) {
                            targetX = targetX - 5;
                            getTurns(targetX, targetY);
                            dpadPress = true;
                        } else if (gamepad2.dpad_right) {
                            targetX = targetX + 5;
                            getTurns(targetX, targetY);
                            dpadPress = true;
                        } else if (gamepad2.dpad_down) {
                            targetY = targetY - 5;
                            getTurns(targetX, targetY);
                            dpadPress = true;
                        } else if (gamepad2.dpad_up) {
                            targetY = targetY + 5;
                            getTurns(targetX, targetY);
                            dpadPress = true;
                        }
                    } else {
                        if(gamepad2.dpad_left == false && gamepad2.dpad_right == false && gamepad2.dpad_up == false && gamepad2.dpad_down == false){
                            dpadPress = false;
                        }
                    }
                }

                // move arm

//                if ((armDist < armMin && armPower >= 0) || (armMin <= armDist && armDist <= armMax) || (armDist > armMax && armPower <= 0)) {
//                    motorCollectorArm.setPower(armPower);
//                } else {
//                    motorCollectorArm.setPower(0);
//                }

                // extend linear lift

                linearLiftDist = motorLinearLift.getCurrentPosition();
                linearLift = 0.5 * -gamepad2.right_stick_y;
                telemetry.addData("linear lift dist: ", linearLiftDist);
                telemetry.addData("linear lift: ", linearLift);

                if (motorCollectorArm.isBusy() && motorCollectorArm.getPower() != 0) {

                } else {
                    motorCollectorArm.setPower(0);
                    if (armDown == 3) {
                        armDown = 1;
                        motorCollector.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        motorCollector.setPower(1);
                        collecting = true;
                    } else if (armDown == 2) {
                        armDown = 0;
                        transferOpen = 5;
                    }
                    if (armDist < armMax && armPower > 0) {
                        armDown = 5;
                    } else if (armDist > armMin && armPower < 0) {
                        armDown = 4;
                    }
                }

                if (motorLinearLift.isBusy() && motorLinearLift.getPower() != 0 && liftOut > 1) {
                    telemetry.addData("Running ", linearLiftDist);
                } else {
                    if (liftOut == 3) {
                        liftOut = 1;
                        motorLinearLift.setPower(0.3);
                        droppingFreight = 5;
                    } else if (liftOut == 2) {
                        liftOut = 0;
                    }
                    if (linearLiftDist - 10 < maxDist && linearLift > 0) {
                        liftOut = 5;
                    } else if (linearLiftDist + 10 > minDist && linearLift < 0) {
                        liftOut = 4;
                        telemetry.addData("linear lift dist: ", linearLiftDist);
                    }
                }

                // deposit freight

                if (gamepad2.a) {
                    droppingFreight = 5;
                } else if (gamepad2.y) {
                    droppingFreight = 4;
                }

                if (gamepad2.x) {
                    transferOpen = 5;
                } else if (gamepad2.b) {
                    transferOpen = 4;
                }

                if(FrontLeft < 0 && FrontRight <0 && BackLeft < 0 && BackRight < 0 && collecting == false && armDown == 1){
                    armDown = 4;
                }

                if(runtime.milliseconds() >= pause) {
                    if (armDown == 5 && (transferOpen == 0 || transferOpen > 3)) {
                        motorCollectorArm.setTargetPosition(armMax);
                        motorCollectorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        motorCollectorArm.setPower(1);
                        armDown = 3;
                    } else if (armDown == 4 && (transferOpen == 0 || transferOpen > 3)) {
                        motorCollectorArm.setTargetPosition(armMin);
                        motorCollectorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        motorCollectorArm.setPower(-1);
                        armDown = 2;
                    }

                    //                if ((linearLiftDist < minDist && linearLift >= 0) || (minDist <= linearLiftDist && linearLiftDist <= maxDist) || (linearLiftDist > maxDist && linearLift <= 0)) {
                    //                    motorLinearLift.setPower(linearLift);
                    //                } else {
                    //                    motorLinearLift.setPower(0);
                    //                }

                    //                 while (motorLinearLift.getCurrentPosition() != 0 && opModeIsActive() && !motorLinearLift.isBusy()) {
                    //                     motorLinearLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    //                     linearLiftDist = motorLinearLift.getCurrentPosition();
                    //                     telemetry.addData("linear lift dist: ", linearLiftDist);
                    //                 }

                    if (liftOut == 5 && (droppingFreight == 0 || droppingFreight > 3)) {
                        motorLinearLift.setTargetPosition((int)maxDist);
                        motorLinearLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        motorLinearLift.setPower(1);
                        liftOut = 3;
                    } else if (liftOut == 4 && (droppingFreight == 0 || droppingFreight > 3)) {
                        motorLinearLift.setTargetPosition(minDist);
                        motorLinearLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        motorLinearLift.setPower(0.4);
                        liftOut = 2;
                    }

                    if ((droppingFreight == 5 || droppingFreight == 3) && (linearLiftDist > 800)) {
                        telemetry.addData("Dropping ", linearLiftDepositorHeight);
                        droppingFreight = 3;
                        if (linearLiftDepositorHeight > 0.25) {
                            telemetry.addData("Moving ", linearLiftDepositorHeight);
                            linearLiftDepositorHeight -= 0.08;
                            servoLinearLift.setPosition(linearLiftDepositorHeight);
                        } else {
                            droppingFreight = 1;
                            pause = runtime.milliseconds() + 1500;
                            droppingFreight = 4;
                        }
                    }

                    if ((droppingFreight == 4 || droppingFreight == 2) && (liftOut == 1 || liftOut > 3)) {
                        droppingFreight = 2;
                        if (linearLiftDepositorHeight < 0.63) {
                            linearLiftDepositorHeight += 0.05;
                            servoLinearLift.setPosition(linearLiftDepositorHeight);
                        } else {
                            droppingFreight = 0;
                            liftOut = 4;
                        }
                    }

                    if ((transferOpen == 5 || transferOpen == 3) && (armDown == 0 || armDown > 3)) {
                        transferOpen = 3;
                        if (transferServoPosition > 0.3) {
                            transferServoPosition -= 0.05;
                            servoTransfer.setPosition(transferServoPosition);
                        } else {
                            transferOpen = 1;
                            motorCollector.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                            motorCollector.setPower(1);
                            collecting = true;
                            pause = runtime.milliseconds() + 500;
                            transferOpen = 4;
                        }
                    }

                    if ((transferOpen == 4 || transferOpen == 2) && (armDown == 0 || armDown > 3)) {
                        transferOpen = 2;
                        if (transferServoPosition < 0.63) {
                            transferServoPosition += 0.03;
                            servoTransfer.setPosition(transferServoPosition);
                        } else {
                            transferOpen = 0;
                            motorCollector.setPower(0);
                            collecting = false;
                        }
                    }
                }
            }

            // manual utility

            else {
                // move arm
                motorCollectorArm.setPower(armPower);

                // extend linear lift
                linearLift = 0.5 * -gamepad2.right_stick_y;
                motorLinearLift.setPower(linearLift);

                // deposit freight

                if (gamepad2.a) {
                    linearLiftDepositorHeight -= 0.01;
                } else if (gamepad2.y) {
                    linearLiftDepositorHeight += 0.01;
                }

                telemetry.addData("linear lift depositor height: ", linearLiftDepositorHeight);

                servoLinearLift.setPosition(linearLiftDepositorHeight);

            }

            telemetry.addData("Arm Status: ", armDown);
            telemetry.addData("Lift Status: ", liftOut);
            telemetry.addData("Transfer Status: ", transferOpen);
            telemetry.addData("Depositor Status: ", droppingFreight);
            telemetry.update();

        }

    }

    public void getTurns (double x, double y){
        int L = 30;
        int H = 36;
        double lengthPerTurn = 5*Math.PI;
        double lengthPerSecond = lengthPerTurn / 3.2;
        double requiredAngle = Math.asin(y / Math.sqrt(x*x + y*y));
        double extensionTurns = (Math.sqrt(x*x + y*y) - L);
        double angleTurns = Math.sqrt(L*L + H*H - 2*L*H*Math.cos(Math.PI / 3 - requiredAngle));
        double moveSeconds = (angleTurns - currLength)/lengthPerSecond;

        if (moveSeconds > 0){
            anglePower = 1;
        } else if (moveSeconds < 0){
            anglePower = -1;
        } else {
            anglePower = 0;
        }
        moveTime = angleTime.milliseconds() + Math.abs(moveSeconds*1000);
        currLength = angleTurns;
        maxDist = Range.clip(36.62 * extensionTurns, 0, 1500);
        if(liftOut == 1){
            liftOut = 5;
        }
    }
}