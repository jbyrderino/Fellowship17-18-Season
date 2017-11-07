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

import android.text.method.BaseKeyListener;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Pushbot: Teleop POV", group="Pushbot")
public class MotorTest extends LinearOpMode {

    public DcMotor FrontLeft = null;
    public DcMotor FrontRight = null;
    public DcMotor BackLeft = null;
    public DcMotor BackRight = null;


    public void moveLeft() {
        FrontLeft.setPower(-0.5);
        FrontRight.setPower(-0.5);
        BackRight.setPower(0.5);
        BackLeft.setPower(0.5);
        sleep(1000);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void moveRight() {
        FrontLeft.setPower(0.5);
        FrontRight.setPower(0.5);
        BackRight.setPower(-0.5);
        BackLeft.setPower(-0.5);
        sleep(1000);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void moveForward() {
        FrontLeft.setPower(-0.5);
        FrontRight.setPower(0.5);
        BackRight.setPower(0.5);
        BackLeft.setPower(-0.5);
        sleep(1000);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void moveBack() {
        FrontLeft.setPower(0.5);
        FrontRight.setPower(-0.5);
        BackRight.setPower(-0.5);
        BackLeft.setPower(0.5);
        sleep(1000);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void moveDiagonalForwardRight() {
        FrontLeft.setPower(-0.5);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0.5);
        sleep(1000);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void moveDiagonalForwardLeft() {
        FrontLeft.setPower(0);
        FrontRight.setPower(0.5);
        BackLeft.setPower(-0.5);
        BackRight.setPower(0);
        sleep(1000);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void moveDiagonalBackwardRight() {
        FrontLeft.setPower(0.5);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(-0.5);
        sleep(1000);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void moveDiagonalBackwardLeft() {
        FrontLeft.setPower(0);
        FrontRight.setPower(-0.5);
        BackLeft.setPower(0.5);
        BackRight.setPower(0);
        sleep(1000);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void spinRight() {
        FrontLeft.setPower(-1);
        FrontRight.setPower(-1);
        BackLeft.setPower(-1);
        BackRight.setPower(-1);
        sleep(1000);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    public void spinLeft() {
        FrontLeft.setPower(1);
        FrontRight.setPower(1);
        BackLeft.setPower(1);
        BackRight.setPower(1);
        sleep(1000);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackRight.setPower(0);
        BackLeft.setPower(0);
    }

    @Override
    public void runOpMode() {

        HardwareMap hwMap = hardwareMap;
        telemetry.addLine("DcMotors Set...");

        FrontLeft = hwMap.get(DcMotor.class, "Front Left");
        FrontRight = hwMap.get(DcMotor.class, "Front Right");
        BackLeft = hwMap.get(DcMotor.class, "Back Left");
        BackRight = hwMap.get(DcMotor.class, "Back Right");

        telemetry.addLine("DcMotors Named...");

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);

        telemetry.addLine("DcMotors Power Set...");

        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addLine("Running Without Encoder...");

        waitForStart();



/*
        FrontLeft.setPower(0.5);
        sleep(2000);
        FrontLeft.setPower(0);

        FrontRight.setPower(0.5);
        sleep(2000);
        FrontRight.setPower(0);

        BackRight.setPower(0.5);
        sleep(2000);
        BackRight.setPower(0);

        BackLeft.setPower(0.5);
        sleep(2000);
        BackLeft.setPower(0);
*/

        while (opModeIsActive()) {
            if (gamepad1.x) {
                if (gamepad1.right_bumper) {
                    moveDiagonalForwardLeft();
                } else {
                    moveLeft();
                }
            } else if (gamepad1.y) {
                if (gamepad1.right_bumper) {
                    moveDiagonalBackwardRight();
                } else {
                    moveForward();
                }
            } else if (gamepad1.b) {
                if (gamepad1.right_bumper) {
                    moveDiagonalForwardRight();
                } else {
                    moveRight();
                }
            } else if (gamepad1.a) {
                if (gamepad1.right_bumper) {
                    moveDiagonalBackwardLeft();
                } else {
                    moveBack();
                }

            }

            if (gamepad1.start) {
                spinLeft();
            } else if (gamepad1.left_bumper) {
                spinRight();
            }
        }
    }
}