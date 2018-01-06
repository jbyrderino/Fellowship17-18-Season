package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class KeithElevator extends Elevator {


    public DcMotor ElevatorMotor = null;

    KeithElevator (DcMotor eM) {
        ElevatorMotor = eM;
    }

    @Override
    public void elevatorStart(double Pwr) {
        ElevatorMotor.setPower(Pwr);
    }

    public void elevatorStop() {
        ElevatorMotor.setPower(0.0);
    }

    //ToDo Finish building the xBox controller functionality 
}