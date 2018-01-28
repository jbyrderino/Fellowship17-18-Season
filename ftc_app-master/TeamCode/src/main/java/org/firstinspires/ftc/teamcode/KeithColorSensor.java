package org.firstinspires.ftc.teamcode;

import android.hardware.Sensor;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by KAEGAN on 1/28/2018.
 */

public class KeithColorSensor {

	ColorSensor KnockerCS = null;
	Telemetry tel;

	KeithColorSensor(HardwareMap hwMap, Telemetry t) {
		tel = t;
		KnockerCS = hwMap.get(ColorSensor.class, "JewlColorSensor");
	}

	public void TestColor(){
	}

	public void KnockJewl(){}

}
