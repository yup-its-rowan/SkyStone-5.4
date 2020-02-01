package org.firstinspires.ftc.teamcode.Yibbon.Runnable.Auto;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static org.firstinspires.ftc.teamcode.Yibbon.GyroAutoAngleTracker.saveGyroAngle;

@Autonomous(name = "foundationAndParkBlue", group = "foundationPark")
public class FoundationAutoBlue extends LinearOpMode {

    DcMotorEx fl, fr, bl, br;
    Servo t1, t2, abgr2, abgl2;
    String path = "wall";
    //BNO055IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.get(DcMotorEx.class, "fl");
        bl = hardwareMap.get(DcMotorEx.class, "bl");
        fr = hardwareMap.get(DcMotorEx.class, "fr");
        br = hardwareMap.get(DcMotorEx.class, "br");
        t1 = hardwareMap.servo.get("t1");
        t2 = hardwareMap.servo.get("t2");
        abgl2 = hardwareMap.servo.get("l2");
        abgr2 = hardwareMap.servo.get("r2");
        /*
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        */

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fl.setTargetPositionTolerance(15);
        fr.setTargetPositionTolerance(15);
        bl.setTargetPositionTolerance(15);
        br.setTargetPositionTolerance(15);

        trapperUp();
        abgr2.setPosition(0.05);
        abgl2.setPosition(0.95);

        while (!isStarted()){
            telemetry.addData("Path ", path);
            telemetry.addData("Change path with", "dpad up bridge, dpad down wall");
            telemetry.update();
            if (gamepad1.dpad_up){
                path = "bridge";
            } else if (gamepad1.dpad_down){
                path = "wall";
            }
        }

        waitForStart();

        if (opModeIsActive()){
            if (path == "bridge"){
                goForward(-850, 0.3);
                goRight(300, 0.3);
                goForward(-150, 0.2);
                trapperDown();
                sleep(1000);
                goForward(1075, 0.3);
                trapperUp();
                sleep(1000);
                goRight(-950, 0.3);
                goForward(-650, 0.3);
                goRight(-400, 0.3);
                sleep(5000);
                /*
                Orientation angles   = imu.getAngularOrientation(AxesReference.INTRINSIC,
                        AxesOrder.ZYX, AngleUnit.RADIANS);
                double pose = angles.firstAngle;
                saveGyroAngle(pose + Math.toRadians(180));

                 */
            } else if (path == "wall"){
                goForward(-850, 0.3);
                goRight(300, 0.3);
                goForward(-150, 0.2);
                trapperDown();
                sleep(1000);
                goForward(1075, 0.3);
                trapperUp();
                sleep(1000);
                goRight(-1450, 0.3);
                sleep(5000);
                /*
                Orientation angles   = imu.getAngularOrientation(AxesReference.INTRINSIC,
                        AxesOrder.ZYX, AngleUnit.RADIANS);
                double pose = angles.firstAngle;
                saveGyroAngle(pose + Math.toRadians(180));
                 */
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
        while (((bl.isBusy() || br.isBusy()) || (fl.isBusy() || fr.isBusy()))){}
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
