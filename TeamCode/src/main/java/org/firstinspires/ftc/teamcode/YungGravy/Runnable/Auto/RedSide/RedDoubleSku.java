package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Auto.RedSide;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.YungGravy.RobotMovement;

@Autonomous(name = "RedDoubleSky", group = "bruh")
public class RedDoubleSku extends RobotMovement {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        int skystonePos = getSkystonePosWhileWaitForStart(false);
        waitForStart();

        // approach the blocks
        double currentAngle = 0;
        goForwardStraight(680, 0.2, currentAngle);
        currentAngle = 90;
        turnToAngle(currentAngle);

        // drive to pick up block 1
        if (skystonePos == 3) {
            goForwardStraight(-270, 0.3, currentAngle);
        } else if (skystonePos == 2) {
            goForwardStraight(-110, 0.3, currentAngle);
        } else {
            goForwardStraight(160, 0.3, currentAngle);
        }

        // strafe into the blocks and grab it
        rightLineUp();
        sleep(300);
        goRightStraight(150, 0.3, currentAngle);
        rightGrab();
        sleep(300);
        // back off the blocks
        rightKeepIn();
        goRightStraight(-300, 0.3, currentAngle);

        // drive to the platform
        if (skystonePos == 3) {
            goForwardStraightRamped(-2400, 0.2, 0.5, 400, currentAngle);
        } else if (skystonePos == 2) {
            goForwardStraightRamped(-2600, 0.2, 0.5, 400, currentAngle);
        } else {
            goForwardStraightRamped(-2900, 0.2, 0.5, 400, currentAngle);
        }

        // back into the wall to square up
        goForwardStraight(-300, 0.2, currentAngle);
        // approach the platform
        goForwardStraight(400, 0.3, currentAngle);
        goRightStraight(300, 0.3, currentAngle);
        // drop block 1
        rightLetGo();
        sleep(300);
        // back off the platform
        rightUndeployed();
        goRightStraight(-200, 0.3, currentAngle);

        // drive back to stones for second block
        goForwardStraightRamped(3200, 0.2, 0.5, 400, currentAngle);
        // square up against the wall
        goForwardStraight(400, 0.2, currentAngle);

        // drive back pased on skyston pos
        if (skystonePos == 3) {
            goForwardStraight(-470, 0.3, currentAngle);
        } else if (skystonePos == 2) {
            goForwardStraight(-210, 0.3, currentAngle);
        }

        // approach the blocks
        rightLineUp();
        goRightStraight(350, 0.18, currentAngle);
        sleep(200);
        // grab block 2
        rightGrab();
        sleep(300);
        // back off the blocks
        rightKeepIn();
        goRightStraight(-150, 0.3, currentAngle);

        // go back to platform to deliver stone 2
        if (skystonePos == 3) {
            goForwardStraightRamped(-2800, 0.2, 0.5, 400, currentAngle);
        } else if (skystonePos == 2) {
            goForwardStraightRamped(-3050, 0.2, 0.5, 400, currentAngle);
        } else {
            goForwardStraightRamped(-3300, 0.2, 0.5, 400, currentAngle);
        }

        // square into the wall
        goForwardStraight(-400, 0.2, currentAngle);
        // approach platform
        goForwardStraight(400, 0.3, currentAngle);
        goRightStraight(350, 0.3, currentAngle);
        // drop the block
        rightLetGo();
        sleep(300);
        rightUndeployed();

        // turn so back is at platform
        currentAngle = 180;
        turnToAngle(currentAngle);
        // drive to and grab platform
        goForwardStraight(-150, 0.2, currentAngle);
        trapPlate();
        sleep(800);
        goForwardStraight(100, 0.3, currentAngle);

        currentAngle = 165;
        turnToAngle(currentAngle);
        goForwardStraight(300, 0.3, currentAngle);
        // turn platform
        currentAngle = 90;
        turnToAngle(currentAngle);
        // push into wall
        goForwardStraight(-300, 0.3, currentAngle);
        // let go of platform
        unTrapPlate();
        sleep(600);
        // park
        goRightStraight(200, 0.34, currentAngle);
        goForwardStraight(1100, 0.4, currentAngle);
        sleep(1000);
    }
}
