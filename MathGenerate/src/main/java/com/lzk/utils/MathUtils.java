package com.lzk.utils;



import java.util.Random;

public class MathUtils {
    private static int numRange = 10;
    private static char[] op= {'+','-','×','÷'};

    public static void setNumRange(int numRange) {
        if (numRange<=0){
            System.out.println("数字范围设置必须大于0");
            return;
        }
        MathUtils.numRange = numRange;
    }

    /*
    * 生成随机数，包括有：
    * 0.分数 2.整数
    * @return num String类型
    * */
    public static String getNum() {
        String num = "";
        Random random = new Random();
        int type = random.nextInt(2);
        switch (type){
            case 0 : num = getInteger();break;
            case 1 : num = getFraction();break;
        }
        return num;
    }
    public static String[] getNum(int i){
        String[] nums = new String[i];
        for (int j = 0;j < i;j++){nums[j] = getNum();}
        return nums;
    }
    private static String getInteger() {
        Random random = new Random();
        String integer = String.valueOf(random.nextInt(numRange)+1);
        return integer;
    }
    private static String getFraction(){
        int a;
        int b;
        int c;
        String e;
        while (true) {
            a = Integer.valueOf(getInteger());
            b = Integer.valueOf(getInteger());
            c = (c = Integer.valueOf(getInteger()))==numRange?c-1:c;
            e = c == 1 || c == 0 ? "" : String.valueOf(c)+"’";
            if (a!=b){
                if (a>b){int d = b;b = a;a = d;break;}
            }
        }
        int gcd = gcd(a,b);
        return e+a/gcd+"/"+b/gcd;
    }
    //获取a，b的最大公因子，利用辗转相除法
    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
    public static char getOperation() {
        int index = new Random().nextInt(op.length);
        return op[index];
    }
    public static char[] getOperation(int i){
        char[] chars = new char[i];
        for (int j = 0;j < i;j++){chars[j] = getOperation();}
        return chars;
    }

}
