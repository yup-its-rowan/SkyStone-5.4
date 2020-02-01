package org.firstinspires.ftc.teamcode.Yibbette;

import android.os.Environment;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GyroAutoAngleTracker {

    BNO055IMU imu;
    Orientation angles;
    double angleToSave, angleToGet;
    String stringyyy, finalString;

    public GyroAutoAngleTracker (BNO055IMU imuu){
        this.imu = imuu;
    }

    public void assignGyroTracker (BNO055IMU imuu){
        this.imu = imuu;
    }

    public void saveGyroAngle(){
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        angleToSave = Math.toRadians(angles.firstAngle);
        stringyyy = Double.toString(angleToSave);
        try {
            File rooty = new File(Environment.getExternalStorageDirectory(), "blarg");

            if (!rooty.exists()){
                rooty.mkdir();
            }

            File filepath = new File(rooty, "angleSave.txt");

            FileWriter writer = new FileWriter(filepath);
            writer.append(stringyyy);
            writer.flush();
            writer.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public double getPrevGyroAngle(){
        File rooty = new File(Environment.getExternalStorageDirectory(), "blarg");
        if (!rooty.exists()){
        }
        File filepath = new File(rooty, "angleSave.txt");

        String temp="";
        try{
            Scanner in = new Scanner(filepath);
            finalString = in.nextLine();
        } catch(IOException e) {
            e.printStackTrace();
        }

        angleToGet = Double.valueOf(finalString);
        return angleToGet;
    }
}
