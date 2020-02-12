package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Tele;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl;
import org.firstinspires.ftc.teamcode.YungGravy.RandomTelemetry;
import org.firstinspires.ftc.teamcode.YungGravy.Robot.RegRobot;

@TeleOp(name = "RoadToStatesReg", group = "eee")
public class RegBot extends RegRobot {

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