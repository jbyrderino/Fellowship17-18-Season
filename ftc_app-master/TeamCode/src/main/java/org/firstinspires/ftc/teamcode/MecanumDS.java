package org.firstinspires.ftc.teamcode;

import android.os.Environment;
import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import com.sun.tools.javac.util.Context;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Joshua on 10/28/2017.
 */

public class MecanumDS extends DriveSystem {
    public DcMotor FrontLeft = null;
    public DcMotor FrontRight = null;
    public DcMotor BackLeft = null;
    public DcMotor BackRight = null;
    Telemetry tl = null;

    MecanumDS(HardwareMap hwMap, Telemetry telemetry, IMUSystem imuSys, String flLabel, String frLabel, String blLabel, String brLabel) {
        tl = telemetry;
        imu = imuSys;
        FrontLeft = hwMap.get(DcMotor.class, flLabel);
        FrontRight = hwMap.get(DcMotor.class, frLabel);
        BackLeft = hwMap.get(DcMotor.class, blLabel);
        BackRight = hwMap.get(DcMotor.class, brLabel);

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);

        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setMotorPower(double FL, double FR, double BL, double BR) {
        tl.addData("", "FL: %.3f, FR: %.3f, BL: %.3f, BR: %.3f", FL, FR, BL, BR);
        FrontLeft.setPower(-FL);
        FrontRight.setPower(FR);
        BackLeft.setPower(-BL);
        BackRight.setPower(BR);
    }

    public void setEncoders(boolean allow) {
        if (allow) {
            FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
            FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
            BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
            BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        } else {
            FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    static final double MAJOR_FIX_COEFFICENT = 2.5;
//    static final double MINOR_FIX_COEFFICENT = 1;

    public boolean Move(double power, double direction, double spin, double distance, int timeout) {
        //range check
        assert 0 <= power && power <= 1;
        //coordinate convertion
        direction = Math.toRadians(direction);
        double x = power * Math.sin(-direction);
        double y = power * Math.cos(direction);
        //power configuration
        double frontLeftPower = y - x;
        double frontRightPower = y + x;
        double backLeftPower = y + x;
        double backRightPower = y - x;
        //squeeze range to [-1,1]
        double HighValue = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        HighValue = Math.max(HighValue, Math.abs(backLeftPower));
        HighValue = Math.max(HighValue, Math.abs(backRightPower));
        frontLeftPower = Range.clip(frontLeftPower, -1, 1);
        frontRightPower = Range.clip(frontRightPower, -1, 1);
        backLeftPower = Range.clip(backLeftPower, -1, 1);
        backRightPower = Range.clip(backRightPower, -1, 1);
        //record starting time
        long startTime = System.currentTimeMillis();
        //stop robot(if in motion)
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
        //reset heading to zero
        imu.ResetHeading();
        StringBuffer output = new StringBuffer();
        //main loop
        do {
            //do stuff depending on what the power and direction is:
            double heading = imu.GetHeading();
            tl.addLine("current heading " + heading);
            double ratio = heading / 90.0;
            double deltaP = MAJOR_FIX_COEFFICENT * ratio;
            output.append("curr heading " + heading);
            output.append("deltaP " + deltaP);
            tl.addLine("delta p " + deltaP);
            double max = Math.max(Math.abs(frontLeftPower + deltaP), Math.abs(backLeftPower + deltaP));
            max = Math.max(max, Math.max(Math.abs(frontRightPower - deltaP), Math.abs(backRightPower - deltaP)));
            if (max > 1.0) {
                //clip if out of range
                setMotorPower((frontLeftPower + deltaP) / max, (frontRightPower - deltaP) / max, (backLeftPower + deltaP) / max, (backRightPower - deltaP) / max);
            } else {
                setMotorPower(frontLeftPower + deltaP, frontRightPower - deltaP, backLeftPower + deltaP, backRightPower - deltaP);
            }
            tl.update();
        } while (System.currentTimeMillis() - startTime <= distance);
        //stop robot
        saveToFile(output.toString());
        tl.addLine("file outputted");
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
        //timeout detection
//        if(System.currentTimeMillis()-startTime>=timeout){
//            return false;
//        }
        return true;
    }

    final static String fileName = "data.txt";
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/instinctcoder/readwrite/";

    public static boolean saveToFile(String data) {
        try {
            new File(path).mkdir();
            File file = new File(path + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;


    }
}
