package org.firstinspires.ftc.teamcode.Yibbette.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class WaffleTrapper {

    private Servo t1, t2;
    private boolean leftButton;
    private double time, timeToggleWaffle = 0;

    enum stateToggleWaffleTrapper {
        trapperUp, trapperDown
    }

    stateToggleWaffleTrapper toggleWaffleTrapper = stateToggleWaffleTrapper.trapperUp;

    public WaffleTrapper (Servo t1e, Servo t2e){
        this.t1 = t1e;
        this.t2 = t2e;
    }

    public void assignWaffleTrapper (Servo t1e, Servo t2e){
        this.t1 = t1e;
        this.t2 = t2e;
    }

    public void waffleTrapperInputs (boolean leftStickButton, double time){
        this.leftButton = leftStickButton;
        this.time = time;
        waffleToggle();
    }

    public void waffleToggle(){
        if (time > (timeToggleWaffle + 0.2)){
            switch (toggleWaffleTrapper){
                case trapperDown:
                    t1.setPosition(1);
                    t2.setPosition(0);
                    if (leftButton){toggleWaffleTrapper = stateToggleWaffleTrapper.trapperUp; timeToggleWaffle = time;}
                    break;
                case trapperUp:
                    t1.setPosition(0);
                    t2.setPosition(1);
                    if (leftButton){toggleWaffleTrapper = stateToggleWaffleTrapper.trapperDown; timeToggleWaffle = time;}
                    break;
            }
        }
    }
}
