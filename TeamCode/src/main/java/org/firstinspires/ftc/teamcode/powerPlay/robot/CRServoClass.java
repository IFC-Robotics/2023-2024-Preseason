package org.firstinspires.ftc.teamcode.powerPlay.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class CRServoClass {

    LinearOpMode opMode;
    Telemetry telemetry;

    public CRServo crServo;

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

        if (this.reverseDirection) crServo.setDirection(CRServo.Direction.REVERSE);

        timer.reset();

    }

    // autonomous

    public void runToPosition(String position) { runToPosition(position, false); }

    public void runToPosition(String position, boolean isSynchronous) {
        double downPower = (position == this.minPositionName) ? -1 : 0;
        double upPower   = (position == this.maxPositionName) ? 1 : 0;
        teleOpAssistMode(downPower, upPower, isSynchronous);
    }

    public void waitForCRServo() {
        while (timer.milliseconds < this.time) {}
    }

    public void stop() {
        crServo.setPower(0);
    }

    // teleOp

    public void teleOpAssistMode(double downPower, double upPower) { teleOpAssistMode(downPower, upPower, false); }

    public void teleOpAssistMode(double downPower, double upPower, boolean isSynchronous) {
        
        if (downPower > 0 || upPower > 0) {

            crServo.setPower(upPower ? 1 : -1);

            timer.reset();

            if (isSynchronous) waitForCRServo();
            if (timer.milliseconds() >= this.time) stop();
        
        }
    
    }

    public void teleOpManualMode(double downPower, double upPower) {
        crServo.setPower(upPower - downPower);
    }

    public void printData() {
        telemetry.addLine(String.format("%1$s power: %2$s", this.name, crServo.getPower()));
    }

}