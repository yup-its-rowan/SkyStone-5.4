package org.firstinspires.ftc.teamcode.Yibbette.Runnable.Auto;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//@Autonomous(name = "northernBlueBlock", group = "ee")
public class BlueBlock extends LinearOpMode {
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

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        goRight(-1225);
        pickThing2();
        goRight(300);
        goForward(-2200);
        dropThing2();
        goForward(1200);

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

    public void pickThing2(){
        goRight(75); //adjusts back
        abgl2.setPosition(1); //opens claw
        abgl1.setPosition(0.4); //puts arm down
        sleep(100); //wait
        goRight(-225); //adjusts back again
        abgl1.setPosition(0.32);
        sleep(300);
        abgl2.setPosition(0.2); //closes claw
        sleep(400); //wait
        abgl1.setPosition(0.78); //moves arm up
        goRight(125);
    }

    public void dropThing2(){
        abgl1.setPosition(0.5); //moves arm down
        sleep(100); //wait
        abgl2.setPosition(1); //opens claw
        sleep(100); //waits
        abgl1.setPosition(0.9); //arm back up
    }

}
