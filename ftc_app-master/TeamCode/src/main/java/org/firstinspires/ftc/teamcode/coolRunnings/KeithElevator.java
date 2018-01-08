package org.firstinspires.ftc.teamcode.coolRunnings;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.coolRunnings.Elevator;

public class KeithElevator extends Elevator {


    private DcMotor ElevatorMotor = null;
    private Servo ElevatorServo = null;
    private double motorPower;

    //Temporary until controller class is finished
    private boolean lBumperPressed;
    private boolean rBumperPressed;
    private boolean DPadDownPressed;

    KeithElevator (DcMotor eM, Servo eS) {
        ElevatorMotor = eM; ElevatorServo = eS;
    }

    @Override
    public void elevatorStart(double Pwr) {
        ElevatorMotor.setPower(Pwr);
    }

    public void elevatorStop() {
        ElevatorMotor.setPower(0.0);
    }

    public void kickerInit(){
        ElevatorServo.scaleRange(0.0, 1.0);
        ElevatorServo.setPosition(0.0);
    }

    public void kickerSetPosition(double Pos) {
        ElevatorServo.setPosition(Pos);
    }

    public void kickerReset() {
        ElevatorServo.setPosition(0.0);
    }

    public void ElevatorInit(double mp) {
        motorPower = mp;
        kickerInit();
    }

    public void ElevatorRun() {
        kickerReset();
        elevatorStop();

        if (lBumperPressed){
            elevatorStart(motorPower);

            while (true) {
                 if (lBumperPressed) {
                    elevatorStop();
                }
            }
        }
        if (rBumperPressed) {
            elevatorStart(-motorPower);

            while (true) {
                if (lBumperPressed) {
                    elevatorStop();
                }
            }
        }

        if (DPadDownPressed) {
            kickerSetPosition(1.0);
        }
    }
}