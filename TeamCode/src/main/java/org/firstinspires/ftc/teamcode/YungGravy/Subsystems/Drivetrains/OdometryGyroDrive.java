package org.firstinspires.ftc.teamcode.YungGravy.Subsystems.Drivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.YungGravy.AutoTeleTransition;
import org.firstinspires.ftc.teamcode.YungGravy.MotorCache;

import static org.firstinspires.ftc.teamcode.YungGravy.UsefulMath.AngleWrap;

public class OdometryGyroDrive {

    private DcMotor fl, fr, bl, br;
    private double x2, y2, flPower, frPower, blPower, brPower;
    public double offsetAngle = 0, startingAngle = 0;
    private double time, timeResetAngle = 0;
    boolean telemetryEnabled = true;
    boolean autoAngle = true;

    private double g1lx = 0, g1ly = 0, g1rx = 0;
    private boolean g2lbutton = false, g1back = false, g1lb = false, g1rb = false;
    private MotorCache flm = new MotorCache();
    private MotorCache frm = new MotorCache();
    private MotorCache blm = new MotorCache();
    private MotorCache brm = new MotorCache();

    public OdometryGyroDrive(){ }

    public void init(HardwareMap hardwareMap, boolean autoAngle){
        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");

        /*
        l = hardwareMap.dcMotor.get("i1");
        r = hardwareMap.dcMotor.get("i2");
        m = hardwareMap.dcMotor.get("sl");

         */

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.autoAngle = autoAngle;
        this.offsetAngle = 0;
        this.startingAngle = AutoTeleTransition.getValue();
        resetEncoders();
        runUsingEncoder();

        offsetL = leftEncoder;
        offsetR = rightEncoder;
        offsetM = middleEncoder;

        globalX = 0;
        globalY = 0;
    }
    int offsetL = 0, offsetR = 0, offsetM = 0;

    public void start(){
        globalY = 0;
        globalX = 0;
    }

