package org.firstinspires.ftc.teamcode.Yibbon.Subsystems;

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
    Gamepad gamepad1;

    public Intake(){
    }

    public void init(HardwareMap hardwareMap, Gamepad gamepad1){
        this.gamepad1 = gamepad1;
        i1 = hardwareMap.dcMotor.get("i1");
        i2 = hardwareMap.dcMotor.get("i2");
    }

    public void intakeInputs(double time){
        this.time = time;
        intakeToggle();
    }

    private void intakeToggle(){
        if (time > (timeIntakeToggle + 0.2)){
            switch (toggleIntake){
                case intakeOut:
                    i1.setPower(-0.3);
                    i2.setPower(0.3);
                    if (gamepad1.y){toggleIntake = stateToggleIntake.intakeOff;
                    timeIntakeToggle = time;}
                    if (gamepad1.x){toggleIntake = stateToggleIntake.intakeOn;
                    timeIntakeToggle = time;}
                    break;
                case intakeOff:
                    i1.setPower(0);
                    i2.setPower(0);
                    if (gamepad1.x){toggleIntake = stateToggleIntake.intakeOn;
                    timeIntakeToggle = time;}
                    if (gamepad1.y){toggleIntake = stateToggleIntake.intakeOut;
                    timeIntakeToggle = time;}
                    break;
                case intakeOn:
                    i1.setPower(0.9);
                    i2.setPower(-0.9);
                    if (gamepad1.y){toggleIntake = stateToggleIntake.intakeOut;
                    timeIntakeToggle = time;}
                    if (gamepad1.x){toggleIntake = stateToggleIntake.intakeOff;
                    timeIntakeToggle = time;}
                    break;
            }
        }
    }


}
