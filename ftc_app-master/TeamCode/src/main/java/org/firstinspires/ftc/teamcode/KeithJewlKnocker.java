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
        knockerBase.setPosition(0.25);
        servoKnocker = hwMap.servo.get(labelKnocker);
        servoKnocker.setPosition(0.75);
        tl = telemetry;
    }

    public void setBasePosition (double position) {
        knockerBase.setPosition(position);
    }
    public void setKnockerPosition (double position) {
        servoKnocker.setPosition(position);
    }

    public double getBasePosition() {
        double servoPosition = knockerBase.getPosition();
        return servoPosition;
    }
    public double getKnockerPosition() {
        double servoPosition = servoKnocker.getPosition();
        return servoPosition;
    }
    public void knockerDown() {
<<<<<<< HEAD
        servoKnocker.setPosition(.3);
=======
        servoKnocker.setPosition(.55);
>>>>>>> f052989fae9df69d52bf12e2df3fdbfd81549df3
        sleep(500);
    }

    public void knockerUp(){
        servoKnocker.setPosition(.95);
        sleep(500);
    }

    public void baseKnockerRotateLeft() {
        knockerBase.setPosition(.20);
        sleep(500);
    }

    public void baseKnockerRotateRight(){
        knockerBase.setPosition(.76);
        sleep(500);
    }

    public void knockLeft() {
        knockerBase.setPosition(.56);
        sleep(500);
        knockerBase.setPosition(.76);
        sleep(500);
    }

    public void knockRight() {
        knockerBase.setPosition(.96);
        sleep(500);
        knockerBase.setPosition(.76);
        sleep(500);
    }
}
