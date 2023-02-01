package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ServoClass {

    LinearOpMode opMode;
    Telemetry telemetry;

    public Servo servo;
    public double servoPosition;

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

    public void init(LinearOpMode opMode) {

        opMode = opMode;
        telemetry = opMode.telemetry;

        servo = opMode.hardwareMap.get(Servo.class, this.name);
        servoPosition = servo.getPosition();

        if (this.reverseDirection) servo.setDirection(Servo.Direction.REVERSE);

    }

    // autonomous

    public void runToPosition(String position, boolean isSynchronous) {
        teleOpAssistMode((position == this.minPositionName), (position == this.maxPositionName));
        if (isSynchronous) opMode.sleep(this.time);
    }
    
    // teleOp

    public void teleOpAssistMode(boolean minCondition, boolean maxCondition) {
        if (minCondition || maxCondition) {
            double servoOldPosition = servoPosition;
            servoPosition = minCondition ? this.minPosition : this.maxPosition;
            telemetry.addLine(String.format("running %1$s from position %2$s to %3$s", this.name, servoOldPosition, servoPosition));
            servo.setPosition(servoPosition);
        }
    }

    public void teleOpManualMode(double joystick) {
        if (Math.abs(joystick) > 0.02) {
            double servoOldPosition = servoPosition;
            if (joystick < 0 && servoPosition > this.minPosition) servoPosition -= this.speed;
            if (joystick > 0 && servoPosition < this.maxPosition) servoPosition += this.speed;
            telemetry.addLine(String.format("increment %1$s from position %2$s by %3$s", this.name, servoOldPosition, (servoPosition - servoOldPosition)));
            servo.setPosition(servoPosition);
        }
    }

}