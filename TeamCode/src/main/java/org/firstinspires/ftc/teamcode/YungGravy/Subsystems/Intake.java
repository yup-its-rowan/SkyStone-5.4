package org.firstinspires.ftc.teamcode.YungGravy.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    enum stateToggleIntake {
        intakeOn, intakeOff, intakeOut
    }

    private stateToggleIntake toggleIntake = stateToggleIntake.intakeOff;

    private DcMotor i1, i2;
    private double time, timeIntakeToggle = 0;
    boolean g1x = false, g1y = false;

    public Intake(){
    }

    public void init(HardwareMap hardwareMap){
        i1 = hardwareMap.dcMotor.get("i1");
        i2 = hardwareMap.dcMotor.get("i2");
    }

    public void intakeInputs(double time, boolean g1x, boolean g1y){
        this.time = time;
        this.g1x = g1x;
        this.g1y = g1y;
        intakeToggle();
    }

    private void intakeToggle(){
        if (time > (timeIntakeToggle + 0.2)){
            switch (toggleIntake){
                case intakeOut:
                    i1.setPower(-0.3);
                    i2.setPower(0.3);
                    if (g1y){toggleIntake = stateToggleIntake.intakeOff;
                    timeIntakeToggle = time;}
                    if (g1x){toggleIntake = stateToggleIntake.intakeOn;
                    timeIntakeToggle = time;}
                    break;
                case intakeOff:
                    i1.setPower(0);
                    i2.setPower(0);
                    if (g1x){toggleIntake = stateToggleIntake.intakeOn;
                    timeIntakeToggle = time;}
                    if (g1y){toggleIntake = stateToggleIntake.intakeOut;
                    timeIntakeToggle = time;}
                    break;
                case intakeOn:
                    i1.setPower(0.9);
                    i2.setPower(-0.9);
                    if (g1y){toggleIntake = stateToggleIntake.intakeOut;
                    timeIntakeToggle = time;}
                    if (g1x){toggleIntake = stateToggleIntake.intakeOff;
                    timeIntakeToggle = time;}
                    break;
            }
        }
    }


}
