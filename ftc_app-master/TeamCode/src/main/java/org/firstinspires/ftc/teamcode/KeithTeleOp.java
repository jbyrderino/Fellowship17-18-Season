/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name = "Keith - TeleOp", group = "TeleOp")

public class KeithTeleOp extends OpMode {

    // Declare OpMode members.

    private ElapsedTime runtime = new ElapsedTime();

    KeithRobot keithRobot = null;
    MecanumDS ds = null;
    boolean linearMode = false;
    boolean allowEncoders = true;
    boolean debugTelemetry = false;
    double MaxPower = 1.0;
    double MaxDomain = 7;
    boolean engageMotors = true;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        keithRobot = new KeithRobot(hardwareMap, telemetry);
        ds = (MecanumDS)(keithRobot.GetDriveSystem());
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    double TransformInterval (double Value, double SourceStart, double SourceEnd, double TargetStart, double TargetEnd) {
        double sourceRange = SourceEnd - SourceStart;
        double targetRange = TargetEnd - TargetStart;
        return TargetStart + (Value - SourceStart) * targetRange / sourceRange;
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        if (runtime.milliseconds() >= 200) {
            boolean selection = false;
            if (gamepad1.left_bumper) {
                selection = true;
                MaxPower = MaxPower - 0.1;
            } else if (gamepad1.right_bumper) {
                selection = true;
                MaxPower = MaxPower + 0.1;
            }
            if (gamepad1.a) {
                selection = true;
                linearMode = !linearMode;
            }
            if (gamepad1.b) {
                selection = true;
                allowEncoders = !allowEncoders;
            }
            if (gamepad1.x) {
                selection = true;
                engageMotors = !engageMotors;
            }
            if (selection) {
                runtime.reset();
            }
        }
        if (MaxPower > 1) {
            MaxPower = 1;
        }
        if (MaxPower < 0) {
            MaxPower = 0;
        }
        String Mode = "Lin";
        if (!linearMode) {
            Mode = "Exp";
        }
        String allowEncodersStr = "Y";
        if (!allowEncoders) {
            allowEncodersStr = "N";
        }
        telemetry.addData("", "Power(Bumpers): %.1f, Input(A): %s, Encoders(B): %s", MaxPower, Mode, allowEncodersStr);
        telemetry.addLine("==============================================");

        ds.setEncoders(allowEncoders);

        // Get the input from the joysticks
        double LeftX = gamepad1.left_stick_x;
        double LeftY = -gamepad1.left_stick_y;
        double RightX = gamepad1.right_stick_x;
        double RightY = -gamepad1.right_stick_y;
        telemetry.addData("", "LX: %.3f, LY: %.3f, RX: %.3f, RY: %.3f", LeftX, LeftY, RightX, RightY);

        double minExpX = -5;
        double maxExpX = 2;

        if (!linearMode) {
            if ((LeftX != 0) || (LeftY != 0)) {
                double angleLeft = (LeftX == 0) ? Math.toRadians(90) : Math.atan(Math.abs(LeftY) / Math.abs(LeftX));
                if (LeftX > 0) {
                    if (LeftY > 0) {
                        // no change
                    } else {
                        angleLeft = Math.toRadians(360) - angleLeft;
                    }
                } else {
                    if (LeftY > 0) {
                        angleLeft = Math.toRadians(180) - angleLeft;
                    } else {
                        angleLeft = Math.toRadians(180) + angleLeft;
                    }
                }
                telemetry.addData("", "AngleL: %.1f", Math.toDegrees(angleLeft));
                double diagLeft = Range.clip(Math.sqrt(Math.pow(LeftX, 2) + Math.pow(LeftY, 2)), 0, 1);
                diagLeft = TransformInterval(diagLeft, 0, 1, minExpX, maxExpX);
                diagLeft = Math.exp(diagLeft);
                diagLeft = TransformInterval(diagLeft, Math.exp(minExpX), Math.exp(maxExpX), 0, 1);
                LeftX = Math.cos(angleLeft) * diagLeft;
                LeftY = Math.sin(angleLeft) * diagLeft;
            }

            if ((RightX != 0) || (RightY != 0)) {
                double angleRight = (RightX == 0) ? Math.toRadians(90) : Math.atan(Math.abs(RightY) / Math.abs(RightX));
                if (RightX > 0) {
                    if (RightY > 0) {
                        // no change
                    } else {
                        angleRight = Math.toRadians(360) - angleRight;
                    }
                } else {
                    if (RightY > 0) {
                        angleRight = Math.toRadians(180) - angleRight;
                    } else {
                        angleRight = Math.toRadians(180) + angleRight;
                    }
                }
                telemetry.addData("", "AngleR: %.1f", Math.toDegrees(angleRight));
                double diagRight = Range.clip(Math.sqrt(Math.pow(RightX, 2) + Math.pow(RightY, 2)), 0, 1);
                diagRight = TransformInterval(diagRight, 0, 1, minExpX, maxExpX);
                diagRight = Math.exp(diagRight);
                diagRight = TransformInterval(diagRight, Math.exp(minExpX), Math.exp(maxExpX), 0, 1);
                RightX = Math.cos(angleRight) * diagRight;
                RightY = Math.sin(angleRight) * diagRight;
            }
        }

        Utilities.PowerLevels powerLevels = new Utilities.PowerLevels(
                LeftY - LeftX,
                RightY + RightX,
                LeftY + LeftX,
                RightY - RightX);
        telemetry.addData("", "FL(IP): %.2f, FR(IP): %.2f, BL(IP): %.2f, BR(IP): %.2f", powerLevels.powerFL, powerLevels.powerFR, powerLevels.powerBL, powerLevels.powerBR);

        powerLevels = Utilities.NormalizePower(powerLevels, MaxPower);
        telemetry.addData("", "FL(PL): %.2f, FR(PL): %.2f, BL(PL): %.2f, BR(PL): %.2f", powerLevels.powerFL, powerLevels.powerFR, powerLevels.powerBL, powerLevels.powerBR);
        if (engageMotors) {
            ds.setMotorPower(powerLevels.powerFL, powerLevels.powerFR, powerLevels.powerBL, powerLevels.powerBR);
        }

        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
