package org.firstinspires.ftc.teamcode.Yibbon.Runnable.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous(name = "5795", group= "eee")
public class ParkAutoSouthern5795 extends LinearOpMode {
    DcMotorEx fl, fr, bl, br;


    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.get(DcMotorEx.class, "fl");
        bl = hardwareMap.get(DcMotorEx.class, "bl");
        fr = hardwareMap.get(DcMotorEx.class, "fr");
        br = hardwareMap.get(DcMotorEx.class, "br");

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fl.setTargetPositionTolerance(15);
        fr.setTargetPositionTolerance(15);
        bl.setTargetPositionTolerance(15);
        br.setTargetPositionTolerance(15);

        waitForStart();

        goForward(500, 0.3);

    }

    public void goForward(int gofron, double power){
        int tickCalc = (gofron);
        motorReset();
        fl.setTargetPosition(tickCalc);
        fr.setTargetPosition(-tickCalc);
        br.setTargetPosition(-tickCalc);
        bl.setTargetPosition(tickCalc);

        fl.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        powerBusy(power);
    }

    public int inchesToTicks(double inches){

        double inchesPerRotation = 2.952*Math.PI;
        double ticksPerRotation = 336;

        double ticksPerInch = ticksPerRotation/inchesPerRotation;

        double temp = ticksPerInch*inches;

        int ticky = (int)temp;

        return ticky;
    }

    private void motorReset() {
        fl.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
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
