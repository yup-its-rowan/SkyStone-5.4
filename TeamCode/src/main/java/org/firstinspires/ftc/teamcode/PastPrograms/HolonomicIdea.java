package org.firstinspires.ftc.teamcode.PastPrograms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Rohan's Account on 8/18/2017.
 */

//@TeleOp (name = "LazyBotCode", group = "HolonomicTest1")
public class HolonomicIdea extends OpMode {

    private DcMotor leftWheel;
    private DcMotor rightWheel;
    private DcMotor frontWheel;
    private DcMotor backWheel;


    @Override
    public void init(){
        //you just got vandalized,!! how does it feel to be utterly destroyed!111!!!!1!
        //this part is for what is defined or set at when u press initiate on the phone thingmajigger

        leftWheel = hardwareMap.dcMotor.get("left");
        rightWheel = hardwareMap.dcMotor.get("right");
        frontWheel = hardwareMap.dcMotor.get("front");
        backWheel = hardwareMap.dcMotor.get("back");

        //this thingy basically sets the variables of the different wheels to a hardware map
        //leftWheel, rightWheel, frontWheel, and backWheel are the names of the motors
        //make sure to set each motor to one of these


        leftWheel.setPower(0);
        frontWheel.setPower(0);
        backWheel.setPower(0);
        rightWheel.setPower(0);
        


        //this sets the powers of the motors to 0, making sure that it doesn't move as soon as the robot is initiated
        //stops accidents from occurring

    }

    @Override
    public void start() {

        //don't touch this area unless u want the robot to do something when it starts moving

    }

    @Override
    public void loop(){

        //this part basically loops over and over every couple milliseconds so if you want to track a dynamic variable then put it here bb

        double x = gamepad1.left_stick_x;
        //x is set to gamepad 1's left stick on the x axis
        double y = -gamepad1.left_stick_y;
        //y is set to gamepad 1's left stick on the y axis. Since the y axis of joysticks has a negative value at the top and positive at the bottom, a negative sign is needed
        double z = gamepad1.right_stick_x;
        //z is set to gamepad 1's right stick on the x axis


        double l = (y + x + z);
        double f = (-y + x + z);
        double b = (y -x + z);
        double r = (-y -x + z);

        telemetry.addData("ee: ", gamepad1.right_stick_y);

        //the variables of l, b, r, and f are all determined by where the joysticks are.
        //this is kinda confusing so ill try to explain it in words in the comments
        //z is the turning variable, and since every motor is pointed the same way, they all will turn counterclockwise when the right stick is pointed to the right
        //since the x and y values can either be positive or negative, there are 4 possible combinations
        //x&y, x&-y, -x&y, and -x&-y
        //these values are mapped to each specific motor, f for front, l for left, etc...
        //imagine a point on a graph, moving the left joystick will move the point on a graph similar to the robot on a floor
        //The more you move the joystick in a certain direction, the more power will be sent to the motors, causing that motor to move faster
        // for example if the left joy stick is moved to the furthest right on the pad, the robot will have a high x value and move directly to the right instead of up or down.


        //this part is important to setting the deadzone on the controller, as it sets a small circle for a deadzone
        //the square root part is just makin a circle, as sqrt x^2 + y^2 = radius is the size of a circle, the radius this time becing .1


        leftWheel.setPower(l);
        frontWheel.setPower(f);
        backWheel.setPower(b);
        rightWheel.setPower(r);


    }

    @Override
    public void stop(){

        //don't touch this area either u banana!, unless you want the robot to do something when you press stop

    }
}