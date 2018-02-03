package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Joshua on 1/30/2018.
 */

@Autonomous(name = "NewRed1: Next to relic recovery", group = "Auto")
public class NewRed1 extends LinearOpMode {

    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {
        KeithRobot keith = new KeithRobot(hardwareMap, telemetry);
        MecanumDS ds = (MecanumDS) (keith.GetDriveSystem());
        KeithElevator ele = keith.GetKeithElevator();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.UNKNOWN;
        KeithJewlKnocker jks = keith.GetJewelKnockerSubsystem();
        NewUtilities utility = new NewUtilities(this);
        jks.setKnockerPosition(0.95);
        jks.setBasePosition(0.2);
        ele.kickerSetPosition(0.7);
<<<<<<< HEAD
=======
        //Note that this color sensor stuff may not be working.
        //However, the rest of the program(s) should be all good.

        //COLOR SENSOR INITIALIZATION
//        ColorSensor color_sensor = hardwareMap.colorSensor.get("color");
>>>>>>> 4312fc7d9208dc016220572b1b0e7cbadda9680c

        //SCORING POSITION EITHER LEFT (-1), CENTER (0), OR RIGHT (1)
        int cryptoPosition = -1;

        double movePower = 0.1;
        double spinPower = 0.1;



        waitForStart();

        //  color_sensor.argb(); <--- OVERALL COLOR
        //  color_sensor.red();  <--- RED CHANNEL OF SENSOR

<<<<<<< HEAD
            NewUtilities.KnockJewel(jks, true);
=======
        if (!opModeIsActive()){
            //abort due to turning off OpMode
            return;
        }
>>>>>>> 4312fc7d9208dc016220572b1b0e7cbadda9680c

        utility.KnockJewel(jks, true);
//        if (color_sensor.red() > 200) {
//            NewUtilities.KnockJewel(jks, true); // <--- Use either vuforia or color sensor code to find which side to knock.
//        }else if (color_sensor.blue() > 200) {
//            NewUtilities.KnockJewel(jks, false); // <--- Use either vuforia or color sensor code to find which side to knock.
//        }else {
//            telemetry.addLine("Could not find a jewel color. Do nothing.");
//        }

        if (!opModeIsActive()){
            //abort due to turning off OpMode
            return;
        }

        utility.ExecuteMovesRed(ds, ele, movePower, spinPower, true, telemetry, cryptoPosition);

//        stop();

//        while (opModeIsActive()) {
//
//        }

    }

}