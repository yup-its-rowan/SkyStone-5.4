package org.firstinspires.ftc.teamcode.YungGravy;

public final class UsefulMath {

    public static double AngleWrap(double angle){
        while (angle > 180){
            angle -= 360;
        }
        while (angle < -180){
            angle +=360;
        }
        return angle;
    }
}
