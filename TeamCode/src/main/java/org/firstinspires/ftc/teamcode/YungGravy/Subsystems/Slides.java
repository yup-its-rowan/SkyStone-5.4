package org.firstinspires.ftc.teamcode.YungGravy.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slides {

    private DcMotor s1, s2;
    private DigitalChannel slideSwitch;
    private double g2rt = 0, g2lt = 0;

    public Slides(){
    }

    public void init(HardwareMap hardwareMap){
        this.s1 = hardwareMap.dcMotor.get("sl");
        this.s2 = hardwareMap.dcMotor.get("sr");
        this.slideSwitch = hardwareMap.digitalChannel.get("to");
        slideSwitch.setMode(DigitalChannel.Mode.INPUT);
        s1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        s1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        s1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        s2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void slideInputs(double g2rt, double g2lt){
        this.g2lt = g2lt;
        this.g2rt = g2rt;
        slideCheck();
    }

    int zeroPoint = 0;

    public void slideCheck(){
        double SLIDE_MAX_HEIGHT = 5780 + zeroPoint;

        if (g2rt > 0.1){
            if (s1.getCurrentPosition() < SLIDE_MAX_HEIGHT){
                //up
                s1.setPower(0.9);
                s2.setPower(-0.9);
            }
        } else if (g2lt > 0.1 && slideSwitch.getState()){
            s1.setPower(-0.6);
            s2.setPower(0.6);

        } else{
            s1.setPower(0);
            s2.setPower(0);
        }

        if (!slideSwitch.getState()){
            zeroPoint = s1.getCurrentPosition();
        }
    }

    private double slideHeight(){
        return s1.getCurrentPosition() - zeroPoint;
    }



}
