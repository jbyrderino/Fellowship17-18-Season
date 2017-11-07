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
    KeithRobot (HardwareMap hardwareMap, Telemetry telemetry){
        imu = new AdafruitIMU(hardwareMap, "imu");
        mds = new MecanumDS(hardwareMap, telemetry, "Front Left", "Front Right", "Back Left", "Back Right");
    }

    public IMUSystem GetIMUSystem (){
        return imu;
    }

    public DriveSystem GetDriveSystem (){
        return mds;
    }
}
