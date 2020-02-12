package org.firstinspires.ftc.teamcode.FirstOdoAutoPackage;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;

public class RobotMovement {

    DcMotor fl, fr, bl, br;
    private double globalX, globalY, globalT;

    public RobotMovement(){}

    public void motorSet(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br){
        this.bl = bl;
        this.br = br;
        this.fl = fl;
        this.fr = fr;
    }

    public void movementUpdate(double globalX, double globalY, double globalT){
        this.globalT = globalT;
        this.globalX = globalX;
        this.globalY = globalY;
    }


    public void goToPosition2(double targetX, double targetY, double preferredHeading, double movementSpeed, double turnSpeed){

        if (preferredHeading == 400){
            preferredHeading = (Math.atan2(targetY- globalY, targetX-globalX)) - Math.toRadians(90);
        }

        double distanceToTarget = Math.hypot(targetX-globalX, targetY- globalY);
        double targetHeading;

        //optimizer
        if (distanceToTarget < 5){ //five is arbitrary
            targetHeading = preferredHeading;
        } else {
            targetHeading = (Math.atan2(targetY- globalY, targetX-globalX))
                    - Math.toRadians(90);
        }

        //deltas for stuff later
        double deltaX = targetX - globalX;
        double deltaY = targetY - globalY;
        double deltaHeading = targetHeading - globalT;

        //this keeps everything from breaking DO NOT REMOVE IT
        if (deltaX == 0){
            deltaX = 0.0001;
        } else if (deltaY == 0){
            deltaY = 0.0001;
        } else if (deltaHeading == 0){
            deltaY = 0.0001;
        }

        //circ
        double snippedX = deltaX/(Math.abs(deltaX) + Math.abs(deltaY));
        double snippedY = deltaY/(Math.abs(deltaX) + Math.abs(deltaY));
        double snippedHeading = Range.clip(deltaHeading/(Math.toRadians(30)), -1, 1); //30 degrees is also arbitary still cant spell it

        double x2 = movementSpeed * (snippedX*Math.cos(globalT) + snippedY*Math.sin(globalT));
        double y2 = movementSpeed * (snippedY*Math.cos(globalT) - snippedX*Math.sin(globalT));
        double z2 = turnSpeed * snippedHeading;

        double flPower = x2 + y2 + z2;
        double blPower = -x2 + y2 + z2;
        double frPower = x2 - y2 + z2;
        double brPower = -x2 - y2 + z2;

        fl.setPower(flPower);
        bl.setPower(blPower);
        fr.setPower(frPower);
        br.setPower(brPower);
    }

    int tickerFollow = 0;

    public boolean followPath(ArrayList<Waypoint> allPoints){
        if ((tickerFollow+1) < allPoints.size()){
            if (inTriggerZone(allPoints.get(tickerFollow))){ tickerFollow++;}
            Waypoint targetPoint = allPoints.get(tickerFollow);
            goToPosition2(targetPoint.x(), targetPoint.y(), targetPoint.targetAngle(),
                    targetPoint.moveSpeed(), targetPoint.turnSpeed());
            return true;
        } else {
            return false;
        }
    }

    public boolean inTriggerZone(Waypoint waypoint){
        boolean isIt;
        double distanceToTarget = Math.hypot(waypoint.x()-globalX, waypoint.y()-globalY);

        if (distanceToTarget < waypoint.triggerRadius()){
            isIt = true;
        } else {
            isIt = false;
        }
        return isIt;
    }
}
