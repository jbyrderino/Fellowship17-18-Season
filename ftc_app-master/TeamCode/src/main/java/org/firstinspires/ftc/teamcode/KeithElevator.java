package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareDevice;

public class KeithElevator{

//
//    private HardwareDevice ElevatorMotor = null;
//    private HardwareDevice ElevatorServo = null;
//    private double motorPower;
//
//    //Temporary until controller class is finished
//    private boolean lBumperPressed;
//    private boolean rBumperPressed;
//    private boolean DPadDownPressed;
//
//    KeithElevator (HardwareMap hwmap, double MotorPower) {
//        ElevatorMotor = hwmap.get("ElevatorMotor");
//        ElevatorServo = hwmap.get("ElevatorMotor");
//        ElevatorInit(MotorPower);
//    }
//
//    @Override
//    public void elevatorStart(double Pwr) {
//        ElevatorMotor.setPower(Pwr);
//    }
//
//    public void elevatorStop() {
//        ElevatorMotor.setPower(0.0);
//    }
//
//    public void kickerInit(){
//        ElevatorServo.scaleRange(0.0, 1.0);
//        ElevatorServo.setPosition(0.0);
//    }
//
//    public void kickerSetPosition(double Pos) {
//        ElevatorServo.setPosition(Pos);
//    }
//
//    public void kickerReset() {
//        ElevatorServo.setPosition(0.0);
//    }
//
//    public void ElevatorInit(double mp) {
//        motorPower = mp;
//        kickerInit();
//    }
//
//    public void ElevatorRun() {
//        kickerReset();
//        elevatorStop();
//
//        if (lBumperPressed){
//            elevatorStart(motorPower);
//
//            while (true) {
//                 if (lBumperPressed) {
//                    elevatorStop();
//                }
//            }
//        }
//        if (rBumperPressed) {
//            elevatorStart(-motorPower);
//
//            while (true) {
//                if (lBumperPressed) {
//                    elevatorStop();
//                }
//            }
//        }
//
//        if (DPadDownPressed) {
//            kickerSetPosition(1.0);
//        }
//    }

    private DcMotor ElevatorMotor = null;
    private Servo ElevatorServo = null;
    private double motorPower;

    KeithElevator (HardwareMap hwmap, double MotorPower) {
        ElevatorMotor = hwmap.get(DcMotor.class, "ElevatorMotor");
        ElevatorServo = hwmap.get(Servo.class, "ElevatorServo");;
        ElevatorInit(MotorPower);
    }

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

    public void kickerKick() {
        kickerReset();
        kickerSetPosition(1.0);
        kickerReset();
    }

    public void ElevatorInit(double mp) {
        motorPower = mp;
        kickerInit();
    }
}