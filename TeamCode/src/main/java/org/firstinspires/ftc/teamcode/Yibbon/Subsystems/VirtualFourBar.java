package org.firstinspires.ftc.teamcode.Yibbon.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class VirtualFourBar {

    enum stateToggleGrab {
        grab, notgrab
    }

    private Servo grab, v1, v2;
    private stateToggleGrab toggleGrab = stateToggleGrab.notgrab;
    private double time, timeGrabToggle = 0;
    Gamepad gamepad1, gamepad2;

    public VirtualFourBar(){
    }

    public void init(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2){
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        v1 = hardwareMap.servo.get("v1");
        v2 = hardwareMap.servo.get("v2");
        grab = hardwareMap.servo.get("grab");
    }

    public void v4bInputs(double time){
        this.time = time;
        grabToggle();
        v4b();
    }

    private void v4b(){
        if (gamepad2.dpad_up){
            v1.setPosition(0.9);
            v2.setPosition(0.1);
        } else if (gamepad2.dpad_down){
            v1.setPosition(0.1);
            v2.setPosition(0.9);
        } else {
            v1.setPosition(0.5);
            v2.setPosition(0.5);
        }
    }

    private void grabToggle(){
        if (time > (timeGrabToggle + 0.2)){
            switch (toggleGrab){
                case notgrab:
                    grab.setPosition(0.7);
                    if (gamepad2.a){toggleGrab = stateToggleGrab.grab;
                        timeGrabToggle = time;}
                    break;
                case grab:
                    grab.setPosition(0.3);
                    if (gamepad2.a){toggleGrab = stateToggleGrab.notgrab;
                        timeGrabToggle = time;}
                    break;
            }
        }
    }
}
