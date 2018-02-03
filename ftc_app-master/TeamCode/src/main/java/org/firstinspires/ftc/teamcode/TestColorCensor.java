package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by 28761 on 2/3/2018.
 */
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cDevice;
@TeleOp(name="ColorTest", group="Iterative Opmode")
public class TestColorCensor extends LinearOpMode {

    ModernRoboticsI2cColorSensor2 sensor;

    @Override
    public void runOpMode() throws InterruptedException {
        I2cDevice i2c = hardwareMap.i2cDevice.get("colorSensor");
        sensor = new ModernRoboticsI2cColorSensor2(i2c.getI2cController(), i2c.getPort());
        sensor.enableLed(true);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addLine(String.format("Red: %d", sensor.red()));
            telemetry.addLine(String.format("Green: %d", sensor.green()));
            telemetry.addLine(String.format("Blue: %d", sensor.blue()));
            telemetry.addLine(String.format("Color #: %d", sensor.colorNumber()));
            telemetry.update();
        }
        sensor.enableLed(false);
    }
}
