package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static android.os.SystemClock.sleep;

/**
 * Created by Jack's Rec on 12/16/2017.
 */

public class KeithJewlKnocker extends JewlKnocker {

    Servo knockerBase = null;
    Servo servoKnocker = null;
    Telemetry tl = null;

    KeithJewlKnocker (HardwareMap hwMap, String labelBase, String labelKnocker, Telemetry telemetry) {
        knockerBase = hwMap.servo.get(labelBase);
        knockerBase.setPosition(0);
        servoKnocker = hwMap.servo.get(labelKnocker);
        servoKnocker.setPosition(.5);
        tl = telemetry;
    }

    public double getKnockerPosition() {
        double servoPosition = servoKnocker.getPosition();

        return servoPosition;
    }
    public void knockerDown() {
        servoKnocker.setPosition(.9);
        sleep(300);
    }

    public void knockerUp(){
        servoKnocker.setPosition(.5);
        sleep(300);
    }

    public void baseKnockerRotateLeft() {
        knockerBase.setPosition(0);
        sleep(300);
    }

    public void baseKnockerRotateRight(){
        knockerBase.setPosition(.5);
        sleep(300);
    }

    public void knockLeft() {
        knockerBase.setPosition(.25);
        sleep(300);
    }

    public void knockright() {
        knockerBase.setPosition(.75);
        sleep(300);
    }
}
