package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Tele;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.YungGravy.RandomTelemetry;
import org.firstinspires.ftc.teamcode.YungGravy.Robot.RegRobot;

@TeleOp(name = "SouthTele", group = "eee")
public class SouthernTele extends RegRobot {

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