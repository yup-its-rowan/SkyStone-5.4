package org.firstinspires.ftc.teamcode.Yibbette;

import java.util.Random;

public class RandomTelemetry {



    public static String RandomTelemetry(){
        String jokelist[] = new String[30];
        Random random = new Random();
        int placeholder;

        jokelist[0] = "mapoopoo peeejima";
        jokelist[1] = "i wonder what steve harveys thinkin about rn";
        jokelist[2] = "imagine spending an hour and a half messing around in java to make a randomized statement creator that has absolutely no purpose";
        jokelist[3] = "now that this class works im gonna run a randomizer for control schemes so get ready for surprise pain controls";
        jokelist[4] = "if any of you piss me off im gonna put the robot on a moan loop for states";
        jokelist[5] = "advika be like tsundere noises";
        jokelist[6] = "poggers";
        jokelist[7] = "[Placeholder Text]";
        jokelist[8] = "fuit gummi";
        jokelist[9] = "slur";
        jokelist[10] = "its really sad that this might be the only working part of the robot";
        jokelist[11] = "its currently 2:15 AM when im writing this please put me to sleep however you like";
        jokelist[12] = "i have a 1750 word essay due literally tomorrow and i haven't started why am i making this";
        jokelist[13] = "god i wish i had sharper cheekbones id probably get off to myself";
        jokelist[14] = "why do i have fingers";
        jokelist[15] = "these might be randomly generated. who knows?";
        jokelist[16] = "something something harambe something";
        jokelist[17] = "theres a very small chance you get this message at an actual competition, but good luck :)";
        jokelist[18] = "maybe the programs not broken and you're just bad at pressing buttons correctly. ever think of that? huh? bitch.";
        jokelist[19] = "one of these statements is gonna be a cry for help and you'll only find it if you do enough drive practice";
        jokelist[20] = "pussy smell like yoshi side b";
        jokelist[21] = ":pig:";
        jokelist[22] = "if we don't go to worlds im actually going to set the robot on fire";
        jokelist[23] = "robert be like 'imo' noises";
        jokelist[24] = "h";
        jokelist[25] = "hhhhhhhhhhhhh";
        jokelist[26] = "[ERROR program 'YinBot' has crashed, restart robot]";
        jokelist[27] = "tired";
        jokelist[28] = "resounding";
        jokelist[29] = "h    h    h";


        placeholder = random.nextInt(30);

        return jokelist[placeholder];
    }
}
