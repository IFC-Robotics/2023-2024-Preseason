package org.firstinspires.ftc.teamcode.charlieTesting;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.charlieTesting.robot;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;

@TeleOp(name="Advanced TeleOp")
public class AdvancedTeleOpCopy extends LinearOpMode {

    robot robot = new robot();

    int leftDistance;
    int rightDistance;
    int lowerDistance;
    int bottomDistance;

    int bottomSensorDistFromGround = 5;
    double bottomSensorDistError = 0.5;

    double drive;
    double strafe;
    double turn;

    double FrontRight;
    double FrontLeft;
    double BackRight;
    double BackLeft;

    boolean collecting = false;
    boolean depositing = false;
    int armDown = 0;
    int transferOpen = 0;
    int liftOut = 0;
    int droppingFreight = 0;
    double linearLiftDepositorHeight = 0.63;
    double transferServoPosition = 0.63;
    boolean bumperRelease = true;
    boolean assistMode = true;
    boolean assistRelease = true;

    double linearLiftDist;
    double linearLift;
    double armDist;
    double armPower;

    double pause = 0;
    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() {

        robot.init(hardwareMap);
        robot.setModeForOtherMotors(hardwareMap);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            leftDistance = robot.leftRangeSensor.rawUltrasonic();
            rightDistance = robot.rightRangeSensor.rawUltrasonic();
            lowerDistance = Math.min(leftDistance, rightDistance);

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

            robot.motorFrontRight.setPower(FrontRight);
            robot.motorFrontLeft.setPower(FrontLeft);
            robot.motorBackRight.setPower(BackRight);
            robot.motorBackLeft.setPower(BackLeft);

            // collecting

            if (gamepad1.right_bumper) {
                if(bumperRelease) {
                    if (collecting) {
                        robot.motorCollector.setPower(0);
                        collecting = false;
                    } else {
                        robot.motorCollector.setPower(1);
                        collecting = true;
                    }
                }
                bumperRelease = false;
            }

            // depositing

            else if (gamepad1.left_trigger > 0) {
                if(bumperRelease) {
                    if (depositing) {
                        robot.motorCollector.setPower(0);
                        depositing = false;
                    } else {
                        robot.motorCollector.setPower(-1);
                        depositing = true;
                    }
                }
                bumperRelease = false;
            }

            else{
                bumperRelease = true;
            }

            // Carousel spinner

            if (gamepad2.left_bumper) {
                robot.motorSpinner.setPower(-0.45);
            } else if (gamepad2.right_bumper) {
                robot.motorSpinner.setPower(0.45);
            } else {
                robot.motorSpinner.setPower(0);
            }

            // arm variables
            armDist = robot.motorCollectorArm.getCurrentPosition();
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

            if(gamepad2.dpad_up){
                robot.crServoArm.setPower(1);
            } else if(gamepad2.dpad_down){
                robot.crServoArm.setPower(-1);
            } else {
                robot.crServoArm.setPower(0);
            }

            // assist mode specific utility

            if(assistMode) {

                // move arm

                // if ((armDist < armMin && armPower >= 0) || (armMin <= armDist && armDist <= armMax) || (armDist > armMax && armPower <= 0)) {
                //     robot.motorCollectorArm.setPower(armPower);
                // } else {
                //     robot.motorCollectorArm.setPower(0);
                // }

                // extend linear lift

                linearLiftDist = robot.motorLinearLift.getCurrentPosition();
                linearLift = 0.5 * -gamepad2.right_stick_y;
                telemetry.addData("linear lift dist: ", linearLiftDist);
                telemetry.addData("linear lift: ", linearLift);

                if (robot.motorCollectorArm.isBusy() && robot.motorCollectorArm.getPower() != 0) {

                } else {
                    robot.motorCollectorArm.setPower(0);
                    if (armDown == 3) {
                        armDown = 1;
                        robot.motorCollector.setPower(1);
                        collecting = true;
                    } else if (armDown == 2) {
                        armDown = 0;
                        transferOpen = 5;
                    }
                    if (armDist < robot.ARM_MAX_DIST && armPower > 0) {
                        armDown = 5;
                    } else if (armDist > robot.ARM_MIN_DIST && armPower < 0) {
                        armDown = 4;
                    }
                }

                if (robot.motorLinearLift.isBusy() && robot.motorLinearLift.getPower() != 0 && liftOut > 1) {
                    telemetry.addData("Running ", linearLiftDist);
                } else {
                    if (liftOut == 3) {
                        liftOut = 1;
                        robot.motorLinearLift.setPower(0.3);
                        droppingFreight = 5;
                    } else if (liftOut == 2) {
                        liftOut = 0;
                    }
                    if (linearLiftDist - 10 < robot.LIFT_MAX_DIST && linearLift > 0) {
                        liftOut = 5;
                    } else if (linearLiftDist + 10 > robot.LIFT_MAX_DIST && linearLift < 0) {
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
                        robot.motorCollectorArm.setTargetPosition(robot.ARM_MAX_DIST);
                        robot.motorCollectorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        robot.motorCollectorArm.setPower(1);
                        armDown = 3;
                    } else if (armDown == 4 && (transferOpen == 0 || transferOpen > 3)) {
                        robot.motorCollectorArm.setTargetPosition(robot.ARM_MIN_DIST);
                        robot.motorCollectorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        robot.motorCollectorArm.setPower(-1);
                        armDown = 2;
                    }

                    if (liftOut == 5 && (droppingFreight == 0 || droppingFreight > 3)) {
                        robot.motorLinearLift.setTargetPosition(robot.LIFT_MAX_DIST);
                        robot.motorLinearLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        robot.motorLinearLift.setPower(1);
                        liftOut = 3;
                    } else if (liftOut == 4 && (droppingFreight == 0 || droppingFreight > 3)) {
                        robot.motorLinearLift.setTargetPosition(robot.LIFT_MIN_DIST);
                        robot.motorLinearLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        robot.motorLinearLift.setPower(0.4);
                        liftOut = 2;
                    }

                    if ((droppingFreight == 5 || droppingFreight == 3) && linearLiftDist > 800) {
                        telemetry.addData("Dropping ", linearLiftDepositorHeight);
                        droppingFreight = 3;
                        if (linearLiftDepositorHeight > 0.25) {
                            telemetry.addData("Moving ", linearLiftDepositorHeight);
                            linearLiftDepositorHeight -= 0.05;
                            robot.servoLinearLift.setPosition(linearLiftDepositorHeight);
                        } else {
                            droppingFreight = 1;
                            pause = runtime.milliseconds() + 2000;
                            droppingFreight = 4;
                        }
                    }

                    if ((droppingFreight == 4 || droppingFreight == 2) && (liftOut == 1 || liftOut > 3)) {
                        droppingFreight = 2;
                        if (linearLiftDepositorHeight < 0.63) {
                            linearLiftDepositorHeight += 0.03;
                            robot.servoLinearLift.setPosition(linearLiftDepositorHeight);
                        } else {
                            droppingFreight = 0;
                            liftOut = 4;
                        }
                    }

                    if ((transferOpen == 5 || transferOpen == 3) && (armDown == 0 || armDown > 3)) {
                        transferOpen = 3;
                        if (transferServoPosition > 0.3) {
                            transferServoPosition -= 0.05;
                            robot.servoTransfer.setPosition(transferServoPosition);
                        } else {
                            transferOpen = 1;
                            robot.motorCollector.setPower(1);
                            collecting = true;
                            pause = runtime.milliseconds() + 500;
                            transferOpen = 4;
                        }
                    }

                    if ((transferOpen == 4 || transferOpen == 2) && (armDown == 0 || armDown > 3)) {
                        transferOpen = 2;
                        if (transferServoPosition < 0.63) {
                            transferServoPosition += 0.03;
                            robot.servoTransfer.setPosition(transferServoPosition);
                        } else {
                            transferOpen = 0;
                            robot.motorCollector.setPower(0);
                            collecting = false;
                        }
                    }
                }
            }

            // manual utility

            else {

                // move arm
                robot.motorCollectorArm.setPower(armPower);

                // extend linear lift
                linearLift = 0.5 * -gamepad2.right_stick_y;
                robot.motorLinearLift.setPower(linearLift);

                // deposit freight

                if (gamepad2.a) {
                    linearLiftDepositorHeight -= 0.01;
                } else if (gamepad2.y) {
                    linearLiftDepositorHeight += 0.01;
                }

                telemetry.addData("linear lift depositor height: ", linearLiftDepositorHeight);

                robot.servoLinearLift.setPosition(linearLiftDepositorHeight);

            }

            telemetry.addData("Arm Status: ", armDown);
            telemetry.addData("Lift Status: ", liftOut);
            telemetry.addData("Transfer Status: ", transferOpen);
            telemetry.addData("Depositor Status: ", droppingFreight);
            telemetry.update();

        }

    }

}
