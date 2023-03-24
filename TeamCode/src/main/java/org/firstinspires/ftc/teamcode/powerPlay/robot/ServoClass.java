package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

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
        servo.scaleRange(this.minPosition, this.maxPosition);

        servoPosition = servo.getPosition();

        if (this.reverseDirection) servo.setDirection(Servo.Direction.REVERSE);

    }

    // autonomous

    public void runToPosition(String position) {
        runToPosition(position, false);
    }

    public void runToPosition(String position, boolean isSynchronous) {

        teleOpAssistMode((position == this.minPositionName), (position == this.maxPositionName));

        if (this.name == "servo_rotate_hook" && position == "middle") { // special case
            servoPosition = 0.7;
            servo.setPosition(servoPosition);
        }

        if (isSynchronous) opMode.sleep(this.time);

    }
    
    // teleOp

    public void teleOpAssistMode(boolean minConditionButton, boolean maxConditionButton) {

        if (minConditionButton || maxConditionButton) {

            // you can only move servoRotateHook from collect -> score IF verticalLift is NOT at 0
//            if (this.name == "servo_rotate_hook" && this.robot.verticalLift.motor.getCurrentPosition() == 0.0 && maxConditionButton) {
//                telemetry.addLine("SERVO ROTATE HOOK IS TRYING TO MOVE TO SCORE, BUT VERTICAL LIFT IS AT ZERO");
//                return;
//            }

            servoPosition = minConditionButton ? 0 : 1;
            servo.setPosition(servoPosition);

        }

    }

    public void teleOpManualMode(boolean minConditionButton, boolean maxConditionButton) {
        if (minConditionButton || maxConditionButton) {
            if (minConditionButton && servoPosition > 0) servoPosition -= this.speed;
            if (maxConditionButton && servoPosition < 1) servoPosition += this.speed;
            servo.setPosition(servoPosition);
        }
    }

    public void printData() {
        telemetry.addLine(String.format("%1$s position: %2$s", this.name, servo.getPosition()));
    }

}