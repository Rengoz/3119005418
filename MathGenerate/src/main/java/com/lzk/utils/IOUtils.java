package com.lzk.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class IOUtils {

    private static Map<String,BufferedWriter> bwMap = new HashMap<>();
    private static Map<String,BufferedReader> brMap = new HashMap<>();
    public static void write(String path,String res) {
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try{
            writer = new FileWriter(path);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(res+"\n");
            bufferedWriter.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void frequentWrite(String path, String res) throws IOException {
        BufferedWriter bw = bwMap.get(path);
        if (bw==null){
            FileWriter writer = new FileWriter(path);
            BufferedWriter bwriter = new BufferedWriter(writer);
            bwMap.put(path,bwriter);
            bw = bwMap.get(path);
        }
        bw.write(res);
        bw.flush();
    }

    public static String frequentRead(String path) throws IOException{
        BufferedReader br = brMap.get(path);
        if (br==null){
            FileReader fr = new FileReader(path);
            BufferedReader bReader = new BufferedReader(fr);
            brMap.put(path,bReader);
            br = brMap.get(path);
        }
        return br.readLine();
    }
}
