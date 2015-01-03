package com.test.tudou.multitagview.util;

import java.util.Random;

/**
 * Created by tudou on 15-1-3.
 */
public class DrawableUtils {

    private static String[] colors1 = new String[] {"#81aac5", "#c99d84", "#dd8787", "#8c85bc", "#a2c389", "#cba37e", "#78b6ab","#995050", "#5b71ac", "#528a7d", "#ad5957", "#578dad", "#aa5f6b", "#7c5b97"};

    public static String getBackgoundColor() {
        Random r = new Random();
        int x = r.nextInt(14);
        return colors1[x];
    }

}
