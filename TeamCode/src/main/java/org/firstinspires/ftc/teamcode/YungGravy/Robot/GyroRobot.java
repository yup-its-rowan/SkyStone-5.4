package org.firstinspires.ftc.teamcode.YungGravy.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.YungGravy.Subsystems.AutoGrabbers;
import org.firstinspires.ftc.teamcode.YungGravy.Subsystems.Drivetrains.GyroDrive;
import org.firstinspires.ftc.teamcode.YungGravy.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.YungGravy.Subsystems.Slides;
import org.firstinspires.ftc.teamcode.YungGravy.Subsystems.VirtualFourBar;
import org.firstinspires.ftc.teamcode.YungGravy.Subsystems.WaffleTrapper;

public class GyroRobot extends OpMode {

    GyroDrive gyroDrive = new GyroDrive();
    Intake intake = new Intake();
    Slides slides = new Slides();
    WaffleTrapper waffleTrapper = new WaffleTrapper();
    VirtualFourBar v4b = new VirtualFourBar();
    AutoGrabbers autoGrab = new AutoGrabbers();
    ElapsedTime eTime = new ElapsedTime();

    @Override
    public void init() {
        gyroDrive.init(hardwareMap,false);
        intake.init(hardwareMap);
        slides.init(hardwareMap);
        waffleTrapper.init(hardwareMap);
        v4b.init(hardwareMap);
        autoGrab.init(hardwareMap, false);
    }

    @Override
    public void loop() {
        gyroDrive.drivetrainInputs(eTime.time(), gamepad1.left_stick_x,
                gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad2.left_stick_button,
                gamepad1.back, gamepad1.left_bumper, gamepad1.right_bumper, telemetry);
        intake.intakeInputs(eTime.time(), gamepad1.x, gamepad1.y);
        slides.slideInputs(gamepad2.right_trigger, gamepad2.left_trigger, telemetry);
        waffleTrapper.waffleTrapperInputs(gamepad1.dpad_down, gamepad1.dpad_up);
        v4b.v4bInputs(gamepad2.dpad_up, gamepad2.dpad_down, gamepad2.a, gamepad2.b);
        //autoGrab.autoGrabInputs(gamepad2.a, gamepad2.b, gamepad2.x, gamepad2.y);
        telemetry.addData("OffsetAngle", gyroDrive.offsetAngle);
    }
}
