package com.lzk.utils;

import com.lzk.myException.NegativeNumberException;
import com.lzk.myException.ZeroDivException;

import java.util.Stack;

public class CalculatorUtils {


    public static Stack<String> calculate(String expression) throws ZeroDivException, NegativeNumberException {
        String newException = expression.replace("=","");
        String[] parts = newException.split(" ");
        Stack<String> numStack= new Stack<>();
        Stack<String> opStack =new Stack<>();
        Stack<String> progressStack =new Stack<>();//过程栈
        //3+2×(3+4)÷2-1
        for (String part : parts) {
            if (ExpressionUtils.isNum(part)){numStack.push(part);continue;}//如果是数字，直接入栈；
            if (ExpressionUtils.isOp(part)){
                if (")".equals(part)){
                    while (!"(".equals(opStack.peek())){
                        String op = opStack.pop();//弹出运算符
                        String b = numStack.pop();//先弹出的是b
                        String a = numStack.pop();//后弹出的是a
                        String result = calculate(a,b,op);
                        numStack.push(result);
                        progressStack.push(result);//中间结果入栈
                    }
                    opStack.pop();
                    continue;
                }//如果当前符号是右括号)，一直弹栈直到遇到第一个左括号,把左括号弹出并结束循环
                if (opStack.empty()){opStack.push(part);continue;}//如果运算符栈为空直接入栈
                if ("(".equals(part)){opStack.push(part);continue;}
                if (ExpressionUtils.priority(part)>ExpressionUtils.priority(opStack.peek())){//如果运算符栈不为空，判断优先级
                    opStack.push(part);//如果优先级高于当前栈顶
                }else {//如果优先级不高于栈顶
                    while (!opStack.empty()){
                        if (ExpressionUtils.priority(opStack.peek())<ExpressionUtils.priority(part)){break;}//直到运算符栈顶运算符优先级低于当前的，或者
                        if ("(".equals(opStack.peek())){break;}//如果栈顶是左括号，将运算符入栈并跳出循环
                        String op = opStack.pop();//弹出运算符
                        String b = numStack.pop();//先弹出的是b
                        String a = numStack.pop();//后弹出的是a
                        String result = calculate(a,b,op);
                        numStack.push(result);//结果入栈
                        progressStack.push(result);//中间结果入栈
                    }
                    opStack.push(part);
                }
            }
        }//将表达式全数处理完，入栈
        //将栈中剩余的内容计算完成
        while (!opStack.empty()){
            String op = opStack.pop();//弹出运算符
            String b = numStack.pop();//先弹出的是b
            String a = numStack.pop();//后弹出的是a
            String result = calculate(a,b,op);
            numStack.push(result);
            progressStack.push(result);//中间结果入栈
        }
//        System.out.println("答案："+numStack.pop());

        return progressStack;//过程栈记录了中间过程，栈顶是最终结果
    }

