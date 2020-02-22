package org.firstinspires.ftc.teamcode.HomebrewPathFollowing;

public class MovementPoint {

    public double targetX, targetY, targetHeading, movementSpeed, turnSpeed, followDistance;

    public MovementPoint (double targetX, double targetY, double targetHeading, double movementSpeed, double turnSpeed, double followDistance){
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetHeading = targetHeading;
        this.movementSpeed = movementSpeed;
        this.turnSpeed = turnSpeed;
        this.followDistance = followDistance;
    }

    public MovementPoint (MovementPoint movePoint){
        this.targetX = movePoint.targetX;
        this.targetY = movePoint.targetY;
        this.targetHeading = movePoint.targetHeading;
        this.movementSpeed = movePoint.movementSpeed;
        this.turnSpeed = movePoint.turnSpeed;
        this.followDistance = movePoint.followDistance;
    }
}
