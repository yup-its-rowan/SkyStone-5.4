package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Auto.BlueSide;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.YungGravy.RobotMovement;

@Autonomous(name = "pos1blue", group = "bruh")
public class Pos1Blue extends LinearOpMode {

    RobotMovement move = new RobotMovement();

    @Override
    public void runOpMode() throws InterruptedException {
        move.init(hardwareMap, telemetry);
        waitForStart();
        move.goForward(750, 0.27, false);
        move.turn(630, 0.3, true);
        move.goForward(-250, 0.3, false);
        move.leftLineUp();
        sleep(300);
        move.goRight(-115, 0.2, false);
        move.leftGrab();
        sleep(400);
        move.leftKeepIn();
        move.goRight(180, 0.2, false);
        move.goForward(-2270, 0.6, false);
        //line up against wall ^
        move.goRight(-460, 0.2, false);
        move.leftLetGo();
        sleep(500);
        move.leftUndeployed();
        move.goRight(260, 0.2, false);
        move.goForward(-400, 0.2, false);
        move.goForward(3050, 0.45, false);
        move.goForward(200, 0.2, true);
        //move.turn(40, 0.2, false);
        move.leftLineUp();
        sleep(300);
        move.goRight(-250, 0.2, false);
        move.leftGrab();
        sleep(400);
        move.leftKeepIn();
        move.goRight(230, 0.2, false);
        move.goForward(-2850, 0.6, false);
        //^second long move
        move.goRight(-300, 0.2, false);
        move.leftLetGo();
        sleep(500);
        move.leftUndeployed();
        move.turn(610, 0.3, false);
        move.goForward(-200, 0.2, false);
        move.trapPlate();
        sleep(800);
        move.goRight(-300, 0.3, false);
        //move.splineIsh(185, 280, 2050, 1950, 0.07, 0.07, 0.52, 0.52, false);
        move.goForward(600, 0.3, false);
        move.turn(-600, 0.4, false);
        move.goForward(-600, 0.4, false);
        move.unTrapPlate();
        move.goForward(1000, 0.6, false);
    }
}
