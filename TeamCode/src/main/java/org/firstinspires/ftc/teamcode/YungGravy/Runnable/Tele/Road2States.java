package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Tele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.YungGravy.RandomTelemetry;
import org.firstinspires.ftc.teamcode.YungGravy.Robot.GyroRobot;

@TeleOp(name = "RoadToStates", group = "eee")
public class Road2States extends GyroRobot {

    String teleMessage = RandomTelemetry.RandomTelemetry();
    ElapsedTime eTimee = new ElapsedTime();

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void loop() {
        super.loop();
        telemetry.addData("", this.teleMessage);
        telemetry.addData("time", eTimee.time());
        eTimee.reset();
    }
}