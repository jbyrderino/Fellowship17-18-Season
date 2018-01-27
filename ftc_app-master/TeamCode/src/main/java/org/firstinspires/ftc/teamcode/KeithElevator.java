package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class KeithElevator {
	private DcMotor ElevatorMotor = null;
	private Servo ElevatorServo = null;
	private Telemetry t;
	private double motorPower;
	public boolean UP;
	public double upPos = 0.0;
	public double downPos = 0.7;

	KeithElevator(HardwareMap hwmap, Telemetry tel, String elevatorMotor, String elevatorServo, double MotorPower) {
		ElevatorMotor = hwmap.get(DcMotor.class, elevatorMotor);
		ElevatorServo = hwmap.get(Servo.class, elevatorServo);
		ElevatorInit(MotorPower);
		t = tel;
	}

	public void servoTest(){
		int ti = 0;

		ElevatorServo.setPosition(0.6);
		while (ElevatorServo.getPosition() < 0.6 || ti < 10000){ t.addLine("0.6"); ti++;}
		ti = 0;

		ElevatorServo.setPosition(0.5);
		while (ElevatorServo.getPosition() < 0.5 || ti < 10000){ t.addLine("0.5"); }
		ti = 0;

		ElevatorServo.setPosition(0.4);
		while (ElevatorServo.getPosition() < 0.4){ t.addLine("0.4"); }
		ElevatorServo.setPosition(0.3);
		while (ElevatorServo.getPosition() < 0.3){ t.addLine("0.3"); }
		ElevatorServo.setPosition(0.2);
		while (ElevatorServo.getPosition() < 0.2){ t.addLine("0.2"); }
	}

	public void elevatorStart(double Pwr) {
		ElevatorMotor.setPower(Pwr);
	}

	/*public void elevatorStop() {
		ElevatorMotor.setPower(0.0);
	}*/

	public void kickerInit(){
		ElevatorServo.scaleRange(0.0, 1.0);
		//ElevatorServo.setPosition(downPos);
	}

	public double getKickerPosition(){
		return ElevatorServo.getPosition();
	}

	public boolean isKickerUp(){
		if (getKickerPosition() == upPos){
			UP = true;
		}
		if (getKickerPosition() <= downPos && getKickerPosition() != upPos) {
			UP = false;
		}

		return UP;
	}


	public void kickerSetPosition(double Pos) {t.addLine("kicker servo position is being set"); ElevatorServo.setPosition(Pos); }

	public void kickerReset() {
		kickerSetPosition(downPos);
		while (ElevatorServo.getPosition() < downPos){ t.addLine("kicker reseting..."); }
		t.addLine("kicker reset");

	}

	public void kickerKick() {
		kickerSetPosition(upPos);
		while (ElevatorServo.getPosition() > upPos){ t.addLine("kicker kicking..."); }
		t.addLine("kicker kicked");
	}

	public void ElevatorInit(double mp) {
		motorPower = mp;
		kickerInit();
	}
}