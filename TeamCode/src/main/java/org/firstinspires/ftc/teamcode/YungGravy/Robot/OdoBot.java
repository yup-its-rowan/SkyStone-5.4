package org.firstinspires.ftc.teamcode.YungGravy.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.YungGravy.Subsystems.AutoGrabbers;
import org.firstinspires.ftc.teamcode.YungGravy.Subsystems.Drivetrains.OdometryGyroDrive;
import org.firstinspires.ftc.teamcode.YungGravy.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.YungGravy.Subsystems.Slides;
import org.firstinspires.ftc.teamcode.YungGravy.Subsystems.VirtualFourBar;
import org.firstinspires.ftc.teamcode.YungGravy.Subsystems.WaffleTrapper;


public class OdoBot extends OpMode {

    public OdometryGyroDrive odoGyro = new OdometryGyroDrive();
    Intake intake = new Intake();
    Slides slides = new Slides();
    WaffleTrapper waffleTrapper = new WaffleTrapper();
    VirtualFourBar v4b = new VirtualFourBar();
    AutoGrabbers autoGrab = new AutoGrabbers();
    ElapsedTime eTime = new ElapsedTime();
    private boolean enableStartingAngle = true;

    @Override
    public void init() {
        odoGyro.init(hardwareMap,true);
        intake.init(hardwareMap);
        slides.init(hardwareMap);
        waffleTrapper.init(hardwareMap);
        v4b.init(hardwareMap);
        autoGrab.init(hardwareMap, false);
    }

    @Override
    public void init_loop(){
        if (enableStartingAngle == true){
            telemetry.addData("currently running autoAngle", "press a to change");
            if (gamepad1.a){
                enableStartingAngle = false;
            }
        } else if (enableStartingAngle == false){
            telemetry.addData("autoAngle disabled", "press b to change");
            if (gamepad1.b){
                enableStartingAngle = true;
            }
        }
    }

    @Override
    public void start(){
        if (enableStartingAngle == false){
            odoGyro.startingAngle = 0;
        }
    }

    @Override
    public void loop() {
        odoGyro.drivetrainInputs(eTime.time(), gamepad1.left_stick_x,
                gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad2.left_stick_button,
                gamepad1.b, gamepad1.left_bumper, gamepad1.right_bumper, telemetry);

        slides.slideInputs(gamepad2.right_trigger, gamepad2.left_trigger, gamepad2.left_bumper, gamepad2.right_bumper, gamepad2.x, telemetry, eTime.time());
        waffleTrapper.waffleTrapperInputs(gamepad1.dpad_down, gamepad1.dpad_up);
        v4b.v4bInputs(gamepad2.dpad_up, gamepad2.dpad_down, gamepad2.a, gamepad2.b);
        intake.intakeInputs(eTime.time(), gamepad1.x, gamepad1.y);

    }
}
