package org.firstinspires.ftc.teamcode.YungGravy.Subsystems.Drivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotDrive {
    private DcMotor fl, fr, bl, br;
    double flPower, frPower, blPower, brPower;
    private double g1lx = 0, g1ly = 0, g1rx = 0;
    private boolean g1lb = false, g1rb = false;

    public RobotDrive(){
    }

    public void init(HardwareMap hardwareMap){
        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        resetEncoders();
        runUsingEncoder();

    }

    public void driveInputs(double g1lx, double g1ly, double g1rx, boolean g1lb, boolean g1rb, Telemetry telemetry){
        this.g1lx = g1lx;
        this.g1ly = g1ly;
        this.g1rx = g1rx;
        this.g1lb = g1lb;
        this.g1rb = g1rb;

        weBeDrivin();
        encoderStuff(telemetry);
    }

    public void resetEncoders(){
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runUsingEncoder(){
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runWithoutEncoders(){
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void encoderStuff(Telemetry telemetry){
        telemetry.addData("fl", fl.getCurrentPosition());
        telemetry.addData("fr", fr.getCurrentPosition());
        telemetry.addData("bl", bl.getCurrentPosition());
        telemetry.addData("br", br.getCurrentPosition());
    }

    public void weBeDrivin(){

        flPower = this.g1lx - this.g1ly + this.g1rx;
        frPower = this.g1lx + this.g1ly + this.g1rx;
        blPower = -this.g1lx - this.g1ly + this.g1rx;
        brPower = -this.g1lx + this.g1ly + this.g1rx;

        if (this.g1lb == true){
            flPower/=4;
            frPower/=4;
            blPower/=4;
            brPower/=4;
        } else if (this.g1rb == true){
            flPower/=7;
            frPower/=7;
            blPower/=7;
            brPower/=7;
        }

        fl.setPower(flPower);
        fr.setPower(frPower);
        bl.setPower(blPower);
        br.setPower(brPower);
    }
}
