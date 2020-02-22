package org.firstinspires.ftc.teamcode.HomebrewPathFollowing;

public class MathFunc {

    public static double AngleWrap(double angle){
        while(angle < -Math.PI){
            angle += Math.PI;
        }
        while(angle > Math.PI){
            angle -= Math.PI;
        }
        return angle;
    }

}
