package org.firstinspires.ftc.teamcode.Yin;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Yin extends OpMode {

    DcMotor fl, bl, br, fr, in1, in2, sl1, sl2;
    Servo li1, li2, grab;

    YinDT drivetrain = new YinDT(null, null, null, null);
    Yintake intake = new Yintake(null, null);
    Yinslide slides = new Yinslide(null, null);
    Yinout scorer = new Yinout(null, null, null);
    YinOdo odo = new YinOdo(null, null, null);

    public Yin (){}

    @Override
    public void init() {
        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");
        in1 = hardwareMap.dcMotor.get("in1");
        in2 = hardwareMap.dcMotor.get("in2");
        sl1 = hardwareMap.dcMotor.get("sl1");
        sl2 = hardwareMap.dcMotor.get("sl2");

        li1 = hardwareMap.servo.get("li1");
        li2 = hardwareMap.servo.get("li2");
        grab = hardwareMap.servo.get("grab");

        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.drivetrain.dtReset(fl, fr, bl, br);
        this.intake.takereset(in1,in2);
        this.slides.slidereset(sl1, sl2);
        this.scorer.outreset(li1, li2, grab);
        this.odo.odoReset(fl, fr, bl);

    }

    @Override
    public void loop() {

    }

    public YinDT getDrivetrain(){
        return drivetrain;
    }

    public Yinout getScorer(){
        return scorer;
    }

    public Yintake getIntake(){
        return intake;
    }

    public Yinslide getSlides(){
        return slides;
    }

    public YinOdo getOdo(){
        return odo;
    }

    public double getX(){
        return odo.globalX;
    }

    public double getY(){
        return odo.globalY;
    }

    public double getAngle(){
        return odo.robotHeading();
    }


}
