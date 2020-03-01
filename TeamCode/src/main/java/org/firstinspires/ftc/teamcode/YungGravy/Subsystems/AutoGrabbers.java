package org.firstinspires.ftc.teamcode.YungGravy.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class AutoGrabbers{

    private Servo abgl1, abgl2, abgr1, abgr2;
    private boolean g2rstick;

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

    public void autoGrabInputs(boolean g2rstick){
        autoGrabbin();
        this.g2rstick = g2rstick;
    }

    public void defaultTele(){
                abgl1.setPosition(0.9);
                abgr1.setPosition(0.0);
                abgl2.setPosition(0.1);
                abgr2.setPosition(0.9);
    }

    private void autoGrabbin(){

        if (g2rstick){
            defaultTele();
        }
    }
}
