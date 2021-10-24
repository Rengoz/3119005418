package com.lzk.utils;

import com.lzk.myException.NegativeNumberException;
import com.lzk.myException.ZeroDivException;
import junit.framework.TestCase;


public class CalculatorUtilsTest extends TestCase {

//    public void testCalculate() {
//        MathUtils.setNumRange(10);
//        for (int i = 0;i<10;){
//            String expression = ExpressionUtils.getExpressionAsString();
//            try {
//                String result = CalculatorUtils.calculate(expression).pop();
//                i++;
//                System.out.println(expression+result);
//            }catch (NegativeNumberException e){
//                e.getMessage();
//            }catch (ZeroDivException zeroDivException){
//                zeroDivException.getMessage();
//            }
//        }
//
//    }


    public void testCalculate1() throws ZeroDivException, NegativeNumberException {
//        MathUtils.setNumRange(10);
//            String expression = "9’2/9 - 10’4/5 + 9 - 2’2/3 =";
//            try {
//                System.out.print(expression);
//                System.out.print(CalculatorUtils.calculate(expression));
//                System.out.println();
//            }catch (NegativeNumberException e){
//                System.out.println(e.getMessage());
//            }
    }

    public void testIsNum() {
        System.out.println(ExpressionUtils.isNum("12#/2"));
        System.out.println(ExpressionUtils.isNum("12/2"));
        System.out.println(ExpressionUtils.isNum("1’2/82"));
        System.out.println(ExpressionUtils.isNum("12/2’3"));
        System.out.println(ExpressionUtils.isNum("12/2/3"));
        System.out.println(ExpressionUtils.isNum("1’22’3"));
        System.out.println(ExpressionUtils.isNum("1’2/2’3"));
        System.out.println(ExpressionUtils.isNum(""));
        System.out.println(ExpressionUtils.isNum("1’/2"));
        System.out.println(ExpressionUtils.isNum("1"));
        System.out.println(ExpressionUtils.isNum("33"));
        System.out.println(ExpressionUtils.isNum("/1"));
        System.out.println(ExpressionUtils.isNum("1’’//"));
    }

    public void testIsOp() {
        System.out.println(ExpressionUtils.isOp("+"));
        System.out.println(ExpressionUtils.isOp("-"));
        System.out.println(ExpressionUtils.isOp("×"));
        System.out.println(ExpressionUtils.isOp("÷"));
        System.out.println(ExpressionUtils.isOp("("));
        System.out.println(ExpressionUtils.isOp(")"));
        System.out.println(ExpressionUtils.isOp("7"));
    }

    public void testAdd() {
        try {
            System.out.println(CalculatorUtils.add("4’6/3","3’2/3"));
        } catch (ZeroDivException e) {
            e.printStackTrace();
        }
    }
    public void testSub() {
        try {
            System.out.println(CalculatorUtils.sub("3’1/2","3’3/5"));
        } catch (ZeroDivException | NegativeNumberException e) {
            e.printStackTrace();
        }
    }
//    public void testMul() {
//        try {
//            System.out.println(CalculatorUtils.mul("-2’3/5","-0’3/5"));
//        } catch (ZeroDivException e) {
//            e.printStackTrace();
//        }
//    }
    public void testOther(){

    }

}