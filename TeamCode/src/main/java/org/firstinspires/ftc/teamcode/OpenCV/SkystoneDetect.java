package org.firstinspires.ftc.teamcode.OpenCV;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

//@TeleOp(name= "RowanSksystoneDete", group="h")
public class SkystoneDetect extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private final int rows = 640;
    private final int cols = 480;
    private String position;

    OpenCvCamera phoneCam;
    SkystonePipe skystonepipey = new SkystonePipe();

    @Override
    public void init() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        phoneCam.openCameraDevice();//open camera
        phoneCam.setPipeline(skystonepipey);//different stages
        phoneCam.startStreaming(rows, cols, OpenCvCameraRotation.SIDEWAYS_RIGHT);//display on RC
        //width, height
        //width = height in this case, because camera is in portrait mode.

    }

    @Override
    public void start(){
    }

    @Override
    public void loop(){
        runtime.reset();
        telemetry.addData("Values", skystonepipey.valLeft + "   " + skystonepipey.valMid + "   " + skystonepipey.valRight);
        telemetry.addData("Height", rows);
        telemetry.addData("Width", cols);
        if (skystonepipey.valRight == 255 && skystonepipey.valMid == 255){
            position = "Left";
        } else if (skystonepipey.valRight == 255 && skystonepipey.valLeft == 255){
            position = "Mid";
        } else if (skystonepipey.valMid == 255 && skystonepipey.valLeft == 255){
            position = "Right";
        } else {
            position = "unseen";
        }

        telemetry.addData("Position", position);

    }

    @Override
    public void stop(){
    }
}