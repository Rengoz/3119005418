package com.lzk.function;

import com.lzk.function.io.utils.HtmlSpirit;
import com.lzk.function.io.utils.TxtIO;
import org.junit.Test;

public class TxtIOUtilsTest {
    private static String currentPath;
    static {
        currentPath = Thread.currentThread().getContextClassLoader().getResource("").toString().substring(6)+"articles/";
    }
    @Test
    public void readTxtTest() {
        String str = TxtIO.read(currentPath+"orig.txt").get("orig.txt");
        System.out.println(str);
    }

    @Test
    public void writeTxtTest() {
        double[] elem = {0.11, 0.22, 0.33, 0.44, 0.55};
        for (int i = 0; i < elem.length; i++) {
            TxtIO.txtWrite(currentPath+"result.txt",String.valueOf(elem[i]));
        }
    }

    @Test
    public void readTxtFailTest() {
        String str = TxtIO.read("D:/test/none.txt").get("none.txt");
    }

    @Test
    public void writeTxtFailTest() {
        // 路径错误，写入失败
        double[] elem = {0.11, 0.22, 0.33, 0.44, 0.55};
        for (int i = 0; i < elem.length; i++) {
            TxtIO.txtWrite(currentPath+"res.txt",String.valueOf(elem[i]));
        }
    }

    @Test
    public void htmlSpiritTest() {
        System.out.println(HtmlSpirit.delHTMLTag("<html>sfggs<asdasd>12321<div><script>"));
    }
}
