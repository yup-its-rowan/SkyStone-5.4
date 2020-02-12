package org.firstinspires.ftc.teamcode.FirstOdoAutoPackage;

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

    double WIDTH_BETWEEN_ENCODERS = 12.825;
    double OFFSET_OF_MIDDLE_WHEEL;
    int COUNTS_PER_REV = 8192;
    double WHEEL_DIAMETER =  1.96;

    public static double globalX = 0;
    public static double globalY = 0;
    public static double heading = 0;



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

        heading = robotHeading();

        deltal = l3 - l2;
        deltar = r3 - r2;
        deltam = m3 - m2;

        double localDeltaYf = ((deltal+deltar)/2);
        localDeltaX = ticksToInches(deltam);
        localDeltaY = ticksToInches(localDeltaYf);

        globalDeltaX = ((localDeltaX*Math.cos(heading))+(localDeltaY*Math.sin(heading)));
        globalDeltaY = ((localDeltaY*Math.cos(heading))-(localDeltaX*Math.sin(heading)));

        globalX =+ globalDeltaX;
        globalY =+ globalDeltaY;

        reset();
    }

    public double robotHeading(){
        double temp = ((ticksToInches(l3)-ticksToInches(r3))
                /WIDTH_BETWEEN_ENCODERS);
        return temp;
    }


    public double ticksToInches(double ticks){
        double temp = (ticks*Math.PI*WHEEL_DIAMETER);
        double temp2 = temp/COUNTS_PER_REV;
        return temp2;
    }

    public void reset(){
        l2 = l3;
        r2 = r3;
        m2 = m3;
    }

}
