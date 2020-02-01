package org.firstinspires.ftc.teamcode.Yin;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Yinslide extends OpMode{

    DcMotor sl1, sl2;

    public Yinslide(DcMotor sl1, DcMotor sl2){this.sl1 = sl1; this.sl2 = sl2;}

    public void slidereset(DcMotor sl1, DcMotor sl2){this.sl1 = sl1; this.sl2 = sl2;}

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}
