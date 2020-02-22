package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Auto;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.YungGravy.AutoTeleTransition;


@Autonomous(name = "flexOnbees", group = "come on")
public class flexOnbees extends LinearOpMode {

    BNO055IMU imu;
    Orientation angles;
    DcMotor fl, bl, br, fr;

    @Override
    public void runOpMode() throws InterruptedException {

        fl = hardwareMap.get(DcMotor.class, "fl");
        bl = hardwareMap.get(DcMotor.class, "bl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        br = hardwareMap.get(DcMotor.class, "br");

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        AutoTeleTransition.teleOnStop(this, "RoadToStates");

        waitForStart();

        goForward(100, 0.3);
        goRight(-400, 0.3);
        turn(400, 0.3);

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        double pose = angles.firstAngle;
        telemetry.addData("endingAngle", pose);
        telemetry.update();
        AutoTeleTransition.holdValue(pose);
    }

    public void goForward(int gofron, double power){
        int tickCalc = (gofron);
        motorReset();
        fl.setTargetPosition(tickCalc);
        fr.setTargetPosition(-tickCalc);
        br.setTargetPosition(-tickCalc);
        bl.setTargetPosition(tickCalc);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        powerBusy(power);
    }


    public void turn(int gofron, double power){
        int tickCalc = (gofron);
        motorReset();
        fl.setTargetPosition(tickCalc);
        fr.setTargetPosition(tickCalc);
        br.setTargetPosition(tickCalc);
        bl.setTargetPosition(tickCalc);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        powerBusy(power);
    }

    public void goRight(int goright, double power){
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
        powerBusy(power);
    }

    private void motorReset() {
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void powerBusy(double power) {
        fl.setPower(power);
        fr.setPower(power);
        bl.setPower(power);
        br.setPower(power);
        while (((bl.isBusy() || br.isBusy()) || (fl.isBusy() || fr.isBusy()))){}
        bl.setPower(0);
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
    }

}
