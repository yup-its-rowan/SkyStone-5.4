package org.firstinspires.ftc.teamcode.YungGravy;

public final class UsefulMath {

    public static double AngleWrap(double angle){
        while (angle > 360){
            angle -= 360;
        }
        while (angle < 0){
            angle +=360;
        }
        return angle;
    }
}
