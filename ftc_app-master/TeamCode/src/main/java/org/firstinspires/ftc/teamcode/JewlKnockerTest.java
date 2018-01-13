package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by 28761 on 11/11/2017.
 */

@TeleOp(name="JewlKnockerTest", group="Iterative Opmode")
public class JewlKnockerTest extends LinearOpMode {

    KeithJewlKnocker jewlKnockerSystem = null;

    @Override
    public void runOpMode() {
        //initialize hardwareMap
        HardwareMap hwMap = hardwareMap;

        jewlKnockerSystem = new KeithJewlKnocker(hwMap, "JewlKnocker", telemetry);
        waitForStart();

        while (true) {
            boolean pressed = false;
            if (gamepad1.y) {
                break;
            }
            if (gamepad1.dpad_up) {
                pressed = true;
                jewlKnockerSystem.knockerUp();
            }
            if (gamepad1.dpad_down) {
                pressed = true;
                jewlKnockerSystem.knockerDown();
            }
            if (pressed) {
                sleep(200);
            }
        }

        //jewlKnockerSystem.knockerDown();
        //sleep(5000);
        //jewlKnockerSystem.knockerUp();
        //sleep(5000);
    }
}
