//package org.firstinspires.ftc.teamcode.powerPlay.teleOp;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;
//import org.firstinspires.ftc.teamcode.powerPlay.robot.LiftClass;
//import org.firstinspires.ftc.teamcode.powerPlay.robot.ServoClass;
//
//@TeleOp(name="FSM teleOp", group="competition")
//@Disabled
//public class FSMteleOp extends LinearOpMode {
//
//    public enum RobotState {
//        START,
//        MOVE_HORIZONTAL_TO_COLLECT,
//        LOWER_CLAW,
//        MOVE_HORIZONTAL_INTO_CONE,
//        COLLECT_CONE,
//        RAISE_CLAW,
//        MOVE_HORIZONTAL_TO_TRANSFER,
//        TRANSFER_CONE,
//        RELEASE_CONE,
//        MOVE_VERTICAL_TO_SCORE,
//        ROTATE_HOOK_TO_SCORE,
//        SCORE_CONE,
//        ROTATE_HOOK_TO_TRANSFER,
//        MOVE_VERTICAL_TO_TRANSFER
//    }
//
//    RobotState state = RobotState.START;
//    ElapsedTime timer = new ElapsedTime();
//
//    @Override
//    public void runOpMode() {
//
//        telemetry.addLine("Initializing opMode...");
//        telemetry.update();
//
//        Robot.init(this);
//        Robot.mode = "assist";
//
//        waitForStart();
//
//        timer.reset();
//
//        telemetry.addLine("Executing opMode...");
//        telemetry.update();
//
//        while (opModeIsActive()) {
//
//            // Switching modes
//
//            telemetry.addLine(String.format("Robot.mode: %s", Robot.mode));
//
//            if (gamepad1.back) {
//                Robot.resetScoring();
//                Robot.mode = "FSM";
//            }
//
//            if (gamepad1.guide) Robot.mode = "assist";
//            if (gamepad1.start) Robot.mode = "manual";
//
//            // Finite State Machine mode
//
//            if (Robot.mode == "FSM") {
//
//                telemetry.addData("current FSM state", state);
//
//                switch (state) {
//
//                    case START:
//                        if (gamepad1.x) { // start FSM
//                            state = RobotState.MOVE_HORIZONTAL_TO_COLLECT;
//                        }
//                        break;
//
//                    // move horizontal lift to wait to collect
//                    // rotate claw down
//                    // move horizontal lift into cone
//                    // close claw
//                    // rotate claw up
//                    // move horizontal lift to transfer
//                    // open claw
//                    // extend hook
//                    // raise vertical lift to high junction
//                    // rotate hook to score
//                    // release hook
//                    // rotate hook to transfer
//                    // lower vertical lift to transfer
//
//                    case MOVE_HORIZONTAL_TO_COLLECT:  liftToLift  (Robot.verticalLift,    Robot.horizontalLift,  "wait to collect", RobotState.LOWER_CLAW          ); break;
//                    case LOWER_CLAW:                  liftToServo (Robot.horizontalLift,  Robot.servoRotateClaw, "collect",  RobotState.MOVE_HORIZONTAL_INTO_CONE  ); break;
//                    case MOVE_HORIZONTAL_INTO_CONE:   servoToLift (Robot.servoRotateClaw, Robot.horizontalLift,  "collect",  RobotState.COLLECT_CONE               ); break;
//                    case COLLECT_CONE:                liftToServo (Robot.horizontalLift,  Robot.servoClaw,       "hold",     RobotState.RAISE_CLAW                 ); break;
//                    case RAISE_CLAW:                  servoToServo(Robot.servoClaw,       Robot.servoRotateClaw, "transfer", RobotState.MOVE_HORIZONTAL_TO_TRANSFER); break;
//                    case MOVE_HORIZONTAL_TO_TRANSFER: servoToLift (Robot.servoRotateClaw, Robot.horizontalLift,  "transfer", RobotState.RELEASE_CONE               ); break;
//                    case RELEASE_CONE:                liftToServo (Robot.horizontalLift,  Robot.servoClaw,       "release",  RobotState.TRANSFER_CONE              ); break;
//                    case TRANSFER_CONE:               servoToServo(Robot.servoClaw,       Robot.servoHook,       "hold",     RobotState.MOVE_VERTICAL_TO_SCORE     ); break;
//                    case MOVE_VERTICAL_TO_SCORE:      servoToLift (Robot.servoHook,       Robot.verticalLift,    "high",     RobotState.ROTATE_HOOK_TO_SCORE       ); break;
//                    case ROTATE_HOOK_TO_SCORE:        liftToServo (Robot.verticalLift,    Robot.servoRotateHook, "score",    RobotState.SCORE_CONE                 ); break;
//                    case SCORE_CONE:                  servoToServo(Robot.servoRotateHook, Robot.servoHook,       "release",  RobotState.ROTATE_HOOK_TO_TRANSFER    ); break;
//                    case ROTATE_HOOK_TO_TRANSFER:     servoToServo(Robot.servoHook,       Robot.servoRotateHook, "transfer", RobotState.MOVE_VERTICAL_TO_TRANSFER  ); break;
//                    case MOVE_VERTICAL_TO_TRANSFER:   servoToLift (Robot.servoRotateHook, Robot.verticalLift,    "transfer", RobotState.MOVE_HORIZONTAL_TO_COLLECT ); break;
//
//                    default: state = RobotState.START;
//
//                }
//
//                // pause FSM
//
//                if (gamepad1.y && state != RobotState.START) {
//                    state = RobotState.START;
//                }
//
//            } else {
//
//                if (Robot.mode == "assist") { // assist mode
//
//                    Robot.servoClaw.teleOpAssistMode(gamepad1.dpad_left, gamepad1.dpad_right);
//                    Robot.servoRotateClaw.teleOpAssistMode(gamepad1.dpad_down, gamepad1.dpad_up);
//
//                    Robot.servoHook.teleOpAssistMode(gamepad1.y, gamepad1.a);
//                    Robot.servoRotateHook.teleOpAssistMode(gamepad1.x, gamepad1.b);
//
//                    Robot.closeHookUsingSensor();
//
//                } else if (Robot.mode == "manual") { // manual mode
//
//                    Robot.servoClaw.teleOpManualMode(gamepad1.dpad_left, gamepad1.dpad_right);
//                    Robot.servoRotateClaw.teleOpManualMode(gamepad1.dpad_down, gamepad1.dpad_up);
//
//                    Robot.servoHook.teleOpManualMode(gamepad1.y, gamepad1.a);
//                    Robot.servoRotateHook.teleOpManualMode(gamepad1.x, gamepad1.b);
//
//                }
//
//                // both assist and manual
//
//                Robot.horizontalLift.teleOp(-gamepad2.left_stick_y, gamepad2.left_bumper, gamepad2.dpad_down, gamepad2.dpad_left, gamepad2.dpad_right, gamepad2.dpad_up);
//                Robot.verticalLift.teleOp(-gamepad2.right_stick_y, gamepad2.right_bumper, gamepad2.a, gamepad2.x, gamepad2.b, gamepad2.y);
//
//            }
//
//            Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_bumper);
//
//            printRobotData();
//
//        }
//
//    }
//
//    public void printRobotData() {
//
//        telemetry.addLine("\nRobot data:\n");
//
//        Robot.servoClaw.printData();
//        Robot.servoHook.printData();
//        Robot.servoRotateClaw.printData();
//        Robot.servoRotateHook.printData();
//
//        Robot.horizontalLift.printData();
//        Robot.verticalLift.printData();
//
//        Robot.hookSensor.printImportantData();
//
//        telemetry.update();
//
//    }
//
//    public void liftToLift(LiftClass liftClass, LiftClass nextLiftClass, String position, RobotState nextState) {
//        if (liftIsntMoving(liftClass)) {
//            nextLiftClass.runToPosition(position);
//            timer.reset();
//            state = nextState;
//        }
//    }
//
//    public void liftToServo(LiftClass liftClass, ServoClass servoClass, String position, RobotState nextState) {
//        if (liftIsntMoving(liftClass)) {
//            servoClass.runToPosition(position);
//            timer.reset();
//            state = nextState;
//        }
//    }
//
//    public void servoToLift(ServoClass servoClass, LiftClass liftClass, String position, RobotState nextState) {
//        if (servoIsntMoving(servoClass)) {
//            liftClass.runToPosition(position);
//            timer.reset();
//            state = nextState;
//        }
//    }
//
//    public void servoToServo(ServoClass servoClass, ServoClass newServoClass, String position, RobotState nextState) {
//        if (servoIsntMoving(servoClass)) {
//            newServoClass.runToPosition(position);
//            timer.reset();
//            state = nextState;
//        }
//    }
//
//    public boolean liftIsntMoving(LiftClass liftClass) {
//        int currentPos = liftClass.motor.getCurrentPosition();
//        int targetPos = liftClass.motor.getTargetPosition();
//        return Math.abs(currentPos - targetPos) < 10;
//    }
//
//    public boolean servoIsntMoving(ServoClass servoClass) {
//        return timer.milliseconds() > servoClass.time;
//    }
//
//    /*
//
//        import java.util.function.Consumer;
//
//        interface Interface {
//            void run(String str);
//        }
//
//        interface Interface2 {
//            void run();
//        }
//
//        public class Main {
//
//            public static void main(String[] args) {
//
//                Interface method = (s) -> System.out.println(s + "!");
//                method.run("Hello");
//
//                Interface2 method2 = () -> System.out.println("Hi");
//                method2.run();
//
//            }
//
//        }
//
//    */
//
//}
//
///*
//
//Gamepad 1 - driving, servos, FSM, and modes
//
//gamepad1.left_stick_x:  strafing
//gamepad1.left_stick_y:  driving
//gamepad1.right_stick_x: turning
//
//gamepad1.a:             retract hook (to release cone)
//gamepad1.x:             rotate hook up
//gamepad1.b:             rotate hook down
//gamepad1.y:             extend hook (to hold cone)
//
//gamepad1.x (FSM mode):  start FSM
//gamepad1.y (FSM mode):  stop FSM
//
//gamepad1.dpad_down:     rotate claw down
//gamepad1.dpad_left:     open claw
//gamepad1.dpad_right:    close claw
//gamepad1.dpad_up:       rotate claw upe
//
//gamepad1.left_bumper:   toggle max drivetrain speed
//
//gamepad1.back:          switch to FSM mode
//gamepad1.guide:         switch to assist mode
//gamepad1.start:         switch to manual mode
//
//Gamepad 2 - lifts
//
//gamepad2.left_stick_y:  moving horizontal lift
//gamepad2.right_stick_y: moving vertical lift
//
//gamepad2.a:             move vertical lift to transfer
//gamepad2.x:             move vertical lift to low
//gamepad2.b:             move vertical lift to middle
//gamepad2.y:             move vertical lift to high
//
//gamepad2.dpad_down:     move horizontal lift to zero
//gamepad2.dpad_left:     move horizontal lift to wait to collect cone
//gamepad2.dpad_right:    move horizontal lift to collect cone
//
//gamepad2.left_bumper:   toggle encoder limits for horizontal lift
//gamepad2.right_bumper:  toggle encoder limits for vertical lift
//
//*/