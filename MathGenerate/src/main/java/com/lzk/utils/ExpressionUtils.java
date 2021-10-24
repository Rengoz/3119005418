package com.lzk.utils;

import com.lzk.domain.MyBean;
import com.lzk.domain.Progress;
import com.lzk.myException.NegativeNumberException;
import com.lzk.myException.ZeroDivException;

import java.util.*;

public class ExpressionUtils {
    private ExpressionUtils(){}
    private static Random random;//共用一个对象生成随机数
    private static int maxNumCount = 4;//参与运算的最大元素个数
    private static Progress progress = null;
    private static List[] listSortedByLength = new List[3];
    public static int testCount = 0;

    public static void setMaxNumCount(int maxNumCount) {
        ExpressionUtils.maxNumCount = maxNumCount;
    }

    static {
        random = new Random();
    }
    
    //获取完整表达式，合法的数字、符号、括号顺序
    public static List<String> getExpression(){
        int numCount = random.nextInt(maxNumCount-1)+2;
        int opCount = numCount-1;
        Stack<String> numStack = new Stack<>();
        Stack<Character> opStack = new Stack<>();
        Stack<String> numStack1 = (Stack<String>) numStack.clone();
        Stack<Character> opStack1 = (Stack<Character>) opStack.clone();
        //接收随机数和随机运算符；
        String[] nums = MathUtils.getNum(numCount);
        char[] operations = MathUtils.getOperation(opCount);
        //分别将数字和符号入栈
        for (String num:nums) {
            numStack.push(num);
        }
        for (Character op:operations) {
            opStack.push(op);
        }

        List<String> expressionList = new ArrayList<>();
        int leftC = 0;
        int leftNum = 0;
        while (!opStack.empty()){
            expressionList.add(numStack.pop());
            leftNum++;
            if ( (int)(Math.random()*10)%3==0 && leftC>0 && leftNum>1){expressionList.add(")");leftC--;expressionList.add(String.valueOf(opStack.pop()));leftNum=0;break;}
            expressionList.add(String.valueOf(opStack.pop()));
            if ( (int)(Math.random()*10)%4==0 && numStack.size() >2){expressionList.add("(");leftC++;leftNum=0;}
        }expressionList.add(numStack.pop());
        if (leftC>0){
            for (int j = 0;j < leftC;j++){
                expressionList.add(")");
            }
        }
        expressionList.add("=");


        return expressionList;
    }

    public static List[] getListSortedByLength() {
        return listSortedByLength;
    }

    public static String getExpressionAsString(){
        List<String> expressionList = getExpression();
        StringBuilder builder = new StringBuilder();
        for (int i = 0;i<expressionList.size();i++){
            builder.append(expressionList.get(i)+" ");
        }
        String expression = builder.toString();
        Stack<String> cloneStack;
        try {
            //如果计算成功,开始查重;
            Stack<String> stack = CalculatorUtils.calculate(expression);
            cloneStack= (Stack<String>) stack.clone();
            //中间结果步数，区分三条查重链表
            int size = stack.size();
            //分别向三种长度的分支插入数据

            if (listSortedByLength[size-1]==null){List<MyBean> list = new ArrayList<>();listSortedByLength[size-1] = list;}
            List<MyBean> tempList = listSortedByLength[size-1];

            //倒叙取出计算的中间结果
            Stack<String> reStack = new Stack<>();
            while (!stack.empty()){
                reStack.push(stack.pop());
            }

            boolean flag = add(reStack,tempList,false);
            if (flag == true){testCount++;}

        } catch (ZeroDivException e) {
            e.getMessage();
            return getExpressionAsString();
        } catch (NegativeNumberException e) {
            e.getMessage();
            return getExpressionAsString();
        }

        //最后复位链表

        return expression + cloneStack.pop();
    }
    //拆分表达式，返回以空格分割开的数字+符号链表    
    public static String[] adjust(String expression) {
        return expression.trim().split(" ");
    }