    public void resetEncoders(){
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runUsingEncoder(){
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void leftStickBut2EncoderReset(){
        resetEncoders();
        runUsingEncoder();
    }

    public void drivetrainInputs(double time,  double g1lx, double g1ly, double g1rx,
                                 boolean g2lb, boolean g1back, boolean g1lb, boolean g1rb, Telemetry telemetry){
        positionCalculation();

        this.time = time;
        this.g1lx = g1lx;
        this.g1ly = g1ly;
        this.g1rx = g1rx;
        this.g2lbutton = g2lb;
        this.g1back = g1back;
        this.g1lb = g1lb;
        this.g1rb = g1rb;

        if (this.autoAngle == true){
            weBeDrivin(telemetry);
        } else {
            weBeDrivin2();
        }
        if (this.g2lbutton){
            leftStickBut2EncoderReset();
        }


        telemetry.addData("left", leftEncoder);
        telemetry.addData("right", rightEncoder);
        telemetry.addData("middle", middleEncoder);


        telemetry.addData("X", globalX);
        telemetry.addData("Y", globalY);
        telemetry.addData("T", globalHeading);

        reset();
        //telemetryDT(telemetry);
    }

    public void reset(){
        previousHeading = globalHeading;
        previousLeft = leftEncoder;
        previousMid = middleEncoder;
        previousRight = rightEncoder;
    }

    public static int leftEncoder, rightEncoder, middleEncoder;
    public static double globalHeading, globalX, globalY;
    private double previousHeading = 0, previousLeft = 0, previousRight = 0, previousMid = 0;

    public void positionCalculation(){
        double actualLeft = leftEncoder - offsetL;
        double actualRight = rightEncoder - offsetR;
        double actualMiddle = middleEncoder - offsetM;

        double pose2 = ((ticksToInches(actualRight)-ticksToInches(actualLeft))/WIDTH_BETWEEN_ENCODERS);
        globalHeading = AngleWrap(Math.toDegrees(pose2));

        double deltaHeading = globalHeading - previousHeading;
        double deltaMiddle = actualMiddle - previousMid;
        double adjustedMiddleEncoder = deltaMiddle - (deltaHeading*(3790));
        double deltaLeft = actualLeft - previousLeft;
        double deltaRight = actualRight - previousRight;
        double adjustedVerticalEncoders = (deltaLeft+deltaRight)/2;

        double radianHeading = Math.toRadians(globalHeading);

        double globalDeltaX = ((adjustedMiddleEncoder*Math.cos(radianHeading))+(adjustedVerticalEncoders*Math.sin(radianHeading)));
        double globalDeltaY = ((adjustedVerticalEncoders*Math.cos(radianHeading))-(adjustedMiddleEncoder*Math.sin(radianHeading)));

        double inchX = ticksToInches(globalDeltaX);
        double inchY = ticksToInches(globalDeltaY);

        globalX = globalX + inchX;
        globalY = globalY + inchY;

    }

    public void weBeDrivin(Telemetry telemetry){

        double pose = (Math.toRadians(globalHeading) - this.offsetAngle + this.startingAngle);
        if (time > (timeResetAngle + 0.2)){
            if (this.g1back){
                this.offsetAngle = pose;
                timeResetAngle = time;
            }
        }

        x2 = (g1lx*Math.cos(pose) + (-g1ly)*Math.sin(pose));
        y2 = ((-g1ly)*Math.cos(pose) - g1lx*Math.sin(pose));

        flPower = x2 + y2 + g1rx;
        frPower = x2 - y2 + g1rx;
        blPower = -x2 + y2 + g1rx;
        brPower = -x2 - y2 + g1rx;

        if (g1lb){
            flPower/=4;
            frPower/=4;
            blPower/=4;
            brPower/=4;
        } else if (g1rb){
            flPower/=7;
            frPower/=7;
            blPower/=7;
            brPower/=7;
        }

        if (flm.cache(flPower)){
            fl.setPower(flPower);
        }
        if (frm.cache(frPower)){
            fr.setPower(frPower);
        }
        if (blm.cache(blPower)){
            bl.setPower(blPower);
        }
        if (brm.cache(brPower)){
            br.setPower(brPower);
        }

    }

    public void weBeDrivin2(){

        double pose = (Math.toRadians(globalHeading) - this.offsetAngle);
        if (time > (timeResetAngle + 0.2)){
            if (g1back){
                this.offsetAngle = pose;
                timeResetAngle = time;
            }
        }

        x2 = (g1lx*Math.cos(pose) + (-g1ly)*Math.sin(pose));
        y2 = ((-g1ly)*Math.cos(pose) - g1lx*Math.sin(pose));

        flPower = x2 + y2 + g1rx;
        frPower = x2 - y2 + g1rx;
        blPower = -x2 + y2 + g1rx;
        brPower = -x2 - y2 + g1rx;

        if (g1lb){
            flPower/=4;
            frPower/=4;
            blPower/=4;
            brPower/=4;
        } else if (g1rb){
            flPower/=7;
            frPower/=7;
            blPower/=7;
            brPower/=7;
        }

        if (flm.cache(flPower)){
            fl.setPower(flPower);
        }
        if (frm.cache(frPower)){
            fr.setPower(frPower);
        }
        if (blm.cache(blPower)){
            bl.setPower(blPower);
        }
        if (brm.cache(brPower)){
            br.setPower(brPower);
        }
    }

    private double TICKS_PER_ROTATION = 8192, WHEEL_DIAMETER = 1.9685, WIDTH_BETWEEN_ENCODERS = 13.9799911094;

    private double ticksToInches(double ticks){
        double inchesPerRotation = Math.PI*WHEEL_DIAMETER;
        double conversionTicksToInches = (inchesPerRotation/TICKS_PER_ROTATION);
        return conversionTicksToInches*ticks;
    }

}
