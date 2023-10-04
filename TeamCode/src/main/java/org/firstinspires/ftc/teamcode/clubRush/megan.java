package org.firstinspires.ftc.teamcode.clubRush;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
@Autonomous(name="Megan code", group = "clubRush")
public class megan extends LinearOpMode{
  public int multiply(){
  int a=99;
  int b=20;
  int c = a*b;
  return c;
  }
  public void runOpMode() {
    int i = 0;
    waitForStart();
    while (opModeIsActive()) {
      telemetry.addData("i: ", i);
      telemetry.update();
      i += 1;
      DcMotor motor;
      motor = hardwareMap.get(DcMotor.class, "motor_1");
    }
  }
}
