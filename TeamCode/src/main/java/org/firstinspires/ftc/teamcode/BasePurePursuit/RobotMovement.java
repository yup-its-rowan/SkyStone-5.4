package org.firstinspires.ftc.teamcode.BasePurePursuit;


//import static com.company.GyroRobot.*;
import com.qualcomm.robotcore.util.Range;

import org.opencv.core.Point;

import java.util.ArrayList;

import static org.firstinspires.ftc.teamcode.BasePurePursuit.BlankOdo.worldHeading;
import static org.firstinspires.ftc.teamcode.BasePurePursuit.BlankOdo.worldX;
import static org.firstinspires.ftc.teamcode.BasePurePursuit.BlankOdo.worldY;
import static org.firstinspires.ftc.teamcode.BasePurePursuit.HandlingMotorPowers.fieldX;
import static org.firstinspires.ftc.teamcode.BasePurePursuit.HandlingMotorPowers.fieldY;
import static org.firstinspires.ftc.teamcode.BasePurePursuit.HandlingMotorPowers.fieldTurn;
import static org.firstinspires.ftc.teamcode.BasePurePursuit.MathFunctions.AngleWrap;
import static org.firstinspires.ftc.teamcode.BasePurePursuit.MathFunctions.lineCircleIntersection;

public class RobotMovement {


    public static void followCurve(ArrayList<CurvePoint> allPoints, double followAngle){

        CurvePoint followMe = getFollowPointPath(allPoints, new Point(worldX, worldY), allPoints.get(0).followDistance);
        goToPosition(followMe.x, followMe.y, followMe.moveSpeed, followAngle, followMe.turnSpeed);
    }


    public static CurvePoint getFollowPointPath(ArrayList<CurvePoint> pathPoints, Point robotLocation, double followRadius){
        CurvePoint followMe = new CurvePoint(pathPoints.get(0));

        for(int i = 0; i < pathPoints.size() - 1; i ++){
            CurvePoint startLine = pathPoints.get(i);
            CurvePoint endLine = pathPoints.get(i+1);

            ArrayList<Point> intersections = lineCircleIntersection(robotLocation, followRadius, startLine.toPoint(), endLine.toPoint());

            double closestAngle =  100000000;

            for(Point thisIntersection : intersections){
                double angle = Math.atan2(thisIntersection.y - worldY, thisIntersection.x - worldX);
                double deltaAngle = Math.abs(MathFunctions.AngleWrap((angle - worldHeading)));

                if (deltaAngle < closestAngle){
                    closestAngle = deltaAngle;
                    followMe.setPoint(thisIntersection);
                }
            }
        }
        return followMe;
    }

 

    public static void goToPosition(double x, double y, double movementSpeed, double preferredAngle, double turnSpeed){

        double distanceToTarget = Math.hypot(x-worldX, y-worldY);

        double absoluteAngleToTarget = Math.atan2(y-worldY, x-worldX);

        double relativeAngleToPoint = AngleWrap(absoluteAngleToTarget - (worldHeading- Math.toRadians(90)));


        double relativeXToPoint = Math.cos(relativeAngleToPoint) * distanceToTarget;
        double relativeYToPoint = Math.sin(relativeAngleToPoint) * distanceToTarget;

        double movementXPower = relativeXToPoint/(Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));
        double movementYPower = relativeYToPoint/(Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));

        fieldX = movementXPower * movementSpeed;
        fieldY = movementYPower * movementSpeed;

        double relativeTurnAngle = relativeAngleToPoint - Math.toRadians(180) + preferredAngle;
        fieldTurn = Range.clip(relativeTurnAngle/(Math.toRadians(30)), -1, 1) * turnSpeed;

        if (distanceToTarget < 10){
            fieldTurn = 0;
        }



    }


}
