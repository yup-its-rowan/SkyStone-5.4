package org.firstinspires.ftc.teamcode.YungGravy.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class AutoGrabbers{


    private Servo abgl1, abgl2, abgr1, abgr2;
    private boolean g2a = false, g2x = false, g2y = false, g2b = false;


    public AutoGrabbers(){
    }

    public void init(HardwareMap hardwareMap, boolean initPositionBool){
        abgl1 = hardwareMap.servo.get("l1");
        abgl2 = hardwareMap.servo.get("l2");
        abgr1 = hardwareMap.servo.get("r1");
        abgr2 = hardwareMap.servo.get("r2");
        if (initPositionBool){
            initPosition();
        }
    }

    public void initPosition(){
        abgl1.setPosition(0.9);
        abgl2.setPosition(0.2);
        abgr1.setPosition(0.1);
        abgr2.setPosition(0.85);
    }

    public void autoGrabInputs(boolean g2a, boolean g2b, boolean g2x, boolean g2y){
        //autoGrabbin();
        this.g2a = g2a;
        this.g2b = g2b;
        this.g2x = g2x;
        this.g2y = g2y;

        abgr2.setPosition(0.15);
        abgl2.setPosition(0.85);
    }

    private void autoGrabbin(){

        if (g2a){
            abgl1.setPosition(0.22);
            abgr1.setPosition(0.82);
        } else if (g2x){
            abgl1.setPosition(0.9);
            abgr1.setPosition(0.0);
        }

        if (g2y){
            //out
            abgl2.setPosition(0.1);
            abgr2.setPosition(0.9);
        } else if (g2b){
            //in
            abgl2.setPosition(0.8);
            abgr2.setPosition(0.2);
        }
    }
}
