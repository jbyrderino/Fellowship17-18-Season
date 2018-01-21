package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by KAEGAN on 1/11/2018.
 */

public class KeithCarriage extends Carriage {

<<<<<<< HEAD
    DcMotor slideMotor = null;
    DcMotor flipMotor = null;
    Servo lServo = null;
    Servo rServo = null;
    Telemetry tl;

//    KeithCarriage(DcMotor cM, DcMotor fM, Servo lS, Servo rS) {
//        slideMotor = cM;
//        flipMotor = fM;
//        lServo = lS;
//        rServo = rS;
//    }

//    public void carriageInit() {
//        lServo.scaleRange(0.0, 1.0);
//        lServo.setPosition(0.0);
//
//        rServo.scaleRange(0.0, 1.0);
//        rServo.setPosition(0.0);
//    }

    public KeithCarriage(HardwareMap hwMap, Telemetry telemetry, String SMLabel, String FMLabel, String LSLabel, String RSLabel) {
        tl = telemetry;
        slideMotor = hwMap.get(DcMotor.class, SMLabel);
        flipMotor = hwMap.get(DcMotor.class, FMLabel);
        lServo = hwMap.get(Servo.class, LSLabel);
        rServo = hwMap.get(Servo.class, RSLabel);
    }

    //TBD
    public static final int LEFT = -50;
    public static final int CENTER = 0;
    public static final int RIGHT = 50;
    public boolean slideActive = false;
    public int destination;

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
        slideMotor.setPower(Integer.signum(state - slideMotor.getCurrentPosition()));
        while (Math.abs(state - slideMotor.getCurrentPosition()) > 5) {
            //wait until finish
        }
        slideMotor.setPower(0.0);
    }

    private boolean inRange(int target, int value) {
        return Math.abs(target - value) < 10;
    }

    boolean currentState;
    static final int UP = 30;
    static final int DOWN = 0;
    boolean flipperToggleActive = false;

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

    //test function
    public void flipperToggle() {
        flipMotor.setPower(currentState ? 1.0 : -1.0);
        while ((currentState ? UP : DOWN) - flipMotor.getCurrentPosition() > 5) {
            //wait until finish
        }
        currentState = !currentState;
        flipMotor.setPower(0);
    }

    static final double hold = 0.9;
    static final double release = 0.1;
    static final boolean LEFTS = true;
    static final boolean RIGHTS = false;

    public void holderToggle(boolean side) {
        if (side) {
            lServo.setPosition(lServo.getPosition() == hold ? release : hold);
        } else {
            rServo.setPosition(rServo.getPosition() == hold ? release : hold);
        }
    }


=======
	DcMotor slideMotor = null;
	DcMotor flipMotor = null;
	Servo lServo = null;
	Servo rServo = null;
	Telemetry tl;

	public KeithCarriage(HardwareMap hwMap, Telemetry telemetry, String SMLabel, String FMLabel, String LSLabel, String RSLabel) {
		tl = telemetry;
		slideMotor = hwMap.get(DcMotor.class, SMLabel);
		flipMotor = hwMap.get(DcMotor.class, FMLabel);
		lServo = hwMap.get(Servo.class, LSLabel);
		rServo = hwMap.get(Servo.class, RSLabel);
	}


	public static final int LEFT = -1;
	public static final int CENTER = 0;
	public static final int RIGHT = 1;

	public static final int DIS = 100;


	public void setState(int state) {
		slideMotor.setPower(Integer.signum(state * DIS - slideMotor.getCurrentPosition()));
		while (Math.abs(slideMotor.getCurrentPosition() - state * DIS) > 10) {
			//wait until finish
		}
		slideMotor.setPower(0.0);
	}

	private boolean inRange(int target, int value) {
		return Math.abs(target - value) < 10;
	}

	boolean currentState;

	public void flipToggle() {
		flipMotor.setPower(currentState ? 1.0 : -1.0);
		while ((currentState ? UP : DOWN) - flipMotor.getCurrentPosition() > 5) {
			//wait until finish
		}
		currentState = !currentState;
		flipMotor.setPower(0);
	}

	static boolean holded;
	static final double hold = 0.9;
	static final double release = 0.1;

	public void holderToggle(boolean side) {
		if (side) {
			lServo.setPosition(holded ? release : hold);
		} else {
			lServo.setPosition(holded ? release : hold);
		}
	}

	//TBD
	public boolean slideActive = false;
	public int destination;

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
		slideMotor.setPower(Integer.signum(state - slideMotor.getCurrentPosition()));
		while (Math.abs(state - slideMotor.getCurrentPosition()) > 5) {
			//wait until finish
		}
		slideMotor.setPower(0.0);
	}

	static final int UP = 30;
	static final int DOWN = 0;
	boolean flipperToggleActive = false;

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

	//test function
	public void flipperToggle() {
		flipMotor.setPower(currentState ? 1.0 : -1.0);
		while ((currentState ? UP : DOWN) - flipMotor.getCurrentPosition() > 5) {
			//wait until finish
		}
		currentState = !currentState;
		flipMotor.setPower(0);
	}
>>>>>>> 56eff89e94c81cb9e65e71ede2cbddc963942576
}
