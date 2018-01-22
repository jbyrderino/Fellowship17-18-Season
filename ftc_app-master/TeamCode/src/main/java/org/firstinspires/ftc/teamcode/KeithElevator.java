package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class KeithElevator {
	private DcMotor ElevatorMotor = null;
	private Servo ElevatorServo = null;
	private double motorPower;
	public boolean UP;
	public double upPos = 0.0;
	public double downPos = 0.5;

	KeithElevator(HardwareMap hwmap, String elevatorMotor, String elevatorServo, double MotorPower) {
		ElevatorMotor = hwmap.get(DcMotor.class, elevatorMotor);
		ElevatorServo = hwmap.get(Servo.class, elevatorServo);
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
		ElevatorServo.setPosition(downPos);
	}

	public double getKickerPosition(){
		return ElevatorServo.getPosition();
	}

	public boolean isKickerUp(){
		if (getKickerPosition() == upPos){
			UP = true;
		}
		if (getKickerPosition() <= downPos) {
			UP = false;
		}

		return UP;
	}


	public void kickerSetPosition(double Pos) {
		ElevatorServo.setPosition(Pos);
	}

	public void kickerReset() {
		ElevatorServo.setPosition(downPos);
		while (getKickerPosition() < downPos){}
	}

	public void kickerKick() {
		kickerSetPosition(upPos);

		while (getKickerPosition() > upPos){}
	}

	public void ElevatorInit(double mp) {
		motorPower = mp;
		kickerInit();
	}
}