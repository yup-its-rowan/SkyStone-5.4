package org.firstinspires.ftc.teamcode.PastPrograms;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import java.util.List;

/**Created by Rohan's Account on 2/26/2019.**/

//@Autonomous(name = "AndrewPierce", group = "StateAutoes")
public class AndrewPierce extends LinearOpMode{

    public static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    public static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    public static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    public static final String VUFORIA_KEY = "AS/CBMf/////AAABmTZ+tZGNC0x4jrOP3v2ETpk/AwIwV/YB5PP7/PzxQAKqHmbN/fy3VZ3PEZ59Dj4C+Rf0ZTmpnQCGnfur4kMSwjzwT+V+b+qlyMK+e3XJy3KAb2jRYcOv64KgDaD6wdMNbUjPTC6rfbmxXkudWM6dA9flYzt5Sw9tMjE37R753ArmQ2nsRh8n5Z+ig9Bh009dnPV16FFR7ImvRhiz18VB5O53SZG2jKVjR5VxyZ8W/jgc3HPL2XDvofAVSt+9h79e/7Trd+ERxyCYGSqa7rh7sxBwtikH53xVH4nLpGNzepUV3RPwmZjZXJBiU47/yyezTv12A80B2SxF7iGUJImCkKoDEEfFlJreeL/RLvRPOvUF";
    public VuforiaLocalizer vuforia;
    public TFObjectDetector tfod;
    public DcMotor leftWheel;
    public DcMotor rightWheel;
    public DcMotor frontWheel;
    public DcMotor backWheel;
    public DcMotor lift;
    public DcMotor collectionFlippy;
    public CRServo vexIntake;

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }
    private void motorReset() {
        backWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    private void powerBusy() {
        backWheel.setPower(0.65);
        frontWheel.setPower(0.65);
        leftWheel.setPower(0.65);
        rightWheel.setPower(0.65);
        while ((backWheel.isBusy() && frontWheel.isBusy()) && (rightWheel.isBusy() && leftWheel.isBusy())){}
        backWheel.setPower(0);
        frontWheel.setPower(0);
        leftWheel.setPower(0);
        rightWheel.setPower(0);
    }
    private void loft(int lofty){
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setTargetPosition(lofty);
        lift.setPower(1);
        while (lift.isBusy()){}
        lift.setPower(0);
    }
    private void shift(int shifty){
        motorReset();
        leftWheel.setTargetPosition(shifty);
        rightWheel.setTargetPosition(shifty);
        backWheel.setTargetPosition(shifty);
        frontWheel.setTargetPosition(shifty);
        powerBusy();
    }
    private void goBack(int gobac){
        motorReset();
        backWheel.setTargetPosition(-gobac);
        frontWheel.setTargetPosition(gobac);
        rightWheel.setTargetPosition(gobac);
        leftWheel.setTargetPosition(-gobac);
        powerBusy();
    }
    private void goFront(int gofron){
        motorReset();
        backWheel.setTargetPosition(gofron);
        frontWheel.setTargetPosition(-gofron);
        rightWheel.setTargetPosition(-gofron);
        leftWheel.setTargetPosition(gofron);
        powerBusy();
    }
    private void goRight(int gorigh){
        motorReset();
        backWheel.setTargetPosition(-gorigh);
        frontWheel.setTargetPosition(gorigh);
        rightWheel.setTargetPosition(-gorigh);
        leftWheel.setTargetPosition(gorigh);
        powerBusy();
    }
    private void goLeft(int golef){
        motorReset();
        backWheel.setTargetPosition(golef);
        frontWheel.setTargetPosition(-golef);
        rightWheel.setTargetPosition(golef);
        leftWheel.setTargetPosition(-golef);
        powerBusy();
    }
    private void collFlip(int collFli){
        collectionFlippy.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        collectionFlippy.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        collectionFlippy.setTargetPosition(collFli);
        collectionFlippy.setPower(0.3);
        while (collectionFlippy.isBusy()){}
        collectionFlippy.setPower(0);
    }

    ElapsedTime eTime = new ElapsedTime();

    @Override
    public void runOpMode(){
        leftWheel = hardwareMap.dcMotor.get("leftWheel");
        rightWheel = hardwareMap.dcMotor.get("rightWheel");
        backWheel = hardwareMap.dcMotor.get("backWheel");
        frontWheel = hardwareMap.dcMotor.get("frontWheel");
        lift = hardwareMap.dcMotor.get("extender1");
        collectionFlippy = hardwareMap.dcMotor.get("collFlip");
        vexIntake = hardwareMap.crservo.get("vexIntake");
        initVuforia();
        initTfod();
        waitForStart();
        if(opModeIsActive()){
            loft(13600);
            goRight(400);
            goBack(300);
            shift(-110);
            if (tfod != null) { tfod.activate(); }
            while (opModeIsActive()) {
                if (tfod != null) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        telemetry.update();
                        if (updatedRecognitions.size() == 1){
                            shift(-25);
                        }
                        if (updatedRecognitions.size() == 2) {
                            int goldMineral = -1;
                            int silverMineral = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineral = (int) recognition.getTop();
                                } else if (recognition.getLabel().equals(LABEL_SILVER_MINERAL)) {
                                    silverMineral = (int) recognition.getTop();
                                }
                            }
                            if (goldMineral == -1) {
                                telemetry.addData("Gold Mineral Position", "Right");
                                telemetry.update();
                                goBack(1050);
                                goLeft(1150);
                                goBack(630);
                                goFront(730);
                                shift(1450);
                                goBack(3800);
                                shift(800);
                                goLeft(300);
                                goBack(2800);
                                //drop
                                collFlip(-500);
                                //outtake if possible

                                goFront(4400);
                                collFlip(500);
                                loft(-13600);
                                tfod.shutdown();
                            } else if (goldMineral > silverMineral) {
                                telemetry.addData("Gold Mineral Position", "Center");
                                telemetry.update();
                                goBack(1050);
                                goLeft(250);
                                goBack(630);
                                goFront(730);
                                shift(1450);
                                goBack(3100);
                                shift(770);
                                goLeft(150);
                                goBack(1400);
                                goLeft(150);
                                goBack(1200);
                                collFlip(-500);
                                //outtake if possible

                                goFront(4600);
                                collFlip(500);
                                loft(-13600);
                                tfod.shutdown();
                            } else if (silverMineral > goldMineral) {
                                telemetry.addData("Gold Mineral Position", "Left");
                                telemetry.update();
                                goBack(1050);
                                goRight(750);
                                goBack(630);
                                goFront(730);
                                shift(1450);
                                goBack(1750);
                                shift(770);
                                goLeft(300);
                                goBack(2700);
                                collFlip(-500);
                                //outtake here if possibl


                                goFront(4600);
                                collFlip(500);
                                loft(-13600);
                                tfod.shutdown();
                            }
                        }
                    }
                }
            }
        }
        if (tfod != null) {
            tfod.shutdown();
        }
    }
}

