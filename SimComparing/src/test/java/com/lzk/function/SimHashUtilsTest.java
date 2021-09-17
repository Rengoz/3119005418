package com.lzk.function;

import com.lzk.function.io.utils.TxtIO;
import com.lzk.function.simhash.utils.SimHashUtils;
import org.junit.Test;

public class SimHashUtilsTest {
    private static String currentPath;
    static {
        currentPath = Thread.currentThread().getContextClassLoader().getResource("").toString().substring(6)+"articles/";
    }
    @Test
    public void getHashTest(){
        String[] strings = {"测试", "ceshi", "ceshi测试", "测", "试"};
        for (String string : strings) {
            String stringHash = SimHashUtils.getHash(string);
            System.out.println(stringHash.length());
            System.out.println("补0");
            if (stringHash.length() < 128) {
                // hash值可能少于128位，在低位以0补齐
                int dif = 128 - stringHash.length();
                for (int j = 0; j < dif; j++) {
                    stringHash += "0";
                }
            }
            System.out.println(stringHash);
        }
    }

    @Test
    public void getSimHashTest(){
        String str0 = TxtIO.read(currentPath+"orig.txt").get("orig.txt");
        String str1 = TxtIO.read(currentPath+"orig_0.8_add.txt").get("orig_0.8_add.txt");
        System.out.println(SimHashUtils.getSimHash(str0));
        System.out.println(SimHashUtils.getSimHash(str1));
    }

}
