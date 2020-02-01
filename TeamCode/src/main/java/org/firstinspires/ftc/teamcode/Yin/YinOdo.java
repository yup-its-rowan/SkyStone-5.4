package org.firstinspires.ftc.teamcode.Yin;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class YinOdo extends OpMode {

    DcMotor left, right, middle;
    int deltal, deltar, deltam;
    int l2 = 0, r2 = 0, m2 = 0;
    int l3 = 0, r3 = 0, m3 = 0;
    //fl is left encoder
    //fr is right encoder
    //bl is middle encoder

    double localDeltaX, localDeltaY;
    double globalDeltaX, globalDeltaY;
    double theta;
    double WIDTH_BETWEEN_ENCODERS = 34.724;
    int COUNTS_PER_REV = 8192;
    double WHEEL_DIAMETER =  5;

    double globalX = 0;
    double globalY = 0;



    public YinOdo(DcMotor fl, DcMotor fr, DcMotor bl){this.left = fl; this.right = fr; this.middle = bl;}

    public void odoReset(DcMotor fl, DcMotor fr, DcMotor bl){this.left = fl; this.right = fr; this.middle = bl;}

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        l3 = left.getCurrentPosition();
        r3 = right.getCurrentPosition();
        m3 = middle.getCurrentPosition();

        theta = robotHeading();

        deltal = l3 - l2;
        deltar = r3 - r2;
        deltam = m3 - m2;

        localDeltaX = ticksToCentimeters(deltam);
        localDeltaY = ticksToCentimeters(localDeltaXf());

        globalDelta();

        globalX =+ globalDeltaX;
        globalY =+ globalDeltaY;

        telemetryglobal();

        reset();
    }

    public double robotHeading(){
        double temp = ((l3-r3)/WIDTH_BETWEEN_ENCODERS);
        return temp;
    }

    public void telemetryglobal(){
        telemetry.addData("Global X", globalX);
        telemetry.addData("Global Y", globalY);
    }

    public double localDeltaXf(){
        double temp = ((deltal+deltar)/2);
        return temp;
    }

    public double ticksToCentimeters(double ticks){
        double temp = (ticks*Math.PI*WHEEL_DIAMETER);
        double temp2 = temp/8192;
        return temp2;
    }

    public void globalDelta(){
        globalDeltaX = ((localDeltaX*Math.cos(theta))+(localDeltaY*Math.sin(theta)));
        globalDeltaY = ((localDeltaY*Math.cos(theta))-(localDeltaX*Math.sin(theta)));
    }

    public void reset(){
        l2 = l3;
        r2 = r3;
        m2 = m3;
    }

}
