package org.firstinspires.ftc.teamcode.Yibbette.Subsystems.Drivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;

public class RobotDrive {
    private DcMotor fl, fr, bl, br;
    double leftStickX, leftStickY, rightStickX, flPower, frPower, blPower, brPower;
    boolean leftBumper;



    public RobotDrive(DcMotor fle, DcMotor fre, DcMotor ble, DcMotor bre){
        this.fl = fle;
        this.fr = fre;
        this.bl = ble;
        this.br = bre;
    }

    public void assignRobotDrive(DcMotor fle, DcMotor fre, DcMotor ble, DcMotor bre){
        this.fl = fle;
        this.fr = fre;
        this.bl = ble;
        this.br = bre;
    }

    public void drivetrainInputs(double leftStickXe, double leftStickYe, double rightStickXe){
        this.leftStickX = leftStickXe;
        this.rightStickX = rightStickXe;
        this.leftStickY = -leftStickYe;
    }

    public void drivetrainInputs(double leftStickXe, double leftStickYe, double rightStickXe, boolean leftBumpere){
        this.leftStickX = leftStickXe;
        this.rightStickX = rightStickXe;
        this.leftStickY = -leftStickYe;
        this.leftBumper = leftBumpere;
    }

    public void weBeDrivin(){

        flPower = this.leftStickX + this.leftStickY + this.rightStickX;
        frPower = this.leftStickX - this.leftStickY + this.rightStickX;
        blPower = -this.leftStickX + this.leftStickY + this.rightStickX;
        brPower = -this.leftStickX - this.leftStickY + this.rightStickX;

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
