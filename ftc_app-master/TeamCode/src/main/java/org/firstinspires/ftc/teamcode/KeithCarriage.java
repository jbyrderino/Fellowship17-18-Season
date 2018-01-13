package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by KAEGAN on 1/11/2018.
 */

public class KeithCarriage extends Carriage {

    private DcMotor CarriageMotor = null;
    private DcMotor FlipMotor = null;
    private Servo lServo = null;
    private Servo rServo = null;

    private boolean whichservo;

    KeithCarriage (DcMotor cM, DcMotor fM, Servo lS, Servo rS){
        CarriageMotor = cM; FlipMotor = fM; lServo = lS; rServo = rS;
    }

    public void carriageInit(){
        lServo.scaleRange(0.0, 1.0);
        lServo.setPosition(0.0);

        rServo.scaleRange(0.0, 1.0);
        rServo.setPosition(0.0);
    }

    public void carriageStart(double Pwr){
        CarriageMotor.setPower(Pwr);
    }

    public void carriageStop(){
        CarriageMotor.setPower(0.0);
    }

    public void flipClass(double Dir){
        FlipMotor.setPower(Dir);
    }

    public void blockEjector(double Pos){
        if(whichservo == true){
            lServo.setPosition(Pos);
        }

        if(whichservo == false){
           rServo.setPosition(Pos);
        }
    }

}
