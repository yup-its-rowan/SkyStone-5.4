package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Auto.BlueSide;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.YungGravy.RobotMovement;
@Autonomous(name = "eeee", group = "eeeeeeeeeee")
public class gyrothinkg extends RobotMovement {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        waitForStart();
        rightUndeployed();
        sleep(1000);
        rightLineUp();
        sleep(1000);
        rightGrab();
        sleep(1000);
        rightKeepIn();
        sleep(1000);
        rightLetGo();
        sleep(1000);

    }
}
