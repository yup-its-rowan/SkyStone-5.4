package org.firstinspires.ftc.teamcode.PastPrograms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

//@TeleOp(name = "beek", group = "beeokr")
public class BeekBeek extends OpMode {

    DcMotor l, r;

    @Override
    public void init() {
        l = hardwareMap.dcMotor.get("l");
        r = hardwareMap.dcMotor.get("r");
    }

    @Override
    public void loop() {
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;

        double leftPower = -y - x;
        double rightPower = -x + y;

        l.setPower(leftPower);
        r.setPower(rightPower);
    }
}