    public static String calculate(String a,String b,String op) throws ZeroDivException, NegativeNumberException {
        String result = "";
        switch (op){
            case "+" : result = add(a,b);break;
            case "-" : result = sub(a,b);break;
            case "×" : result = mul(a,b);break;
            case "÷" : result = div(a,b);break;
            default:result="中间值";
        }
        return result;
    }
    public static String add(String a,String b) throws ZeroDivException {
        Integer[] num1 = new Integer[3];//加数拆分三部分，带进位，分子，分母
        Integer[] num2 = new Integer[3];

        if (ExpressionUtils.isFraction(a)){//对加数a进行处理
            num1 = fractionHandle(a);
        }
        else {  num1[0] = Integer.valueOf(a);num1[1]=0;num1[2]=1;}

        if (ExpressionUtils.isFraction(b)){//对加数b进行处理
            num2 = fractionHandle(b);
        }
        else {  num2[0] = Integer.valueOf(b);num2[1]=0;num2[2]=1;}

        Integer[] result = new Integer[3];//带进位单独相加，分数通分相加
        result[0] = num1[0] + num2[0];
        result[1] = num1[1]*num2[2] + num2[1]*num1[2];
        result[2] = num1[2]*num2[2];
        if (result[1]>=result[2]){result[0]+=result[1]/result[2];result[1]%=result[2];}//如果分子比分母大，进带分数
        int gcd = MathUtils.gcd(result[1],result[2]);//分数进行化简;
        if (gcd>1) {
            result[1] /= gcd;
            result[2] /= gcd;
        }
        if (result[0]==0){return result[1]==0?"0":result[1]+"/"+result[2];}
        return result[1]==0?result[0]+"":result[0]+"’"+result[1]+"/"+result[2];//返回值处理，分子为0则证明是整数；
    }
    public static String sub(String a,String b) throws ZeroDivException, NegativeNumberException {
        Integer[] num1 = new Integer[3];//加数拆分三部分，带进位，分子，分母
        Integer[] num2 = new Integer[3];

        if (ExpressionUtils.isFraction(a)){//对加数a进行处理
            num1 = fractionHandle(a);
            num1[0]--;
            num1[1]+=num1[2];
        }
        else {  num1[0] = Integer.valueOf(a)-1;num1[1]=1;num1[2]=1;}

        if (ExpressionUtils.isFraction(b)){//对加数b进行处理
            num2 = fractionHandle(b);
        }
        else {  num2[0] = Integer.valueOf(b);num2[1]=0;num2[2]=1;}

        Integer[] result = new Integer[3];//带进位单独相加，分数通分相加
        result[0]=num1[0]-num2[0];
        result[2] = num1[2]*num2[2];//分母相乘
        int i = num1[1]*num2[2]-num2[1]*num1[2];
        result[1] = i;
        if (i<0){
            result[1] = result[2]+i;
            result[0] -= 1;
        }//分子相减后小于0则往前
        result[0]+=result[1]/result[2];
        result[1] %= result[2];
        int gcd = MathUtils.gcd(result[1],result[2]);//分数进行化简;
        if (gcd>1) {
            result[1] /= gcd;
            result[2] /= gcd;
        }
        if (result[0]<0){throw new NegativeNumberException();}
        if (result[1]==0){return result[0]+"";}
        return result[0]==0?result[1]+"/"+result[2]:result[0]+"’"+result[1]+"/"+result[2];
    }
    public static String mul(String a, String b) throws ZeroDivException {
        Integer[] num1 = new Integer[3];//加数拆分三部分，带进位，分子，分母
        Integer[] num2 = new Integer[3];
        if (ExpressionUtils.isFraction(a)){//a
            num1 = fractionHandle(a);
        }
        else {  num1[0] = Integer.valueOf(a);num1[1]=0;num1[2]=1;}
        if (ExpressionUtils.isFraction(b)){//b
            num2 = fractionHandle(b);
        }
        else {  num2[0] = Integer.valueOf(b);num2[1]=0;num2[2]=1;}


        num1[1]+=num1[0]*num1[2];num1[0]=0;
        num2[1]+=num2[0]*num2[2];num2[0]=0;

        Integer[] result = new Integer[3];
        result[1] = num1[1]*num2[1];
        result[2] = num1[2]*num2[2];
        result[0] = result[1]/result[2];
        result[1] = result[1]%result[2];
        int gcd = MathUtils.gcd(result[1],result[2]);
        if (gcd>1){
            result[1] /= gcd;
            result[2] /= gcd;
        }

        if (result[0]==0){return result[1]==0?"0":result[1]+"/"+result[2];}
        return result[1]==0?result[0]+"":result[0]+"’"+result[1]+"/"+result[2];
    }
    public static int getInt(String str){
        return Integer.valueOf(str.split("/")[0].split("’")[0]);
    }
    private static String div(String a,String b) throws ZeroDivException {
        Integer[] num2 = new Integer[3];
//        Integer[] num2 = new Integer[3];
        if (ExpressionUtils.isFraction(b)){
            num2 = fractionHandle(b);
            num2[1]+=num2[0]*num2[2];
            int i = num2[2];
            num2[2]=num2[1];
            num2[1]=i;
        }else {num2[0]=0;num2[1]=1;num2[2]=Integer.valueOf(b);}
        b = num2[1]+"/"+num2[2];

        return mul(a,b);
    }
    private static Integer[] fractionHandle(String a) throws ZeroDivException {
        Integer[] parts = new Integer[3];
        if (!a.contains("’")){a="0’"+a;}
        String[] a0 = a.split("’");
        String[] a1_2 = a0[1].split("/");
        parts[0] = Integer.valueOf(a0[0]);
        parts[1] = Integer.valueOf(a1_2[0]);
        if ((parts[2] = Integer.valueOf(a1_2[1]))==0){throw new ZeroDivException();}
        return parts;
    }
}
