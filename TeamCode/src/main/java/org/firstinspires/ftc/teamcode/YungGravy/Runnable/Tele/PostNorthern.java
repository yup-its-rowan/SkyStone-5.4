package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Tele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.YungGravy.RandomTelemetry;
import org.firstinspires.ftc.teamcode.YungGravy.Robot.GyroRobot;

@TeleOp(name = "RoadToStates", group = "eee")
public class PostNorthern extends GyroRobot {

    String teleMessage = RandomTelemetry.RandomTelemetry();

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void loop() {
        super.loop();
        telemetry.addData("", this.teleMessage);
    }
}