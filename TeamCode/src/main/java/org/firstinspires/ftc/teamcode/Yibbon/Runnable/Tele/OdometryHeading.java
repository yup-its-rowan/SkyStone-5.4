package org.firstinspires.ftc.teamcode.Yibbon.Runnable.Tele;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Yibbon.Subsystems.Drivetrains.GyroDrive;

import static org.firstinspires.ftc.teamcode.Yibbon.UsefulMath.AngleWrap;

@TeleOp(name = "OdometryHeading", group = "eee")
public class OdometryHeading extends OpMode {

    GyroDrive gyroDrive = new GyroDrive();
    ElapsedTime eTime = new ElapsedTime();
    DcMotor l, r, m;
    private double COUNTS_PER_REV = 8192, WHEEL_DIAMETER = 1.96, WIDTH_BETWEEN_ENCODERS = 13.32;

    @Override
    public void init() {
        gyroDrive.init(hardwareMap, gamepad1, gamepad2, true, false, telemetry);
        l = hardwareMap.dcMotor.get("i1");
        r = hardwareMap.dcMotor.get("i2");
        m = hardwareMap.dcMotor.get("sr");

        l.setDirection(DcMotorSimple.Direction.REVERSE);
        l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    @Override
    public void loop() {
        gyroDrive.drivetrainInputs(eTime.time(), 1);

        gyroDrive.angles = gyroDrive.imu.getAngularOrientation(AxesReference.INTRINSIC,
                AxesOrder.ZYX, AngleUnit.DEGREES);
        double pose = (gyroDrive.angles.firstAngle);
        telemetry.addData("IMU pose", pose);
        double pose2 = ((ticksToInches(l.getCurrentPosition())-ticksToInches(r.getCurrentPosition()))/WIDTH_BETWEEN_ENCODERS);
        telemetry.addData("Odometry pose", AngleWrap(Math.toDegrees(pose2)));
    }

    public double ticksToInches(double ticks){
        double temp = (ticks*Math.PI*WHEEL_DIAMETER);
        double temp2 = temp/COUNTS_PER_REV;
        return temp2;
    }
}