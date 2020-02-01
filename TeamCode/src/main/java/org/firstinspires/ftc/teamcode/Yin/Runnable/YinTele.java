package org.firstinspires.ftc.teamcode.Yin.Runnable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Yin.Yin;

public class YinTele extends OpMode {

    Yin yin = new Yin();

    @Override
    public void init() {
        yin.init();
    }

    @Override
    public void loop() {
        yin.getDrivetrain().driveloop();
        yin.getOdo().loop();
        yin.getIntake().stateIntake();
    }
}
