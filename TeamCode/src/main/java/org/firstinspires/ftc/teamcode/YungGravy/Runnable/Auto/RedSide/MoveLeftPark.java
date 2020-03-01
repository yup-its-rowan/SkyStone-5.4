package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Auto.RedSide;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.YungGravy.RobotMovement;

@Autonomous(name = "moveLeftPark", group = "gamer")
public class MoveLeftPark extends RobotMovement {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        waitForStart();
        goForwardStraight(80, 0.2, 0);
        goRightStraight(-400, 0.2, 0);
        goForwardStraight(-120, 0.2, 0);
        sleep(100);

    }
}
