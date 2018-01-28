package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by KAEGAN on 1/11/2018.
 */

public class KeithCarriage extends Carriage {

    DcMotor slideMotor = null;
    DcMotor flipMotor = null;
    Servo lServo = null;
    Servo rServo = null;
    Telemetry tl;

    //gripper constants
    static final double holdL = 1.0;
    static final double releaseL = 0.2;
    static final double holdR = 0.7;
    static final double releaseR = 0.1;
    static final boolean LEFTS = true;
    static final boolean RIGHTS = false;

    public KeithCarriage(HardwareMap hwMap, Telemetry telemetry, String SMLabel, String FMLabel, String LSLabel, String RSLabel) {
        tl = telemetry;
        slideMotor = hwMap.get(DcMotor.class, SMLabel);
        flipMotor = hwMap.get(DcMotor.class, FMLabel);
        flipMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        lServo = hwMap.get(Servo.class, LSLabel);
        rServo = hwMap.get(Servo.class, RSLabel);
    }

    //TBD
    public static final int LEFT = 1000;
    public static final int CENTER = 0;
    public static final int RIGHT = -1000;
    public static final double POWER = 0.6;
    private int currState = 0;
    public boolean slideActive = false;
    public int destination;
    //threshold of velocity(ticks/ms)
    public static final double THRESHOLD_VL = 0.8;

    public void powerSlideMotor(double pwr) {
        slideMotor.setPower(pwr);
    }

    public void slideStart(int dest) {
        if (!slideActive) {
            return;
        }
        if (Math.abs(dest - slideMotor.getCurrentPosition()) > 5) {
            slideActive = true;
            destination = dest;
            slideMotor.setPower(Integer.signum(dest - slideMotor.getCurrentPosition()));
        }
    }

    public boolean slideVerify() {
        if (!slideActive) {
            return true;
        }
        if (Math.abs(destination - slideMotor.getCurrentPosition()) <= 5) {
            slideMotor.setPower(0);
            slideActive = false;
            return true;
        }
        return false;
    }

    public void slideTo(int state) {
//        if (currState == -1 && state == LEFT) {
//            slideMotor.setPower(-0.1);
//            sleep(100);
//            slideMotor.setPower(0.0);
//            return;
//        }
//        if (currState == 1 && state == RIGHT) {
//            slideMotor.setPower(0.1);
//            sleep(100);
//            slideMotor.setPower(0.0);
//        }
        if (Math.abs(state - slideMotor.getCurrentPosition()) < 5) {
            tl.addLine(String.format("target:%d, tick:%d", state, slideMotor.getCurrentPosition()));
            tl.addLine("in position");
            tl.update();
            sleep(500);
            return;
        }
        tl.addLine("Carriage GO");
        tl.update();
        slideMotor.setPower(POWER * Integer.signum(state - slideMotor.getCurrentPosition()));
        long lastTime = System.currentTimeMillis();
//        int lastTick = slideMotor.getCurrentPosition();
        while (Math.abs(state - slideMotor.getCurrentPosition()) > 10) {
            //wait until finish
            sleep(5);
            if (System.currentTimeMillis()-lastTime>2000){
                break;
            }
//            double dt = System.currentTimeMillis() - lastTime;
//            double dx = slideMotor.getCurrentPosition() - lastTick;
//            double v = dx / dt;
//            tl.addLine("velocity: " + v);
//            tl.update();
//            if (Math.abs(v) < THRESHOLD_VL) {
//                break;
//            }
//            lastTime = System.currentTimeMillis();
//            lastTick = slideMotor.getCurrentPosition();
        }
        slideMotor.setPower(0.0);
    }

    private boolean inRange(int target, int value) {
        return Math.abs(target - value) < 10;
    }


    public void flipperToggleStart() {
        if (!flipperToggleActive) {
            return;
        }
        flipperToggleActive = true;
        flipMotor.setPower(currentState ? 1.0 : -1.0);
    }

    //return true iff flipper has reached destination or isn't active
    public boolean flipperToggleVerify() {
        if (!flipperToggleActive) {
            return true;
        }
        if ((currentState ? UP : DOWN) - flipMotor.getCurrentPosition() > 5) {
            flipMotor.setPower(0.0);
            currentState = !currentState;
            flipperToggleActive = false;
            return true;
        }
        return false;
    }

    //false: down true:up
    boolean currentState;
    static final int FlipperTimeOut = 1200;
    static final int UP = 450;
    static final int DOWN = 0;
    boolean flipperToggleActive = false;

    //test function
    public void flipperToggle() {

        tl.addLine(String.format("current state: %s", currentState ? "up" : "down"));
        tl.addLine(String.format("goto: %s", currentState ? "down" : "up"));
        tl.update();
        flipMotor.setPower(currentState == false ? 0.75 : -0.125);
        long startTime = System.currentTimeMillis();
        int target = currentState ? DOWN : UP;
        while (Math.abs(target - flipMotor.getCurrentPosition()) > 5) {
            if (!KeithTeleOp.isIsStopped()) {
                //wait until finish
                tl.addLine(String.format("current position: %d", flipMotor.getCurrentPosition()));
                tl.update();
                if (System.currentTimeMillis() - startTime > FlipperTimeOut) {
                    break;
                }
                if (target == DOWN && flipMotor.getCurrentPosition() < DOWN) {
                    break;
                }
                if (target == UP && flipMotor.getCurrentPosition() > UP) {
                    break;
                }
            }else{
                break;
            }
        }
        currentState = !currentState;
        flipMotor.setPower(0.0);
        tl.addLine("finish");
        tl.update();
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void holderToggle(boolean side) {
        if (side) {
            tl.addLine("LEFT " + String.format("to %s", lServo.getPosition() == holdL ? "release" : "hold"));
            tl.update();
            lServo.setPosition(lServo.getPosition() == holdL ? releaseL : holdL);
            sleep(500);
        } else {
            tl.addLine("RIGHT " + String.format("to %s", rServo.getPosition() == holdR ? "release" : "hold"));
            tl.update();
            rServo.setPosition(rServo.getPosition() == holdR ? releaseR : holdR);
            sleep(500);
        }
    }

//	public static final int DIS = 100;


//    public void flipToggle() {
//        flipMotor.setPower(currentState ? 1.0 : -1.0);
//        while ((currentState ? UP : DOWN) - flipMotor.getCurrentPosition() > 5) {
//            //wait until finish
//        }
//        currentState = !currentState;
//        flipMotor.setPower(0);
//    }

//    public void setState(int state) {
//		slideMotor.setPower(Integer.signum(state * DIS - slideMotor.getCurrentPosition()));
//		while (Math.abs(slideMotor.getCurrentPosition() - state * DIS) > 10) {
//			//wait until finish
//		}
//		slideMotor.setPower(0.0);
//	}
}
