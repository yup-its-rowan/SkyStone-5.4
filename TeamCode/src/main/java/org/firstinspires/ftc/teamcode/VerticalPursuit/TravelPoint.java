package org.firstinspires.ftc.teamcode.VerticalPursuit;

public class TravelPoint {
    public double xPos;
    public double yPos;
    public double heading;
    public double moveSpeed;
    public double triggerRadius;

    public TravelPoint(double xPos, double yPos, double heading, double moveSpeed, double triggerRadius){
        this.xPos = xPos;
        this.yPos = yPos;
        this.heading = heading;
        this.moveSpeed = moveSpeed;
        this.triggerRadius = triggerRadius;
    }

    public TravelPoint(TravelPoint travelPoint){
        this.heading = travelPoint.heading;
        this.xPos = travelPoint.xPos;
        this.yPos = travelPoint.yPos;
        this.moveSpeed = travelPoint.moveSpeed;
    }
}
