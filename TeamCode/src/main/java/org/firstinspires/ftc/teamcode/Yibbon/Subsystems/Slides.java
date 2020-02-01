package org.firstinspires.ftc.teamcode.Yibbon.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Slides {

    private DcMotor s1, s2;
    HardwareMap hardwareMap;
    Gamepad gamepad1, gamepad2;
    private DigitalChannel slideSwitch;

    public Slides(){
    }

    public void init(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2){
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.s1 = hardwareMap.dcMotor.get("sl");
        this.s2 = hardwareMap.dcMotor.get("sr");
        this.slideSwitch = hardwareMap.digitalChannel.get("to");
        slideSwitch.setMode(DigitalChannel.Mode.INPUT);
        s1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        s1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        s1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        s2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void slideInputs(){
        slideCheck();
    }

    public void slideInputs2(){
        slideCheck2();
    }

    public void slideCheck(){
        if (gamepad1.dpad_up){
            if (s1.getCurrentPosition() < 5782){
                s1.setPower(1);
                s2.setPower(-1);
            }
        } else if (gamepad1.dpad_down){
            s1.setPower(-0.6);
            s2.setPower(0.6);
        } else {
            s1.setPower(0);
            s2.setPower(0);
        }
    }
    double zeroPoint = 0;

    public void slideCheck2(){
        double SLIDE_MAX_HEIGHT = 5780 + zeroPoint;

        if (gamepad2.right_trigger > 0.1){
            if (s1.getCurrentPosition() < SLIDE_MAX_HEIGHT){
                //up
                s1.setPower(0.9);
                s2.setPower(-0.9);
            }
        } else if (gamepad2.left_trigger > 0.1 && slideSwitch.getState()){
            s1.setPower(-0.6);
            s2.setPower(0.6);

        } else{
            s1.setPower(0);
            s2.setPower(0);
        }

        if (!slideSwitch.getState()){
            zeroPoint = s1.getCurrentPosition();
        }
    }

    private double slideHeight(){
        return s1.getCurrentPosition() - zeroPoint;
    }

    public double slideHeightMovementDampener(){
        if (slideHeight() < 800){
            return 1;
        } else {
            double coefficient = 1 - (slideHeight()-800)/5300;
            return coefficient;
        }

        //ramps from 0 to 5300

    }


}
