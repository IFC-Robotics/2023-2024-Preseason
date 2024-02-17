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
    public final double medPosition;
    public final double maxPosition;
    public final String minPositionName;
    public final String medPositionName;
    public final String maxPositionName;
    public final double speed;
    public final int time;
    public final boolean reverseDirection;



    public ServoClass(String name, String minPositionName, double minPosition, String medPositionName, double medPosition, String maxPositionName, double maxPosition, double speed, int time, boolean reverseDirection) {

        this.name = name;
        this.minPosition = minPosition;
        this.medPosition = medPosition;
        this.maxPosition = maxPosition;
        this.minPositionName = minPositionName;
        this.medPositionName = medPositionName;
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

        // reset launching servo at initialization
        if (this.name == "servo_launcher") {
            servo.setPosition(this.maxPosition);
        }
        if (this.name == "servo_deposit") {
            servo.setPosition(this.minPosition);
        }

        servoPosition = servo.getPosition();

        if (this.reverseDirection) servo.setDirection(Servo.Direction.REVERSE);

    }

    // autonomous

    public void runToPosition(String position) {
        runToPosition(position, false);
    }

    public void runToPosition(String position, boolean isSynchronous) {

        teleOpAssistMode(position == this.minPositionName, position == this.medPositionName,position == this.maxPositionName);

        if (isSynchronous) {
            opMode.sleep(this.time); //broken
        }

    }
    
    // teleOp

    public void teleOpAssistMode(boolean minConditionButton, boolean medConditionButton, boolean maxConditionButton) {

            // you can only move servoDeposit from collect -> score IF verticalLift is above LOW (600)
//            if (this.name == "servo_deposit" && Robot.verticalLift.motor.getCurrentPosition() <= 10 && maxConditionButton) {
//                telemetry.addLine("SERVO DEPOSIT IS TRYING TO MOVE, BUT VERTICAL LIFT IS TOO LOW");
//                return;
//            }
            if (minConditionButton) {
                servoPosName = minPositionName;
                servoPosition = minPosition;
            }
            else if (medConditionButton) {
                servoPosName = medPositionName;
                servoPosition = medPosition; // tune this
            }
            else if (maxConditionButton) {
                servoPosName = maxPositionName;
                servoPosition = 0.46; //tune this
            }
            servo.setPosition(servoPosition);



    }

    public void teleOpManualMode(boolean minConditionButton, boolean maxConditionButton) {
        if (minConditionButton || maxConditionButton) {
            if (minConditionButton && servoPosition > 0.2) servoPosition -= this.speed;
            if (maxConditionButton && servoPosition < 1) servoPosition += this.speed;
            servo.setPosition(servoPosition);
        }
    }

    public void printData() {
        telemetry.addLine(String.format("%1$s position: %2$s %3$s", this.name, servo.getPosition(),servoPosName));
    }

}
