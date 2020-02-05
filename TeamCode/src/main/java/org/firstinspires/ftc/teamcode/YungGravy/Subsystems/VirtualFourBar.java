package org.firstinspires.ftc.teamcode.YungGravy.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class VirtualFourBar {

    private Servo grab, v1, v2;
    private boolean g2dup = false, g2ddown = false, g2a = false, g2b = false;


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

    private void v4b(){
        if (g2dup){
            v1.setPosition(0.9);
            v2.setPosition(0.1);
        } else if (g2ddown){
            v1.setPosition(0.1);
            v2.setPosition(0.9);
        } else {
            v1.setPosition(0.5);
            v2.setPosition(0.5);
        }
    }

    private void grabToggle(){
        if (g2a){
            grab.setPosition(0.3);
            //grab
        } else if (g2b){
            grab.setPosition(0.7);
            //not grab
        }
    }
}
