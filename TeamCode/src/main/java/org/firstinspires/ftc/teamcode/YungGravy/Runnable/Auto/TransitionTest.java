package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.YungGravy.AutoTeleTransition;

@Autonomous(name = "transitionTest", group = "please work")
public class TransitionTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        AutoTeleTransition.teleOnStop(this, "RoadToStates");

        waitForStart();
        sleep(2000);
        telemetry.addData("About to switch!", "please work");
        telemetry.update();
        sleep(2000);

    }
}
