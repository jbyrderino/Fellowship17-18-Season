package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Joshua on 1/30/2018.
 */

@Autonomous(name = "NewRed1: Next to relic recovery", group = "Auto")
public class NewRed1 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        KeithRobot keith = new KeithRobot(hardwareMap, telemetry);
        MecanumDS ds = (MecanumDS) (keith.GetDriveSystem());
        KeithElevator ele = keith.GetKeithElevator();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.UNKNOWN;
        KeithJewlKnocker jks = keith.GetJewelKnockerSubsystem();
        jks.setKnockerPosition(0.65);
        jks.setBasePosition(0.0);
        ele.kickerSetPosition(0.7);
        //Note that this color sensor stuff may not be working.
        //However, the rest of the program(s) should be all good.

        //COLOR SENSOR INITIALIZATION
        ColorSensor color_sensor;

        //SCORING POSITION EITHER LEFT (-1), CENTER (0), OR RIGHT (1)
        int cryptoPosition = -1;

        double movePower = 0.1;
        double spinPower = 0.1;

        NewUtilities.VuforiaInitialize();

        waitForStart();

        while (opModeIsActive()) {

            color_sensor = hardwareMap.colorSensor.get("color");
            //  color_sensor.argb(); <--- OVERALL COLOR
            //  color_sensor.red();  <--- RED CHANNEL OF SENSOR

            if (color_sensor.red() > 200) {
                NewUtilities.KnockJewel(jks, true); // <--- Use either vuforia or color sensor code to find which side to knock.
            }else if (color_sensor.blue() > 200) {
                NewUtilities.KnockJewel(jks, false); // <--- Use either vuforia or color sensor code to find which side to knock.
            }else {
                telemetry.addLine("Could not find a jewel color. Do nothing.");
            }

            NewUtilities.ExecuteMovesRed(ds, ele, movePower, spinPower, true, telemetry, cryptoPosition);
        }

    }

}