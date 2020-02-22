package org.firstinspires.ftc.teamcode.BasePurePursuit;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

import static org.firstinspires.ftc.teamcode.BasePurePursuit.RobotMovement.followCurve;

//import static org.firstinspires.ftc.teamcode.BasePurePursuit.RobotMovement.followCurve;


public class MyOpMode extends OpMode {

    ArrayList<CurvePoint> allPoints = new ArrayList<>();

    @Override
    public void init() {
        allPoints.add(new CurvePoint(0,0,1.0, 1.0, 50,Math.toRadians(50), 1.0));
        allPoints.add(new CurvePoint(180,180,1.0, 1.0, 50,Math.toRadians(50), 1.0));
        allPoints.add(new CurvePoint(0,0,1.0, 1.0, 50,Math.toRadians(50), 1.0));
        allPoints.add(new CurvePoint(0,0,1.0, 1.0, 50,Math.toRadians(50), 1.0));
    }

    @Override
    public void loop() {

        followCurve(allPoints, Math.toRadians(90));

    }
}
