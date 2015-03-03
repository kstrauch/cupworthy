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

    public static final double HARD_ALC_CONTENT = 0.40;
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

    public final static double[] ALC_ARRAY = {BEER_CONTENT, LIGHT_BEER_CONTENT, WINE_CONTENT, WINE_COOLER_CONTENT, VODKA_CONTENT, GIN_CONTENT, RUM_CONTENT, TEQUILA_CONTENT, BOURBON_CONTENT, SCOTCH_CONTENT};
    //public final static String[] ALC_NAME = {"Beer", "Light Beer", "Wine", "Wine Cooler", "Vodka",""};

    public static final int RESULT_OK = 0;
    public static final int RED_CUP_INTENT = 1;

    public static final String effect_1 = "You should feel relaxed, slightly warm, and may experience some loss of judgment";
    public static final String effect_2 = "Judgment is impaired, alertness is lowered, and inhibitions are fleeting";
    public static final String effect_3 = "Loss of muscle coordination, judgment and reasoning are impaired";
    public static final String effect_4 = "Slurred speech and poor coordination. NOT SAFE to drive.";
    public static final String effect_5 = "Major loss of balance/muscle control. Vomiting may occur. U R CUT OFF";

    public final static String[] EFFECTS_ARR = {effect_1,effect_2, effect_3, effect_4, effect_5 };

}
