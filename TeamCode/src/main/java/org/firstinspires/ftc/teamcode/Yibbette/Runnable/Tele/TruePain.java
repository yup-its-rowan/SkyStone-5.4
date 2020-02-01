package org.firstinspires.ftc.teamcode.Yibbette.Runnable.Tele;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Yibbette.RandomTelemetry;
import org.firstinspires.ftc.teamcode.Yibbette.Subsystems.Drivetrains.GyroDrive;
import org.firstinspires.ftc.teamcode.Yibbette.Subsystems.Drivetrains.PainDrive;
import org.firstinspires.ftc.teamcode.Yibbette.Subsystems.Drivetrains.RobotDrive;

import java.util.Random;

public class TruePain extends OpMode {

    String teleMessage = new String(RandomTelemetry.RandomTelemetry());
    Random rando = new Random();
    int chooser;
    String[] dtList = {"gyro", "regular", "pain"};
    String dtChosen;
    ElapsedTime time = new ElapsedTime();

    GyroDrive gyroDrive = new GyroDrive(null, null, null, null, null, null);
    PainDrive painDrive = new PainDrive(null, null, null, null);
    RobotDrive robotDrive = new RobotDrive(null, null, null, null);

    DcMotor fl, fr, bl, br, i1, i2;
    BNO055IMU imu;

    @Override
    public void init() {
        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        gyroDrive.assignGyroDrive(fl, fr, bl, br, imu, telemetry);
        robotDrive.assignRobotDrive(fl, fr, bl, br);
        painDrive.assignPainDrive(fl, fr, bl, br);
    }

    @Override
    public void loop() {
        if (time.time()>10){
            chooser = rando.nextInt(3);
            this.dtChosen = dtList[chooser];
            time.reset();
        }

        if (this.dtChosen == "gyro"){
            gyroDrive.drivetrainInputs(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_bumper, gamepad1.right_stick_button);
            gyroDrive.weBeDrivin();
            telemetry.addData("", "field oriented");
        } else if (this.dtChosen == "regular"){
            robotDrive.drivetrainInputs(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            robotDrive.weBeDrivin();
            telemetry.addData("", "robot oriented");
        } else if (this.dtChosen == "pain"){
            painDrive.drivetrainInputs(gamepad1.right_stick_x, gamepad1.left_trigger, gamepad1.right_trigger, gamepad1.left_bumper);
            painDrive.weBeCryin();
            telemetry.addData("", "fuck you");
        }

        telemetry.addData("", teleMessage);
    }
}
