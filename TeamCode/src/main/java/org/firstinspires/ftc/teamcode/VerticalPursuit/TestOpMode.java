package org.firstinspires.ftc.teamcode.VerticalPursuit;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

import static org.firstinspires.ftc.teamcode.VerticalPursuit.PositionTracking.globalT;
import static org.firstinspires.ftc.teamcode.VerticalPursuit.PositionTracking.globalX;
import static org.firstinspires.ftc.teamcode.VerticalPursuit.PositionTracking.globalY;

@Autonomous(name = "verticalTest")
public class TestOpMode extends OpMode {
    Movement movement = new Movement();
    PositionTracking pT = new PositionTracking();
    DcMotor ol, or;
    ArrayList <TravelPoint> allPoints = new ArrayList<>();

    @Override
    public void init() {
        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor bl = hardwareMap.dcMotor.get("fl");
        DcMotor fr = hardwareMap.dcMotor.get("fl");
        DcMotor br = hardwareMap.dcMotor.get("fl");

        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ol = hardwareMap.dcMotor.get("i1");
        or = hardwareMap.dcMotor.get("i2");

        movement.init(fl, fr, bl, br);
        pT.init(ol.getCurrentPosition(), or.getCurrentPosition());

        //allPoints.add(new TravelPoint(0,0,0,0.2,2));
        allPoints.add(new TravelPoint(2,6,13,0.2,2));
    }

    @Override
    public void loop() {
        pT.updateTicks(ol.getCurrentPosition(), or.getCurrentPosition());
        movement.followPath(allPoints);
        telemetry.addData("x", globalX);
        telemetry.addData("y", globalY);
        telemetry.addData("t", globalT);
    }
}
