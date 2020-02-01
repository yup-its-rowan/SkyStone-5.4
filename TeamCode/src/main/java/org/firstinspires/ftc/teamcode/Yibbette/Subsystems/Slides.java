package org.firstinspires.ftc.teamcode.Yibbette.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Slides {

    private boolean dup, ddown;
    private DcMotor s1, s2;

    public Slides(DcMotor s1, DcMotor s2){
        this.s1 = s1;
        this.s2 = s2;
    }

    public void assignSlides(DcMotor s1, DcMotor s2){
        this.s1 = s1;
        this.s2 = s2;
    }

    public void slideInputs(boolean dup, boolean ddown){
        this.dup = dup;
        this.ddown = ddown;
        slideCheck();
    }

    public void slideInputs2(boolean dup, boolean ddown){
        this.dup = dup;
        this.ddown = ddown;
        slideCheck2();
    }

    public void slideCheck(){
        if (dup){
            if (s1.getCurrentPosition() < 5782){
                s1.setPower(1);
                s2.setPower(-1);
            }
        } else if (ddown){
            s1.setPower(-0.5);
            s2.setPower(0.5);
        } else {
            s1.setPower(0);
            s2.setPower(0);
        }
    }

    public void slideCheck2(){
        if (dup){
            if (s1.getCurrentPosition() < 5780){
                s1.setPower(0.7);
                s2.setPower(-0.7);
            }
        } else if (ddown){
            if (s1.getCurrentPosition() > 0){
                s1.setPower(-0.3);
                s2.setPower(0.3);
            }
        } else {
            s1.setPower(0);
            s2.setPower(0);
        }
    }
}
