package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class Elevator {

    public DcMotor ElevatorMotor;

    public abstract void elevatorStart();

    public abstract void elevatorStop();

}