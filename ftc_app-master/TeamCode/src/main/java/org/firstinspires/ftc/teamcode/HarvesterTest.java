package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 28761 on 1/16/2018.
 */

@TeleOp(name = "CarriageTestOp", group = "Iterative Opmode")
public class HarvesterTest extends LinearOpMode {

    public KeithCarriage kc;
    public KeithElevator ke;

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap hwMap = hardwareMap;
        kc = new KeithCarriage(hardwareMap, telemetry, "slideMotor", "flipMotor", "lServo", "rServo");
        waitForStart();

        while (opModeIsActive()) {
            ke.ElevatorInit(0);
            ke.kickerInit();
            ke.kickerReset();

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
                kc.holderToggle(KeithCarriage.LEFTS);
            }
            if (gamepad1.b) {
                telemetry.addLine("right servo toggle");
                kc.holderToggle(KeithCarriage.RIGHTS);
            }

            if (gamepad1.left_bumper) {
                ke.elevatorStart(0.5);
            }
            if (gamepad1.right_bumper) {
                ke.elevatorStop();
            }
            if (gamepad1.y) {
                ke.kickerKick();
            }

            telemetry.update();
        }
    }
}
