package org.firstinspires.ftc.teamcode.YungGravy.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class WaffleTrapper {

    private Servo t1, t2;
    enum stateToggleWaffleTrapper {
        trapperUp, trapperDown
    }

    private stateToggleWaffleTrapper toggleWaffleTrapper = stateToggleWaffleTrapper.trapperUp;
    private boolean g1ddown = false, g1dup = false;

    public WaffleTrapper (){
    }

    public void init(HardwareMap hardwareMap){
        t1 = hardwareMap.servo.get("t1");
        t2 = hardwareMap.servo.get("t2");
    }


    public void waffleTrapperInputs (boolean g1down, boolean g1up){
        this.g1dup = g1up;
        this.g1ddown = g1down;
        waffleToggle();
    }

    public void waffleToggle(){
        switch (toggleWaffleTrapper){
            case trapperUp:
                t1.setPosition(1);
                t2.setPosition(0);
                if (g1ddown){toggleWaffleTrapper = stateToggleWaffleTrapper.trapperDown;}
                break;
            case trapperDown:
                t1.setPosition(0);
                t2.setPosition(1);
                if (g1dup){toggleWaffleTrapper = stateToggleWaffleTrapper.trapperUp;}
                break;
        }
    }
}
