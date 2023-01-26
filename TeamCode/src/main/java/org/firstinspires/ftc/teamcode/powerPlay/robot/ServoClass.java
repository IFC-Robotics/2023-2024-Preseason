package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ServoClass {

    LinearOpMode opMode;
    Telemetry telemetry;

    public Servo servo;
    public double servoPosition;

    public String NAME;
    public double MIN_POSITION;
    public double MAX_POSITION;
    public double SPEED;
    public double TIME;

    public ServoClass() {}

    public void init(LinearOpMode opModeParam, String name, double minPosition, double maxPosition, double speed, double time, boolean reverseDirection) {

        opMode = opModeParam;
        telemetry = opModeParam.telemetry;

        NAME = name;
        MIN_POSITION = minPosition;
        MAX_POSITION = maxPosition;
        SPEED = speed;
        TIME = time;

        servo = opMode.hardwareMap.get(Servo.class, NAME);
        servoPosition = servo.getPosition();

        if (reverseDirection) servo.setDirection(Servo.Direction.REVERSE);

    }

    // autonomous

    public void runToPosition(String position, boolean isSynchronous) {
        teleOpAssistMode((position == "extend"), (position == "retract"));
        if (isSynchronous) opMode.sleep(TIME);
    }
    
    // teleOp

    public void teleOpAssistMode(boolean minCondition, boolean maxCondition) {
        if (minCondition || maxCondition) {
            double servoOldPosition = servoPosition;
            if (minCondition) servoPosition = MIN_POSITION;
            if (maxCondition) servoPosition = MAX_POSITION;
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