package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class CRServoClass {

    LinearOpMode opMode;
    Telemetry telemetry;

    public CRServo crServo;
    public String crServoPosition;

    public final String name;
    public final String minPositionName;
    public final String maxPositionName;
    public final int time;
    public final boolean reverseDirection;

    ElapsedTime timer = new ElapsedTime();

    public CRServoClass(String name, String minPositionName, String maxPositionName, int time, boolean reverseDirection) {

        this.name = name;
        this.minPositionName = minPositionName;
        this.maxPositionName = maxPositionName;
        this.time = time;
        this.reverseDirection = reverseDirection;

    }

    public void init(LinearOpMode opModeParam) {

        opMode = opModeParam;
        telemetry = opMode.telemetry;

        crServo = opMode.hardwareMap.get(CRServo.class, this.name);
        crServoPosition = this.maxPositionName;

        if (this.reverseDirection) crServo.setDirection(CRServo.Direction.REVERSE);

        timer.reset();

    }

    // autonomous

    public void runToPosition(String position) { runToPosition(position, false); }

    public void runToPosition(String position, boolean isSynchronous) {
        if (position == this.minPositionName) teleOpAssistMode(1, 0, isSynchronous);
        if (position == this.maxPositionName) teleOpAssistMode(0, 1, isSynchronous);
    }

    // teleOp

    public void teleOpAssistMode(double upPower, double downPower) { teleOpAssistMode(upPower, downPower, false); }

    public void teleOpAssistMode(double upPower, double downPower, boolean isSynchronous) {

        telemetry.addData("upPower", upPower);
        telemetry.addData("downPower", downPower);
        telemetry.addData("crServoPosition", crServoPosition);

        if (upPower > 0 && crServoPosition != this.minPositionName) {
            crServo.setPower(-1);
            crServoPosition = this.minPositionName;
            timer.reset();
            if (isSynchronous) waitForCRServo();
        }

        if (downPower > 0 && crServoPosition != this.maxPositionName) {
            crServo.setPower(1);
            crServoPosition = this.maxPositionName;
            timer.reset();
            if (isSynchronous) waitForCRServo();
        }

        if (crServo.getPower() != 0 && timer.milliseconds() >= this.time) {
            telemetry.addLine("set the power to 0");
            crServo.setPower(0);
        }

    }

    public void waitForCRServo() {
        while (timer.milliseconds() < this.time) {}
    }

    public void teleOpManualMode(double upPower, double downPower) {
        crServo.setPower(upPower - downPower);
    }

    public void printData() {
        telemetry.addLine(String.format("\n%1$s crServoPosition: %2$s", this.name, crServoPosition));
        telemetry.addLine(String.format("%1$s power: %2$s", this.name, crServo.getPower()));
    }

}