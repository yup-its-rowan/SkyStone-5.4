package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Auto.BlueSide;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.YungGravy.RobotMovement;
@Autonomous(name = "eeee", group = "eeeeeeeeeee")
public class gyrothinkg extends LinearOpMode {
    RobotMovement move = new RobotMovement();
    @Override
    public void runOpMode() throws InterruptedException {
        move.init(hardwareMap, telemetry);
        waitForStart();
        move.goForwardStraight(500, 0.3, 0);
        move.turnToAngle(90);
        move.goForwardStraight(2000, 0.3, 90);
        move.turnToAngle(90);
        sleep(1000);

    }
}
