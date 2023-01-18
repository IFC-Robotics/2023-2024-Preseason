package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import static java.lang.Thread.sleep;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ServoClass {

    Telemetry telemetry;

    public Servo servo;
    public double servoPosition;

    public String NAME;
    public double MIN_POSITION;
    public double MAX_POSITION;
    public double SPEED;
    public double TIME;

    public ServoClass() {}

    public void init(HardwareMap hardwareMap, Telemetry telemetryParameter, String name, double minPosition, double maxPosition, double speed, double time, boolean reverseDirection) {

        NAME = name;
        MIN_POSITION = minPosition;
        MAX_POSITION = maxPosition;
        SPEED = speed;
        TIME = time;

        servo = hardwareMap.get(Servo.class, NAME);
        servoPosition = servo.getPosition();

        if (reverseDirection) servo.setDirection(Servo.Direction.REVERSE);

        telemetry = telemetryParameter;

    }

    public void runToPosition(String position) {
        telemetry.addLine(String.format("running hook from position %1$s to position %2$s", servoPosition, position));
        if (position == "extend")  servo.setPosition(MIN_POSITION);
        if (position == "retract") servo.setPosition(MAX_POSITION);
    }

    public void teleOpAssistMode(boolean button1, boolean button2) {
        if (button1 || button2) {
            if (button1) servoPosition = MIN_POSITION;
            if (button2) servoPosition = MAX_POSITION;
            servo.setPosition(servoPosition);
        }
    }

    public void teleOpManualMode(double joystick) {
        if (Math.abs(joystick) > 0.05) {
            if (joystick < 0 && servoPosition > MIN_POSITION) servoPosition -= SPEED;
            if (joystick > 0 && servoPosition < MAX_POSITION) servoPosition += SPEED;
            servo.setPosition(servoPosition);
            telemetry.addLine(String.format("running %1$s with speed %2$s at (current) position %3$s", NAME, SPEED, servoPosition));
        }
    }

//    Robot.servoHook.teleOpManualMode(gamepad2.dpad_left, gamepad2.dpad_right);

//    public void teleOpManualMode(boolean button1, boolean button2) {
//        if (button1 || button2) {
//            if (button1 && servoPosition > MIN_POSITION) servoPosition -= SPEED;
//            if (button2 && servoPosition < MAX_POSITION) servoPosition += SPEED;
//            servo.setPosition(servoPosition);
//        }
//    }

}