package org.firstinspires.ftc.teamcode.YungGravy;

import java.util.Random;

public class RandomTelemetry {



    public static String RandomTelemetry(){
        String listy[] = new String[30];
        Random random = new Random();
        int placeholder;

        listy[0] = "mapoopoo peeejima";
        listy[1] = "i wonder what steve harveys thinkin about rn";
        listy[2] = "imagine spending an hour and a half messing around in java to make a randomized statement creator that has absolutely no purpose";
        listy[3] = "now that this class works im gonna run a randomizer for control schemes so get ready for surprise pain controls";
        listy[4] = "if any of you piss me off im gonna put the robot on a moan loop for states";
        listy[5] = "advika be like tsundere noises";
        listy[6] = "poggers";
        listy[7] = "[Placeholder Text]";
        listy[8] = "fuit gummi";
        listy[9] = "slur";
        listy[10] = "its really sad that this might be the only working part of the robot";
        listy[11] = "its currently 2:15 AM when im writing this please put me to sleep however you like";
        listy[12] = "i have a 1750 word essay due literally tomorrow and i haven't started why am i making this";
        listy[13] = "god i wish i had sharper cheekbones id probably get off to myself";
        listy[14] = "why do i have fingers";
        listy[15] = "these might be randomly generated. who knows?";
        listy[16] = "something something harambe something";
        listy[17] = "theres a very small chance you get this message at an actual competition, but good luck :)";
        listy[18] = "maybe the programs not broken and you're just bad at pressing buttons correctly. ever think of that? huh? bitch.";
        listy[19] = "one of these statements is gonna be a cry for help and you'll only find it if you do enough drive practice";
        listy[20] = "pussy smell like yoshi side b";
        listy[21] = ":pig:";
        listy[22] = "if we don't go to worlds im actually going to set the robot on fire";
        listy[23] = "robert be like 'imo' noises";
        listy[24] = "h";
        listy[25] = "hhhhhhhhhhhhh";
        listy[26] = "[ERROR program 'YinBot' has crashed, restart robot]";
        listy[27] = "tired";
        listy[28] = "resounding";
        listy[29] = "h    h    h";


        placeholder = random.nextInt(30);

        return listy[placeholder];
    }
}
