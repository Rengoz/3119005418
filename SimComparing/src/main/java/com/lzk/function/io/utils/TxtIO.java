package com.lzk.function.io.utils;

import java.io.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TxtIO {
    private static String origPath;
    private static String testDirectoryPath;
    private static String resultPath;

    public static String getOrigPath() {
        return origPath;
    }

    public static String getTestDirectoryPath() {
        return testDirectoryPath;
    }

    public static String getResultPath() {
        return resultPath;
    }

    static {
        ResourceBundle properties = ResourceBundle.getBundle("config");
        String currentPath = Thread.currentThread().getContextClassLoader().getResource("").toString().substring(6);
        origPath = currentPath+"articles/orig.txt";
        testDirectoryPath = currentPath+"articles";
        resultPath = currentPath+"articles/result.txt";
    }

    private TxtIO() {}

//    public static String txtRead(String path){
//
//    }
//    public static ArrayList<String> txtRead(){
//        /*
//        * 默认读取指定文件夹中所有文件
//        * 拼接所有文字
//        * 返回一个ArrayList存放
//        * */
//        String strLine = null;
//        String str = "";
//        ArrayList<String> articleList = new ArrayList<>();
//        File file = null;
//        try {
//            if (readOne!=null){file = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().toString().substring(6)+"articles/"+readOne);}
//            else {file = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI().toString().substring(5)+"articles");}
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        File[] files = null;
//        if (readOne!=null){files = new File[1];files[0]=file;}
//        else {
//            files = file.listFiles();
//        }
//        FileInputStream fileInputStream = null;
//        try {
//            for (File element:files
//                 ) {
//                fileInputStream = new FileInputStream(element);
//                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//                while ((strLine = bufferedReader.readLine()) != null) {
//                    str += strLine;
//                }
//                fileInputStream.close();
//                inputStreamReader.close();
//                bufferedReader.close();
//                articleList.add(str);
//            }
//            return articleList;
//        }catch (IOException e){
//            e.printStackTrace();
//            System.out.println("articles中读不到文件");
//        }
//        return null;
//    }


    /*
    * 判断是文件还是文件夹 分别读取
    * */
    public static Map<String,String> read(){
        Map<String,String> result = new HashMap<>();
        result.put(new File(origPath).getName(),readTxt(new File(origPath)));
        result.putAll(read(testDirectoryPath));
        return result;
    }
    public static Map<String,String> read(String path){
        if (""==path){return null;}
        File file = new File(path);
        Map result = new HashMap<String,String>();
        if (file.isFile()){
            result.put(file.getName(),readTxt(file));
        }
        if (file.isDirectory()){
            readTxts(file,result);
        }
        return result;
    }
    private static String readTxt(File file){
        String str = "";
        String strLine = "";
        FileInputStream inputStream = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = new FileInputStream(file);
            reader = new InputStreamReader(inputStream,"UTF-8");
            bufferedReader = new BufferedReader(reader);
            while ((strLine = bufferedReader.readLine())!=null){
                str += strLine;
            }
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader!=null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (str.indexOf("<html>")!=-1){HtmlSpirit.delHTMLTag(str);}
        return str;
    }
    private static Map<String, String> readTxts(File file, Map<String,String> result){
        File[] files = file.listFiles();
        for (File element:files
             ) {
            result.put(element.getName(),readTxt(element));
        }
        return result;
    }

    public static void txtWrite(String path,String context) {
        File file = new File(path);
        try {
            FileWriter writer = new FileWriter(file,true);
            writer.write(context);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
