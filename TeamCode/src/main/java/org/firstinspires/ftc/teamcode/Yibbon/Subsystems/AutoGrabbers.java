package org.firstinspires.ftc.teamcode.Yibbon.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class AutoGrabbers{


    private Servo abgl1, abgl2, abgr1, abgr2;
    Gamepad gamepad1, gamepad2;


    public AutoGrabbers(){
    }

    public void init(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, boolean initPositionBool){
        abgl1 = hardwareMap.servo.get("l1");
        abgl2 = hardwareMap.servo.get("l2");
        abgr1 = hardwareMap.servo.get("r1");
        abgr2 = hardwareMap.servo.get("r2");
        this.gamepad2 = gamepad2;
        this.gamepad1 = gamepad1;
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

    public void autoGrabInputs(){
        //autoGrabbin();
        abgr2.setPosition(0.15);
        abgl2.setPosition(0.85);
    }

    private void autoGrabbin(){

        if (gamepad2.a){
            abgl1.setPosition(0.22);
            abgr1.setPosition(0.82);
        } else if (gamepad2.x){
            abgl1.setPosition(0.9);
            abgr1.setPosition(0.0);
        }

        if (gamepad2.y){
            //out
            abgl2.setPosition(0.1);
            abgr2.setPosition(0.9);
        } else if (gamepad2.b){
            //in
            abgl2.setPosition(0.8);
            abgr2.setPosition(0.2);
        }
    }
}
