package com.lzk.function;

import com.lzk.function.hamming.utils.HammingUtils;
import com.lzk.function.io.utils.TxtIO;
import com.lzk.function.simhash.utils.SimHashUtils;
import org.junit.Test;

public class HammingUtilsTest {
    public HammingUtilsTest() {
    }
    private static String currentPath;
static {
    currentPath = Thread.currentThread().getContextClassLoader().getResource("").toString().substring(6)+"articles/";
}
    @Test
    public void getHammingDistanceTest() {
        String str0 = TxtIO.read(currentPath+"orig.txt").get("orig.txt");
        String str1 = TxtIO.read(currentPath+"orig_0.8_add.txt").get("orig_0.8_add.txt");
        int distance = HammingUtils.getHammingDistance(SimHashUtils.getSimHash(str0), SimHashUtils.getSimHash(str1));
        System.out.println("海明距离：" + distance);
        System.out.println("相似度: " + (100 - distance * 100 / 128) + "%");
    }

    @Test
    public void getHammingExceptionTest() {
        String str0 = "10101010";
        String str1 = "11111111";
        String str2 = "11111111000";
        System.out.println(HammingUtils.getHammingDistance(str0, str1));
        System.out.println(HammingUtils.getSimilarity(str0,str2));
    }

    @Test
    public void getSimilarityTest() {
        String str0 = TxtIO.read(currentPath+"orig.txt").get("orig.txt");
        String str1 = TxtIO.read(currentPath+"orig_0.8_add.txt").get("orig_0.8_add.txt");
        int distance = HammingUtils.getHammingDistance(SimHashUtils.getSimHash(str0), SimHashUtils.getSimHash(str1));
        double similarity = HammingUtils.getSimilarity(SimHashUtils.getSimHash(str0), SimHashUtils.getSimHash(str1));
        System.out.println("str0和str1的汉明距离: " + distance);
        System.out.println("str0和str1的相似度:" + similarity);
    }

}