    public static boolean add(Stack<String> reStack,List<MyBean> tempList,boolean flag){
        if (reStack.empty()){
            if (flag==true) {return true;}
            else {return false;}
        }
        String result = reStack.pop();
        //如果不重复则插入当前层次的链表
        int index = ExpressionUtils.checkRepeat(tempList,result);
        if (index == -1){
            MyBean aBean = new MyBean(result);
            ExpressionUtils.addBean(tempList,aBean);
            return add(reStack,aBean.list,false);//如果上次没重复，这次也没重复，则不重复
        }
        else {
            return add(reStack,tempList.get(index).list,true);
        }
    }

    public static boolean check(String expression){
        String[] parts = adjust(expression);
        if (parts.length<4){return false;}//最少的二元运算也能分成四部分
        for (String part : parts) {//判断每部分是否合法
            if (!(isNum(part)||isOp(part)||"=".equals(part))){return false;}
        }

        return true;
    }

    public static int priority(String op) {
        return ("+".equals(op)||"-".equals(op)) ? 0 : 1;
    }
    //判断字符串是否是一个数字；
    public static boolean isNum(String num) {
        if (num.length()<1){return false;}
        char[] chars = num.toCharArray();
        //首个字符不是数字，直接返回false
        if (!(Character.isDigit(chars[0]))){return false;}
        //遍历字符串.检测是否有非法字符
        int fenCount = 0;
        int divCount = 0;
        int mutex = 0;
        int gap = 0;
        for (int index = 1; index < chars.length;index++){
            if (!Character.isDigit(chars[index])){
                if (chars[index]!='’' && chars[index]!='/'){return false;};
                if (chars[index]=='’'){fenCount++;mutex=1;if (!Character.isDigit(chars[index+1])){return false;}continue;}
                if (chars[index]=='/'){divCount++;mutex=0;}
        }
        }

        if (fenCount>1||divCount>1){return false;}
        if (fenCount == 1 && divCount == 0){return false;}
        if (mutex!=0){return false;}
        return true;
    }
    //判断字符串是否是运算符
    public static boolean isOp(String op){
        switch (op){
            case "+" : return true;
            case "-" : return true;
            case "×" : return true;
            case "÷" : return true;
            case "(" : return true;
            case ")" : return true;
            default:
        }
        return false;
    }
    //判断数字是否为分数
    public static boolean isFraction(String fraction){
        return isNum(fraction) && fraction.contains("/");
    }

    public static int checkRepeat(List<MyBean> list,String result){
        if (list==null||list.size()==0){return -1;}
        int left = 0;//左索引
        int right = list.size()-1;//右索引
        String low = list.get(left).result;
        String high = list.get(right).result;
        try {
            String resultStr = CalculatorUtils.sub(result,low);
            int lowInt = CalculatorUtils.getInt(low);
            int highInt = CalculatorUtils.getInt(high);
            int gap = CalculatorUtils.getInt(resultStr);
            if (left==right){ return list.get(left).result.equals(result)?left:-1;}
            int mid = left+gap*(right-left)/(highInt-lowInt);
            return select(list,left,right,mid,result);
        } catch (Throwable e){
            return -1;
        }
    }
    public static int select(List<MyBean> list,int left,int right,int mid,String result) {
        String midStr = list.get(mid).result;
        if (left>right){return -1;}
        if (result.equals(midStr)){return mid;}
        try {
            CalculatorUtils.sub(result,midStr);//不报错则证明是result大于mid
            return select(list,mid+1,right,(mid+1+right)/2,result);
        } catch (Throwable e) {
            return select(list,left,mid-1,(mid-1+right)/2,result);
        }
    }

    public static void addBean(List<MyBean> beans, MyBean bean){
        beans.add(bean);
        Collections.sort(beans, new Comparator<MyBean>() {
            @Override
            public int compare(MyBean o1, MyBean o2) {
                try {
                    CalculatorUtils.sub(o1.result,o2.result);
                } catch (Throwable e) {
                    return -1;
                }
                return 1;
            }
        });
    }
}
