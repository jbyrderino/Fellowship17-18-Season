package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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

    static long RAMP_UP_TIME = 1000;
    static long RAMP_DOWN_DISTANCE = 1000;
    static double SPIN_SLOWDOWN_THRESHOLD = 180.0f;

    double minPowerSpin = 0;
    double minPowerForward = 0;
    double minPowerSide = 0;
    Telemetry tl = null;

    MecanumDS(HardwareMap hwMap, Telemetry telemetry, IMUSystem imuSys, String flLabel, String frLabel, String blLabel, String brLabel) {
        tl = telemetry;
        imu = imuSys;
        FrontLeft = hwMap.get(DcMotor.class, flLabel);
        FrontRight = hwMap.get(DcMotor.class, frLabel);
        BackLeft = hwMap.get(DcMotor.class, blLabel);
        BackRight = hwMap.get(DcMotor.class, brLabel);

        //Left motors on Keith robot are mounted backwards.
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        setMotorPower(0, 0, 0, 0);

        setEncoders(true);
    }

    public void setMotorPower(double FL, double FR, double BL, double BR) {
        tl.addData("", "FL: %.3f, FR: %.3f, BL: %.3f, BR: %.3f", FL, FR, BL, BR);
        FrontLeft.setPower(FL);
        FrontRight.setPower(FR);
        BackLeft.setPower(BL);
        BackRight.setPower(BR);
    }

    public void setEncoders(boolean allow) {
        if (allow) {
            FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public double GetAverageEncodersValue () {
        double flAbs = Math.abs(FrontLeft.getCurrentPosition());
        double frAbs = Math.abs(FrontRight.getCurrentPosition());
        double blAbs = Math.abs(BackLeft.getCurrentPosition());
        double brAbs = Math.abs(BackRight.getCurrentPosition());

        return (flAbs + frAbs + blAbs + brAbs) / 4;
    }

    public void ResetEncoders () {
        DcMotor.RunMode flMode=FrontLeft.getMode();
        DcMotor.RunMode frMode=FrontRight.getMode();
        DcMotor.RunMode blMode=BackLeft.getMode();
        DcMotor.RunMode brMode=BackRight.getMode();
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(flMode);
        FrontRight.setMode(frMode);
        BackLeft.setMode(blMode);
        BackRight.setMode(brMode);
    }

    static final double FIX_COEFFICENT = 2.0;

    double Calculate_S_Curve (double startReturnValue, double endReturnValue, double startValue, double endValue, double currentValue) {
        double valueRatio = Math.abs(endValue - startValue) / 12; // domain is between -6 and +6
        double powerRatio = Math.abs(endReturnValue - startReturnValue) / 1; // range is between 0 and 1
        double x = Math.abs(currentValue - startValue) / valueRatio - 6;
        double y = Math.pow(Math.E, x) / (Math.pow(Math.E, x) + 1);
        double result = 0;
        if (startReturnValue > endReturnValue) {
            result = startReturnValue - y * powerRatio;
        } else {
            result = startReturnValue + y * powerRatio;
        }
        return result;
    }

    boolean ExecuteSpin (double headingGoal, double maxPower, double minPower, double tolerance) {
        double currentHeading = imu.GetHeading();
        long originalTime = System.currentTimeMillis();
        double targetPower = minPower + Math.abs(headingGoal - currentHeading) / SPIN_SLOWDOWN_THRESHOLD;
        if (targetPower > maxPower) {
            targetPower = maxPower;
        }
        double slowDownDegrees = SPIN_SLOWDOWN_THRESHOLD * (targetPower - minPower);
        while (currentHeading > (headingGoal + tolerance) || currentHeading < (headingGoal - tolerance)) {

            double currentPower = targetPower;
            long diffTime = System.currentTimeMillis() - originalTime;
            if (diffTime <= RAMP_UP_TIME) {
                //apply the "s" curve on the power based on time for acceleration
                currentPower = Calculate_S_Curve(0, currentPower, 0, RAMP_UP_TIME, diffTime);
            }

            currentHeading = imu.GetHeading();
            if (Math.abs(headingGoal - currentHeading) <= slowDownDegrees) {
                //apply the "s" curve on the power based on the remaining number of degrees for deceleration
                currentPower = Calculate_S_Curve(currentPower, minPower, slowDownDegrees, 0, Math.abs(headingGoal - currentHeading));
            }

            tl.addData("", "CP: %.3f, CH: %.3f", currentPower, currentHeading);
            tl.update();

            if(currentHeading > headingGoal) {
                setMotorPower(currentPower, -currentPower, currentPower, -currentPower);
            }
            if(currentHeading < headingGoal) {
                setMotorPower(-currentPower, currentPower, -currentPower, currentPower);
            }
        }
        setMotorPower(0, 0, 0, 0);
        return true;
    }

    boolean ExecuteMove (double direction, double distance, double maxPower, double minPower, double tolerance) {

        //coordinate conversion
        double directionR = Math.toRadians(direction);
        double x = maxPower * Math.sin(-directionR);
        double y = maxPower * Math.cos(directionR);

        //power configuration
        double frontLeftPower = y - x;
        double frontRightPower = y + x;
        double backLeftPower = y + x;
        double backRightPower = y - x;

        //normalize range to [-1,1]
        double highValue = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        highValue = Math.max(highValue, Math.abs(backLeftPower));
        highValue = Math.max(highValue, Math.abs(backRightPower));
        frontLeftPower = frontLeftPower / highValue * maxPower;
        frontRightPower = frontRightPower / highValue * maxPower;
        backLeftPower = backLeftPower / highValue * maxPower;
        backRightPower = backRightPower / highValue * maxPower;

        //record starting time
        long originalTime = System.currentTimeMillis();

        ResetEncoders();
        double originalEncoderValue = GetAverageEncodersValue();

        while(true) {
            double diffEncoderValue = GetAverageEncodersValue() - originalEncoderValue;
            if (diffEncoderValue >= (distance - tolerance)) {
                break;
            }
            long diffTime = System.currentTimeMillis() - originalTime;
            double modifier = 1;
            if (diffTime <= RAMP_UP_TIME) {
                //apply the "s" curve on the power based on time for acceleration
                modifier = Calculate_S_Curve(0, modifier, 0, RAMP_UP_TIME, diffTime);
            }

            if ((distance - diffEncoderValue) <= RAMP_DOWN_DISTANCE) {
                //apply the "s" curve on the power based on the remaining distance for deceleration
                modifier = Calculate_S_Curve(modifier, 0, RAMP_DOWN_DISTANCE, 0, distance - diffEncoderValue);
            }
            setMotorPower(frontLeftPower * modifier, frontRightPower * modifier, backLeftPower * modifier, backRightPower * modifier);
        }
        return true;
    }

    public boolean Move(double power, double direction, double spin, double distance, int timeout) {
        //power range check
        assert 0 <= power && power <= 1;
        //spin check
        assert -180 <= spin && spin <= 180;
        //distance check
        assert distance >= 0;

        //make sure encoders are on
        setEncoders(true);
        //reset heading to zero
        imu.ResetHeading();

        if (distance > 0) {
            ExecuteMove(direction, distance, power, 0.03, 50);
        }
        if (spin != 0) {
            ExecuteSpin(spin, power, 0.03, 0.1);
            tl.addData("", "Heading after loop: %.2f", imu.GetHeading());
        }

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime <= 1000) {};

        tl.addData("", "Current heading: %.2f", imu.GetHeading());
        tl.update();

        return true;
    }
}
