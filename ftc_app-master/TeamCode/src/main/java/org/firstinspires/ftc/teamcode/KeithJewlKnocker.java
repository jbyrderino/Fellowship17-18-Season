package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static android.os.SystemClock.sleep;

/**
 * Created by Jack's Rec on 12/16/2017.
 */

public class KeithJewlKnocker extends JewlKnocker {

    Servo servoKnocker = null;
    Telemetry tl = null;

    KeithJewlKnocker (HardwareMap hwMap, String label, Telemetry telemetry) {
        servoKnocker = hwMap.servo.get(label);
        tl = telemetry;
    }

    public double getKnockerPosition() {
        double servoPosition = servoKnocker.getPosition();

        return servoPosition;
    }
    public void knockerDown() {
        servoKnocker.setPosition(.7);
        sleep(1000);
    }

    public void knockerUp(){
        servoKnocker.setPosition(.2);
        sleep(1000);
    }


}
