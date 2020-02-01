package org.firstinspires.ftc.teamcode.Yibbon.Runnable.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OpenCV.SkystonePipe;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

//@Autonomous(name = "south", group = "h")
public class ParkAutoTest extends LinearOpMode {

    DcMotorEx fl, fr, bl, br;
    Servo t1, t2, abgl1, abgl2, abgr1, abgr2;
    private ElapsedTime eTime = new ElapsedTime();
    private ElapsedTime runtime = new ElapsedTime();

    private final int rows = 640;
    private final int cols = 480;
    private String position;

    OpenCvCamera phoneCam;
    SkystonePipe skystonepipey = new SkystonePipe();

    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.get(DcMotorEx.class, "fl");
        bl = hardwareMap.get(DcMotorEx.class, "bl");
        fr = hardwareMap.get(DcMotorEx.class, "fr");
        br = hardwareMap.get(DcMotorEx.class, "br");
        t1 = hardwareMap.servo.get("t1");
        t2 = hardwareMap.servo.get("t2");
        abgl1 = hardwareMap.servo.get("l1");
        abgl2 = hardwareMap.servo.get("l2");
        abgr1 = hardwareMap.servo.get("r1");
        abgr2 = hardwareMap.servo.get("r2");

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fl.setTargetPositionTolerance(15);
        fr.setTargetPositionTolerance(15);
        bl.setTargetPositionTolerance(15);
        br.setTargetPositionTolerance(15);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        phoneCam.openCameraDevice();//open camera
        phoneCam.setPipeline(skystonepipey);//different stages
        phoneCam.startStreaming(rows, cols, OpenCvCameraRotation.SIDEWAYS_RIGHT);

        abgr1.setPosition(0.72);
        abgl1.setPosition(0.32);
        while (!isStarted()){
            telemetry.addData("Values", skystonepipey.valLeft + "   " + skystonepipey.valMid + "   " + skystonepipey.valRight);
            telemetry.addData("Height", rows);
            telemetry.addData("Width", cols);
            telemetry.update();
            if (skystonepipey.valRight == 255 && skystonepipey.valMid == 255){
                position = "Left";
            } else if (skystonepipey.valRight == 255 && skystonepipey.valLeft == 255){
                position = "Mid";
            } else if (skystonepipey.valMid == 255 && skystonepipey.valLeft == 255){
                position = "Right";
            } else {
                position = "unseen";
            }

            telemetry.addData("Position", position);
        }
        waitForStart();

        eTime.reset();
        if (opModeIsActive()){
            if (position == "Left"){
                trapperUp();
                goRight(900, 0.4);
                goForward(-500, 0.3);
                abgr2.setPosition(0.6);
                goRight(250, 0.2);
                abgr2.setPosition(0.9);
                sleep(500);
                abgr1.setPosition(0);
                sleep(500);
                abgr2.setPosition(0.2);
                sleep(300);
                goRight(-175, 0.3);
                goForward(3250, 0.3);
                goRight(200, 0.3);
                abgr2.setPosition(0.6);
                abgr1.setPosition(0.72);
                sleep(100);
                abgr2.setPosition(0.2);
                goRight(200, 0.3);
                turn(-650, 0.2);
                goForward(-270, 0.3);
                trapperDown();
                sleep(400);
                goForward(975, 0.3);
                trapperUp();
                sleep(400);
                goRight(-1550, 0.5);

            }else if (position == "Mid"){

            }else if (position == "Right"){

            }else if (position == "unseen"){
                telemetry.addData("boo hoo", "didnt detect pog");
                telemetry.update();
            }


        }
    }

    public void goForward(int gofron, double power){
        int tickCalc = (gofron);
        motorReset();
        fl.setTargetPosition(tickCalc);
        fr.setTargetPosition(-tickCalc);
        br.setTargetPosition(-tickCalc);
        bl.setTargetPosition(tickCalc);

        fl.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        powerBusy(power);
    }

    public int inchesToTicks(double inches){

        double inchesPerRotation = 2.952;
        double ticksPerRotation = 336;

        double ticksPerInch = ticksPerRotation/inchesPerRotation;

        double temp = ticksPerInch*inches;

        int ticky = (int)temp;

        return ticky;
    }

    private void motorReset() {
        fl.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void trapperUp(){
        t1.setPosition(1);
        t2.setPosition(0);
    }

    private void trapperDown(){
        t1.setPosition(0);
        t2.setPosition(1);
    }

    private void powerBusy(double power) {
        fl.setPower(power);
        fr.setPower(power);
        bl.setPower(power);
        br.setPower(power);
        eTime.reset();
        while (((bl.isBusy() || br.isBusy()) || (fl.isBusy() || fr.isBusy())) && eTime.time() < 8){}
        bl.setPower(0);
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
    }

    public void goRight(int goright, double power){
        int tickCalc = goright;
        motorReset();
        fl.setTargetPosition(tickCalc);
        fr.setTargetPosition(tickCalc);
        br.setTargetPosition(-tickCalc);
        bl.setTargetPosition(-tickCalc);

        fl.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        powerBusy(power);
    }

    public void gyroCorrection(double angle){

    }

    public void turn(int ticks, double power){
        int tickCalc = ticks;
        motorReset();
        fl.setTargetPosition(tickCalc);
        fr.setTargetPosition(tickCalc);
        br.setTargetPosition(tickCalc);
        bl.setTargetPosition(tickCalc);

        fl.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        powerBusy(power);
    }

}
