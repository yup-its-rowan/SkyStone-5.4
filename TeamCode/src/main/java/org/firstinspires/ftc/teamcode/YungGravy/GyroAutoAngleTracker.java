package org.firstinspires.ftc.teamcode.YungGravy;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public final class GyroAutoAngleTracker {

    public static void saveGyroAngle(double angleToSave){
        String stringyyy = Double.toString(angleToSave);
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

    public static double getGyroAngle(){
        File rooty = new File(Environment.getExternalStorageDirectory(), "blarg");
        if (!rooty.exists()){
        }
        File filepath = new File(rooty, "angleSave.txt");

        String finalString = "";
        double angleToGet = 0;
        try{
            Scanner in = new Scanner(filepath);
            finalString = in.nextLine();
            angleToGet = Double.valueOf(finalString);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return angleToGet;
    }
}
