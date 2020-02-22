package org.firstinspires.ftc.teamcode.FirstOdoAutoPackage;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class IMUOdo {

    DcMotor vertical, horizontal;
    BNO055IMU imu;
    int deltav, deltah;
    int v2 = 0, h2 = 0;
    int v3 = 0, h3 = 0;
    //fl is left encoder
    //fr is right encoder
    //bl is middle encoder

    double localDeltaX, localDeltaY;
    double globalDeltaX = 0, globalDeltaY = 0;

    double OFFSET_OF_VERTICAL_WHEEL = 12.825;
    double OFFSET_OF_MIDDLE_WHEEL;
    int COUNTS_PER_REV = 8192;
    double WHEEL_DIAMETER =  1.96;

    public static double globalX = 0;
    public static double globalY = 0;
    public static double heading = 0;

    public IMUOdo(){}

    public void odoReset(HardwareMap hardwareMap){
        this.vertical = hardwareMap.dcMotor.get("i1");
        this.horizontal = hardwareMap.dcMotor.get("i2");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    public void setGlobalX(double X){
        this.globalX = X;
    }

    public void setGlobalY(double Y){
        this.globalY = Y;
    }

    public void odoloop() {
        v3 = vertical.getCurrentPosition();
        h3 = horizontal.getCurrentPosition();

        heading = robotHeading();

        deltav = v3 - v2;
        deltah = h3 - h2;

        double localDeltaYf = (deltav);
        localDeltaX = ticksToInches(deltah);
        localDeltaY = ticksToInches(localDeltaYf);

        globalDeltaX = ((localDeltaX*Math.cos(heading))+(localDeltaY*Math.sin(heading)));
        globalDeltaY = ((localDeltaY*Math.cos(heading))-(localDeltaX*Math.sin(heading)));

        globalX =+ globalDeltaX;
        globalY =+ globalDeltaY;

        reset();
    }

    public double robotHeading(){
        Orientation angles   = imu.getAngularOrientation(AxesReference.INTRINSIC,
                AxesOrder.ZYX, AngleUnit.RADIANS);
        double pose = (angles.firstAngle);
        return pose;
    }


    public double ticksToInches(double ticks){
        double temp = (ticks*Math.PI*WHEEL_DIAMETER);
        double temp2 = temp/COUNTS_PER_REV;
        return temp2;
    }

    public void reset(){
        v2 = v3;
        h2 = h3;
    }

}
