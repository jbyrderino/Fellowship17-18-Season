package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Joshua on 10/28/2017.
 */

public class KeithRobot extends FTCRobot {
    BoschIMU imu;
    MecanumDS mds;
    FishingRodSystem frs;

    KeithRobot(HardwareMap hardwareMap, Telemetry telemetry) {
        imu = new BoschIMU(hardwareMap, "imu");
        mds = new MecanumDS(hardwareMap, telemetry, imu, "Front Left", "Front Right", "Back Left", "Back Right");
        frs = new FishingRodSystem(hardwareMap, telemetry, "lowerReel", "upperReel", "claw", "rodMotor");
    }

    public IMUSystem GetIMUSystem() {
        return imu;
    }

    public DriveSystem GetDriveSystem() {
        return mds;
    }

    public RelicArmSubsystem GetRelicArmSubsystem() {
        return frs;
    }
}
