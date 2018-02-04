package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.hardware.I2cDevice;

/**
 * Created by Jack's Rec on 12/16/2017.
 */

public class KeithJewlKnocker extends JewlKnocker {

    Servo knockerBase = null;
    Servo servoKnocker = null;
    ModernRoboticsI2cColorSensor2 sensor;
    Telemetry tl = null;

    LinearOpMode lop;
    boolean useAborting;

    public void setLinearOpMode(LinearOpMode lop) {
        if (lop != null) {
            this.lop = lop;
            useAborting = true;
        } else {
            this.lop = null;
            useAborting = false;
        }
    }


    KeithJewlKnocker(HardwareMap hwMap, String labelBase, String labelKnocker, String labelSensor, Telemetry telemetry) {
        I2cDevice i2c = hwMap.i2cDevice.get(labelSensor);
        sensor = new ModernRoboticsI2cColorSensor2(i2c.getI2cController(), i2c.getPort());
        sensor.enableLed(true);
        knockerBase = hwMap.servo.get(labelBase);
        knockerBase.setPosition(0.25);
        servoKnocker = hwMap.servo.get(labelKnocker);
        servoKnocker.setPosition(0.1);
        tl = telemetry;

    }

    public int detectColor() {
        return sensor.colorNumber();
    }

    public void setBasePosition(double position) {
        knockerBase.setPosition(position);
    }

    public void setKnockerPosition(double position) {
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
        servoKnocker.setPosition(0.55);
        sleep(500);
    }

    public void knockerUp() {
        servoKnocker.setPosition(.1);
        sleep(500);
    }

    public void baseKnockerRotateLeft() {
        knockerBase.setPosition(.45);
        if (!sleep(700)) {
            return;
        }
        servoKnocker.setPosition(.65);
        if (!sleep(700)) {
            return;
        }
        knockerBase.setPosition(.76);
        if (!sleep(700)) {
            return;
        }
        servoKnocker.setPosition(.55);
        if (!sleep(1000)) {
            return;
        }
    }

    public void baseKnockerRotateRight() {
        servoKnocker.setPosition(.55);
        if (!sleep(700)) {
            return;
        }
        knockerBase.setPosition(.76);
        if (!sleep(700)) {
            return;
        }
        servoKnocker.setPosition(.65);
        if (!sleep(700)) {
            return;
        }
        knockerBase.setPosition(.45);
        if (!sleep(1000)) {
            return;
        }
    }

    public void knockLeft() {
        tl.addLine("knock left");
        tl.update();
        knockerBase.setPosition(.1);
        if (!sleep(700)) {
            return;
        }
        knockerBase.setPosition(.76);
        if (!sleep(700)) {
            return;
        }
    }

    public void knockRight() {
        tl.addLine("knock right");
        tl.update();
        knockerBase.setPosition(.96);
        if (!sleep(700)) {
            return;
        }
        knockerBase.setPosition(.76);
        if (!sleep(700)) {
            return;
        }
    }

    public boolean sleep(int millis) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < millis) {
            if (useAborting && !lop.opModeIsActive()) {
                return false;
            }
        }
        return true;
    }
}
