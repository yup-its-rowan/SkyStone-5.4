package org.firstinspires.ftc.teamcode.Yibbon.Subsystems.Drivetrains;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static org.firstinspires.ftc.teamcode.Yibbon.GyroAutoAngleTracker.getGyroAngle;

public class GyroDrive {

    private DcMotor fl, fr, bl, br;
    private double pose, x2, y2, flPower, frPower, blPower, brPower, power;
    public double offsetAngle = 0;
    private double time, timeSlowReset = 0;
    public BNO055IMU imu;
    public Orientation angles;
    Gamepad gamepad1, gamepad2;
    boolean telemetryEnabled = true;
    boolean autoAngle = true;
    Telemetry telemetry;

    public GyroDrive(){ }

    public void init(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, boolean encoderBool, boolean autoAngle, Telemetry telemetry){
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.telemetry = telemetry;
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

    public void runWithoutEncoders(){
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void leftStickBut2EncoderReset(){
        resetEncoders();
        runUsingEncoder();
    }

    public void drivetrainInputs(double time, double power){
        this.power = power;
        this.time = time;
        if (autoAngle == true){
            weBeDrivin();
        } else {
            weBeDrivin2();
        }
        if (gamepad2.left_stick_button){
            leftStickBut2EncoderReset();
        }
    }

    public void enableTelemetry(){
        this.telemetryEnabled = true;
    }

    public void telemetryDT(){
        telemetry.addData("fl", fl.getCurrentPosition());
        telemetry.addData("fr", fr.getCurrentPosition());
        telemetry.addData("bl", bl.getCurrentPosition());
        telemetry.addData("br", br.getCurrentPosition());
    }

    public void weBeDrivin(){
        this.angles   = imu.getAngularOrientation(AxesReference.INTRINSIC,
                AxesOrder.ZYX, AngleUnit.RADIANS);
        this.pose = (angles.firstAngle - this.offsetAngle - getGyroAngle());
        if (time > (timeSlowReset + 0.2)){
            if (this.gamepad1.back){
                this.offsetAngle = this.pose;
                timeSlowReset = time;
            }
        }
        double yy = this.gamepad1.left_stick_y;

        x2 = (this.gamepad1.left_stick_x*Math.cos(pose) + (-yy)*Math.sin(pose));
        y2 = ((-yy)*Math.cos(pose) - this.gamepad1.left_stick_x*Math.sin(pose));

        flPower = x2 + y2 + this.gamepad1.right_stick_x;
        frPower = x2 - y2 + this.gamepad1.right_stick_x;
        blPower = -x2 + y2 + this.gamepad1.right_stick_x;
        brPower = -x2 - y2 + this.gamepad1.right_stick_x;

        if (this.gamepad1.left_bumper == true){
            flPower/=4;
            frPower/=4;
            blPower/=4;
            brPower/=4;
        } else if (this.gamepad1.right_bumper == true){
            flPower/=7;
            frPower/=7;
            blPower/=7;
            brPower/=7;
        }

        fl.setPower(flPower);
        fr.setPower(frPower);
        bl.setPower(blPower);
        br.setPower(brPower);

    }

    public void weBeDrivin2(){
        this.angles   = imu.getAngularOrientation(AxesReference.INTRINSIC,
                AxesOrder.ZYX, AngleUnit.RADIANS);
        this.pose = (angles.firstAngle - this.offsetAngle);
        if (time > (timeSlowReset + 0.2)){
            if (this.gamepad1.back){
                this.offsetAngle = this.pose;
                timeSlowReset = time;
            }
        }
        double yy = this.gamepad1.left_stick_y;

        x2 = (this.gamepad1.left_stick_x*Math.cos(pose) + (-yy)*Math.sin(pose));
        y2 = ((-yy)*Math.cos(pose) - this.gamepad1.left_stick_x*Math.sin(pose));

        flPower = x2 + y2 + this.gamepad1.right_stick_x;
        frPower = x2 - y2 + this.gamepad1.right_stick_x;
        blPower = -x2 + y2 + this.gamepad1.right_stick_x;
        brPower = -x2 - y2 + this.gamepad1.right_stick_x;

        if (this.gamepad1.left_bumper == true){
            flPower/=4;
            frPower/=4;
            blPower/=4;
            brPower/=4;
        } else if (this.gamepad1.right_bumper == true){
            flPower/=7;
            frPower/=7;
            blPower/=7;
            brPower/=7;
        }

        fl.setPower(flPower);
        fr.setPower(frPower);
        bl.setPower(blPower);
        br.setPower(brPower);

        telemetryDT();

    }

}
