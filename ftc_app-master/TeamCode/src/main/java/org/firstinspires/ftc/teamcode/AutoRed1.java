package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.WeakHashMap;

/**
 * Created by 28761 on 10/7/2017.
 */

@Autonomous(name = "Red 1: Next to relic recovery", group = "Auto")
public class AutoRed1 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
            KeithRobot keith = new KeithRobot(hardwareMap, telemetry);
            MecanumDS ds = (MecanumDS) (keith.GetDriveSystem());
            KeithElevator ele = keith.GetKeithElevator();
            KeithCarriage car = keith.GetKeithCarriage();
            JewlDetect jwld = keith.GetKeithJewlDetect();
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.UNKNOWN;
            ele.kickerSetPosition(0.7);
            KeithJewlKnocker jks = keith.GetJewelKnockerSubsystem();
            jks.setKnockerPosition(0.65);
            jks.setBasePosition(0.0);

            double movePower = 0.1;
            double spinPower = 0.1;

            boolean isBlue;

            jwld.JewlDetectForInit(telemetry, hardwareMap);
            telemetry.update();

            // All is initialized, wait for the start
            waitForStart();

            while (opModeIsActive()) {

                AutoUtilities.CarriageGrip(car, opModeIsActive());

                // At this point we should have a pictogram and a color for the
                // jewel that is close to the pictogram, i.e. on the left side of
                // the jewel knocker. We will now make a decision about which jewel
                // to knock off using the jewel knocker.
                // since we are in the Red zone, we'll try to remove the Blue jewel

                isBlue = jwld.JewlColor();
                telemetry.addData("", jwld.JewlColor());
                telemetry.update();

                if (isBlue == true) {
                    telemetry.addData("", "Left side jewel is BLUE");
                    AutoUtilities.KnockJewel(jks, true, opModeIsActive());
                } else if (isBlue == false) {
                    telemetry.addData("", "Left side jewel is RED");
                    AutoUtilities.KnockJewel(jks, false, opModeIsActive());
                } else {
                    telemetry.addData("", "Could not find jewel color.");
                }
                telemetry.update();
                // irrespective of whether we knocked the jewel or not, we are now
                // going to navigate towards the glyph box. We are in the Red zone
                // and we are close to the relic recovery area.
                AutoUtilities.ExecuteMovesRed(ds, movePower, spinPower, Color.RED, true, telemetry, opModeIsActive());

                // At this point we should be in front of the glyph box. Let's unload
                // the block we have based on the cypher that we read
                if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
                    // we were not able to read the cypher, don't do anything
                    telemetry.addData("", "Could not read the cypher, nothing to do.");
                } else {
                    telemetry.addData("", "Cypher column: %s", vuMark);
                    // TODO - implement this
                }

                AutoUtilities.CarriageFlip(car, opModeIsActive());

                ds.Move(0.1, 0, 0, 200, 1000);
                sleep(1000);
                ds.Move(0.1, 180, 0, 500, 1000);

                // make all the telemetry messages appear
                telemetry.update();

                //Stop
                stop();
            }
        }
    }

