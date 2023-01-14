package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.powerPlay.robot.Robot;

@TeleOp(name="no claw teleOp", group="competition")
public class noClawTeleOp extends OpMode {

    @Override
    public void init() {
        telemetry.addLine("Initializing opMode...");
        telemetry.update();
        Robot.init(hardwareMap, telemetry);
    }

    @Override
    public void start() {
        telemetry.addLine("Executing opMode...");
        telemetry.update();
    }

    @Override
    public void loop() {
        Robot.servoHook.teleOpAssistMode(gamepad2.dpad_down, gamepad2.dpad_up);
        Robot.verticalLift.teleOpVerticalLiftAssistMode(gamepad2.a, gamepad2.x, gamepad2.b, gamepad2.y, gamepad2.right_bumper);
//        Robot.verticalLift.teleOpManualMode(-gamepad2.right_stick_y);
        Robot.drivetrain.teleOp(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
    }

}

/*

gamepad1.left_stick_x:  strafe
gamepad1.left_stick_y:  drive
gamepad1.right_stick_x: turn

gamepad2.right_stick_y: move vertical lift
gamepad2.a:             move vertical lift to transfer cone
gamepad2.x:             move vertical lift to ground junction
gamepad2.b:             move vertical lift to low junction
gamepad2.y:             move vertical lift to middle junction
gamepad2.right_bumper:  move vertical lift to high junction

gamepad2.dpad_up:       retract hook
gamepad2.dpad_down:     extend hook

*/