package com.lzk.function.hamming.utils;

import java.util.ArrayList;

public class HammingUtils {
    public static int getHammingDistance(String simHash1, String simHash2) {
        int distance = 0;
        if (simHash1.length() != simHash2.length()) {
            // 出错，返回-1
            distance = -1;
        } else {
            for (int i = 0; i < simHash1.length(); i++) {
                if (simHash1.charAt(i) != simHash2.charAt(i)) {
                    distance++;
                }
            }
        }
        return distance;
    }

    public static double[] getSimilarity(String simHash1, ArrayList<String> otherSims){
        double[] simResult = new double[otherSims.size()];
        int i = 0;
        for (String sim2:otherSims
             ) {
            simResult[i++] = getSimilarity(simHash1,sim2);
        }
        return simResult;
    }

    public static double getSimilarity(String simHash1, String simHash2) {
        java.text.DecimalFormat   df =new   java.text.DecimalFormat("######0.00");
        int distance = getHammingDistance(simHash1, simHash2);
        return Double.valueOf(df.format(0.01 * (100 - distance * 100 / 128)));
    }
}
