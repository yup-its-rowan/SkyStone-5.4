package org.firstinspires.ftc.teamcode.Yin;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Yinout extends OpMode {

    Servo li1, li2, grab;

    public Yinout(Servo li1, Servo li2, Servo grab){this.li1 = li1; this.li2 = li2; this.grab = grab;}

    public void outreset(Servo li1, Servo li2, Servo grab){this.li1 = li1; this.li2 = li2; this.grab = grab;}

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}
