package org.firstinspires.ftc.teamcode.YungGravy.Subsystems.Drivetrains;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.YungGravy.AutoTeleTransition;
import org.firstinspires.ftc.teamcode.YungGravy.MotorCache;

public class GyroDrive {

    private DcMotor fl, fr, bl, br;
    private double pose, x2, y2, flPower, frPower, blPower, brPower;
    public double offsetAngle = 0, startingAngle = 0;
    private double time, timeSlowReset = 0;
    public BNO055IMU imu;
    public Orientation angles;
    boolean telemetryEnabled = true;
    boolean autoAngle = true;

    private double g1lx = 0, g1ly = 0, g1rx = 0;
    private boolean g2lbutton = false, g1back = false, g1lb = false, g1rb = false;
    private MotorCache flm = new MotorCache();
    private MotorCache frm = new MotorCache();
    private MotorCache blm = new MotorCache();
    private MotorCache brm = new MotorCache();

    public GyroDrive(){ }

    public void init(HardwareMap hardwareMap, boolean autoAngle){
        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        this.autoAngle = autoAngle;
        this.offsetAngle = 0;
        this.startingAngle = AutoTeleTransition.getValue();
        resetEncoders();
        runUsingEncoder();
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

        //telemetryDT(telemetry);
    }

    public void enableTelemetry(){
        this.telemetryEnabled = true;
    }

    public void telemetryDT(Telemetry telemetry){
        telemetry.addData("fl", fl.getCurrentPosition());
        telemetry.addData("fr", fr.getCurrentPosition());
        telemetry.addData("bl", bl.getCurrentPosition());
        telemetry.addData("br", br.getCurrentPosition());
    }

    public void weBeDrivin(Telemetry telemetry){
        this.angles   = imu.getAngularOrientation(AxesReference.INTRINSIC,
                AxesOrder.ZYX, AngleUnit.RADIANS);
        telemetry.addData("startingAngle", this.startingAngle);
        this.pose = (angles.firstAngle - this.offsetAngle + this.startingAngle);
        if (time > (timeSlowReset + 0.2)){
            if (this.g1back){
                this.offsetAngle = this.pose;
                timeSlowReset = time;
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
        this.angles   = imu.getAngularOrientation(AxesReference.INTRINSIC,
                AxesOrder.ZYX, AngleUnit.RADIANS);
        this.pose = (angles.firstAngle - this.offsetAngle);
        if (time > (timeSlowReset + 0.2)){
            if (g1back){
                this.offsetAngle = this.pose;
                timeSlowReset = time;
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

}
