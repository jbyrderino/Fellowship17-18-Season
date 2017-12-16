package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Jack's Rec on 12/16/2017.
 */

public class KeithJewlKnocker extends JewlKnocker {


    Servo jKServo;

    public void knockerDown() {
        jKServo.setPosition(0.5);
    }
    public void knockerUp(){
        jKServo.setPosition(0);
    }


}
