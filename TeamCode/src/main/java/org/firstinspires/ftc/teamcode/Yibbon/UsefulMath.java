package org.firstinspires.ftc.teamcode.Yibbon;

public final class UsefulMath {

    public static double AngleWrap(double angle){
        if (angle > 360){
            angle -= 360;
        } else if (angle < 0){
            angle +=360;
        }
        return angle;
    }
}
