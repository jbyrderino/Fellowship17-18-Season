package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Keith - ElevatorCarriage", group = "TeleOp")

public class KiethECopmode extends OpMode {

   public double Pwr;
   public HardwareMap hwMap;

   KeithElevator kE = new KeithElevator(hwMap, 0.5);

   public void init() {
       //KeithCarriage.carriageInit();
   }

   public void loop() {
       if (gamepad2.left_bumper){
           kE.elevatorStart(0.5);
       }else {
           kE.elevatorStop();
       }

       if (gamepad2.right_bumper) {
           kE.elevatorStart(-0.5);
       }else {
           kE.elevatorStop();
       }

       if (gamepad2.dpad_down) {
           kE.kickerSetPosition(1.0);
       }


   }

}
