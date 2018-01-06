package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class Elevator {

    public DcMotor ElevatorMotor;

    public abstract void elevatorStart(double Pwr);

    public abstract void elevatorStop();

}