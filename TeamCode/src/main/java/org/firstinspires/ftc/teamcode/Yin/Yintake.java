package org.firstinspires.ftc.teamcode.Yin;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Yintake extends OpMode {

    DcMotor in1,in2;

    enum stateToggleIntake {
        intakeOn, intakeOff, intakeOut
    }

    public Yintake(DcMotor in1, DcMotor in2){this.in1 = in1; this.in2 = in2;}

    public void takereset(DcMotor in1, DcMotor in2){this.in1 = in1; this.in2 = in2;}

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }

    public void stateIntake(){

        stateToggleIntake toggleIntake = stateToggleIntake.intakeOff;

        switch (toggleIntake){

            case intakeOn:
                if (gamepad1.x){toggleIntake = stateToggleIntake.intakeOff;}
                if (gamepad1.a){toggleIntake = stateToggleIntake.intakeOut;}
                in1.setPower(-1);
                in2.setPower(0.8);
                break;
            case intakeOff:
                if (gamepad1.y){toggleIntake = stateToggleIntake.intakeOn;}
                if (gamepad1.a){toggleIntake = stateToggleIntake.intakeOut;}
                in1.setPower(0);
                in2.setPower(0);
                break;
            case intakeOut:
                if (gamepad1.y){toggleIntake = stateToggleIntake.intakeOn;}
                if (gamepad1.x){toggleIntake = stateToggleIntake.intakeOff;}
                in1.setPower(1);
                in2.setPower(-1);
                break;
            default:
                toggleIntake = stateToggleIntake.intakeOff;
                break;
        }
    }
}
