package com.lzk.main;

import com.lzk.function.hamming.utils.HammingUtils;
import com.lzk.function.io.utils.TxtIO;
import com.lzk.function.simhash.utils.SimHashUtils;


import java.util.Iterator;
import java.util.Map;

public class ArticleCompare {
    public static void main(String[] args) {
        Object myReader = null;
        if (args.length==3){
            Map<String,String> orig = TxtIO.read(args[0]);
            Map<String,String> other = TxtIO.read(args[1]);
            String origName = orig.keySet().iterator().next();
            //原文本只能选一个，若选择文件夹 则默认将第一个文件作为原文本
            String otherName = other.keySet().iterator().next();
            //hanlp版本1.8---》结果0.72
            //11110000111010011111111100010101010011010010011101111011001010000111110100100011000101011100011011100001101110100100111111010000
            //10110101010000010011110110010000010000010010011011110001000010010011111100100110011101010110001111100011111111100101111100000000
            //hanlp版本1.5----》0.82
            //11110000111111010111111100110101010011010110011001111011001010000111110100000010000101011100011010100000111110100100111111110000
            //11110001011111000111110001100011010010010010010101111011001010000011111100000010010101010100011010100110110111110100111010110000
            String result = origName+"与"+otherName+"相似度："+HammingUtils.getSimilarity(SimHashUtils.getSimHash(orig.get(origName)),SimHashUtils.getSimHash(other.get(otherName)));
            System.out.println(result);
            TxtIO.txtWrite(args[2],result);
        }
        else {
            Map<String,String> result = TxtIO.read();
            String orig = result.get("orig.txt");
            String origSim = SimHashUtils.getSimHash(orig);
            String otherSim = "";
            result.remove("orig.txt");
            result.remove("result.txt");
            Iterator iterator = result.keySet().iterator();
            String key = "";
            String report = "";
            double similarity = 0.0;
            while (iterator.hasNext()){
                key = (String) iterator.next();
                otherSim = SimHashUtils.getSimHash(result.get(key));
                similarity = HammingUtils.getSimilarity(origSim,otherSim);
                report += "原文本"+TxtIO.getOrigPath()+"与"+key+"相似度："+similarity+"\n";
            }
            TxtIO.txtWrite(TxtIO.getResultPath(),report);

        }
    }
}