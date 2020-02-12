package org.firstinspires.ftc.teamcode.YungGravy.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.YungGravy.MotorCache;

public class Slides {

    private DcMotor s1, s2;
    private DigitalChannel slideSwitch;
    private double g2rt = 0, g2lt = 0;
    private MotorCache s1m = new MotorCache();
    private MotorCache s2m = new MotorCache();

    public Slides(){
    }

    public void init(HardwareMap hardwareMap){
        this.s1 = hardwareMap.dcMotor.get("sl");
        this.s2 = hardwareMap.dcMotor.get("sr");
        this.slideSwitch = hardwareMap.digitalChannel.get("to");
        slideSwitch.setMode(DigitalChannel.Mode.INPUT);
        s2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        s2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        s1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        s2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void slideInputs(double g2rt, double g2lt, Telemetry telemetry){
        this.g2lt = g2lt;
        this.g2rt = g2rt;
        slideCheck();
    }

    int zeroPoint = 0;
    private double s1P = 0;
    private double s2P = 0;

    public void slideCheck(){

        double SLIDE_MAX_HEIGHT = 5780;
        if (g2rt > 0.1){
            if (slideHeight() < SLIDE_MAX_HEIGHT){
                //up
                s1P = 0.9;
                s2P = -0.9;
                if (s1m.cache(s1P)){
                    s1.setPower(s1P);
                }
                if (s2m.cache(s2P)){
                    s2.setPower(s2P);
                }
            }
        } else if (g2lt > 0.1 && slideSwitch.getState()){
            s1P = -0.5;
            s2P = 0.5;
            if (slideHeight() < 300){
                s1P = -0.3;
                s2P = 0.3;
            }
            if (s1m.cache(s1P)){
                s1.setPower(s1P);
            }
            if (s2m.cache(s2P)){
                s2.setPower(s2P);
            }

        } else{
            s1P = 0;
            s2P = 0;

            //ADJUSTMENT CODE HERE

            if (s1m.cache(s1P)){
                s1.setPower(s1P);
            }
            if (s2m.cache(s2P)){
                s2.setPower(s2P);
            }
        }

        if (!slideSwitch.getState()){
            zeroPoint = s2.getCurrentPosition();
        }
    }

    private double slideHeight(){
        return -(s2.getCurrentPosition() - zeroPoint);
    }



}
