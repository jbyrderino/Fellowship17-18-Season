package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Joshua on 10/28/2017.
 */

public class KeithRobot extends FTCRobot {
    BoschIMU imu;
    MecanumDS mds;
    FishingRodSystem frs;
    KeithJewlKnocker jks;

    KeithRobot(HardwareMap hardwareMap, Telemetry telemetry) {
        imu = new BoschIMU(hardwareMap, "imu");
        mds = new MecanumDS(hardwareMap, telemetry, imu, "Front Left", "Front Right", "Back Left", "Back Right");
        frs = new FishingRodSystem(hardwareMap, telemetry, "lowerReel", "upperReel", "claw", "rodMotor");
        jks = new KeithJewlKnocker(hardwareMap, "JewlBase", "JewlKnocker", telemetry);

        // TODO - remove this code once elevator/carriage set the servos
        // TODO - to good default values in their contructor/init
        Servo carriageLeftServo = hardwareMap.servo.get("CarriageLeft");
        carriageLeftServo.setPosition(0.35);
        Servo carriageRightServo = hardwareMap.servo.get("CarriageRight");
        carriageRightServo.setPosition(0.42);
        Servo kickerServo = hardwareMap.servo.get("Kicker");
        kickerServo.setPosition(0.52);
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

    public KeithJewlKnocker GetJewelKnockerSubsystem() {
        return jks;
    }
}
