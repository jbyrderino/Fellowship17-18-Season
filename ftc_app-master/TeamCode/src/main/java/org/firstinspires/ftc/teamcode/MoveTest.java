package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.Vector;

/**
 * Created by 28761 on 11/11/2017.
 */

@TeleOp(name="MoveTest", group="Iterative Opmode")
public class MoveTest extends LinearOpMode {

    KeithRobot keith;
    Navigation nav;

    public DriveSystem ds = null;

    @Override
    public void runOpMode() {
        //initialize hardwareMap
        HardwareMap hwMap = hardwareMap;

        telemetry.addLine("DcMotors Set...");


        keith = new KeithRobot(hwMap, telemetry);
        //initialize Mecanum Driving System
        ds = keith.GetDriveSystem();
        nav = keith.GetNavigationSystem();
        waitForStart();

        Acceleration acceleration = nav.GetAcceleration();
        Velocity velocity = nav.GetVelocity();
        String VelocityStr = nav.GetVelocity().toString();

        telemetry.addLine(VelocityStr);
        telemetry.update();

        double[][] command = new double[8][2];
        //square
        command[0][0] = 0;
        command[0][1] = 2000;
        command[1][0] = -90;
        command[1][1] = 2000;
        command[2][0] = -180;
        command[2][1] = 2000;
        command[3][0] = 90;
        command[3][1] = 2000;
        //diamond
        double rt2 = Math.sqrt(2);
        command[4][0] = -45;
        command[4][1] = 2000 * rt2;
        command[5][0] = -135;
        command[5][1] = 2000 * rt2;
        command[6][0] = 135;
        command[6][1] = 2000 * rt2;
        command[7][0] = 45;
        command[7][1] = 2000 * rt2;

        int count = 0;
        while (count < 8) {
            if (gamepad1.x) {
                telemetry.addLine("execute command " + count);
                ds.Move(.5, command[count][0], 0, command[count][1], 0);
                telemetry.addLine("command " + count + " is finished");
                count++;
            }
        }
    }
}
