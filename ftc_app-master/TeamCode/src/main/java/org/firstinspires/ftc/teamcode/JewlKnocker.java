package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
/**
 * Created by Jack's Rec on 12/16/2017.
 */

public abstract class JewlKnocker {

    Servo servoKnocker;

    public abstract void knockerDown();

    public abstract void knockerUp();

    public abstract double getKnockerPosition();
    }





