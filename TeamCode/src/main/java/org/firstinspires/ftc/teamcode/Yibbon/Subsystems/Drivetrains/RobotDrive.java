package org.firstinspires.ftc.teamcode.Yibbon.Subsystems.Drivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotDrive {
    private DcMotor fl, fr, bl, br;
    double leftStickX, leftStickY, rightStickX, flPower, frPower, blPower, brPower;
    Gamepad gamepad1, gamepad2;


    public RobotDrive(){
    }

    public void init(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2){
        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        resetEncoders();
        runUsingEncoder();

    }

    public void driveInputs(){
        weBeDrivin();
    }

    public void resetEncoders(){
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runUsingEncoder(){
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runWithoutEncoders(){
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void weBeDrivin(){

        flPower = this.gamepad1.left_stick_x - this.gamepad1.left_stick_y + this.gamepad1.right_stick_x;
        frPower = this.gamepad1.left_stick_x + this.gamepad1.left_stick_y + this.gamepad1.right_stick_x;
        blPower = -this.gamepad1.left_stick_x - this.gamepad1.left_stick_y + this.gamepad1.right_stick_x;
        brPower = -this.gamepad1.left_stick_x + this.gamepad1.left_stick_y + this.gamepad1.right_stick_x;

        if (this.gamepad1.left_bumper == true){
            flPower/=4;
            frPower/=4;
            blPower/=4;
            brPower/=4;
        } else if (this.gamepad1.right_bumper == true){
            flPower/=7;
            frPower/=7;
            blPower/=7;
            brPower/=7;
        }

        fl.setPower(flPower);
        fr.setPower(frPower);
        bl.setPower(blPower);
        br.setPower(brPower);
    }
}
