package org.firstinspires.ftc.teamcode.YungGravy.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.YungGravy.ServoCache;

public class VirtualFourBar {

    private Servo grab, v1, v2;
    private boolean g2dup = false, g2ddown = false, g2a = false, g2b = false;
    private ServoCache grabc = new ServoCache();
    private ServoCache v1c = new ServoCache();
    private ServoCache v2c = new ServoCache();


    public VirtualFourBar(){
    }

    public void init(HardwareMap hardwareMap){
        v1 = hardwareMap.servo.get("v1");
        v2 = hardwareMap.servo.get("v2");
        grab = hardwareMap.servo.get("grab");
    }

    public void v4bInputs(boolean up, boolean down, boolean a, boolean b){
        this.g2a = a;
        this.g2b = b;
        this.g2ddown = down;
        this.g2dup = up;

        grabToggle();
        v4b();
    }
    private double v1pos = 0, v2pos = 0, grabpos = 0;
    private void v4b(){
        if (g2ddown){
            v1pos = 0.9;
            v2pos = 0.1;
            if (v1c.cache(v1pos)){
                v1.setPosition(v1pos);
            }
            if (v2c.cache(v2pos)){
                v2.setPosition(v2pos);
            }
        } else if (g2dup){
            v1pos = 0.1;
            v2pos = 0.9;
            if (v1c.cache(v1pos)){
                v1.setPosition(v1pos);
            }
            if (v2c.cache(v2pos)){
                v2.setPosition(v2pos);
            }
        } else {
            v1pos = 0.5;
            v2pos = 0.5;
            if (v1c.cache(v1pos)){
                v1.setPosition(v1pos);
            }
            if (v2c.cache(v2pos)){
                v2.setPosition(v2pos);
            }
        }
    }

    private void grabToggle(){
        if (g2a){
            grabpos = 0.3;
            if (grabc.cache(grabpos)){
                grab.setPosition(grabpos);
            }
            //grab
        } else if (g2b){
            grabpos = 0.62;
            if (grabc.cache(grabpos)){
                grab.setPosition(grabpos);
            }
            //not grab
        }
    }
}
