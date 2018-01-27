package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by 28761 on 10/7/2017.
 */

@Autonomous(name = "Red 2: Opposite to relic recovery", group = "Auto")
public class AutoRed2 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        KeithRobot keith = new KeithRobot(hardwareMap, telemetry);;
        MecanumDS ds = (MecanumDS)(keith.GetDriveSystem());
        KeithJewlKnocker jks = keith.GetJewelKnockerSubsystem();
        KeithElevator ele = keith.GetKeithElevator();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.UNKNOWN;
        int jewelColor = Color.TRANSPARENT;

        double movePower = 0.1;
        double spinPower = 0.1;

        // Initialize the Vuforia system
        VuforiaLocalizer vuforia = AutoUtilities.VuforiaInitialize();

        // All is initialized, wait for the start
        waitForStart();

        // call the Vuforia routine to tell us what pictogram we deal with
        // and what color is the jewel that is closer to the pictogram
        AutoUtilities.VuforiaInfo vuforiaInfo = AutoUtilities.GetVuforiaInfo (vuforia, telemetry);
        if (vuforiaInfo != null) {
            vuMark = vuforiaInfo.vuMark;
            jewelColor = vuforiaInfo.jewelColor;
        }

        //ADD CARRIAGE CODE!

        // At this point we should have a pictogram and a color for the
        // jewel that is close to the pictogram, i.e. on the left side of
        // the jewel knocker. We will now make a decision about which jewel
        // to knock off using the jewel knocker.
        // since we are in the Red zone, we'll try to remove the Blue jewel
        if (jewelColor == Color.BLUE) {
            telemetry.addData("", "Left side jewel is BLUE");
            AutoUtilities.KnockJewel(jks, true);
        } else if (jewelColor == Color.RED) {
            telemetry.addData("", "Left side jewel is RED");
            AutoUtilities.KnockJewel(jks, false);
        } else {
            telemetry.addData("", "Could not find jewel color.");
        }

        // irrespective of whether we knocked the jewel or not, we are now
        // going to navigate towards the glyph box. We are in the Red zone
        // and we are opposite to the relic recovery area.
        AutoUtilities.ExecuteMoves(ds, movePower, spinPower, Color.RED, false, telemetry);

        // At this point we should be in front of the glyph box. Let's unload
        // the block we have based on the cypher that we read
        if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
            // we were not able to read the cypher, don't do anything
            telemetry.addData("", "Could not read the cypher, nothing to do.");
        } else {
            // We now know the column into which we are supposed to score our glyph
            // The glyph is at the top of the elevator at this point. Depending on
            // the column from vuMark, we need to move the carriage into the position
            // to receive the glyph, kick the glyph, then move the carriage into the
            // position so that the glyph is in front of the right column, and then
            // finally unload the glyph.
            telemetry.addData("", "Cypher column: %s", vuMark);
            // TODO - implement this
        }

        //ADD CARRIAGE CODE!

        // make all the telemetry messages appear
        telemetry.update();

        // we are done. Wait here for the time to run out, it'll give a chance to
        // the operator to read the telemetry results.
        while (opModeIsActive());
    }
}
