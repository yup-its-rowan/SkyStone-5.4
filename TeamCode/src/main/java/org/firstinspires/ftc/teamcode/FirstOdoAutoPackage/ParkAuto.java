package org.firstinspires.ftc.teamcode.FirstOdoAutoPackage;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

@Autonomous(name = "ParkAuto", group = "Workers")
public class ParkAuto extends OpMode {

    private DcMotor fl, fr, bl, br, ol, or, h;

    private RobotMovement movement = new RobotMovement();
    private FirstOdo odo = new FirstOdo(null, null, null);
    private ElapsedTime eTime = new ElapsedTime();
    private ElapsedTime eTime2 = new ElapsedTime();
    private double waitTime = 0;

    private enum questionaire {question, left, right, leftNear, leftFar, rightNear, rightFar}
    private enum parkAutoPath {leftNear, rightNear, leftFar, rightFar, running}
    private questionaire question = questionaire.question;
    private parkAutoPath autoPath = parkAutoPath.leftNear;
    ArrayList<Waypoint> path = new ArrayList<>();

    @Override
    public void init() {
        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");
        ol = hardwareMap.dcMotor.get("i1");
        or = hardwareMap.dcMotor.get("i2");
        h = hardwareMap.dcMotor.get("sr");

        odo.odoReset(ol, or, h);
        odo.setGlobalX(0);
        odo.setGlobalY(0);
    }

    @Override
    public void init_loop() {
        switch (question){
            case question:
                telemetry.addData("which side is the robot on", "left for left, right for right");
                if (eTime.time() > 0.3){
                    if (gamepad1.dpad_right){question = questionaire.right;}
                    if (gamepad1.dpad_left){question = questionaire.left;}
                    eTime.reset();
                }
                break;
            case left:
                telemetry.addData("Left, far or near", "up for far, down for near");
                if (eTime.time() > 0.3){
                    if (gamepad1.dpad_down){question = questionaire.leftNear;}
                    if (gamepad1.dpad_up){question = questionaire.leftFar;}
                    eTime.reset();
                }
                break;
            case right:
                telemetry.addData("Right, far or near", "up for far, down for near");
                if (eTime.time() > 0.3){
                    if (gamepad1.dpad_down){question = questionaire.rightNear;}
                    if (gamepad1.dpad_up){question = questionaire.rightFar;}
                    eTime.reset();
                }
                break;
            case leftNear:
                telemetry.addData("left Near", "and i oop");
                autoPath = parkAutoPath.leftNear;
                waitTime();
                break;
            case leftFar:
                telemetry.addData("left Far", "and i oop");
                autoPath = parkAutoPath.leftFar;
                waitTime();
                break;
            case rightNear:
                telemetry.addData("right Near", "and i oop");
                autoPath = parkAutoPath.rightNear;
                waitTime();
                break;
            case rightFar:
                telemetry.addData("right Far", "and i oop");
                autoPath = parkAutoPath.rightFar;
                waitTime();
                break;
        }
    }

    @Override
    public void start(){
        eTime2.reset();
    }

    @Override
    public void loop() {
        odo.odoloop();
        movement.movementUpdate(odo.getGlobalX(), odo.getGlobalY(), odo.getHeading(), fl, fr, bl, br);

        if (eTime2.time() > waitTime) {
            switch (autoPath) {
                case leftFar:
                    path.add(new Waypoint(0, 0, 2, 1, 1));
                    path.add(new Waypoint(2, 6, 2, 0.6, 0.4));
                    path.add(new Waypoint(-4, 6, 1, 0.4, 0.3, 0));
                    autoPath = parkAutoPath.running;
                    break;
                case leftNear:
                    path.add(new Waypoint(0, 0, 2, 1, 1));
                    path.add(new Waypoint(4, 0, 2, 0.6, 0.4, 0));
                    autoPath = parkAutoPath.running;
                    break;
                case rightFar:
                    path.add(new Waypoint(0, 0, 2, 1, 1));
                    path.add(new Waypoint(-2, 6, 2, 0.6, 0.4));
                    path.add(new Waypoint(4, 6, 1, 0.4, 0.3, 0));
                    autoPath = parkAutoPath.running;
                    break;
                case rightNear:
                    path.add(new Waypoint(0, 0, 2, 1, 1));
                    path.add(new Waypoint(-4, 0, 2, 0.6, 0.4, 0));
                    autoPath = parkAutoPath.running;
                case running:
                    telemetry.addData("running", "all gud prolly");
            }
            movement.followPath(path);
        }
    }

    private void waitTime(){
        if (eTime.time() > 0.3){
            if (gamepad1.dpad_down){waitTime -=1;}
            if (gamepad1.dpad_up){waitTime +=1;}
            eTime.reset();
        }
        telemetry.addData("wait before start", waitTime);
    }
}
