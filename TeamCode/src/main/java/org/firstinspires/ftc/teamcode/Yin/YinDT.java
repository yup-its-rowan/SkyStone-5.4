package org.firstinspires.ftc.teamcode.Yin;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class YinDT extends OpMode {

    DcMotor fl, bl, br, fr;


    public YinDT(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br){this.fl = fl; this.fr = fr; this.bl = bl; this.br = br;}

    public void dtReset(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br){this.fl = fl; this.fr = fr; this.bl = bl; this.br = br;}

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }

    public void driveloop(){
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double z = gamepad1.right_stick_x;

        fl.setPower(y + x + z);
        fr.setPower(-y + x + z);
        bl.setPower(y - x + z);
        br.setPower(-y - x + z);
    }

    public void slowloop(){
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double z = gamepad1.right_stick_x;

        fl.setPower((y + x + z)/4);
        fr.setPower((-y + x + z)/4);
        bl.setPower((y - x + z)/4);
        br.setPower((-y - x + z)/4);
    }
}
