package org.firstinspires.ftc.teamcode.YungGravy;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.internal.android.dex.util.ExceptionWithContext;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl;

public class AutoTeleTransition extends Thread {

    private static final AutoTeleTransition BLANK = new AutoTeleTransition();
    private OpModeManagerImpl opModeManager;
    private OpMode opMode;
    private String nameOfTele;

    private AutoTeleTransition(){
        this.start();
    }

    @Override
    public void run(){
        try {
            while (true){
                synchronized (this){
                    if (opMode != null && opModeManager.getActiveOpMode() != opMode){
                        Thread.sleep(1000);
                        opModeManager.initActiveOpMode(nameOfTele);
                        this.opModeManager = null;
                        this.opMode = null;
                        this.nameOfTele = null;
                    }
                }
                Thread.sleep(50);
            }
        } catch (InterruptedException IE){

        }
    }

    private void setTransition(OpMode opMode, String nameOfTele){
        synchronized (this){
            this.nameOfTele = nameOfTele;
            this.opMode = opMode;
            this.opModeManager = (OpModeManagerImpl) opMode.internalOpModeServices;
        }
    }

    public static void teleOnStop(OpMode opMode, String nameOfTele){
        BLANK.setTransition(opMode, nameOfTele);
    }


}
