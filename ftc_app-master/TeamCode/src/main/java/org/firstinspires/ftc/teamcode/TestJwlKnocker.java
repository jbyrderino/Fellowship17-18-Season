package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by 28761 on 1/29/2018.
 */
@TeleOp(name = "JwlTesSSSSSSSSSSSSSSSSSSSSt", group = "Iterative Opmode")
public class TestJwlKnocker extends LinearOpMode {

    KeithJewlKnocker kjk;

    @Override
    public void runOpMode() throws InterruptedException {
        kjk = new KeithJewlKnocker(hardwareMap, "JewlBase", "JewlKnocker", telemetry);
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.x) {
                kjk.baseKnockerRotateRight();
                sleep(500);
                kjk.knockerDown();
                sleep(500);
                kjk.knockLeft();
                sleep(500);
                kjk.knockerUp();
                sleep(500);
                kjk.baseKnockerRotateLeft();
            }
            if (gamepad1.y){
                kjk.baseKnockerRotateRight();
                sleep(500);
                kjk.knockerDown();
                sleep(500);
                kjk.knockRight();
                sleep(500);
                kjk.knockerUp();
                sleep(500);
                kjk.baseKnockerRotateLeft();
            }
        }
    }
}
