package org.firstinspires.ftc.teamcode.VerticalPursuit;

import static java.lang.Math.pow;
import static java.lang.Math.tan;

public class SplineGeneration {

    public SplineGeneration(TravelPoint firstPoint, TravelPoint secondPoint){

        double x1 = firstPoint.xPos;                            double y1 = firstPoint.yPos;
        double x2 = secondPoint.xPos;                           double y2 = secondPoint.yPos;
        double m1 = headingToSlope(firstPoint.heading);         double m2 = headingToSlope(secondPoint.heading);

        double a = aCalc(x1,y1,m1,x2,y2,m2);
        double b = bCalc(x1,y1,m1,x2,y2,m2);
        double c = cCalc(x1,y1,m1,x2,y2,m2);
        double d = dCalc(x1,y1,m1,x2,y2,m2);

    }

    private double headingToSlope(double heading){
        double slope = 0;
        if ((heading + 90 != 0) && (heading + 90 != 180)){
            slope = tan(Math.toRadians(heading+90));
        } else {
            slope = 1000;
        }

        return slope;
    }

    private double aCalc(double x1, double y1, double m1, double x2, double y2, double m2){
        double top = -(-2*y2 + 2*y1 + (m2 + m1)*x2 + (-m2 - m1)*x1);
        double bottom = -(pow(x2,3)) + (3*x1*pow(x2,2)) - (3*x2*pow(x1,2)) + pow(x1, 3);
        double a = top/bottom;
        return a;
    }

    private double bCalc(double x1, double y1, double m1, double x2, double y2, double m2){
        double top = -3*x2*y2 + x1*((m2 - m1)*x2 - 3*y2) +(3*x2 + 3*x1)*y1 + (m2 +2*m1)*pow(x2,2) +(-2*m2-m1)*pow(x1,2);
        double bottom = -(pow(x2,3)) + 3*x1*pow(x2,2) - 3*pow(x1,2)*x2 + pow(x1,3);
        double b = top/bottom;
        return b;
    }

    private double cCalc(double x1, double y1, double m1, double x2, double y2, double m2){
        double top = (x1*((2*m2+m1)*pow(x2,2)-6*x2*y2)+6*x1*x2*y1+m1*pow(x2,3)+(-m2-2*m1)*pow(x1,2)*x2-m2*pow(x1,3));
        double bottom = -(pow(x2,3)) + 3*x1*pow(x2,2) - 3*pow(x1,2)*x2 + pow(x1,3);
        double c = -top/bottom; //REMEMBER THE NEGATIVE
        return c;
    }

    private double dCalc(double x1, double y1, double m1, double x2, double y2, double m2){
        double top = (pow(x1,2)*((m2-m1)*pow(x2,2)-3*x2*y2)+pow(x1,3)*y2-m2*x2)+(3*x1*pow(x2,2)-pow(x2,3)*y1+m1*x1*pow(x2,3));
        double bottom = -(pow(x2,3)) + 3*x1*pow(x2,2) - 3*pow(x1,2)*x2 + pow(x1,3);
        double d = top/bottom;
        return d;
    }


}
