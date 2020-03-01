package org.firstinspires.ftc.teamcode.YungGravy.Runnable.Auto.BlueSide;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.YungGravy.RobotMovement;
import org.firstinspires.ftc.teamcode.YungGravy.StatesSkystonePipe;
import org.openftc.easyopencv.OpenCvCamera;

@Autonomous(name = "BlueDoubleSky", group = "bruh")
public class BlueDoubleSku extends RobotMovement {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        int skystonePos = getSkystonePosWhileWaitForStart(true);
        waitForStart();

        // approach the blocks
        double currentAngle = 0;
        goForwardStraight(680, 0.17, currentAngle);
        currentAngle = -90;
        turnToAngle(currentAngle);

        // drive to deliver block 1
        if (skystonePos == 1) {
            goForwardStraight(-300, 0.3, currentAngle);
        } else if (skystonePos == 2) {
            goForwardStraight(-90, 0.3, currentAngle);
        } else {
            goForwardStraight(130, 0.3, currentAngle);
        }

        // strafe into the blocks and grab it
        leftLineUp();
        sleep(300);
        goRightStraight(-150, 0.3, currentAngle);
        leftGrab();
        sleep(300);
        // back off the blocks
        leftKeepIn();
        goRightStraight(150, 0.3, currentAngle);

        // drive to the platform
        if (skystonePos == 1) {
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
        goRightStraight(-200, 0.3, currentAngle);
        // drop block 1
        leftLetGo();
        sleep(300);
        // back off the platform
        leftUndeployed();
        goRightStraight(300, 0.3, currentAngle);

        // drive back to stones for second block
        goForwardStraightRamped(3200, 0.2, 0.5, 400, currentAngle);
        // square up against the wall
        goForwardStraight(400, 0.2, currentAngle);

        // drive back pased on skyston pos
        if (skystonePos == 1) {
            goForwardStraight(-470, 0.3, currentAngle);
        } else if (skystonePos == 2) {
            goForwardStraight(-270, 0.3, currentAngle);
        }

        // approach the blocks
        leftLineUp();
        goRightStraight(-200, 0.2, currentAngle);
        sleep(100);
        // grab block 2
        leftGrab();
        sleep(500);
        // back off the blocks
        leftKeepIn();
        goRightStraight(200, 0.3, currentAngle);

        // go back to platform to deliver stone 2
        if (skystonePos == 1) {
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
        goRightStraight(-200, 0.3, currentAngle);
        // drop the block
        leftLetGo();
        sleep(300);
        leftUndeployed();

        // turn so back is at platform
        currentAngle = 180;
        turnToAngle(currentAngle);
        // drive to and grab platform
        goForwardStraight(-200, 0.2, currentAngle);
        trapPlate();
        sleep(800);
        goForwardStraight(100, 0.3, currentAngle);

        currentAngle = -165;
        turnToAngle(currentAngle);
        goForwardStraight(300, 0.3, currentAngle);
        // turn platform
        currentAngle = -90;
        turnToAngle(currentAngle);
        // push into wall
        goForwardStraight(-300, 0.3, currentAngle);
        // let go of platform
        unTrapPlate();
        sleep(600);
        // park
        goForwardStraight(1100, 0.4, currentAngle);
        sleep(1000);
    }
}
