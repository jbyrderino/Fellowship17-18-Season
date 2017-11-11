package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Joshua on 10/28/2017.
 */

public class MecanumDS extends DriveSystem {
    public DcMotor FrontLeft = null;
    public DcMotor FrontRight = null;
    public DcMotor BackLeft = null;
    public DcMotor BackRight = null;
    Telemetry tl= null;

    MecanumDS (HardwareMap hwMap, Telemetry telemetry, String flLabel, String frLabel, String blLabel, String brLabel) {
        tl = telemetry;

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

    public void setMotorPower(double FL, double FR, double BL, double BR){
        tl.addData("","FL: %.3f, FR: %.3f, BL: %.3f, BR: %.3f", FL, FR, BL, BR);
        FrontLeft.setPower(-FL);
        FrontRight.setPower(FR);
        BackLeft.setPower(-BL);
        BackRight.setPower(BR);
    }

    public void setEncoders (boolean allow)
    {
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

    public boolean Move (double power, double direction, double spin, double distance, int timeout){
        long startTime = System.currentTimeMillis();
        do {
            //do stuff depending on what the power and direction is:
            FrontLeft.setPower(0);
            FrontRight.setPower(0);
            BackLeft.setPower(0);
            BackRight.setPower(0);

        } while (System.currentTimeMillis() - startTime <= distance);

        return true;
    }
}
