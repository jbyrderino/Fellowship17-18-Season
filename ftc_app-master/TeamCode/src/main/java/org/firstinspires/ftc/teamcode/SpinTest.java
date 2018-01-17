package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 28761 on 11/11/2017.
 */

@TeleOp(name="SpinTest", group="Iterative Opmode")
public class SpinTest extends LinearOpMode {

    KeithRobot keith;
    public MecanumDS ds = null;

    @Override
    public void runOpMode() {
        //initialize hardwareMap
        HardwareMap hwMap = hardwareMap;
        telemetry.addLine("DcMotors Set...");

        keith = new KeithRobot(hwMap, telemetry);
        //initialize Mecanum Driving System
        ds = (MecanumDS)(keith.GetDriveSystem());
        waitForStart();

        double movePower = 0.1;
        double moveDistance = 1000;
        double spinPower = 0.1;
        double spinValue = 90;

        /*
        while (true) {
            boolean exit = false;
            while (!gamepad1.x) {
                if (gamepad1.y) {
                    sleep(200);
                    exit = true;
                    break;
                }
            }
            if (exit) {
                break;
            }
            ds.Calibrate();
        }
        */

        double selectionTime = 0;
        boolean allowEncoders = true;
        boolean spinning = false;

        while (true) {
            if (gamepad1.y) {
                break;
            }
            if (gamepad1.dpad_up) {
                if (gamepad1.dpad_right) {
                    ds.Move(movePower, -45, 0, moveDistance, 0);
                } else if (gamepad1.dpad_left) {
                    ds.Move(movePower, 45, 0, moveDistance, 0);
                } else {
                    ds.Move(movePower, 0, 0, moveDistance, 0);
                }
            }
            if (gamepad1.dpad_down) {
                if (gamepad1.dpad_right) {
                    ds.Move(movePower, -135, 0, moveDistance, 0);
                } else if (gamepad1.dpad_left) {
                    ds.Move(movePower, 135, 0, moveDistance, 0);
                } else {
                    ds.Move(movePower, 180, 0, moveDistance, 0);
                }
            }
            if (gamepad1.dpad_left) {
                if (gamepad1.dpad_up) {
                    ds.Move(movePower, 45, 0, moveDistance, 0);
                } else if (gamepad1.dpad_down) {
                    ds.Move(movePower, 135, 0, moveDistance, 0);
                } else {
                    ds.Move(movePower, 90, 0, moveDistance, 0);
                }
            }
            if (gamepad1.dpad_right) {
                if (gamepad1.dpad_up) {
                    ds.Move(movePower, -45, 0, moveDistance, 0);
                } else if (gamepad1.dpad_down) {
                    ds.Move(movePower, -135, 0, moveDistance, 0);
                } else {
                    ds.Move(movePower, -90, 0, moveDistance, 0);
                }
            }
            /*
            if (gamepad1.left_bumper) {
                ds.Move(spinPower, 0, spinValue, 0, 0);
            }
            if (gamepad1.right_bumper) {
                ds.Move(spinPower, 0, -spinValue, 0, 0);
            }
            */

            if ((System.currentTimeMillis() - selectionTime) >= 300) {
                boolean selection = false;

                // deal with the claw opening/closing
                if (gamepad1.a) {
                    selection = true;
                    allowEncoders = !allowEncoders;
                    ds.setEncoders(allowEncoders);
                }

                if (gamepad1.right_bumper) {
                    selection = true;
                    spinPower += 0.01;
                    movePower += 0.01;
                }
                if (gamepad1.left_bumper) {
                    selection = true;
                    spinPower -= 0.01;
                    movePower -= 0.01;
                }

                if (selection) {
                    selectionTime = System.currentTimeMillis();
                }
            }

            boolean spinningCmd = false;
            if (gamepad1.x) {
                spinning = true;
                spinningCmd = true;
                ds.setMotorPower(-spinPower, spinPower, -spinPower, spinPower);
            }
            if (gamepad1.b) {
                spinning = true;
                spinningCmd = true;
                ds.setMotorPower(spinPower, -spinPower, spinPower, -spinPower);
            }
            if (spinning && (!spinningCmd)) {
                ds.setMotorPower(0, 0, 0, 0);
            }

            telemetry.addData("", "Encoders: %s, Motor power: %.2f", allowEncoders ? "Yes" : "No", spinPower);
            telemetry.update();
        }

        /*
        while (true) {
            boolean exit = false;
            while (!gamepad1.x) {
                if (gamepad1.y) {
                    sleep(200);
                    exit = true;
                    break;
                }
            }
            if (exit) {
                break;
            }
            ds.Move(spinPower, 0, -spinValue, 0, 0);
        }
        */

        /*
        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, -spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, -spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, -spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, -spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, -spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, -spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, spinValue, 0, 0);

        while (!gamepad1.x) {}
        */
    }
}
