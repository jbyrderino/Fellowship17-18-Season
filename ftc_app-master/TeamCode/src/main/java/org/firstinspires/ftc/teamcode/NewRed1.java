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

        //SCORING POSITION EITHER LEFT (-1), CENTER (0), OR RIGHT (1)
        int cryptoPosition = -1;

        double movePower = 0.1;
        double spinPower = 0.1;

        NewUtilities.VuforiaInitialize();

        waitForStart();

        while (opModeIsActive()) {

            NewUtilities.KnockJewel(jks, true);

            NewUtilities.ExecuteMovesRed(ds, ele, movePower, spinPower, true, telemetry, cryptoPosition);
        }

    }

}