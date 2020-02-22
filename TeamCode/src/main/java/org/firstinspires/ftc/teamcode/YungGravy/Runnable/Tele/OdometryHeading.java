package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Tele;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.YungGravy.Subsystems.Drivetrains.GyroDrive;

import static org.firstinspires.ftc.teamcode.YungGravy.UsefulMath.AngleWrap;

@TeleOp(name = "The Entire Bee Movie Script", group = "bee movie")
public class OdometryHeading extends OpMode {

    GyroDrive gyroDrive = new GyroDrive();
    ElapsedTime eTime = new ElapsedTime();
    DcMotor l, r, m;
    private double COUNTS_PER_REV = 8192, WHEEL_DIAMETER = 1.96, WIDTH_BETWEEN_ENCODERS = 13.8460163056; //13.80467

    @Override
    public void init() {
        gyroDrive.init(hardwareMap, false);
        l = hardwareMap.dcMotor.get("i1");
        r = hardwareMap.dcMotor.get("i2");
        m = hardwareMap.dcMotor.get("sl");

        l.setDirection(DcMotorSimple.Direction.REVERSE);
        l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    @Override
    public void loop() {
        gyroDrive.drivetrainInputs(eTime.time(), gamepad1.left_stick_x,
                gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad2.left_stick_button,
                gamepad1.back, gamepad1.left_bumper, gamepad1.right_bumper, telemetry);
        gyroDrive.angles = gyroDrive.imu.getAngularOrientation(AxesReference.INTRINSIC,
                AxesOrder.ZYX, AngleUnit.DEGREES);
        double pose = (gyroDrive.angles.firstAngle);
        telemetry.addData("IMU pose", pose);
        double leftEncoder = -l.getCurrentPosition();
        double rightEncoder = r.getCurrentPosition();
        double pose2 = ((ticksToInches(leftEncoder)-ticksToInches(rightEncoder))/WIDTH_BETWEEN_ENCODERS);
        double actualOdoPose = AngleWrap(360 - Math.toDegrees(pose2));
        telemetry.addData("Odometry pose", actualOdoPose);
    }

    public double ticksToInches(double ticks){
        double temp = (ticks*Math.PI*WHEEL_DIAMETER);
        double temp2 = temp/COUNTS_PER_REV;
        return temp2;
    }
}