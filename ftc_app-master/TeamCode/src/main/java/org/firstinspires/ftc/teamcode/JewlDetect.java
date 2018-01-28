package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.*;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.io.IOException;


/**
 * Created by KAEGAN on 1/27/2018.
 */

public class JewlDetect {

	Telemetry t = null;
	HardwareMap hwMap = null;
	boolean jewlColor;
	private JewelDetector jewelDetector = null;


	public void JewlDetectForInit(Telemetry tel, HardwareMap hw){
		t = tel;
		hwMap = hw;

		t.addData("Status", "Initialized");


		jewelDetector = new JewelDetector();

		jewelDetector.init(hwMap.appContext, CameraViewDisplay.getInstance());

		//Jewel Detector Settings
		jewelDetector.areaWeight = 0.02;
		jewelDetector.detectionMode = JewelDetector.JewelDetectionMode.MAX_AREA; // PERFECT_AREA
		//jewelDetector.perfectArea = 6500; <- Needed for PERFECT_AREA
		jewelDetector.debugContours = true;
		jewelDetector.maxDiffrence = 15;
		jewelDetector.ratioWeight = 15;
		jewelDetector.minArea = 700;

		jewelDetector.enable();
	}

	public void JewlDetectForLoop(){
		t.addData("Current Order", "Jewel Order: " + jewelDetector.getCurrentOrder().toString()); // Current Result
		t.addData("Last Order", "Jewel Order: " + jewelDetector.getLastOrder().toString()); // Last Known Result
	}

	public boolean JewlColor(){
		if (jewelDetector.getCurrentOrder().toString() == "BLUE_RED"){
			jewlColor = true;
		}
		if (jewelDetector.getCurrentOrder().toString() == "RED_BLUE"){
			jewlColor = false;
		}
		return jewlColor;
	}

	public void JewlDetectForStop(){
		jewelDetector.disable();
	}

}
