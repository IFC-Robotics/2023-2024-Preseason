package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.HashMap;

public class ServoClass {

    LinearOpMode opMode;
    Telemetry telemetry;

    public Servo servo;
    public double servoPosition;

    public String NAME;
    public double MIN_POSITION;
    public String MIN_POSITION_NAME;
    public double MAX_POSITION;
    public String MAX_POSITION_NAME;
    public double SPEED;
    public int TIME;

    // look into using servo.scaleRange()
    // add a range sensor to the claw/hook so that it automatically closes when it detects a cone

    public ServoClass() {}

    public void init(LinearOpMode opModeParam, String name, double minPosition, String minPositionName, double maxPosition, String maxPositionName, double speed, int time, boolean reverseDirection) {

        opMode = opModeParam;
        telemetry = opModeParam.telemetry;

        NAME = name;
        MIN_POSITION = minPosition;
        MIN_POSITION_NAME = minPositionName;
        MAX_POSITION = maxPosition;
        MAX_POSITION_NAME = maxPositionName;
        SPEED = speed;
        TIME = time;

        servo = opMode.hardwareMap.get(Servo.class, NAME);
        servoPosition = servo.getPosition();

        if (reverseDirection) servo.setDirection(Servo.Direction.REVERSE);

    }

    // autonomous

    public void runToPosition(String position, boolean isSynchronous) {
        teleOpAssistMode((position == MIN_POSITION_NAME), (position == MAX_POSITION_NAME));
        if (isSynchronous) opMode.sleep(TIME);
    }
    
    // teleOp

    public void teleOpAssistMode(boolean minCondition, boolean maxCondition) {
        if (minCondition || maxCondition) {
            double servoOldPosition = servoPosition;
            servoPosition = minCondition ? MIN_POSITION : MAX_POSITION;
            telemetry.addLine(String.format("running %1$s from position %2$s to %3$s", NAME, servoOldPosition, servoPosition));
            servo.setPosition(servoPosition);
        }
    }

    public void teleOpManualMode(double joystick) {
        if (Math.abs(joystick) > 0.02) {
            double servoOldPosition = servoPosition;
            if (joystick < 0 && servoPosition > MIN_POSITION) servoPosition -= SPEED;
            if (joystick > 0 && servoPosition < MAX_POSITION) servoPosition += SPEED;
            telemetry.addLine(String.format("increment %1$s from position %2$s by %3$s", NAME, servoOldPosition, (servoPosition - servoOldPosition)));
            servo.setPosition(servoPosition);
        }
    }

}