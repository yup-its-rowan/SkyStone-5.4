package org.firstinspires.ftc.teamcode.CopyCat;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@TeleOp(name = "Recorder", group = "PicturePerfect")
public class Recorder extends OpMode {

    private DcMotor fl,fr,bl,br, i1, i2;
    private Servo t1, t2;
    enum stateToggleIntake {
        intakeOn, intakeOff, intakeOut
    }

    stateToggleIntake toggleIntake = stateToggleIntake.intakeOff;

    enum stateToggleWaffleTrapper {
        trapperUp, trapperDown
    }

    stateToggleWaffleTrapper toggleWaffleTrapper = stateToggleWaffleTrapper.trapperUp;

    String pee;
    int ticker = 0;
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
    public void start(){
    }

    @Override
    public void loop() {
        if (runTime.time() > 0.1){
            //put runnable code here

            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double z = gamepad1.right_stick_x;

            fl.setPower((y + x + z)/6);
            fr.setPower((-y + x + z)/6);
            bl.setPower((y - x + z)/6);
            br.setPower((-y - x + z)/6);

            switch (toggleIntake){
                case intakeOn:
                    if (gamepad1.x){toggleIntake = stateToggleIntake.intakeOff;}
                    if (gamepad1.a){toggleIntake = stateToggleIntake.intakeOut;}
                    i1.setPower(-1);
                    i2.setPower(0.8);
                    break;
                case intakeOff:
                    if (gamepad1.x){toggleIntake = stateToggleIntake.intakeOn;}
                    if (gamepad1.a){toggleIntake = stateToggleIntake.intakeOut;}
                    i1.setPower(0);
                    i2.setPower(0);
                    break;
                case intakeOut:
                    if (gamepad1.x){toggleIntake = stateToggleIntake.intakeOn;}
                    if (gamepad1.a){toggleIntake = stateToggleIntake.intakeOff;}
                    i1.setPower(1);
                    i2.setPower(-1);
                    break;
            }

            switch (toggleWaffleTrapper){
                case trapperDown:
                    t1.setPosition(1);
                    t2.setPosition(0);
                    if (eTimerecord.time() > 0.2){
                        if (gamepad1.left_stick_button){toggleWaffleTrapper = stateToggleWaffleTrapper.trapperUp;}
                        eTimerecord.reset();
                    }
                    break;
                case trapperUp:
                    t1.setPosition(0);
                    t2.setPosition(1);
                    if (eTimerecord.time() > 0.2){
                        if (gamepad1.left_stick_button){toggleWaffleTrapper = stateToggleWaffleTrapper.trapperDown;}
                        eTimerecord.reset();
                    }
                    break;
            }


            //end runnable code

            if (gamepad1.start){
                joinText();
            }

            if (ticker < 200){
                this.xLeft[ticker] = gamepad1.left_stick_x;
                this.xRight[ticker] = gamepad1.right_stick_x;
                this.yRight[ticker] = gamepad1.right_stick_y;
                this.yLeft[ticker] = gamepad1.left_stick_y;
                this.a[ticker] = gamepad1.a;
                this.b[ticker] = gamepad1.b;
                this.x[ticker] = gamepad1.x;
                this.y[ticker] = gamepad1.y;
                this.lStick[ticker] = gamepad1.left_stick_button;
                this.rStick[ticker] = gamepad1.right_stick_button;
                this.dup[ticker] = gamepad1.dpad_up;
                this.ddown[ticker] = gamepad1.dpad_down;
                this.dleft[ticker] = gamepad1.dpad_left;
                this.dright[ticker] = gamepad1.dpad_right;
                this.lButton[ticker] = gamepad1.left_bumper;
                this.rButton[ticker] = gamepad1.right_bumper;
                this.lTrigg[ticker] = gamepad1.left_trigger;
                this.rTrigg[ticker] = gamepad1.right_trigger;
            }
            ticker++;
            telemetry.addData("RunTime", runTime.time());
            runTime.reset();
        }
    }

    @Override
    public void stop() {
        save();
    }

    public void joinText(){
        for(int i = 0;i < y.length;i++){
            pee+=y[i]+ "\n";
            pee+=a[i]+ "\n";
            pee+=b[i]+ "\n";
            pee+=x[i]+ "\n";
            pee+=xLeft[i]+ "\n";
            pee+=xRight[i]+ "\n";
            pee+=yLeft[i]+ "\n";
            pee+=yRight[i]+ "\n";
            pee+=lStick[i]+ "\n";
            pee+=rStick[i]+ "\n";
            pee+=ddown[i]+ "\n";
            pee+=dup[i]+ "\n";
            pee+=dleft[i]+ "\n";
            pee+=dright[i]+ "\n";
            pee+=lButton[i]+ "\n";
            pee+=rButton[i]+ "\n";
            pee+=lTrigg[i]+ "\n";
            pee+=rTrigg[i]+ "\n";
        }
    }

    public void save(){
        try {
            File rooty = new File(Environment.getExternalStorageDirectory(), "blarg");

            if (!rooty.exists()){
                rooty.mkdir();
            }

            File filepath = new File(rooty, "beant.txt");

            FileWriter writer = new FileWriter(filepath);
            writer.append(pee);
            writer.flush();
            writer.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
