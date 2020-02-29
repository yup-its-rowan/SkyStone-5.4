package org.firstinspires.ftc.teamcode.VerticalPursuit;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class PositionTracking {
    public static int leftEncoderVal, rightEncoderVal;
    public static double globalX = 0, globalY = 0, globalT = 0;
    private double leftOld = 0, rightOld = 0;

    public void init(int left, int right){
        leftOld = left;
        rightOld = right;
    }

    public void updateTicks(int left, int right){
        leftEncoderVal = left;
        rightEncoderVal = right;
        positionUpdate();
    }

    private double WIDTH_BETWEEN_ENCODERS = 14.138271276;
    public void positionUpdate(){
        globalT = (ticksToInches(rightEncoderVal) - ticksToInches(leftEncoderVal))/WIDTH_BETWEEN_ENCODERS;
        double leftDeltaTicks = ticksToInches(leftEncoderVal - leftOld);
        double rightDeltaTicks = ticksToInches(rightEncoderVal - rightOld);
        double robotDeltaY = (leftDeltaTicks + rightDeltaTicks)/2;
        double globalDeltaX = -robotDeltaY*sin(globalT);
        double globalDeltaY = robotDeltaY*cos(globalT);
        globalX = globalX + globalDeltaX;
        globalY = globalY + globalDeltaY;
        reset();
    }

    private void reset(){
        leftOld = leftEncoderVal;
        rightOld = rightEncoderVal;
    }

    private double WHEEL_DIAMETER = 1.9685; //inches
    private double COUNTS_PER_REV = 8192; //ticks
    public double ticksToInches(double ticks){
        double temp = (ticks*Math.PI*WHEEL_DIAMETER);
        double inches = temp/COUNTS_PER_REV;
        return inches;
    }

    public PositionTracking(){

    }



}
