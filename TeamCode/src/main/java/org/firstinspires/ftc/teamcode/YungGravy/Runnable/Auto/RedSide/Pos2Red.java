package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Auto.RedSide;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.YungGravy.RobotMovement;

@Autonomous(name = "pos2red", group = "bruh")
public class Pos2Red extends LinearOpMode {

    RobotMovement move = new RobotMovement();

    @Override
    public void runOpMode() throws InterruptedException {
        move.init(hardwareMap, telemetry);
        waitForStart();
        move.goForward(700, 0.3, false);
        move.turn(630, 0.2, true);
        move.goForward(-150, 0.2, false);
        move.leftLineUp();
        move.goRight(-230, 0.2, false);
        move.leftGrab();
        sleep(200);
        move.leftKeepIn();
        move.goRight(275, 0.4, false);
        move.goForward(2760, 0.35, false);
        move.goRight(-320, 0.4, false);
        move.leftLetGo();
        move.reinit();//JIC
        sleep(500);
        move.turn(600, 0.4, false);
        move.goForward(-150, 0.4, false);
        move.trapPlate();
        sleep(1000);
        //plate is now trapped
        move.reinit();//JIC
        move.goRight(400, 0.2, false);
        move.splineIsh(2700, 2600, 340, 540,  0.39, 0.39, 0.1, 0.1,  false);
        //move.goForward(700, 0.5, false);
        //move.turn(1050, 0.2, false);
        //move.goRight(-450, 0.2, false);
        move.goForward(-600, 0.3, false);
        move.unTrapPlate();
        sleep(1000);
        move.goForward(1400, 0.3, false);

    }
}
