package org.firstinspires.ftc.teamcode.YungGravy;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import static org.firstinspires.ftc.teamcode.YungGravy.UsefulMath.AngleWrap;

public class RobotMovement extends LinearOpMode {
    DcMotorEx fl, fr, bl, br;
    Servo l1, l2, r1, r2, t1, t2;
    BNO055IMU imu;
    OpenCvCamera phoneCam;
    private final int rows = 640;
    private final int cols = 480;

    public RobotMovement(){}

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("Initializing...");
        telemetry.update();

        fl = hardwareMap.get(DcMotorEx.class, "fl");
        bl = hardwareMap.get(DcMotorEx.class, "bl");
        fr = hardwareMap.get(DcMotorEx.class, "fr");
        br = hardwareMap.get(DcMotorEx.class, "br");

        l1 = hardwareMap.servo.get("l1");
        l2 = hardwareMap.servo.get("l2");
        r1 = hardwareMap.servo.get("r1");
        r2 = hardwareMap.servo.get("r2");

        t1 = hardwareMap.servo.get("t1");
        t2 = hardwareMap.servo.get("t2");

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        leftUndeployed();
        rightUndeployed();
        unTrapPlate();

        telemetry.addLine("Robot Initialized!");
        telemetry.update();
    }

    private int skystonePos = 1;
    public int getSkystonePosWhileWaitForStart(boolean isBlue) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        StatesSkystonePipe skystonepipey = new StatesSkystonePipe(isBlue);
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.showFpsMeterOnViewport(false);
        phoneCam.openCameraDevice();//open camera
        phoneCam.setPipeline(skystonepipey);//different stages
        phoneCam.startStreaming(rows, cols, OpenCvCameraRotation.SIDEWAYS_RIGHT);


        while (!isStarted()) {
            if (skystonepipey.valRight == 255 && skystonepipey.valMid == 255) {
                telemetry.addLine("Left");
                skystonePos = 1;
            } else if (skystonepipey.valRight == 255 && skystonepipey.valLeft == 255) {
                telemetry.addLine("Mid");
                skystonePos = 2;
            } else if (skystonepipey.valMid == 255 && skystonepipey.valLeft == 255) {
                telemetry.addLine("Right");
                skystonePos = 3;
            } else {
                telemetry.addLine("Unseen");
            }
            telemetry.update();
        }
        return skystonePos;
    }

    public void reinit(){
        leftUndeployed();
        rightUndeployed();
    }

    private void motorReset() {
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void powerBusyAccurate(double powerLeftBack, double powerLeftFront, double powerRightFront, double powerRightBack) {
        fl.setPower(powerLeftFront);
        fr.setPower(powerRightFront);
        bl.setPower(powerLeftBack);
        br.setPower(powerRightBack);
        while (((bl.isBusy() || br.isBusy()) || (fl.isBusy() || fr.isBusy()))){}
        bl.setPower(0);
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
    }

    private void powerBusyInaccurate(double powerLeftBack, double powerLeftFront, double powerRightFront, double powerRightBack){
        fl.setPower(powerLeftFront);
        fr.setPower(powerRightFront);
        bl.setPower(powerLeftBack);
        br.setPower(powerRightBack);
        while (((bl.isBusy() && br.isBusy()) && (fl.isBusy() && fr.isBusy()))){}
        bl.setPower(0);
        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
    }

    public void leftGrab(){
        l2.setPosition(0);
        l1.setPosition(0.33);
    }

    public void rightGrab(){
        r2.setPosition(1);
        r1.setPosition(0.68);
    }

    public void leftUndeployed(){
        l2.setPosition(0.33);
        l1.setPosition(0.86);
    }

    public void rightUndeployed(){
        r2.setPosition(0.92);
        r1.setPosition(0.08);
    }

    public void leftKeepIn(){
        l2.setPosition(0);
        l1.setPosition(0.82);
    }

    public void rightKeepIn(){
        r2.setPosition(1);
        r1.setPosition(0.22);
    }

    public void leftLineUp(){
        l2.setPosition(0.69);
        l1.setPosition(0.43);
    }

    public void leftLetGo(){
        l2.setPosition(0.69);
        l1.setPosition(0.6);
    }

    public void rightLetGo(){
        r2.setPosition(0.5);
        r1.setPosition(0.4);
    }

    public void rightLineUp(){
        r2.setPosition(0.5);
        r1.setPosition(0.63);
    }

    public double positionWrappedDegrees(){
        Orientation angles   = imu.getAngularOrientation(AxesReference.INTRINSIC,
                AxesOrder.ZYX, AngleUnit.RADIANS);
        double radianAngle = angles.firstAngle;
        double degrees = Math.toDegrees(radianAngle);
        double wrappedDegrees = AngleWrap(degrees);
        return wrappedDegrees;
    }

    public void gyroTurnRight90(double power){

    }

    public void goForward(double inchesFront, double power, boolean accurate){
        int tickCalc = (int)inchesFront;
        motorReset();
        fl.setTargetPosition(tickCalc);
        fr.setTargetPosition(-tickCalc);
        br.setTargetPosition(-tickCalc);
        bl.setTargetPosition(tickCalc);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (accurate){
            powerBusyAccurate(power, power, power, power);
        } else {
            powerBusyInaccurate(power, power, power, power);
        }
    }

    private final static double TURN_TO_ANGLE_THRESHOLD = 4;
    private final static double TURN_TO_ANGLE_MAX_SPEED = 1;

    public void turnToAngle(double targetAngle){
        runUsingEncoders();

        while (Math.abs(AngleWrap(positionWrappedDegrees() - targetAngle)) > TURN_TO_ANGLE_THRESHOLD && opModeIsActive()){
            double errorDeg = AngleWrap(positionWrappedDegrees() - targetAngle);
            double adjustmentPower = errorDeg/100;

            startTurn(Range.clip(adjustmentPower, -TURN_TO_ANGLE_MAX_SPEED, TURN_TO_ANGLE_MAX_SPEED));
        }
        stopRobot();
    }

    private static final double ERROR_TICKS_FORWAED = 50;
    public void goForwardStraight(int tickCalc, double power, double targetAngle) {
        motorReset();
        runUsingEncoders();
        double dir = Math.signum(tickCalc);

        while (Math.abs(fl.getCurrentPosition()) < Math.abs(tickCalc) - ERROR_TICKS_FORWAED && opModeIsActive()){
            double errorDeg = AngleWrap(positionWrappedDegrees() - targetAngle);
            double adjustmentPower = errorDeg/100;

            startMove(power * dir, 0 , Range.clip(adjustmentPower, -.2, .2));
        }
        stopRobot();
    }

    public void goRightStraight(int tickCalc, double power, double targetAngle) {
        motorReset();
        runUsingEncoders();
        double dir = Math.signum(tickCalc);

        while (Math.abs(fl.getCurrentPosition()) < Math.abs(tickCalc) - ERROR_TICKS_FORWAED && opModeIsActive()){
            double errorDeg = AngleWrap(positionWrappedDegrees() - targetAngle);
            double adjustmentPower = errorDeg/100;

            startMove(0, dir * power, Range.clip(adjustmentPower, -.2, .2));
        }
        stopRobot();
    }

    private void runUsingEncoders() {
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void goForwardStraightRamped(int tickCalc, double minPower, double maxPower, double rampDistanceTicks , double targetAngle) {
        motorReset();
        runUsingEncoders();
        double dir = Math.signum(tickCalc);

        while (Math.abs(fl.getCurrentPosition()) < Math.abs(tickCalc) - ERROR_TICKS_FORWAED && opModeIsActive()){
            double errorDeg = AngleWrap(positionWrappedDegrees() - targetAngle);
            double adjustmentPower = errorDeg/100;

            double rampUpPower = minPower + (maxPower-minPower) * (Math.abs(fl.getCurrentPosition())/rampDistanceTicks);
            double rampDownPower = minPower + (maxPower-minPower) * (Math.abs(tickCalc - fl.getCurrentPosition())/rampDistanceTicks);
            double power = Range.clip(Math.min(rampUpPower, rampDownPower), minPower, maxPower);

            startMove(dir * power, 0 , Range.clip(adjustmentPower, -.2, .2));
        }
        stopRobot();
    }

    public void goForwardStraightOld(int tickCalc, double power, double targetAngle) {
        motorReset();
        fl.setTargetPosition(tickCalc);
        fr.setTargetPosition(-tickCalc);
        br.setTargetPosition(-tickCalc);
        bl.setTargetPosition(tickCalc);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double leftPower = power;
        double rightPower = power;

        fl.setPower(leftPower);
        fr.setPower(rightPower);
        bl.setPower(leftPower);
        br.setPower(rightPower);

        while (((bl.isBusy() || br.isBusy()) || (fl.isBusy() || fr.isBusy()))){
            double errorDeg = AngleWrap(positionWrappedDegrees() - targetAngle);
            double adjustmentPower = errorDeg/100;

            leftPower = power + Range.clip(adjustmentPower, -.2, .2);
            rightPower = power - Range.clip(adjustmentPower, -.2, .2);

            fl.setPower(leftPower);
            fr.setPower(rightPower);
            bl.setPower(leftPower);
            br.setPower(rightPower);
        }
        stopRobot();
    }


    public void splineIsh(int leftFrontTick, int leftBackTick, int rightFrontTick, int rightBackTick, double powerLeftBack, double powerLeftFront, double powerRightFront, double powerRightBack, boolean accurate){
        motorReset();
        fl.setTargetPosition(leftFrontTick);
        fr.setTargetPosition(-rightFrontTick);
        br.setTargetPosition(-rightBackTick);
        bl.setTargetPosition(leftBackTick);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (accurate){
            powerBusyAccurate(powerLeftBack, powerLeftFront, powerRightFront, powerRightBack);
        } else {
            powerBusyInaccurate(powerLeftBack, powerLeftFront, powerRightFront, powerRightBack);
        }
    }

    public void turn(double turn, double power, boolean accurate){
        int tickCalc = (int)turn;
        motorReset();
        fl.setTargetPosition(tickCalc);
        fr.setTargetPosition(tickCalc);
        br.setTargetPosition(tickCalc);
        bl.setTargetPosition(tickCalc);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (accurate){
            powerBusyAccurate(power, power, power, power);
        } else {
            powerBusyInaccurate(power, power, power, power);
        }
    }

    public void goRight(int goright, double power, boolean accurate){
        int tickCalc = goright;
        motorReset();
        fl.setTargetPosition(tickCalc);
        fr.setTargetPosition(tickCalc);
        br.setTargetPosition(-tickCalc);
        bl.setTargetPosition(-tickCalc);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (accurate){
            powerBusyAccurate(power, power, power, power);
        } else {
            powerBusyInaccurate(power, power, power, power);
        }
    }

    public void trapPlate(){
        t1.setPosition(0);
        t2.setPosition(1);
    }

    public void unTrapPlate(){
        t1.setPosition(0.62);
        t2.setPosition(0);
    }

    private int inchesToTicks(double inches){
        double top = 336 * inches;
        double bottom = Math.PI * 2.95276;
        double ticks = top/bottom;
        return (int)ticks;
    }


    private void startDrive(double power) {
        startMove(power, 0, 0);
    }

    private void startStrafe(double power) {
        startMove(0, power, 0);
    }

    private void startTurn(double power) {
        startMove(0, 0, power);
    }

    private void startMove(double drive, double strafe, double turn) {
        startMove(drive, strafe, turn, 1);
    }

    private void startMove(double drive, double strafe, double turn, double scale) {
        double frontLeft = drive + strafe + turn;
        double frontRight = -drive + strafe + turn;
        double backLeft = drive - strafe + turn;
        double backRight = -drive - strafe + turn;

        double maxValue = Math.max( Math.max(Math.abs(frontLeft), Math.abs(frontRight)), Math.max(Math.abs(backLeft), Math.abs(backRight)));
        double divAmount = maxValue > 1 ? maxValue : 1;

        fl.setPower(frontLeft / divAmount * scale);
        fr.setPower(frontRight / divAmount * scale);
        bl.setPower(backLeft / divAmount * scale);
        br.setPower(backRight / divAmount * scale);
    }

    private void stopRobot(){
        startMove(0, 0, 0);
    }
}
