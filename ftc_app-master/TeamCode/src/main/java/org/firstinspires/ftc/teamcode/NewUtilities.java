package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by Joshua on 1/29/2018.
 */

public class NewUtilities {

    static void sleep(long millis) {
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
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
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

    static public void KnockJewel(KeithJewlKnocker jewlKnocker, boolean leftSide) {
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

    //position: [-1,left],[0,center],[+1,right]
    public static void CryptoMove(MecanumDS ds, double motorPower, int position, Telemetry telemetry) {
        if (position != 0) {
            ds.Move(0.2, -90 * position, 0, 250, 5000);
        } else if (position == 0) {
            //do nothing.
        }
    }

    public static void ScoreWithElevator(KeithElevator elevator, Telemetry telemetry) {
        elevator.elevatorPower(-0.8);
        sleep(3000);
        elevator.elevatorPower(0.0);
    }

    public static boolean ExecuteMovesBlue(MecanumDS ds, KeithElevator elevator, double movePower, double spinPower, boolean nextToRelicRecovery, Telemetry telemetry, int position) {
        if (nextToRelicRecovery) {
            telemetry.addData("", "Moves: BLUE, next to RelicRecovery");
            // For our start position, we are facing away from the glyph box area,
            // so we need to move back, then turn left 90 degrees, then move
            // back again a little bit.

            // Move back
            if (!ds.Move(movePower, 180, 0, 1300, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Backward move timed out, abandoning.");
                return false;
            }


            // Turn right 90 degrees
            if (!ds.Move(spinPower, 0, -90, 0, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Left turn timed out, abandoning.");
                return false;
            }

            // Move forward
            if (!ds.Move(movePower, 0, 0, 150, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Backward move timed out, abandoning.");
                return false;
            }

            NewUtilities.CryptoMove(ds, movePower, position, telemetry);
            sleep(3000);
            NewUtilities.ScoreWithElevator(elevator, telemetry);

        } else {
            telemetry.addData("", "Moves: BLUE, away from RelicRecovery");
            // For our start position, we are facing away from the glyph box area so we
            // need to move back, then turn left 90 degrees, then move forward a bit more,
            // turn right 90 degrees and finally move back a little bit.

            // Move back
            if (!ds.Move(movePower, 180, 0, 1000, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Backward move timed out, abandoning.");
                return false;
            }

            // Turn right 90 degrees
            if (!ds.Move(spinPower, 0, -90, 0, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Left turn timed out, abandoning.");
                return false;
            }

            // Move backward
            if (!ds.Move(movePower, 0, 180, 440, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Forward move timed out, abandoning.");
                return false;
            }

            // Turn right 90 degrees
            if (!ds.Move(spinPower, 0, -90, 0, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Right turn timed out, abandoning.");
                return false;
            }

            // Move forward
            if (!ds.Move(movePower, 0, 0, 120, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Backward move timed out, abandoning.");
                return false;
            }

            NewUtilities.CryptoMove(ds, movePower, position, telemetry);
            sleep(3000);
            NewUtilities.ScoreWithElevator(elevator, telemetry);

        }

        return true;
    }

    public static boolean ExecuteMovesRed(MecanumDS ds, KeithElevator elevator, double movePower, double spinPower, boolean nextToRelicRecovery, Telemetry telemetry, int position) {
        if (nextToRelicRecovery) {
            telemetry.addData("", "Moves: RED, next to RelicRecovery");
            // For our start position, we are facing the glyph box area, so we
            // need to move forward, then turn left 90 degrees, then move
            // back a little bit.

            // Move forward
            int dx = 1300 + position * 210;
            if (!ds.Move(movePower, 0, 0, dx, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Forward move timed out, abandoning.");
                return false;
            }

            // Turn right 90 degrees
            if (!ds.Move(spinPower, 0, -88, 0, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Left turn timed out, abandoning.");
                return false;
            }

            // Move forward
            if (!ds.Move(movePower, 0, 0, 300, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Backward move timed out, abandoning.");
                return false;
            }

            //NewUtilities.CryptoMove(ds, movePower, position, telemetry);
            sleep(3000);
            NewUtilities.ScoreWithElevator(elevator, telemetry);
            sleep(4000);
            ds.Move(movePower, 180, 0, 100, 5000);

        } else {
            telemetry.addData("", "Moves: RED, away from RelicRecovery");
            // For our start position, we are facing the glyph box area, so we
            // need to move forward, then turn left 90 degrees, then move forward
            // a bit more, turn left 90 degrees again and finally move back a little bit.

            // Move forward
            if (!ds.Move(movePower, 0, 0, 1000, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Forward move timed out, abandoning.");
                return false;
            }

            // Turn left 90 degrees
            if (!ds.Move(spinPower, 0, 90, 0, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Left turn timed out, abandoning.");
                return false;
            }

            // Move forward
            if (!ds.Move(movePower, 0, 0, 450, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Forward move timed out, abandoning.");
                return false;
            }

            // Turn right 90 degrees
            if (!ds.Move(spinPower, 0, -90, 0, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Left turn timed out, abandoning.");
                return false;
            }

            // Move forward
            if (!ds.Move(movePower, 0, 0, 180, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position
                telemetry.addData("", "Backward move timed out, abandoning.");
                return false;
            }

            //NewUtilities.CryptoMove(ds, movePower, position, telemetry);
            sleep(3000);
            NewUtilities.ScoreWithElevator(elevator, telemetry);
        }
        return true;
    }

}
