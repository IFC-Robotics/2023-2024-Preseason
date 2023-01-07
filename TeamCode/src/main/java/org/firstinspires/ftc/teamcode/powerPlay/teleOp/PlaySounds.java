package org.firstinspires.ftc.teamcode.powerPlay.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.ftccommon.SoundPlayer;

/**
 * This file demonstrates how to play simple sounds on both the RC and DS phones.
 * It illustrates how to build sounds into your application as a resource.
 **
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 *
 * Operation:
 *
 * Note: Time should be allowed for sounds to complete before playing other sounds.
 *
 * For sound files to be used as a compiled-in resource, they need to be located in a folder called "raw" under your "res" (resources) folder.
 * You can create your own "raw" folder from scratch, or you can copy the one from the FtcRobotController module.
 *
 *     Android Studio coders will ultimately need a folder in your path as follows:
 *       <project root>/TeamCode/src/main/res/raw
 *
 *     Copy any .wav files you want to play into this folder.
 *     Make sure that your files ONLY use lower-case characters, and have no spaces or special characters other than underscore.
 *
 *     The name you give your .wav files will become the resource ID for these sounds.
 *     eg:  gold.wav becomes R.raw.gold
 *
 *     If you wish to use the sounds provided for this sample, they are located in:
 *     <project root>/FtcRobotController/src/main/res/raw
 *     You can copy and paste the entire 'raw' folder using Android Studio.
 *
 */

@TeleOp(name="OpMode that plays sound")
@Disabled
public class PlaySounds extends LinearOpMode {

    private boolean goldIsFound;
    private boolean goldWasPlaying = false;

    @Override
    public void runOpMode() {

        int goldSoundID = hardwareMap.appContext.getResources().getIdentifier("gold", "raw", hardwareMap.appContext.getPackageName());

        if (goldSoundID != 0) {
            goldIsFound = SoundPlayer.getInstance().preload(hardwareMap.appContext, goldSoundID);
        }

        telemetry.addData("gold resource", goldIsFound ? "Found" : "NOT found\n Add gold.wav to /src/main/res/raw" );

        telemetry.addData(">", "Press Start to continue");
        telemetry.update();

        waitForStart();

        telemetry.addData(">", "Press X to play sound.");
        telemetry.update();

        while (opModeIsActive()) {

            if (goldIsFound && gamepad1.b && !goldWasPlaying) {
                SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, goldSoundID);
                telemetry.addData("Playing", "Resource Gold");
                telemetry.update();
            }

            goldWasPlaying = gamepad1.x;

        }

    }
}