package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

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
    static long RAMP_DOWN_TIME = 2000;
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

    boolean CalibrateSpin () {
        double currentPower = 0;
        double rotationSpeed = 0;
        long UPDATE_INTERVAL = 100;
        double ROTATION_SPEED_TARGET = 1.0f;
        double POWER_INCREMENT = 0.005f;
        long currentTime = System.currentTimeMillis();
        double originalHeading = imu.GetNormalizedHeading();
        long originalTime = currentTime;

        while (true) {
            long loopTime = System.currentTimeMillis();
            long timeDiff = loopTime - currentTime;
            if (timeDiff >= UPDATE_INTERVAL) {
                double currentHeading = imu.GetNormalizedHeading();
                rotationSpeed = Math.abs(currentHeading - originalHeading) * 1000 / (loopTime - originalTime);
                if (rotationSpeed >= ROTATION_SPEED_TARGET) {
                    tl.addData("", "MCP: %.3f, MRS: %.3f", currentPower, rotationSpeed);
                    tl.update();
                    while (System.currentTimeMillis() - currentTime < 1000) {};
                    minPowerSpin = currentPower;
                    break;
                }
                currentPower += POWER_INCREMENT;
                currentTime = loopTime;
            }
            FrontLeft.setPower(currentPower);
            FrontRight.setPower(currentPower);
            BackLeft.setPower(currentPower);
            BackRight.setPower(currentPower);
            tl.addData("", "CP: %.3f, RS: %.3f", currentPower, rotationSpeed);
            tl.update();
        }
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
        return true;
    }

    boolean CalibrateForward() {
        double currentPower = 0;
        double forwardSpeed = 0;
        long UPDATE_INTERVAL = 100;
        double FORWARD_SPEED_TARGET = 60f;
        double POWER_INCREMENT = 0.005f;
        long currentTime = System.currentTimeMillis();
        double originalEncoderValue = FrontLeft.getCurrentPosition();
        long originalTime = currentTime;

        while (true) {
            long loopTime = System.currentTimeMillis();
            long timeDiff = loopTime - currentTime;
            if (timeDiff >= UPDATE_INTERVAL) {
                double currentEncoderValue = FrontLeft.getCurrentPosition();
                forwardSpeed = Math.abs(currentEncoderValue - originalEncoderValue) * 1000 / (loopTime - originalTime);
                if (forwardSpeed >= FORWARD_SPEED_TARGET) {
                    tl.addData("", "MCP: %.3f, MFS: %.3f", currentPower, forwardSpeed);
                    tl.update();
                    while (System.currentTimeMillis() - currentTime < 1000) {};
                    minPowerForward = currentPower;
                    break;
                }
                currentPower += POWER_INCREMENT;
                currentTime = loopTime;
            }
            FrontLeft.setPower(-currentPower);
            FrontRight.setPower(currentPower);
            BackLeft.setPower(-currentPower);
            BackRight.setPower(currentPower);
            tl.addData("", "CP: %.3f, FS: %.3f", currentPower, forwardSpeed);
            tl.update();
        }
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
        return true;
    }

    boolean CalibrateSide() {
        double currentPower = 0;
        double sideSpeed = 0;
        long UPDATE_INTERVAL = 100;
        double SIDE_SPEED_TARGET = 60f;
        double POWER_INCREMENT = 0.005f;
        long currentTime = System.currentTimeMillis();
        double originalEncoderValue = FrontLeft.getCurrentPosition();
        long originalTime = currentTime;

        while (true) {
            long loopTime = System.currentTimeMillis();
            long timeDiff = loopTime - currentTime;
            if (timeDiff >= UPDATE_INTERVAL) {
                double currentEncoderValue = FrontLeft.getCurrentPosition();
                sideSpeed = Math.abs(currentEncoderValue - originalEncoderValue) * 1000 / (loopTime - originalTime);
                if (sideSpeed >= SIDE_SPEED_TARGET) {
                    tl.addData("", "MCP: %.3f, MFS: %.3f", currentPower, sideSpeed);
                    tl.update();
                    while (System.currentTimeMillis() - currentTime < 1000) {};
                    minPowerSide = currentPower;
                    break;
                }
                currentPower += POWER_INCREMENT;
                currentTime = loopTime;
            }
            FrontLeft.setPower(-currentPower);
            FrontRight.setPower(-currentPower);
            BackLeft.setPower(currentPower);
            BackRight.setPower(currentPower);
            tl.addData("", "CP: %.3f, FS: %.3f", currentPower, sideSpeed);
            tl.update();
        }
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
        return true;
    }

    public boolean Calibrate () {
        setEncoders(true);
        boolean resultSpin = CalibrateSpin();
        boolean resultForward = CalibrateForward();
        boolean resultSide = CalibrateSide();
        return resultSpin && resultForward && resultSide;
    }

    static final double FIX_COEFFICENT = 2.0;

    double Calculate_S_Curve (double startPower, double endPower, double startValue, double endValue, double currentValue) {
        double valueRatio = Math.abs(endValue - startValue) / 12; // domain is between -6 and +6
        double powerRatio = Math.abs(endPower - startPower) / 1; // range is between 0 and 1
        double x = Math.abs(currentValue - startValue) / valueRatio - 6;
        double y = Math.pow(Math.E, x) / (Math.pow(Math.E, x) + 1);
        double result = 0;
        if (startPower > endPower) {
            result = startPower - y * powerRatio;
        } else {
            result = startPower + y * powerRatio;
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
        FrontLeft.getCurrentPosition();

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

        while (System.currentTimeMillis() - originalTime < (distance - tolerance)) {
            long diffTime = System.currentTimeMillis() - originalTime;
            double modifier = 1;
            if (diffTime <= RAMP_UP_TIME) {
                //apply the "s" curve on the power based on time for acceleration
                modifier = Calculate_S_Curve(0, 1, 0, RAMP_UP_TIME, diffTime);
            }

            if ((distance - diffTime) <= RAMP_DOWN_TIME) {
                //apply the "s" curve on the power based on the remaining distance for deceleration
                modifier = Calculate_S_Curve(1, 0, RAMP_DOWN_TIME, 0, distance - diffTime);
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

    public boolean Move1(double power, double direction, double spin, double distance, int timeout) {
        //range check
        assert 0 <= power && power <= 1;
        //spin check
        assert -180 <= spin && spin <= 180;

        // get the encoder values
        double flCurrentPos = FrontLeft.getCurrentPosition();
        double frCurrentPos = FrontRight.getCurrentPosition();
        double blCurrentPos = BackLeft.getCurrentPosition();
        double brCurrentPos = BackRight.getCurrentPosition();

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
        //main loop
        while (System.currentTimeMillis() - startTime < distance) {
            //do stuff depending on what the power and direction is:
            double heading = imu.GetHeading();
            tl.addLine("current heading " + heading);
            double ratio = heading / 90.0;
            double deltaP = FIX_COEFFICENT * ratio;
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
        };
        //stop robot
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
        //timeout detection
//        if(System.currentTimeMillis()-startTime>=timeout){
//            return false;
//        }
        /*
        double currentHeading = imu.GetHeading();

        double spinPower = power;
        double minPower = 0.01;//minPowerSpin;
        double diffThreshold = (power - minPower) * 2 * 100;
        while (currentHeading > (spin+0.1) || currentHeading < (spin-0.1)) {
            currentHeading = imu.GetHeading();
            double diff = Math.abs(currentHeading - spin);
            if (diff <= diffThreshold) {
                // we will slow down
                spinPower = minPower + diff * (power - minPower) / diffThreshold;
            }
            tl.addLine("spin power" + spinPower);
            tl.addLine("current heading " + currentHeading);
            tl.update();

            if(currentHeading > spin) {
                FrontLeft.setPower(-spinPower);
                FrontRight.setPower(-spinPower);
                BackLeft.setPower(-spinPower);
                BackRight.setPower(-spinPower);
            }
            if(currentHeading < spin) {
                FrontLeft.setPower(spinPower);
                FrontRight.setPower(spinPower);
                BackLeft.setPower(spinPower);
                BackRight.setPower(spinPower);
            }
        }
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
        */

        setEncoders(true);
        ExecuteSpin(spin, power, 0.03, 0.1);
        tl.addData("", "Heading after loop: %.2f", imu.GetHeading());

        startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime <= 1000) {};

        tl.addData("", "Current heading: %.2f", imu.GetHeading());
        tl.update();

        return true;

    }

}
