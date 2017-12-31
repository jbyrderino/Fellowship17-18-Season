package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Jack's Rec on 12/16/2017.
 */

public class KeithJewlKnocker extends JewlKnocker {

    Servo servoKnocker = null;

    KeithJewlKnocker (Servo sK) {
        servoKnocker = sK;
    }

    public double getKnockerPosition() {
        double servoPosition = servoKnocker.getPosition();

        return servoPosition;
    }
    public void knockerDown() {
        servoKnocker.setPosition(0.5);
    }
    public void knockerUp(){
        servoKnocker.setPosition(0);
    }


}
