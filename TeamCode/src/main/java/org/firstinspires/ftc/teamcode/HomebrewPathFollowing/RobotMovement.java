package org.firstinspires.ftc.teamcode.HomebrewPathFollowing;

import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;

import static org.firstinspires.ftc.teamcode.HomebrewPathFollowing.CurrentPosition.worldHeading;
import static org.firstinspires.ftc.teamcode.HomebrewPathFollowing.CurrentPosition.worldXPosition;
import static org.firstinspires.ftc.teamcode.HomebrewPathFollowing.CurrentPosition.worldYPosition;
import static org.firstinspires.ftc.teamcode.HomebrewPathFollowing.FuturePosition.futureWorldXPosition;
import static org.firstinspires.ftc.teamcode.HomebrewPathFollowing.FuturePosition.futureWorldYPosition;
import static org.firstinspires.ftc.teamcode.HomebrewPathFollowing.MathFunc.AngleWrap;

public class RobotMovement {

    public void followPath(ArrayList <MovementPoint> allPoints){
        double homerunValue = 100000000;


        for (int i = 0; i < allPoints.size()-1; i++){
            double slope = 0;
            double differenceInY = (allPoints.get(i+1).targetY - allPoints.get(i).targetY);
            double differenceInX = (allPoints.get(i+1).targetX - allPoints.get(i).targetX);
            if (differenceInX < 0.003){
                differenceInX = 0.003;
            }

            slope = differenceInY/differenceInX;

            if (slope == 0){
                slope = 0.003;
            }

            double inverseM = (-1)/slope;
            double tempFutureY = 0;
            if (futureWorldYPosition == 0){
                tempFutureY = 0.003;
            } else {
                tempFutureY = futureWorldYPosition;
            }
            double b = inverseM*futureWorldXPosition/tempFutureY;


        }
        /*
        if (distanceOfNormal < homerunValue){
            homerunValue = distanceOfNormal;
            targetPoint = checkPoint; //distanceOfNormal is linked to checkPoint in how its calculated
        }

        goToPosition(targetPoint);
        goToPosition(allPoints.get(0).targetX, allPoints.get(0).targetY, allPoints.get(0).targetHeading, allPoints.get(0).movementSpeed, allPoints.get(0).turnSpeed);


         */
    }

    public static void goToPosition(double x, double y, double preferredAngle, double movementSpeed, double turnSpeed){

        double distanceToTarget = Math.hypot(x-worldXPosition, y-worldYPosition);

        double absoluteAngleToTarget = Math.atan2(y-worldYPosition, x-worldXPosition);

        double relativeAngleToPoint = AngleWrap(absoluteAngleToTarget - (worldHeading- Math.toRadians(90)));


        double relativeXToPoint = Math.cos(relativeAngleToPoint) * distanceToTarget;
        double relativeYToPoint = Math.sin(relativeAngleToPoint) * distanceToTarget;

        double movementXPower = relativeXToPoint/(Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));
        double movementYPower = relativeYToPoint/(Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));

        double fieldMovement_x = movementXPower * movementSpeed;
        double fieldMovement_y = movementYPower * movementSpeed;

        double relativeTurnAngle = relativeAngleToPoint - Math.toRadians(180) + preferredAngle;
        double fieldMovement_turn = Range.clip(relativeTurnAngle/(Math.toRadians(30)), -1, 1) * turnSpeed;

        if (distanceToTarget < 3){
            fieldMovement_turn = 0;
        }

    }

}
