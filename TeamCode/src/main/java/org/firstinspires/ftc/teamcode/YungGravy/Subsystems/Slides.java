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
    private boolean g2dl = false, g2dr = false, g2lbutton = false;
    private MotorCache s1m = new MotorCache();
    private MotorCache s2m = new MotorCache();
    enum slideState{
        driverOperated, automationUp, headinDown
    }
    enum autoHeightState{
        onTheWay, there
    }

    public slideState stateOfDaSlides = slideState.driverOperated;
    private autoHeightState heightState = autoHeightState.onTheWay;

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

    public void slideInputs(double g2rt, double g2lt, boolean g2lb, boolean g2rb, boolean g2x, Telemetry telemetry, double time){
        this.g2lt = g2lt;
        this.g2rt = g2rt;
        this.g2dl = g2lb;
        this.g2dr = g2rb;
        this.g2lbutton = g2x;
        slideCheck(telemetry, time);
    }

    int zeroPoint = 0;
    private double s1P = 0;
    private double s2P = 0;
    private double autoHeight = 1, capturedTime = 0, capturedTime2 = 0;

    public void slideCheck2(Telemetry telemetry){
        if (g2rt > 0.1){
            s1P = 1;
            s2P = -1;
            // && slideHeight() < SLIDE_MAX_HEIGHT
            //going up
        } else if (g2lt > 0.1){
            // && slideSwitch.getState()
            s1P = -1;
            s2P = 1;
            //going down
        } else{
            //ADJUSTMENT CODE HERE
            antiGravCode();
        }

        telemetry.addData("slidecoders", slideHeight());
    }

    double savedSlideHeight = 0, savedSlideHeight2 = 0;
    public void slideCheck(Telemetry telemetry, double time){

        double SLIDE_MAX_HEIGHT = 1250;
        double SLIDE_SAFE_HEIGHT = 250;
        double SLIDE_DOWN_SPEED = 0.8;
        double SLIDE_DOWN_SLOW_SPEED = 0.4;
        double ERROR_DOWN_MAX = 50;
        switch (stateOfDaSlides){
            case driverOperated:
                if (!(g2lt > 0.1)){
                    savedSlideHeight = slideHeight();
                }

                double errorDown = savedSlideHeight - slideHeight();

                if (g2rt > 0.1 && slideHeight() < SLIDE_MAX_HEIGHT){
                    s1P = 1;
                    s2P = -1;
                    //going up
                } else if (g2lt > 0.1&& slideSwitch.getState()){
                    s1P = -SLIDE_DOWN_SPEED;
                    s2P = SLIDE_DOWN_SPEED;
                    if (errorDown < ERROR_DOWN_MAX){
                        double power = 0.01*Math.pow(Math.E, 0.085*errorDown)+0.3;
                        s1P = -power;
                        s2P = power;
                    }
                    //going down
                    if (slideHeight() < SLIDE_SAFE_HEIGHT){
                        s1P = -SLIDE_DOWN_SLOW_SPEED;
                        s2P = SLIDE_DOWN_SLOW_SPEED;
                    }
                } else{
                    //ADJUSTMENT CODE HERE
                    antiGravCode();
                }



                if (time > capturedTime){
                    if (g2dl && autoHeight > 1){
                        autoHeight -=1;
                        capturedTime = time + 0.2;
                    } else if (g2dr){
                        autoHeight++;
                        capturedTime = time + 0.2;
                    }
                }

                if (time > capturedTime2){
                    if (g2lbutton){
                        stateOfDaSlides = slideState.automationUp;
                        capturedTime2 = time + 0.5;
                    }
                }

                break;
            case automationUp:

                if (g2dl || g2rt > 0.1 || g2lt > 0.1 || g2dr){
                    stateOfDaSlides = slideState.driverOperated;
                }

                if (autoHeight == 1 || autoHeight == 2){
                    s1P = 0.0;
                    s2P = 0.0;
                } else if (autoHeight > 2 && autoHeight < 12){
                    goToHeight((autoHeight-2)*175);
                }

                if (time > capturedTime2){
                    if (g2lbutton){
                        stateOfDaSlides = slideState.headinDown;
                        heightState = autoHeightState.onTheWay;
                        capturedTime2 = time + 0.5;
                        savedSlideHeight2 = slideHeight();
                    }
                }

                break;
            case headinDown:

                if ((g2dl || g2rt > 0.1 || g2lt > 0.1 || g2dr || g2lbutton) && (time > capturedTime2)){
                    stateOfDaSlides = slideState.driverOperated;
                }

                double errorDown2 = savedSlideHeight2 - slideHeight();

                if (!slideSwitch.getState()){
                    s1P = 0;
                    s2P = 0;
                    stateOfDaSlides = slideState.driverOperated;
                }

                if (slideHeight() > SLIDE_SAFE_HEIGHT){
                    s1P = -SLIDE_DOWN_SPEED;
                    s2P = SLIDE_DOWN_SPEED;
                    if (errorDown2 < ERROR_DOWN_MAX){
                        double power2 = 0.01*Math.pow(Math.E, 0.085*errorDown2)+0.3;
                        s1P = -power2;
                        s2P = power2;
                    }

                } else if (slideHeight() < SLIDE_SAFE_HEIGHT){
                    s1P = -SLIDE_DOWN_SLOW_SPEED;
                    s2P = SLIDE_DOWN_SLOW_SPEED;
                }

                break;
        }

        if (s1m.cache(s1P)){
            s1.setPower(s1P);
        }
        if (s2m.cache(s2P)){
            s2.setPower(s2P);
        }

        telemetry.addData("slidecoders", slideHeight());
        telemetry.addData("blockAuto", autoHeight);
        telemetry.addData("state", stateOfDaSlides);

        if (!slideSwitch.getState()){
            zeroPoint = s2.getCurrentPosition();
        }
    }

    private double slideHeight(){
        return -(s2.getCurrentPosition() - zeroPoint);
    }

    private void goToHeight(double targetHeight){
        double maxError = 20;
        double actualError = targetHeight-slideHeight();
        //DO NOT CHANGE THE ORDER OF THESE IF STATEMENTS IT WILL BREAK EVERYTHING PLEASE OH GOD EVERYTHING HURTS
        switch (heightState){
            case onTheWay:
                if (Math.abs(actualError) < maxError){
                    heightState = autoHeightState.there;
                }
                if (actualError > 300){
                    s1P = 1;
                    s2P = -1;
                } else if (actualError > maxError){
                    s1P = 0.8;
                    s2P = -0.8;
                } else if (actualError < 0){
                    s1P = -0.3;
                    s2P = 0.3;
                }
                break;
            case there:
                antiGravCode();
                break;
        }
    }

    private void antiGravCode(){
        if (slideHeight() < 350){
            s1P = 0.0;
            s2P = 0.0;
        } else if (slideHeight() < 900){
            s1P = 0.08;
            s2P = -0.08;
        } else if (slideHeight() >= 900){
            s1P = 0.09;
            s2P = -0.09;
        }
    }



}
