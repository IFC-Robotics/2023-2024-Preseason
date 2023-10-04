package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
@Autonomous(name="Jayden code", group = "Practice")
public class jayden extends LinearOpMode
  {
    public int multiply()
    {
      int a=1;
      int b=1;
      return(a*b);
    }
    public void runOpMode()
    {
      DcMotor motor;
      int i = 0;
      motor = hardwareMap.get(DcMotor.class, "motor_1");
      telemetry.addData("i: ", i);
      telemetry.update();
      i++;
      waitForStart();
        while(opModeIsActive()) {
          //System.out.printIn("h");
        }
    }


  }
