package org.firstinspires.ftc.teamcode.BasePurePursuit;

import com.qualcomm.robotcore.hardware.DcMotor;

public class HandlingMotorPowers {
    DcMotor fl, fr, bl, br;

    public static double fieldX = 0, fieldY = 0, fieldTurn = 0;

    public void settingMotorPowers(){



        double z = fieldTurn;

        /*
        fl.setPower(y + x + z);
        fr.setPower(-y + x + z);
        bl.setPower(y - x + z);
        br.setPower(-y -x + z);

        double x2 = movementSpeed * (snippedX*Math.cos(globalT) + snippedY*Math.sin(globalT));
        double y2 = movementSpeed * (snippedY*Math.cos(globalT) - snippedX*Math.sin(globalT));
        double z2 = turnSpeed * snippedHeading;

        double flPower = x2 + y2 + z2;
        double blPower = -x2 + y2 + z2;
        double frPower = x2 - y2 + z2;
        double brPower = -x2 - y2 + z2;

        fl.setPower(flPower);
        bl.setPower(blPower);
        fr.setPower(frPower);
        br.setPower(brPower);
         */
    }

}
