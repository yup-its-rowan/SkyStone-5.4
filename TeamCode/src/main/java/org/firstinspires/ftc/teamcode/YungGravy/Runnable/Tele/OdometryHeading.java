package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Tele;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.YungGravy.UsefulMath.AngleWrap;

@TeleOp(name = "The Entire Bee Movie Script", group = "bee movie")
public class OdometryHeading extends OpMode {

    ElapsedTime eTime = new ElapsedTime();
    private DcMotor l, r, m;
    private DcMotor fl, fr, bl, br;
    private double COUNTS_PER_REV = 8192, WHEEL_DIAMETER = 1.9685, WIDTH_BETWEEN_ENCODERS = 14.1134159823; //13.9517678078; //13.9623892331;
//14.0077767644; //x`14.0731779497; //14.0405531644; //14.0444544017; //13.9799911094 <- on field, to left is home; //13.653710522 //13.8460163056
    private double horizontalTicksPerRadian = 500; //between 500 and 1000//1000; //3400; //3500; //3600;//3735; //4316; //5800; //6900; //4785;
    @Override
    public void init() {
        l = hardwareMap.dcMotor.get("i1");
        r = hardwareMap.dcMotor.get("i2");
        m = hardwareMap.dcMotor.get("sl");
        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");

        //fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        offsetL = l.getCurrentPosition();
        offsetM = m.getCurrentPosition();
        offsetR = r.getCurrentPosition();
    }

    @Override
    public void init_loop(){
        telemetry.addData("offsetL", offsetL);
        telemetry.addData("offsetR", offsetR);
        telemetry.addData("offsetM", offsetM);

        globalX = 0;
        globalY = 0;

        telemetry.addData("Heading", globalHeading);
        telemetry.addData("X", globalX);
        telemetry.addData("Y", globalY);

        leftEncoder = l.getCurrentPosition() - offsetL;
        rightEncoder = r.getCurrentPosition() - offsetR;
        middleEncoder = m.getCurrentPosition() - offsetM;

        telemetry.addData("Left", leftEncoder);
        telemetry.addData("Right", rightEncoder);
        telemetry.addData("Middle", middleEncoder);

    }

    private int offsetL, offsetR, offsetM;
    private int leftEncoder, rightEncoder, middleEncoder;
    private double globalHeading, globalX = 0, globalY = 0;
    private double previousHeading = 0, previousLeft = 0, previousRight = 0, previousMid = 0;

    @Override
    public void start(){
        globalX = 0;
        globalY = 0;
    }

    @Override
    public void loop() {
        leftEncoder = l.getCurrentPosition() - offsetL;
        rightEncoder = r.getCurrentPosition() - offsetR;
        middleEncoder = m.getCurrentPosition() - offsetM;
        positionCalculation();

        telemetry.addData("loop", eTime.time());
        telemetry.addData("Left", leftEncoder);
        telemetry.addData("Right", rightEncoder);
        telemetry.addData("Middle", middleEncoder);

        telemetry.addData("Heading", AngleWrap(Math.toDegrees(globalHeading)));
        telemetry.addData("X", globalX);
        telemetry.addData("Y", globalY);

        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double z = gamepad1.right_stick_x;

        fl.setPower(y + x + z);
        fr.setPower(-y+x+z);
        bl.setPower(y-x+z);
        br.setPower(-y-x+z);

        eTime.reset();
    }

    public void positionCalculation(){
        globalHeading = ((ticksToInches(rightEncoder)-ticksToInches(leftEncoder))/WIDTH_BETWEEN_ENCODERS);
        double deltaHeading = globalHeading - previousHeading;
        double deltaMiddle = middleEncoder - previousMid;
        double deltaLeft = leftEncoder - previousLeft;
        double deltaRight = rightEncoder - previousRight;
        double adjustedMiddleEncoder = deltaMiddle - (Math.toRadians(deltaHeading)*(horizontalTicksPerRadian));
        double adjustedVerticalEncoders = (deltaLeft+deltaRight)/2;

        double globalDeltaX = ((adjustedMiddleEncoder*Math.cos(globalHeading))-(adjustedVerticalEncoders*Math.sin(globalHeading)));
        double globalDeltaY = ((adjustedVerticalEncoders*Math.cos(globalHeading))+(adjustedMiddleEncoder*Math.sin(globalHeading)));

        double globalDeltaXInch = ticksToInches(globalDeltaX);
        double globalDeltaYInch = ticksToInches(globalDeltaY);

        double gonX, gonY;
        if (Math.abs(globalDeltaX) < 0.003){
            gonX = 0;
        } else {
            gonX = globalDeltaX;
        }

        if (Math.abs(globalDeltaY)< 0.003){
            gonY = 0;
        } else {
            gonY = globalDeltaY;
        }

        telemetry.addData("globaldeltax", gonX);
        telemetry.addData("globaldeltay", gonY);

        globalX = globalX + globalDeltaXInch;
        globalY = globalY + globalDeltaYInch;

        reset();
    }

    public void reset(){
        previousHeading = globalHeading;
        previousLeft = leftEncoder;
        previousMid = middleEncoder;
        previousRight = rightEncoder;
    }

    public double ticksToInches(double ticks){
        double temp = (ticks*Math.PI*WHEEL_DIAMETER);
        double temp2 = temp/COUNTS_PER_REV;
        return temp2;
    }
}