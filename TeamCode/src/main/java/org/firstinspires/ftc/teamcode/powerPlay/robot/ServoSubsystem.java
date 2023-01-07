package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoSubsystem extends LinearOpMode {

    public static Servo servo;
    public static double servoPosition = 0;

    public static String NAME;
    public static double MIN_POSITION;
    public static double MAX_POSITION;
    public static double SPEED;

    // initialize

    public ServoSubsystem() {}

    @Override
    public void runOpMode() {}

    public void init(HardwareMap hardwareMap, String name, double minPosition, double maxPosition, double speed, boolean reverseDirection) {

        NAME = name;
        MIN_POSITION = minPosition;
        MAX_POSITION = maxPosition;
        SPEED = speed;

        servo = hardwareMap.get(Servo.class, NAME);
        servoPosition = servo.getPosition();

        if (reverseDirection) servo.setDirection(Servo.Direction.REVERSE);

    }

    // teleOp

    public void executeTeleOp(boolean assistMode, boolean button1, boolean button2) {

        if (assistMode) {
            if (button1) servoPosition = MIN_POSITION;
            if (button2) servoPosition = MAX_POSITION;
        } else {
            if (button1 && servoPosition > MIN_POSITION) servoPosition -= SPEED;
            if (button2 && servoPosition < MAX_POSITION) servoPosition += SPEED;
        }

        servo.setPosition(servoPosition);

    }

    // autonomous

    public void moveClaw(String direction) {
        if (NAME == "servo_claw") {
            if (direction == "open")  servo.setPosition(MIN_POSITION);
            if (direction == "close") servo.setPosition(MAX_POSITION);
        }
    }

    public void rotateClaw(String direction) {
        if (NAME == "servo_rotate_claw") {
            if (direction == "up")   servo.setPosition(MIN_POSITION);
            if (direction == "down") servo.setPosition(MAX_POSITION);
        }
    }

    public void moveHook(String direction) {
        if (NAME == "servo_hook") {
            if (direction == "extend")  servo.setPosition(MIN_POSITION);
            if (direction == "retract") servo.setPosition(MAX_POSITION);
        }
    }

    public void rotateHook(String direction) {
        if (NAME == "motor_rotate_hook") {
            if (direction == "transfer") servo.setPosition(MIN_POSITION);
            if (direction == "deposit")  servo.setPosition(MAX_POSITION);
        }
    }

}