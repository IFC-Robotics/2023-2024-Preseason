package org.firstinspires.ftc.teamcode.powerPlay.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Joaquin's Drivetrain Test", group = "test")
public class JoaquinDrivetrain extends LinearOpMode{

    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    private DcMotor motor4;
    double frontRightPower;
    double frontLeftPower;
    double backRightPower;
    double backLeftPower;

    public void runOpMode() {
        
        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor3 = hardwareMap.get(DcMotor.class, "motor3");
        motor4 = hardwareMap.get(DcMotor.class, "motor4");
       
        waitForStart();
        
        while opModeIsActive(){
            frontRightPower = Range.clip(drive - turn, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
            frontLeftPower  = Range.clip(drive + turn, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
            backRightPower  = Range.clip(drive - turn, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
            backLeftPower   = Range.clip(drive + turn, -MAX_TELEOP_SPEED, MAX_TELEOP_SPEED);
            motor1.power(frontRightPower);
            motor2.power(frontLeftPower);
            motor3.power(backRightPower);
            motor4.power(backLeftPower);


        }
    }

}