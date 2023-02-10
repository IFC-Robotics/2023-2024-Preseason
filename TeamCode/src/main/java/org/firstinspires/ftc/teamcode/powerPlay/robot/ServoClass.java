package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ServoClass {

    LinearOpMode opMode;
    Telemetry telemetry;

    public Servo servo;
    public double servoPosition;

    public final String name;
    public final double minPosition;
    public final double maxPosition;
    public final String minPositionName;
    public final String maxPositionName;
    public final double speed;
    public final int time;
    public final boolean reverseDirection;

    // look into using servo.scaleRange()
    // add a range sensor to the claw/hook so that it automatically closes when it detects a cone

    public ServoClass(String name, String minPositionName, double minPosition, String maxPositionName, double maxPosition, double speed, int time, boolean reverseDirection) {
        this.name = name;
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        this.minPositionName = minPositionName;
        this.maxPositionName = maxPositionName;
        this.speed = speed;
        this.time = time;
        this.reverseDirection = reverseDirection;
    }

    public void init(LinearOpMode opModeParam) {

        opMode = opModeParam;
        telemetry = opMode.telemetry;

        servo = opMode.hardwareMap.get(Servo.class, this.name);
        servoPosition = servo.getPosition();

        if (this.reverseDirection) servo.setDirection(Servo.Direction.REVERSE);

    }

    // autonomous

    public void runToPosition(String position) {
        runToPosition(position, false);
    }

    public void runToPosition(String position, boolean isSynchronous) {
        teleOpAssistMode((position == this.minPositionName), (position == this.maxPositionName));
        if (isSynchronous) opMode.sleep(this.time);
    }
    
    // teleOp

    public void teleOpAssistMode(boolean minConditionButton, boolean maxConditionButton) {
        if (minConditionButton || maxConditionButton) {
            double servoOldPosition = servoPosition;
            servoPosition = minConditionButton ? this.minPosition : this.maxPosition;
            telemetry.addLine(String.format("running %1$s from position %2$s to %3$s", this.name, servoOldPosition, servoPosition));
            servo.setPosition(servoPosition);
        }
    }

    public void teleOpManualMode(boolean minConditionButton, boolean maxConditionButton) {
        if (minConditionButton || maxConditionButton) {
            double servoOldPosition = servoPosition;
            if (minConditionButton && servoPosition > this.minPosition) servoPosition -= this.speed;
            if (maxConditionButton && servoPosition < this.maxPosition) servoPosition += this.speed;
            telemetry.addLine(String.format("running %1$s from position %2$s to %3$s", this.name, servoOldPosition, servoPosition));
            servo.setPosition(servoPosition);
        }
    }

}