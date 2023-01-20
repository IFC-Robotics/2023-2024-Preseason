package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@Autonomous(name="FSM Left 1+0", group="competition")
public class LeftHighAutonFSM extends LinearOpMode {

    double DRIVE_SPEED = 0.7;
    double STRAFE_SPEED = 0.7;
    double TURN_SPEED = 0.7;

    public enum RobotState {
        SECURE_PRELOAD,            // extend servo
        MOVE_FORWARD_TO_JUNCTION,  // move forward, raise lift
        MOVE_BACKWARD_TO_JUNCTION, // move backward
        TURN_TO_JUNCTION,          // turn
        ALIGN_WITH_JUNCTION,       // if lift is raised, move forward
        DROP_CONE,                 // retract servo
        BACK_AWAY_FROM_JUNCTION,   // move backward
        TURN_TO_PARK,              // turn to park, lower lift
        DRIVE_TO_CORRECT_ZONE,     // drive to correct zone
        DONE
    };

    RobotState state = RobotState.SECURE_PRELOAD;
    ElapsedTime timer = new ElapsedTime();

    boolean continueOpMode = true;

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        Robot.init(this);
        Robot.tag = Robot.camera.getTag();

        waitForStart();

        telemetry.addLine("Executing opMode...");
        telemetry.update();

        timer.reset();

        double MOVE_FORWARD_DIST = 64;
        double MOVE_BACKWARD_DIST = -8;
        double TURN_TO_JUNCTION_DIST = 45;
        double ALIGN_WITH_JUNCTION_DIST = 9;
        double TURN_TO_PARK_DIST = 43;
        double DRIVE_TO_PARK_DIST = 23;

        while (continueOpMode && opModeIsActive()) {

            switch (state) {

                case SECURE_PRELOAD:
                    Robot.servoHook.runToPosition("extend"); // extend hook (to secure cone)
                    timer.reset();
                    state = RobotState.MOVE_FORWARD_TO_JUNCTION;
                    break;

                case MOVE_FORWARD_TO_JUNCTION:
                    if (timer.milliseconds() >= Robot.servoHook.TIME) {
                        Robot.drivetrain.drive(MOVE_FORWARD_DIST, DRIVE_SPEED); // move forward (to push signal cone our of the way)
                        Robot.verticalLift.runToPosition("high"); // raise lift
                        state = RobotState.MOVE_BACKWARD_TO_JUNCTION;
                    }
                    break;

                case MOVE_BACKWARD_TO_JUNCTION:
                    boolean drivetrainIsAtPosition = isAtPosition(Robot.drivetrain.motorBackRight, MOVE_FORWARD_DIST, Robot.drivetrain.COUNTS_PER_INCH);
                    boolean liftIsAtPosition = isAtPosition(Robot.verticalLift.motor, Robot.verticalLift.verticalLiftPosition5, Robot.verticalLift.COUNTS_PER_INCH);
                    if (drivetrainIsAtPosition && liftIsAtPosition) {
                        Robot.drivetrain.drive(MOVE_BACKWARD_DIST, DRIVE_SPEED); // move backward (to be next to high junction)
                        state = RobotState.TURN_TO_JUNCTION;
                    }
                    break;

                case TURN_TO_JUNCTION:
                    if (isAtPosition(Robot.drivetrain.motorBackRight, MOVE_BACKWARD_DIST, Robot.drivetrain.COUNTS_PER_INCH)) {
                        Robot.drivetrain.turn(TURN_TO_JUNCTION_DIST, TURN_SPEED); // turn (to face junction)
                        state = RobotState.ALIGN_WITH_JUNCTION;
                    }
                    break;

                case ALIGN_WITH_JUNCTION:
                    if (isAtPosition(Robot.drivetrain.motorBackRight, TURN_TO_JUNCTION_DIST, Robot.drivetrain.COUNTS_PER_INCH)) {
                        Robot.drivetrain.drive(ALIGN_WITH_JUNCTION_DIST, DRIVE_SPEED); // align with junction
                        state = RobotState.DROP_CONE;
                    }
                    break;

                case DROP_CONE:
                    if (isAtPosition(Robot.drivetrain.motorBackRight, ALIGN_WITH_JUNCTION_DIST, Robot.drivetrain.COUNTS_PER_INCH)) {
                        Robot.servoHook.runToPosition("retract"); // retract hook (to score cone)
                        timer.reset();
                        state = RobotState.BACK_AWAY_FROM_JUNCTION;
                    }
                    break;

                case BACK_AWAY_FROM_JUNCTION:
                    if (timer.milliseconds() >= Robot.servoHook.TIME) {
                        Robot.drivetrain.drive(-ALIGN_WITH_JUNCTION_DIST, DRIVE_SPEED); // move backward (to push signal cone our of the way)
                        Robot.verticalLift.runToPosition("transfer"); // lower lift
                        state = RobotState.TURN_TO_PARK;
                    }
                    break;

                case TURN_TO_PARK: // turn to park
                    if (isAtPosition(Robot.drivetrain.motorBackRight, ALIGN_WITH_JUNCTION_DIST, Robot.drivetrain.COUNTS_PER_INCH)) {
                        Robot.drivetrain.turn(TURN_TO_PARK_DIST, DRIVE_SPEED); // align with junction
                        state = RobotState.DRIVE_TO_CORRECT_ZONE;
                    }
                    break;

                case DRIVE_TO_CORRECT_ZONE:  // drive to correct zone
                    if (isAtPosition(Robot.drivetrain.motorBackRight, TURN_TO_PARK_DIST, Robot.drivetrain.COUNTS_PER_INCH)) {
                        Robot.drivetrain.drive(DRIVE_TO_PARK_DIST, DRIVE_SPEED); // align with junction
                        state = RobotState.DONE;
                    }
                    break;

                case DONE:
                    continueOpMode = false;
                    break;

            }

        }

    }

    public boolean isAtPosition(DcMotor motor, double dist, double countsPerInch) {
        int currentPosition = Math.abs(motor.getCurrentPosition());
        int targetPosition = (int) (dist * countsPerInch);
        return currentPosition >= targetPosition;
    }

}