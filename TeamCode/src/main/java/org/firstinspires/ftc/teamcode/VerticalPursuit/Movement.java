package org.firstinspires.ftc.teamcode.VerticalPursuit;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

import static org.firstinspires.ftc.teamcode.VerticalPursuit.PositionTracking.globalT;
import static org.firstinspires.ftc.teamcode.VerticalPursuit.PositionTracking.globalX;
import static org.firstinspires.ftc.teamcode.VerticalPursuit.PositionTracking.globalY;

public class Movement {
    public Movement(){}
    DcMotor fl;
    DcMotor bl;
    DcMotor br;
    DcMotor fr;

    public void init(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br){
        this.fl = fl;
        this.fr = fr;
        this.bl = bl;
        this.br = br;
    }

    private int tickerFollow = 0;
    public void followPath(ArrayList<TravelPoint> allPoints){
        if (tickerFollow < allPoints.size()){
            if (tickerFollow+1 < allPoints.size()){
                if (inTriggerZone(allPoints.get(tickerFollow+1))){
                    tickerFollow++;
                }
            }
            powerHandler(allPoints.get(tickerFollow));
        } else {
            angleReset(allPoints.get(allPoints.size()-1));
        }
    }

    public boolean inTriggerZone(TravelPoint tP){
        boolean isIt;
        double distanceToTarget = Math.hypot(tP.xPos-globalX, tP.yPos-globalY);

        if (distanceToTarget < tP.triggerRadius){
            isIt = true;
        } else {
            isIt = false;
        }
        return isIt;
    }


    private void powerHandler(TravelPoint travelPoint){
        double leftPower = 0;
        double rightPower = 0;
        double absoluteAngleToTarget = Math.atan2(travelPoint.yPos-globalY, travelPoint.xPos-globalX);

        leftPower = leftPower + angleAdjustment(globalT, absoluteAngleToTarget);
        rightPower = rightPower - angleAdjustment(globalT, absoluteAngleToTarget);
        setPowers(leftPower * travelPoint.moveSpeed, rightPower * travelPoint.moveSpeed);
    }

    private void angleReset(TravelPoint travelPoint){
        double left = angleAdjustment(globalT, travelPoint.heading);
        double right = -angleAdjustment(globalT, travelPoint.heading);
        setPowers(left, right);
    }

    private double angleAdjustment (double currentAngle, double targetAngle){
        double powerAdj = 0;
        if (targetAngle != currentAngle){
            powerAdj = (targetAngle-currentAngle)/Math.toRadians(360);
        }
        return powerAdj;
    }

    private void setPowers (double left, double right){
        fl.setPower(left);
        bl.setPower(left);
        fr.setPower(-right);
        br.setPower(-right);
    }
}
