package org.firstinspires.ftc.teamcode.Yibbette.Subsystems.Drivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;

public class PainDrive {
    private DcMotor fl, fr, bl, br;
    double leftTrigger, rightTrigger, rightStickX, flPower, frPower, blPower, brPower, forwardy, strafey;
    boolean leftBumper;



    public PainDrive(DcMotor fle, DcMotor fre, DcMotor ble, DcMotor bre){
        this.fl = fle;
        this.fr = fre;
        this.bl = ble;
        this.br = bre;
    }

    public void assignPainDrive(DcMotor fle, DcMotor fre, DcMotor ble, DcMotor bre){
        this.fl = fle;
        this.fr = fre;
        this.bl = ble;
        this.br = bre;
    }


    public void drivetrainInputs(double rightStickXe, double leftTriggere, double rightTriggere, boolean leftBumpere){
        this.leftTrigger = leftTriggere;
        this.rightStickX = rightStickXe;
        this.rightTrigger = rightTriggere;
        this.leftBumper = leftBumpere;
    }

    public void weBeCryin(){

        strafey = ((2* leftTrigger)-1);
        forwardy = ((2* rightTrigger)-1);

        flPower = strafey + forwardy + this.rightStickX;
        frPower = strafey - forwardy + this.rightStickX;
        blPower = -strafey + forwardy + this.rightStickX;
        brPower = -strafey - forwardy + this.rightStickX;


        if (this.leftBumper == true){
            flPower/=5;
            frPower/=5;
            blPower/=5;
            brPower/=5;
        }

        fl.setPower(flPower);
        fr.setPower(frPower);
        bl.setPower(blPower);
        br.setPower(brPower);
    }
}
