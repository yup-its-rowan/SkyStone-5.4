package org.firstinspires.ftc.teamcode.YungGravy;

public class ServoCache {

    public ServoCache(){}

    private double cachedPosition = 0;

    public boolean cache(double position){
        boolean setNewPosition = false;

        if (position != cachedPosition){
            setNewPosition = true;
            cachedPosition = position;
        } else if (position == cachedPosition){ //DO NOT TOUCH THIS ORDER OR ELSE IT WILL BREAK
            setNewPosition = false;
        }

        return setNewPosition;
    }

}
