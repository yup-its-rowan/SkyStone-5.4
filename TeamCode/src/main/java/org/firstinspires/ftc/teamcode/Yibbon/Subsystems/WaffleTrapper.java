package org.firstinspires.ftc.teamcode.Yibbon.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class WaffleTrapper {

    private Servo t1, t2;
    private double time, timeToggleWaffle = 0;
    enum stateToggleWaffleTrapper {
        trapperUp, trapperDown
    }

    stateToggleWaffleTrapper toggleWaffleTrapper = stateToggleWaffleTrapper.trapperUp;
    Gamepad gamepad1, gamepad2;

    public WaffleTrapper (){
    }

    public void init(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2){
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        t1 = hardwareMap.servo.get("t1");
        t2 = hardwareMap.servo.get("t2");
    }


    public void waffleTrapperInputs (double time){
        this.time = time;
        waffleToggle();
    }

    public void waffleToggle(){
        if (time > (timeToggleWaffle + 0.2)){
            switch (toggleWaffleTrapper){
                case trapperUp:
                    t1.setPosition(1);
                    t2.setPosition(0);
                    if (gamepad1.left_stick_button){toggleWaffleTrapper = stateToggleWaffleTrapper.trapperDown; timeToggleWaffle = time;}
                    break;
                case trapperDown:
                    t1.setPosition(0);
                    t2.setPosition(1);
                    if (gamepad1.left_stick_button){toggleWaffleTrapper = stateToggleWaffleTrapper.trapperUp; timeToggleWaffle = time;}
                    break;
            }
        }
    }
}
