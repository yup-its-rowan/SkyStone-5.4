package org.firstinspires.ftc.teamcode.Yibbette.Runnable.Auto;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//@Autonomous(name = "northernRedFoundation", group = "ee")
public class RedFoundation extends LinearOpMode {
    DcMotor fl, fr, bl, br;
    Servo abgl1, abgl2, abgr1, abgr2;
    ElapsedTime eTime = new ElapsedTime();
    BNO055IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");

        abgl1 = hardwareMap.servo.get("l1");
        abgl2 = hardwareMap.servo.get("l2");
        abgr1 = hardwareMap.servo.get("r1");
        abgr2 = hardwareMap.servo.get("r2");

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        waitForStart();
        goRight(1225);
        pickThing();
        goRight(-300);
        goForward(-2700);
        dropThing();
        goForward(1700);
    }


    private void motorReset() {
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void powerBusy() {
        fl.setPower(0.4);
        fr.setPower(0.4);
        bl.setPower(0.4);
        br.setPower(0.4);
        eTime.reset();
        while (((bl.isBusy() || br.isBusy()) || (fl.isBusy() || fr.isBusy())) && eTime.time() < 8){}
        bl.setPower(0);
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
    }

    public void goForward(int gofron){
        int tickCalc = gofron;
        motorReset();
        fl.setTargetPosition(tickCalc);
        fr.setTargetPosition(-tickCalc);
        br.setTargetPosition(-tickCalc);
        bl.setTargetPosition(tickCalc);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        powerBusy();
        sleep(100);
    }

    public void goRight(int goright){
        int tickCalc = goright;
        motorReset();
        fl.setTargetPosition(tickCalc);
        fr.setTargetPosition(tickCalc);
        br.setTargetPosition(-tickCalc);
        bl.setTargetPosition(-tickCalc);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        powerBusy();
        sleep(100);
    }

    public void pickThing(){
        goRight(-75); //adjusts back
        abgr2.setPosition(0); //opens claw
        abgr1.setPosition(0.6); //drops arm
        sleep(100); //wait
        goRight(225); //adjusts back again
        abgr1.setPosition(0.72); // drops arm fully
        sleep(300); //wait
        abgr2.setPosition(0.85);
        sleep(300);
        abgr1.setPosition(0.22);
        goRight(-125);
    }

    public void dropThing(){
        abgr1.setPosition(0.5);
        sleep(200);
        abgr2.setPosition(0);
        sleep(200);
        abgr1.setPosition(0.1);
    }

}
