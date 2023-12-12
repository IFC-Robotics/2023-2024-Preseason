package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.Robot;

public class ServoClass {

    LinearOpMode opMode;
    Telemetry telemetry;

    public Servo servo;
    public double servoPosition;
    public String servoPosName = "left";

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

        teleOpAssistMode(position == this.minPositionName, false,position == this.maxPositionName);

        if (isSynchronous) opMode.sleep(this.time);

    }
    
    // teleOp

    public void teleOpAssistMode(boolean minConditionButton, boolean medConditionButton, boolean maxConditionButton) {

            // you can only move servoDeposit from collect -> score IF verticalLift is above LOW (600)
            if (this.name == "servo_deposit" && Robot.verticalLift.motor.getCurrentPosition() <= 600 && maxConditionButton) {
                telemetry.addLine("SERVO DEPOSIT IS TRYING TO MOVE, BUT VERTICAL LIFT IS TOO LOW");
                return;

            } else if (minConditionButton) {
                servoPosName = "collect";
                servoPosition = 0.01;
            }
            else if (medConditionButton) {
                servoPosName = "score";
                servoPosition = 0.55; // tune this
            }
            else if (maxConditionButton) {
                servoPosName = "auton";
                servoPosition = 0.62; //tune this
            }
            servo.setPosition(servoPosition);



    }

    public void teleOpManualMode(boolean minConditionButton, boolean maxConditionButton) {
        if (minConditionButton || maxConditionButton) {
            if (minConditionButton && servoPosition > 0) servoPosition -= this.speed;
            if (maxConditionButton && servoPosition < 1) servoPosition += this.speed;
            servo.setPosition(servoPosition);
        }
    }

    public void printData() {
        telemetry.addLine(String.format("%1$s position: %2$s %3$s", this.name, servo.getPosition(),servoPosName));
    }

}
