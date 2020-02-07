package org.firstinspires.ftc.teamcode.YungGravy;

public class MotorCache {

    public MotorCache(){}

    private double cachedPower = 0;
    private double cacheLimit = 0.01;

    public boolean cache(double power){
        boolean setNewPower = false;
        double cacheMax = cachedPower*(1+cacheLimit);
        double cacheMin = cachedPower*(1-cacheLimit);

        if (power > 0 && cachedPower < 0){
            setNewPower = true;
            cachedPower = power;
        } else if (power < 0 && cachedPower > 0){ //DO NOT TOUCH THIS ORDER OR ELSE IT WILL BREAK
            setNewPower = true;
            cachedPower = power;
        } else if (power == 0 && cachedPower == 0){
            setNewPower = false;
        } else if (Math.abs(cacheMax) < Math.abs(power)){
            setNewPower = true;
            cachedPower = power;
        } else if (Math.abs(cacheMin) > Math.abs(power)){
            setNewPower = true;
            cachedPower = power;
        }

        return setNewPower;
    }

}
