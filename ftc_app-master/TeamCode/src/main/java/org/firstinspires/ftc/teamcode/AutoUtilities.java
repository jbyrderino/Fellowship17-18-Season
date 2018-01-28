package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

import com.disnodeteam.dogecv.DogeCV;
import com.vuforia.HINT;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vec2F;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Joshua on 10/28/2017.
 */

public abstract class AutoUtilities {


    static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static public void CarriageGrip (KeithCarriage carriage) {
        carriage.slideTo(KeithCarriage.CENTER);
        sleep(500);
        carriage.holderToggle(KeithCarriage.RIGHTS);
    }

    static public void KnockJewel (KeithJewlKnocker jewlKnocker, boolean leftSide) {
        jewlKnocker.baseKnockerRotateRight();
        jewlKnocker.knockerDown();
        if (leftSide) {
            jewlKnocker.knockRight();
        } else {
            jewlKnocker.knockLeft();
        }
        jewlKnocker.knockerUp();
        jewlKnocker.baseKnockerRotateLeft();
    }

    static public boolean ExecuteMoves (MecanumDS ds, double movePower, double spinPower, int colorPosition, boolean nextToRelicRecovery, Telemetry telemetry) {
        if (colorPosition == Color.BLUE) {
            if (nextToRelicRecovery) {
                telemetry.addData("", "Moves: BLUE, next to RelicRecovery");
                // For our start position, we are facing away from the glyph box area,
                // so we need to move back, then turn left 90 degrees, then move
                // back again a little bit.

                // Move back
                if (!ds.Move (movePower, 180, 0, 1300, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Backward move timed out, abandoning.");
                    return false;
                }

                // Turn left 90 degrees
                if (!ds.Move (spinPower, 0, 90, 0, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Left turn timed out, abandoning.");
                    return false;
                }

                // Move back
                if (!ds.Move (movePower, 180, 0, 310, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Backward move timed out, abandoning.");
                    return false;
                }
            } else {
                telemetry.addData("", "Moves: BLUE, away from RelicRecovery");
                // For our start position, we are facing away from the glyph box area so we
                // need to move back, then turn left 90 degrees, then move forward a bit more,
                // turn right 90 degrees and finally move back a little bit.

                // Move back
                if (!ds.Move (movePower, 180, 0, 1000, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Backward move timed out, abandoning.");
                    return false;
                }

                // Turn left 90 degrees
                if (!ds.Move (spinPower, 0, 90, 0, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Left turn timed out, abandoning.");
                    return false;
                }

                // Move forward
                if (!ds.Move (movePower, 0, 0, 440, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Forward move timed out, abandoning.");
                    return false;
                }

                // Turn right 90 degrees
                if (!ds.Move (spinPower, 0, -90, 0, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Right turn timed out, abandoning.");
                    return false;
                }

                // Move back
                if (!ds.Move (movePower, 180, 0, 180, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Backward move timed out, abandoning.");
                    return false;
                }
            }
        } else if (colorPosition == Color.RED) {
            if (nextToRelicRecovery) {
                telemetry.addData("", "Moves: RED, next to RelicRecovery");
                // For our start position, we are facing the glyph box area, so we
                // need to move forward, then turn left 90 degrees, then move
                // back a little bit.

                // Move forward
                if (!ds.Move (movePower, 0, 0, 1300, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Forward move timed out, abandoning.");
                    return false;
                }

                // Turn left 90 degrees
                if (!ds.Move (spinPower, 0, 90, 0, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Left turn timed out, abandoning.");
                    return false;
                }

                // Move back
                if (!ds.Move (movePower, 180, 0, 340, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Backward move timed out, abandoning.");
                    return false;
                }
            } else {
                telemetry.addData("", "Moves: RED, away from RelicRecovery");
                // For our start position, we are facing the glyph box area, so we
                // need to move forward, then turn left 90 degrees, then move forward
                // a bit more, turn left 90 degrees again and finally move back a little bit.

                // Move forward
                if (!ds.Move (movePower, 0, 0, 1000, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Forward move timed out, abandoning.");
                    return false;
                }

                // Turn left 90 degrees
                if (!ds.Move (spinPower, 0, 90, 0, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Left turn timed out, abandoning.");
                    return false;
                }

                // Move forward
                if (!ds.Move (movePower, 0, 0, 450, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Forward move timed out, abandoning.");
                    return false;
                }

                // Turn left 90 degrees
                if (!ds.Move (spinPower, 0, 90, 0, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position anymore
                    telemetry.addData("", "Left turn timed out, abandoning.");
                    return false;
                }

                // Move back
                if (!ds.Move (movePower, 180, 0, 240, 5000)) {
                    // we seem to have timed out. Let's not continue this anymore
                    // it's not safe to try to go further as we don't really know
                    // our current position
                    telemetry.addData("", "Backward move timed out, abandoning.");
                    return false;
                }
            }
        } else {
            telemetry.addData("", "Moves: unknown position");
            return false;
        }
        return true;
    }

    static public void CarriageFlip (KeithCarriage carriage) {
        carriage.slideTo(KeithCarriage.RIGHT);
        sleep(2000);
        carriage.flipperToggle();
        sleep(2000);
        carriage.holderToggle(KeithCarriage.RIGHTS);
        sleep(2000);
        carriage.flipperToggle();
        sleep(2000);
        carriage.slideTo(KeithCarriage.CENTER);
    }
}
