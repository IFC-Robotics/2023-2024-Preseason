package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.powerPlay.NewRobotClass;

@TeleOp(name="new teleOp")
public class NewTeleOp extends OpMode {

    robotClass robot = new NewRobotClass();

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void start() {}

    @Override
    public void loop() {

        moveDrivetrain();

        moveClaw();
        rotateClaw();
        horizontalLift();

        moveHook();
        rotateHook();
        verticalLift();

    }

    public void moveDrivetrain() {

        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        double frontRightSpeed = Range.clip(drive - turn - strafe, -robot.MAX_DRIVE_SPEED, robot.MAX_DRIVE_SPEED);
        double frontLeftSpeed = Range.clip(drive + turn + strafe, -robot.MAX_DRIVE_SPEED, robot.MAX_DRIVE_SPEED);
        double backRightSpeed = Range.clip(drive - turn + strafe, -robot.MAX_DRIVE_SPEED, robot.MAX_DRIVE_SPEED);
        double backLeftSpeed = Range.clip(drive + turn - strafe, -robot.MAX_DRIVE_SPEED, robot.MAX_DRIVE_SPEED);

        robot.motorFrontRight.setPower(frontRightSpeed);
        robot.motorFrontLeft.setPower(frontLeftSpeed);
        robot.motorBackRight.setPower(backRightSpeed);
        robot.motorBackLeft.setPower(backLeftSpeed);

    }

    public void moveClaw() {

        if (gamepad2.dpad_left) robot.servoClaw.

    }

    public void rotateClaw() {

        

    }

}

/* 

gamepad1.left_stick_button:  
gamepad1.left_stick_x:       strafing
gamepad1.left_stick_y:       driving
gamepad1.right_stick_button: 
gamepad1.right_stick_x:      turning
gamepad1.right_stick_y:      
gamepad1.y:                  
gamepad1.b:                  
gamepad1.a:                  
gamepad1.x:                  
gamepad1.dpad_up:            
gamepad1.dpad_right:         close claw
gamepad1.dpad_down:          
gamepad1.dpad_left:          open claw
gamepad1.left_bumper:        
gamepad1.left_trigger:       
gamepad1.right_bumper:       
gamepad1.right_trigger:      
gamepad1.start:              
gamepad1.back:               
gamepad1.guide:              

gamepad2.left_stick_button:  
gamepad2.left_stick_x:       
gamepad2.left_stick_y:       
gamepad2.right_stick_button: 
gamepad2.right_stick_x:      
gamepad2.right_stick_y:      
gamepad2.y:                  
gamepad2.b:                  
gamepad2.a:                  
gamepad2.x:                  
gamepad2.dpad_up:            
gamepad2.dpad_right:         
gamepad2.dpad_down:          
gamepad2.dpad_left:          
gamepad2.left_bumper:        
gamepad2.left_trigger:       
gamepad2.right_bumper:       
gamepad2.right_trigger:      
gamepad2.start:              
gamepad2.back:               
gamepad2.guide:              

*/