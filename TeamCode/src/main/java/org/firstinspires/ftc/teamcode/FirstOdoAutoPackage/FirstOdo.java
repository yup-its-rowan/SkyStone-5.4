package org.firstinspires.ftc.teamcode.FirstOdoAutoPackage;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FirstOdo {

    DcMotor left, right, middle;
    int deltal, deltar, deltam;
    int l2 = 0, r2 = 0, m2 = 0;
    int l3 = 0, r3 = 0, m3 = 0;
    double oldHeading = 0;
    //fl is left encoder
    //fr is right encoder
    //bl is middle encoder

    double localDeltaX, localDeltaY;
    double globalDeltaX = 0, globalDeltaY = 0;

    double WIDTH_BETWEEN_ENCODERS = 12.825;
    double TICKS_PER_RADIAN;
    int COUNTS_PER_REV = 8192;
    double WHEEL_DIAMETER =  1.96;

    public static double globalX = 0;
    public static double globalY = 0;
    public static double heading = 0;



    public FirstOdo(){}

    public void init(HardwareMap hardwareMap){
        this.left = hardwareMap.dcMotor.get("i1");
        this.right = hardwareMap.dcMotor.get("i2");
        this.middle = hardwareMap.dcMotor.get("sl");
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
        double deltaHeading = heading - oldHeading;

        deltal = l3 - l2;
        deltar = r3 - r2;
        deltam = m3 - m2;

        double localDeltaYf = ((deltal+deltar)/2);
        double adjustedDeltaXf =  deltam - deltaHeading*TICKS_PER_RADIAN;
        localDeltaX = ticksToInches(adjustedDeltaXf);
        localDeltaY = ticksToInches(localDeltaYf);

        globalDeltaX = ((localDeltaX*Math.cos(heading))+(localDeltaY*Math.sin(heading)));
        globalDeltaY = ((localDeltaY*Math.cos(heading))-(localDeltaX*Math.sin(heading)));

        globalX =+ globalDeltaX;
        globalY =+ globalDeltaY;

        reset();
    }

    public double robotHeading(){
        double temp = ((ticksToInches(l3)-ticksToInches(r3)) /WIDTH_BETWEEN_ENCODERS);
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
        oldHeading = heading;
    }

}
