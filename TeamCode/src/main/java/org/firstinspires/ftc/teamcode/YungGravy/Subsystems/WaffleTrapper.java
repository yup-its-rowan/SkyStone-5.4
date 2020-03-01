package org.firstinspires.ftc.teamcode.YungGravy.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.YungGravy.ServoCache;

public class WaffleTrapper {

    private Servo t1, t2, cap;

    enum stateToggleWaffleTrapper {
        trapperUp, trapperDown
    }
    private stateToggleWaffleTrapper toggleWaffleTrapper
            = stateToggleWaffleTrapper.trapperUp;

    enum cappy {
        starty, done
    }

    private cappy capStuff = cappy.starty;
    private boolean g1ddown = false, g1dup = false, g2LeftStick = false;
    private ServoCache t1c = new ServoCache();
    private ServoCache t2c = new ServoCache();
    private ServoCache capc = new ServoCache();

    public WaffleTrapper (){
    }

    public void init(HardwareMap hardwareMap){
        t1 = hardwareMap.servo.get("t1");
        t2 = hardwareMap.servo.get("t2");
        cap = hardwareMap.servo.get("cap");
    }


    public void waffleTrapperInputs (boolean g1down, boolean g1up, boolean leftStick2){
        this.g1dup = g1up;
        this.g1ddown = g1down;
        this.g2LeftStick = leftStick2;
        waffleToggle();
        capTog();
        switch (capStuff){
            case starty:
                cap.setPosition(0);
                capStuff = cappy.done;
                break;
            case done:
                break;
        }
    }
    private double t1p = 0, t2p = 0, capE = 0;

    private void waffleToggle(){
        switch (toggleWaffleTrapper){
            case trapperUp:
                t1p = 0.62;
                t2p = 0;
                if (g1ddown){toggleWaffleTrapper = stateToggleWaffleTrapper.trapperDown;}
                break;
            case trapperDown:
                t1p = 0;
                t2p = 1;
                if (g1dup){toggleWaffleTrapper = stateToggleWaffleTrapper.trapperUp;}
                break;
        }

        if (t1c.cache(t1p)){
            t1.setPosition(t1p);
        }
        if (t2c.cache(t2p)){
            t2.setPosition(t2p);
        }
    }

    private void capTog(){
        if (g2LeftStick == false){
            cap.setPosition(0);
        } else if (g2LeftStick == true){
            cap.setPosition(0.25);
        }
    }
}
