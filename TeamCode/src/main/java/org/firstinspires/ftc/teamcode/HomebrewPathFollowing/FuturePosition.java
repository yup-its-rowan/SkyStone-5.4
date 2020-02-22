package org.firstinspires.ftc.teamcode.HomebrewPathFollowing;

import static org.firstinspires.ftc.teamcode.HomebrewPathFollowing.CurrentPosition.worldXPosition;
import static org.firstinspires.ftc.teamcode.HomebrewPathFollowing.CurrentPosition.worldYPosition;
import static org.firstinspires.ftc.teamcode.HomebrewPathFollowing.PreviousPosition.oldWorldXPosition;
import static org.firstinspires.ftc.teamcode.HomebrewPathFollowing.PreviousPosition.oldWorldYPosition;

public class FuturePosition {
    public static double futureWorldXPosition;
    public static double futureWorldYPosition;

    public FuturePosition (double futureWorldXPosition, double futureWorldYPosition){

        this.futureWorldXPosition = futureWorldXPosition;
        this.futureWorldYPosition = futureWorldYPosition;
    }

    public void findFuturePos(){
        double deltaX = worldXPosition - oldWorldXPosition;
        double deltaY = worldYPosition - oldWorldYPosition;

        futureWorldXPosition = worldXPosition + deltaX;
        futureWorldYPosition = worldYPosition + deltaY;
    }
}
