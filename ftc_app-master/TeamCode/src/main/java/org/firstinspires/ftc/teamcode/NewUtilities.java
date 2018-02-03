package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by Joshua on 1/29/2018.
 */

public class NewUtilities {

    LinearOpMode lop;

    public NewUtilities(LinearOpMode lop) {
        this.lop = lop;
    }

    void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void KnockJewel(KeithJewlKnocker jewlKnocker, boolean leftSide) {
        jewlKnocker.baseKnockerRotateRight();
        if (!lop.opModeIsActive()) {
            //stop opMODE
            return;
        }
        jewlKnocker.knockerDown();
        if (!lop.opModeIsActive()) {
            //stop opMODE
            return;
        }
        if (leftSide) {
            jewlKnocker.knockRight();
        } else {
            jewlKnocker.knockLeft();
        }
        if (!lop.opModeIsActive()) {
            //stop opMODE
            return;
        }
        jewlKnocker.knockerUp();
        if (!lop.opModeIsActive()) {
            //stop opMODE
            return;
        }
        jewlKnocker.baseKnockerRotateLeft();
        if (!lop.opModeIsActive()) {
            //stop opMODE
            return;
        }
    }

    //position: [-1,left],[0,center],[+1,right]
    public void CryptoMove(MecanumDS ds, double motorPower, int position, Telemetry telemetry) {
        if (position != 0) {
            ds.Move(0.2, -90 * position, 0, 250, 5000);
        } else if (position == 0) {
            //do nothing.
        }
    }

    public void ScoreWithElevator(KeithElevator elevator, Telemetry telemetry) {
        elevator.elevatorPower(-0.8);
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 3000) {
            if (!lop.opModeIsActive()) {
                break;
            }
        }
        elevator.elevatorPower(0.0);
    }

    public boolean ExecuteMovesBlue(MecanumDS ds, KeithElevator elevator, double movePower, double spinPower, boolean nextToRelicRecovery, Telemetry telemetry, int position) {
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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
                return false;
            }

            CryptoMove(ds, movePower, position, telemetry);
            sleep(3000);

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
                return false;
            }
            ScoreWithElevator(elevator, telemetry);

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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
                return false;
            }

            CryptoMove(ds, movePower, position, telemetry);
            sleep(3000);

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
                return false;
            }

            ScoreWithElevator(elevator, telemetry);

        }

        return true;
    }

    public boolean ExecuteMovesRed(MecanumDS ds, KeithElevator elevator, double movePower, double spinPower, boolean nextToRelicRecovery, Telemetry telemetry, int position) {
        if (nextToRelicRecovery) {
            telemetry.addData("", "Moves: RED, next to RelicRecovery");
            // For our start position, we are facing the glyph box area, so we
            // need to move forward, then turn left 90 degrees, then move
            // back a little bit

            // Move forward
            int dx = 1300 + position * 210;
            if (!ds.Move(movePower, 0, 0, dx, 5000)) {
                // we seem to have timed out. Let's not continue this anymore
                // it's not safe to try to go further as we don't really know
                // our current position anymore
                telemetry.addData("", "Forward move timed out, abandoning.");
                return false;
            }

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
                return false;
            }

            //NewUtilities.CryptoMove(ds, movePower, position, telemetry);
            sleep(3000);
            ScoreWithElevator(elevator, telemetry);
            sleep(4000);

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
                return false;
            }

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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
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

            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
                return false;
            }

            //NewUtilities.CryptoMove(ds, movePower, position, telemetry);
            sleep(3000);
            if (!lop.opModeIsActive()){
                //abort due to turning off OpMode
                return false;
            }

            ScoreWithElevator(elevator, telemetry);
        }
        return true;
    }

}
