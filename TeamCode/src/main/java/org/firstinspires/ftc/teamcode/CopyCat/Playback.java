package org.firstinspires.ftc.teamcode.CopyCat;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import android.os.Environment;

@Autonomous(name = "PlaybackAuto", group = "PicturePerfect")
public class Playback extends OpMode {


    private DcMotor fl,fr,bl,br,i1, i2;
    private Servo t1, t2;
    enum stateToggleIntake {
        intakeOn, intakeOff, intakeOut
    }

    stateToggleIntake toggleIntake = stateToggleIntake.intakeOff;

    enum stateToggleWaffleTrapper {
        trapperUp, trapperDown
    }

    stateToggleWaffleTrapper toggleWaffleTrapper = stateToggleWaffleTrapper.trapperUp;

    String bees;
    int ticker = 0;
    int ticker2 = 0;
    ElapsedTime runTime = new ElapsedTime();
    ElapsedTime eTimerecord = new ElapsedTime();
    double xLeft[] = new double[200];
    double xRight[] = new double[200];
    double yLeft[] = new double[200];
    double yRight[] = new double[200];
    double lTrigg[] = new double[200];
    double rTrigg[] = new double[200];
    boolean a[] = new boolean[200];
    boolean b[] = new boolean[200];
    boolean x[] = new boolean[200];
    boolean y[] = new boolean[200];
    boolean lStick[] = new boolean[200];
    boolean rStick[] = new boolean[200];
    boolean dup[] = new boolean[200];
    boolean ddown[] = new boolean[200];
    boolean dleft[] = new boolean[200];
    boolean dright[] = new boolean[200];
    boolean lButton[] = new boolean[200];
    boolean rButton[] = new boolean[200];

    @Override
    public void init() {
        File rooty = new File(Environment.getExternalStorageDirectory(), "blarg");
        if (!rooty.exists()){
            telemetry.addData("No Playback File", " Exists");
        }
        File filepath = new File(rooty, "beant.txt");

        String temp="";
        try{
            Scanner in = new Scanner(filepath);
            while (in.hasNextLine()){
                y[ticker] = Boolean.valueOf(in.nextLine());
                a[ticker] = Boolean.valueOf(in.nextLine());
                b[ticker] = Boolean.valueOf(in.nextLine());
                x[ticker] = Boolean.valueOf(in.nextLine());
                xLeft[ticker] = Double.valueOf(in.nextLine());
                xRight[ticker] = Double.valueOf(in.nextLine());
                yLeft[ticker] = Double.valueOf(in.nextLine());
                yRight[ticker] = Double.valueOf(in.nextLine());
                lStick[ticker] = Boolean.valueOf(in.nextLine());
                rStick[ticker] = Boolean.valueOf(in.nextLine());
                ddown[ticker] = Boolean.valueOf(in.nextLine());
                dup[ticker] = Boolean.valueOf(in.nextLine());
                dleft[ticker] = Boolean.valueOf(in.nextLine());
                dright[ticker] = Boolean.valueOf(in.nextLine());
                lButton[ticker] = Boolean.valueOf(in.nextLine());
                rButton[ticker] = Boolean.valueOf(in.nextLine());
                lTrigg[ticker] = Double.valueOf(in.nextLine());
                rTrigg[ticker] = Double.valueOf(in.nextLine());

                ticker++;
            }
            bees = temp;
        } catch(IOException e) {
            e.printStackTrace();
            bees = "garg";
        }
        runTime.reset();

        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");

        i1 = hardwareMap.dcMotor.get("i1");
        i2 = hardwareMap.dcMotor.get("i2");

        t1 = hardwareMap.servo.get("t1");
        t2 = hardwareMap.servo.get("t2");


        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        i1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        i2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /*
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        */
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        //telemetry.addData("ee", bees);
        if (runTime.time() > 0.1){
            telemetry.addData("time", runTime.time());
            if (ticker2 < 200){

                //start runnable code

                double x2 = this.xLeft[ticker2];
                double y2 = -this.yLeft[ticker2];
                double z2 = this.xRight[ticker2];

                fl.setPower((y2 + x2 + z2)/6);
                fr.setPower((-y2 + x2 + z2)/6);
                bl.setPower((y2 - x2 + z2)/6);
                br.setPower((-y2 - x2 + z2)/6);

                switch (toggleIntake){
                    case intakeOn:
                        if (this.x[ticker2]){toggleIntake = stateToggleIntake.intakeOff;}
                        if (this.a[ticker2]){toggleIntake = stateToggleIntake.intakeOut;}
                        i1.setPower(-1);
                        i2.setPower(0.8);
                        break;
                    case intakeOff:
                        if (this.x[ticker2]){toggleIntake = stateToggleIntake.intakeOn;}
                        if (this.a[ticker2]){toggleIntake = stateToggleIntake.intakeOut;}
                        i1.setPower(0);
                        i2.setPower(0);
                        break;
                    case intakeOut:
                        if (this.x[ticker2]){toggleIntake = stateToggleIntake.intakeOn;}
                        if (this.a[ticker2]){toggleIntake = stateToggleIntake.intakeOff;}
                        i1.setPower(1);
                        i2.setPower(-1);
                        break;
                }

                switch (toggleWaffleTrapper){
                    case trapperDown:
                        t1.setPosition(1);
                        t2.setPosition(0);
                        if (eTimerecord.time() > 0.2){
                            if (this.lButton[ticker2]){toggleWaffleTrapper = stateToggleWaffleTrapper.trapperUp;}
                            eTimerecord.reset();
                        }
                        break;
                    case trapperUp:
                        t1.setPosition(0);
                        t2.setPosition(1);
                        if (eTimerecord.time() > 0.2){
                            if (this.lButton[ticker2]){toggleWaffleTrapper = stateToggleWaffleTrapper.trapperDown;}
                            eTimerecord.reset();
                        }
                        break;
                }

                //end runnable code
            }

            ticker2++;
            runTime.reset();
        }
    }

    @Override
    public void stop() {

    }


}
