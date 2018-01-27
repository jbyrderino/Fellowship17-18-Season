package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

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

    static public class VuforiaInfo {
        public RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.UNKNOWN;
        public int jewelColor = Color.TRANSPARENT;
        VuforiaInfo (RelicRecoveryVuMark dataMark, int dataColor) {
            vuMark = dataMark;
            jewelColor = dataColor;
        }
    }

    static int getR(int rgb) {
        return (rgb & 0xf800) >> 11;
    }

    static int getG(int rgb) {
        return (rgb & 0x7e0) >> 5;
    }

    static int getB(int rgb) {
        return (rgb & 0x1f);
    }

    static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static VuforiaLocalizer VuforiaInitialize() {
        //do all the initialization needed
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        //set camera direction
        params.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        //set license key
        params.vuforiaLicenseKey = "AZngjFj/////AAAAGXl4r+gz20bgi78ZEfdDoSM3BRzoPWF85Z/GS524liytojbME/4mrMSgTIJrEsW1IxxgIy6Po9DKP08uYMrcCpsVG1gd800G3RIRQ0KNtQnC7onvphQ2RBZ+3JXkfdYLct13YRM1TzbJLWaS4Lz5bSMMRpSTJU8zSwzAZ1fIdqwXBevZZMkd+LKtIogK+wl1fBo/SaDcrrSW/BIePFCbk1bBG1eaAetcLjEUngrGYBtmD+PdYbefaBFwuzV+eQDU0E671GNILzDhirYTAcFfe/+F2WK9VgAVZfycin4Iv06GyebuSfTiIsE65jhoXY9FQy3ZWnwZGHcID0e/KRG/+CYdk9A+ltYPi7qfrMh/lk5/";
        //set camera feedback just for fun
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        //initiate vuforia
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        //set maximum amount of objects vuforia can trace
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);


        return vuforia;
    }

    static public VuforiaInfo GetVuforiaInfo (VuforiaLocalizer vuforia, Telemetry telemetry) throws InterruptedException {

        //get image form database, vumark type
        VuforiaTrackables pictograms = vuforia.loadTrackablesFromAsset("RelicVuMark");
        //get the template
        VuforiaTrackable relicTemplate = pictograms.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        //set name for image/object
        pictograms.get(0).setName("pictogram");
        //enables RGB888 format for the image
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
        //only 1 frame at a time
        vuforia.setFrameQueueCapacity(1);
        //vuforia starts running
        pictograms.activate();

        //read the pictogram found, try for 5 seconds
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.UNKNOWN;
        int jewelColor = Color.TRANSPARENT;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 5000) {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
            //vuMark != RelicRecoveryVuMark.UNKNOWN || vuMark == RelicRecoveryVuMark.UNKNOWN
            if (true == true) {
                telemetry.addData("", "Found pictogram: %s", vuMark);
                // we found the pictogram, let's try to find the jewel color now
                OpenGLMatrix rawPose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getRawPose();
                VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take(); //takes the frame at the head of the queue
                Image rgb = null;
                long numImages = frame.getNumImages();

                for (int i = 0; i < numImages; i++) {
                    if (frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565) {
                        rgb = frame.getImage(i);
                        break;
                    }
                }
                Bitmap bm = null;

                if (rgb != null) {
                    bm = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
                    bm.copyPixelsFromBuffer(rgb.getPixels());

                    Vec2F location = new Vec2F(100, 150);
                    int ox = (int) location.getData()[0];
                    int oy = (int) location.getData()[1];
                    int RSum = 0;
                    int GSum = 0;
                    int BSum = 0;
                    int count = 0;
                    String rColor = null;
                    for (int i = ox; i < ox + 3; i++) {
                        for (int j = oy; j > oy - 3; j--) {
                            if (0 < i && i < bm.getWidth() && 0 < j && j < bm.getHeight()) {
                                count++;
                                int color = bm.getPixel(i, j);
                                RSum += getR(color);
                                GSum += getG(color);
                                BSum += getB(color);

                            }
                        }
                    }
                    if (count != 0) {
                        RSum /= count;
                        GSum /= count;
                        BSum /= count;
                    }
                    telemetry.addData("Color ", " Red: " + RSum + " Green: " + GSum + " Blue: " + BSum);

                    // we consider it red if RSum is 10% higher than BSum
                    int totalColors = RSum + BSum;
                    double RPerc = (totalColors > 0) ? RSum * 100 / totalColors : 0;
                    double BPerc = (totalColors > 0) ? BSum * 100 / totalColors : 0;
                    if (RSum > (BSum + 10)) {
                        jewelColor = Color.RED;
                    } else if (BSum > (RSum + 10)) {
                        jewelColor = Color.BLUE;
                    } else {
                        telemetry.addData("", "Color undecided");
                    }
                } else {
                    telemetry.addData("RGB", "is null");
                }
/*
                if ((RSum > 200 && GSum < 50 && BSum < 50) | (RSum > GSum + 100 && RSum > BSum +100)){
                    rColor = "Red";
                } else if ((RSum < 70 && GSum > 70 && BSum > 170) | (BSum > RSum + 100 && BSum > GSum + 50)){
                    rColor = "Blue";
                } else {
                    rColor = "Other";
                }

                telemetry.addData("Color:", rColor);
*/
                break;
            }
        }
        return new VuforiaInfo(vuMark, jewelColor);
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
