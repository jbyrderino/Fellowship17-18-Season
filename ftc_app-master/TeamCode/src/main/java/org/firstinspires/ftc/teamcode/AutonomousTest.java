package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 28761 on 11/11/2017.
 */

@TeleOp(name="AutonomousTest", group="Iterative Opmode")
public class AutonomousTest extends LinearOpMode {

    KeithRobot keith;
    public DriveSystem ds = null;

    @Override
    public void runOpMode() {
        //initialize hardwareMap
        HardwareMap hwMap = hardwareMap;
        telemetry.addLine("DcMotors Set...");

        keith = new KeithRobot(hwMap, telemetry);
        //initialize Mecanum Driving System
        ds = keith.GetDriveSystem();
        waitForStart();

        double movePower = 0.3;
        double moveDistance = 2000;
        double spinPower = 0.1;
        double spinValue = 8;

        while (true) {
            if (gamepad1.y) {
                break;
            }
            if (gamepad1.dpad_up) {
                ds.Move(spinPower, 0, 15, 0, 0);
                sleep(200);
                ds.Move(spinPower, 0, -15, 0, 0);
                sleep(200);
                ds.Move(movePower, 0, 0, 4000, 0);
                sleep(200);
                ds.Move(movePower, -90, 0, 4000, 0);
                sleep(200);
                ds.Move(movePower - 0.1, 0, 0, 500, 0);
                sleep(500);
                break;
            }
        }
    }
}
