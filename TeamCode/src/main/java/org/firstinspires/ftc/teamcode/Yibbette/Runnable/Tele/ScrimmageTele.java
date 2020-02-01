package org.firstinspires.ftc.teamcode.Yibbette.Runnable.Tele;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Yibbette.Subsystems.Drivetrains.GyroDrive;
import org.firstinspires.ftc.teamcode.Yibbette.RandomTelemetry;
import org.firstinspires.ftc.teamcode.Yibbette.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Yibbette.Subsystems.Slides;
import org.firstinspires.ftc.teamcode.Yibbette.Subsystems.VirtualFourBar;
import org.firstinspires.ftc.teamcode.Yibbette.Subsystems.WaffleTrapper;

//@TeleOp(name = "ScrimT", group = "eee")
public class ScrimmageTele extends OpMode {


    DcMotor fl, fr, bl, br, i1, i2, s1, s2;
    Servo t1, t2, v1, v2, grab, abgl1, abgl2, abgr1, abgr2;
    BNO055IMU imu;

    GyroDrive gyroDrive = new GyroDrive(null, null, null, null, null, null);
    Intake intake = new Intake(null, null);
    Slides slides = new Slides(null, null);
    WaffleTrapper waffleTrapper = new WaffleTrapper(null, null);
    VirtualFourBar v4b = new VirtualFourBar(null, null, null);
    ElapsedTime eTime = new ElapsedTime();

    String teleMessage = new String(RandomTelemetry.RandomTelemetry());


    @Override
    public void init() {
        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");

        i1 = hardwareMap.dcMotor.get("i1");
        i2 = hardwareMap.dcMotor.get("i2");

        s1 = hardwareMap.dcMotor.get("sl");
        s2 = hardwareMap.dcMotor.get("sr");

        t1 = hardwareMap.servo.get("t1");
        t2 = hardwareMap.servo.get("t2");

        v1 = hardwareMap.servo.get("v1");
        v2 = hardwareMap.servo.get("v2");
        grab = hardwareMap.servo.get("grab");

        abgl1 = hardwareMap.servo.get("l1");
        abgl2 = hardwareMap.servo.get("l2");
        abgr1 = hardwareMap.servo.get("r1");
        abgr2 = hardwareMap.servo.get("r2");



        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        gyroDrive.assignGyroDrive(fl, fr, bl, br, imu, telemetry);
        intake.assignIntake(i1, i2);
        slides.assignSlides(s1, s2);
        waffleTrapper.assignWaffleTrapper(t1, t2);
        v4b.assignV4B(grab, v1, v2);

        gyroDrive.resetEncoders();
        gyroDrive.runUsingEncoder();

        abgl1.setPosition(0.9);
        abgl2.setPosition(0.2);
        abgr1.setPosition(0.1);
        abgr2.setPosition(0.85);

    }

    @Override
    public void loop() {
        gyroDrive.drivetrainInputs(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x,
                gamepad1.left_bumper, gamepad1.back);
        intake.intakeInputs(gamepad1.x, gamepad1.y, eTime.time());
        slides.slideInputs2(gamepad1.dpad_up, gamepad1.dpad_down);
        waffleTrapper.waffleTrapperInputs(gamepad1.left_stick_button, eTime.time());
        v4b.v4bInputs(gamepad2.a, gamepad2.dpad_up, gamepad2.dpad_down, eTime.time());


        if (gamepad2.left_trigger>0.1){
            abgl1.setPosition(0.22);
            abgr1.setPosition(0.82);
        } else if (gamepad2.right_trigger>0.1){
            abgl1.setPosition(0.9);
            abgr1.setPosition(0.1);
        }

        if (gamepad2.left_stick_button){
            gyroDrive.resetEncoders();
            gyroDrive.runUsingEncoder();
        }

        if (gamepad2.left_bumper){
            abgl2.setPosition(0.1);
            abgr2.setPosition(0.9);
        } if (gamepad2.right_bumper){
            abgl2.setPosition(1);
            abgr2.setPosition(0);
        }

        telemetry.addData("OffsetAngle", gyroDrive.offsetAngle);
        telemetry.addData("rightStickPress", gamepad1.right_stick_button);

        telemetry.addData("", teleMessage);
    }
}
