package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class CRServoClass {

    LinearOpMode opMode;
    Telemetry telemetry;

    public CRServo crServo;
    public double crServoPower;

    public final String name;
    public final String minPositionName;
    public final String maxPositionName;
    public final double speed;
    public final int time;
    public final boolean reverseDirection;

    ElapsedTime timer = new ElapsedTime();

    public CRServoClass(String name, String minPositionName, String maxPositionName, double speed, int time, boolean reverseDirection) {

        this.name = name;
        this.minPositionName = minPositionName;
        this.maxPositionName = maxPositionName;
        this.speed = speed;
        this.time = time;
        this.reverseDirection = reverseDirection;

    }

    public void init(LinearOpMode opModeParam) {

        opMode = opModeParam;
        telemetry = opMode.telemetry;

        crServo = opMode.hardwareMap.get(CRServo.class, this.name);
        crServoPower = 0;

        if (this.reverseDirection) crServo.setDirection(CRServo.Direction.REVERSE);

        timer.reset();

    }

    // autonomous

    public void runToPosition(String position) {
//        runToPosition(position, false);
    }

//    public void runToPosition(String position, boolean isSynchronous) {
//        teleOpAssistMode((position == this.minPositionName), (position == this.maxPositionName));
//    }

    public void stop() {
        crServoPower = 0;
        crServo.setPower(crServoPower);
    }

    // teleOp

//    public void teleOpAssistMode(boolean minConditionButton, boolean maxConditionButton) {
//        teleOpManualMode(minConditionButton, maxConditionButton);
//        if (timer.milliseconds() > this.time) stop();
//    }

    public void teleOpManualMode(double downPower, double upPower) {
        crServoPower = upPower - downPower;
        crServo.setPower(crServoPower);
        timer.reset();
    }

    public void printData() {
        telemetry.addLine(String.format("%1$s power: %2$s", this.name, crServo.getPower()));
    }

}