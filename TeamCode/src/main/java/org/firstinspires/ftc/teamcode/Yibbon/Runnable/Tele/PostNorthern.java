package org.firstinspires.ftc.teamcode.Yibbon.Runnable.Tele;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Yibbon.RandomTelemetry;
import org.firstinspires.ftc.teamcode.Yibbon.Subsystems.AutoGrabbers;
import org.firstinspires.ftc.teamcode.Yibbon.Subsystems.Drivetrains.GyroDrive;
import org.firstinspires.ftc.teamcode.Yibbon.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Yibbon.Subsystems.Slides;
import org.firstinspires.ftc.teamcode.Yibbon.Subsystems.VirtualFourBar;
import org.firstinspires.ftc.teamcode.Yibbon.Subsystems.WaffleTrapper;

//@TeleOp(name = "PostNorthern", group = "eee")
public class PostNorthern extends OpMode {

    GyroDrive gyroDrive = new GyroDrive();
    Intake intake = new Intake();
    Slides slides = new Slides();
    WaffleTrapper waffleTrapper = new WaffleTrapper();
    VirtualFourBar v4b = new VirtualFourBar();
    AutoGrabbers autoGrab = new AutoGrabbers();
    ElapsedTime eTime = new ElapsedTime();
    String teleMessage = RandomTelemetry.RandomTelemetry();

    @Override
    public void init() {
        gyroDrive.init(hardwareMap, gamepad1, gamepad2, true, false, telemetry);
        intake.init(hardwareMap, gamepad1);
        slides.init(hardwareMap, gamepad1, gamepad2);
        waffleTrapper.init(hardwareMap, gamepad1, gamepad2);
        v4b.init(hardwareMap, gamepad1, gamepad2);
        autoGrab.init(hardwareMap, gamepad1, gamepad2, false);
        telemetry.speak("piss");
    }

    @Override
    public void loop() {
        gyroDrive.drivetrainInputs(eTime.time(), slides.slideHeightMovementDampener());
        intake.intakeInputs(eTime.time());
        slides.slideInputs2();
        waffleTrapper.waffleTrapperInputs(eTime.time());
        v4b.v4bInputs(eTime.time());
        autoGrab.autoGrabInputs();
        telemetry.addData("OffsetAngle", gyroDrive.offsetAngle);
        telemetry.addData("", this.teleMessage);
    }
}