package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Joshua on 10/28/2017.
 */

public class KeithRobot extends FTCRobot {
    AdafruitIMU imu;
    MecanumDS mds;
<<<<<<< HEAD
    FishingRodSystem frs;
=======
    Navigation nav;
>>>>>>> 96a26b5f9af6d607120bdb4da26b4e84b87f09fb

    KeithRobot(HardwareMap hardwareMap, Telemetry telemetry) {
        imu = new AdafruitIMU(hardwareMap, "imu");
        mds = new MecanumDS(hardwareMap, telemetry, imu, "Front Left", "Front Right", "Back Left", "Back Right");
<<<<<<< HEAD
        frs = new FishingRodSystem(hardwareMap, telemetry, "lowerReel", "upperReel", "claw", "rodMotor");
=======
        nav = new Navigation(this, telemetry);
>>>>>>> 96a26b5f9af6d607120bdb4da26b4e84b87f09fb
    }

    public IMUSystem GetIMUSystem() {
        return imu;
    }

    public DriveSystem GetDriveSystem() {
        return mds;
    }

<<<<<<< HEAD
    public RelicArmSubsystem GetRelicArmSubsystem() {
        return frs;
    }
=======
    public Navigation GetNavigationSystem() { return nav; }
>>>>>>> 96a26b5f9af6d607120bdb4da26b4e84b87f09fb
}
