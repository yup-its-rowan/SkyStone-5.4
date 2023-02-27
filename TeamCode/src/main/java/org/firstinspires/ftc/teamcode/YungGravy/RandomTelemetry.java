package org.firstinspires.ftc.teamcode.YungGravy;

import java.util.Random;

public class RandomTelemetry {



    public static String RandomTelemetry(){
        String listy[] = new String[30];
        Random random = new Random();
        int placeholder;

        listy[0] = "[REDACTED]";


        placeholder = random.nextInt(30);

        return listy[placeholder];
    }
}
