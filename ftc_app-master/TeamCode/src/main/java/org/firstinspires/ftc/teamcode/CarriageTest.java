package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 28761 on 1/16/2018.
 */

public class CarriageTest extends LinearOpMode {

    public KeithCarriage kc;

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap hwMap = hardwareMap;
        kc = new KeithCarriage(hardwareMap, telemetry, "slideMotor", "flipMotor", "lServo", "rServo");
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_right) {
                telemetry.addLine("Carriage go right");
                kc.slideTo(KeithCarriage.RIGHT);
            }
            if (gamepad1.dpad_left) {
                telemetry.addLine("Carriage go right");
                kc.slideTo(KeithCarriage.LEFT);
            }
            if (gamepad1.dpad_up) {
                telemetry.addLine("Carriage go center");
                kc.slideTo(KeithCarriage.CENTER);
            }
            if (gamepad1.x) {
                telemetry.addLine("Flipper toggle");
                kc.flipperToggle();
            }
            if (gamepad1.a) {
                telemetry.addLine("left servo toggle");
                kc.holderToggle(false);
            }
            if (gamepad1.b) {
                telemetry.addLine("right servo toggle");
                kc.holderToggle(true);
            }
            telemetry.update();
        }
    }
}
