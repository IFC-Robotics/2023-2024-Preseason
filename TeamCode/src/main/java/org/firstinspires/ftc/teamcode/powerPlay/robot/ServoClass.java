package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Servo Class")
@Disabled
public class ServoClass extends LinearOpMode {

    public Servo servo;
    public double servoPosition = 0;

    public String NAME;
    public double MIN_POSITION;
    public double MAX_POSITION;
    public double SPEED;
    public double TIME;

    public ServoClass() {}

    @Override
    public void runOpMode() {}

    public void init(HardwareMap hardwareMap, String name, double minPosition, double maxPosition, double speed, double time, boolean reverseDirection) {

        NAME = name;
        MIN_POSITION = minPosition;
        MAX_POSITION = maxPosition;
        SPEED = speed;
        TIME = time;

        servo = hardwareMap.get(Servo.class, NAME);
        servoPosition = servo.getPosition();

        if (reverseDirection) servo.setDirection(Servo.Direction.REVERSE);

    }

    public void runToPosition(String position) {

        if (NAME == "servo_claw") {
            if (position == "open") servo.setPosition(MIN_POSITION);
            if (position == "close") servo.setPosition(MAX_POSITION);
        }

        if (NAME == "servo_rotate_claw") {
            if (position == "up")   servo.setPosition(MIN_POSITION);
            if (position == "down") servo.setPosition(MAX_POSITION);
        }

        if (NAME == "servo_hook") {
            if (position == "extend")  servo.setPosition(MIN_POSITION);
            if (position == "retract") servo.setPosition(MAX_POSITION);
        }

        if (NAME == "servo_rotate_hook") {
            if (position == "transfer") servo.setPosition(MIN_POSITION);
            if (position == "score")    servo.setPosition(MAX_POSITION);
        }

    }

    public void teleOpAssistMode(boolean button1, boolean button2) {
        telemetry.addData(NAME + " teleOpAssistMode", "");
        if (button1) servoPosition = MIN_POSITION;
        if (button2) servoPosition = MAX_POSITION;
        servo.setPosition(servoPosition);
    }

    public void teleOpManualMode(boolean button1, boolean button2) {
        telemetry.addData(NAME + " teleOpManualMode", "");
        if (button1 && servoPosition > MIN_POSITION) servoPosition -= SPEED;
        if (button2 && servoPosition < MAX_POSITION) servoPosition += SPEED;
        servo.setPosition(servoPosition);
    }

}