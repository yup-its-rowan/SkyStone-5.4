package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Tele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.YungGravy.RandomTelemetry;
import org.firstinspires.ftc.teamcode.YungGravy.Robot.ActualGyroBot;
import org.firstinspires.ftc.teamcode.YungGravy.Robot.OdoBot;

@TeleOp(name = "RoadToStates", group = "eee")
public class RoadToStates extends ActualGyroBot {

    String teleMessage = RandomTelemetry.RandomTelemetry();
    ElapsedTime eTimee = new ElapsedTime();

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void init_loop(){super.init_loop();}

    @Override
    public void start(){super.start();}

    @Override
    public void loop() {
        super.loop();
        telemetry.addData("", this.teleMessage);
        telemetry.addData("time", eTimee.time());
        eTimee.reset();
    }
}