package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.powerPlay.robot.NoSubsystemRobotClass;

@TeleOp(name="No Subsystem TeleOp")
public class NoSubsystemTeleOp extends OpMode {

    // drivetrain variables

    double drive;
    double strafe;
    double turn;

    double frontRightSpeed;
    double frontLeftSpeed;
    double backRightSpeed;
    double backLeftSpeed;

    // other

    NoSubsystemRobotClass robot = new NoSubsystemRobotClass();

    @Override
    public void init () {
        robot.init(hardwareMap);
    }

    @Override
    public void start() {}

    @Override
    public void loop() {

        // drivetrain

        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        frontRightSpeed = Range.clip(drive - turn - strafe, -robot.MAX_DRIVE_SPEED, robot.MAX_DRIVE_SPEED);
        frontLeftSpeed = Range.clip(drive + turn + strafe, -robot.MAX_DRIVE_SPEED, robot.MAX_DRIVE_SPEED);
        backRightSpeed = Range.clip(drive - turn + strafe, -robot.MAX_DRIVE_SPEED, robot.MAX_DRIVE_SPEED);
        backLeftSpeed = Range.clip(drive + turn - strafe, -robot.MAX_DRIVE_SPEED, robot.MAX_DRIVE_SPEED);

        robot.motorFrontRight.setPower(frontRightSpeed);
        robot.motorFrontLeft.setPower(frontLeftSpeed);
        robot.motorBackRight.setPower(backRightSpeed);
        robot.motorBackLeft.setPower(backLeftSpeed);

    }

}