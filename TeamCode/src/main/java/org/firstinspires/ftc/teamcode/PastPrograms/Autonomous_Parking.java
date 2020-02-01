package org.firstinspires.ftc.teamcode.PastPrograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Rohan's Account on 11/12/2017.
 */

//@Autonomous(name = "Autonomous_Parking", group = "team_6183")
public class Autonomous_Parking extends LinearOpMode{

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    @Override
    public void runOpMode() {

        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");

        waitForStart();

        ElapsedTime eTime = new ElapsedTime();

        eTime.reset();

        leftMotor.setPower(1);
        rightMotor.setPower(-1);

        while(eTime.time() < 4){}

        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

}
