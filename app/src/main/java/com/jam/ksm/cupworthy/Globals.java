package com.jam.ksm.cupworthy;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by kimberlystrauch on 2/21/15.
 */
public class Globals {

    // ALC CONSTANTS -- alcohol content in popular kinds of alcohol
    public static final double BEER_CONTENT = 0.05;
    public static final double LIGHT_BEER_CONTENT = 0.04;
    public static final double WINE_CONTENT = 0.12;
    public static final double WINE_COOLER_CONTENT = 0.05;

    //public static final double HARD_ALC_CONTENT = 0.40;
    public static final double VODKA_CONTENT = 0.40;
    public static final double GIN_CONTENT = 0.40;
    public static final double RUM_CONTENT = 0.40;
    public static final double TEQUILA_CONTENT = 0.40;
    public static final double BOURBON_CONTENT = 0.40;
    public static final double SCOTCH_CONTENT = 0.40;


    // average serving sizes for different alcohols
    public static final double BEER_AMT = 12;
    public static final double WINE_AMT = 5;
    public static final double COOLER_AMT = 12;
    public static final double HARD_AMT = 1.5;


    public static final int TYPE_BEER = 0;
    public static final int TYPE_LIGHT_BEER = 1;
    public static final int TYPE_WINE = 2;
    public static final int TYPE_WINE_COOLER = 3;
    public static final int TYPE_VODKA = 4;
    public static final int TYPE_GIN = 5;
    public static final int TYPE_RUM = 6;
    public static final int TYPE_TEQUILA = 7;
    public static final int TYPE_BOURBON = 8;
    public static final int TYPE_SCOTCH = 9;

    public static final int MILLIS_PER_DAY = 1000 * 3600 * 24;
    public static final int MILLIS_PER_HOUR = 1000 * 3600;

    public final static double[] ALC_ARRAY = {BEER_CONTENT, LIGHT_BEER_CONTENT, WINE_CONTENT, WINE_COOLER_CONTENT, VODKA_CONTENT, GIN_CONTENT, RUM_CONTENT, TEQUILA_CONTENT, BOURBON_CONTENT, SCOTCH_CONTENT};
    //public final static String[] ALC_NAME = {"Beer", "Light Beer", "Wine", "Wine Cooler", "Vodka",""};

    public static final int RESULT_OK = 0;
    public static final int RED_CUP_INTENT = 1;


    // different effects for varying BAC levels
    public static final String effect_1 = "You're feeling relaxed, warm, and lacking good judgment";
    public static final String effect_2 = "Impaired judgment, lowered alertness and inhibitions";
    public static final String effect_3 = "Judgment and reasoning are impaired";
    public static final String effect_4 = "Slurred speech, poor coordination, not safe to drive.";
    public static final String effect_5 = "Major loss of balance, judgment severely impaired.";
    public static final String effect_6 = "You're 'sloppy drunk.' You're cut off!";
    public static final String effect_7 = "Call a friend for help. You are highly, highly intoxicated.";
    public static final String effect_8 = "You should seek medical attention immediately.";

    public final static String[] EFFECTS_ARR = {effect_1,effect_2, effect_3, effect_4, effect_5 ,effect_6, effect_7, effect_8};

    public static final double INTOX = 0.08;

    public static final double[] VOLUME_ARR = {0.0, 1.0, 2.2, 3.4, 4.6, 5.8, 7.3, 8.6, 9.8, 11.1, 12.3,  13.4, 14.7, 15.5, 15.5, 15.5};
}
