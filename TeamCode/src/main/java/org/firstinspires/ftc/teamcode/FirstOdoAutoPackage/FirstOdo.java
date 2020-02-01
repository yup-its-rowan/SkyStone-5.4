package org.firstinspires.ftc.teamcode.FirstOdoAutoPackage;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class FirstOdo {

    DcMotor left, right, middle;
    int deltal, deltar, deltam;
    int l2 = 0, r2 = 0, m2 = 0;
    int l3 = 0, r3 = 0, m3 = 0;
    //fl is left encoder
    //fr is right encoder
    //bl is middle encoder

    double localDeltaX, localDeltaY;
    double globalDeltaX = 0, globalDeltaY = 0;
    double theta;
    double WIDTH_BETWEEN_ENCODERS = 12.825;
    double OFFSET_OF_MIDDLE_WHEEL;
    int COUNTS_PER_REV = 8192;
    double WHEEL_DIAMETER =  1.96;

    double globalX = 0;
    double globalY = 0;



    public FirstOdo(DcMotor l, DcMotor r, DcMotor m){this.left = l; this.right = r; this.middle = m;}

    public void odoReset(DcMotor fl, DcMotor fr, DcMotor bl){this.left = fl; this.right = fr; this.middle = bl;}

    public  double getGlobalX(){
        return globalX;
    }

    public double getGlobalY(){
        return globalY;
    }

    public double getHeading(){
        return robotHeading();
    }

    public void setGlobalX(double X){
        this.globalX = X;
    }

    public void setGlobalY(double Y){
        this.globalY = Y;
    }



    public void odoloop() {
        l3 = left.getCurrentPosition();
        r3 = right.getCurrentPosition();
        m3 = middle.getCurrentPosition();

        theta = robotHeading();

        deltal = l3 - l2;
        deltar = r3 - r2;
        deltam = m3 - m2;

        localDeltaX = ticksToInches(deltam);
        localDeltaY = ticksToInches(localDeltaYf());

        globalDelta();

        globalX =+ globalDeltaX;
        globalY =+ globalDeltaY;


        reset();
    }

    public double robotHeading(){
        double temp = ((ticksToInches(l3)-ticksToInches(r3))/WIDTH_BETWEEN_ENCODERS);
        return temp;
    }


    public double localDeltaYf(){
        double temp = ((deltal+deltar)/2);
        return temp;
    }

    public double ticksToInches(double ticks){
        double temp = (ticks*Math.PI*WHEEL_DIAMETER);
        double temp2 = temp/COUNTS_PER_REV;
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
