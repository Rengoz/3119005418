package com.lzk.utils;

import com.lzk.domain.MyBean;
import com.lzk.myException.NegativeNumberException;
import com.lzk.myException.ZeroDivException;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionUtilsTest extends TestCase {

    public void testAdjust() {

    }

    public void testCheck() {
        boolean flag;
        flag = ExpressionUtils.check("1 ÷ 2’9/10 = ");
        System.out.println(flag);
    }

    public void testGetExpression() {
        MathUtils.setNumRange(10);
        for (int i = 0;i<10;i++) {
            ExpressionUtils.getExpression();
        }
        ExpressionUtils.getExpression();
    }

    public void testPriority(){
        System.out.println(ExpressionUtils.priority("×"));
    }

    public void testAddBean() {
       ArrayList list = new ArrayList();
       for (int i = 10;i > 0; i--){
           list.add(new MyBean(i+""));
       }
       ExpressionUtils.addBean(list,new MyBean("3"));
        System.out.println(list);
    }

    public void testCheckRepeat() throws ZeroDivException, NegativeNumberException {
//        for (int i = 0;i<10000;i++){
//            ExpressionUtils.getExpressionAsString();
//        }
//        List<MyBean>[] list = ExpressionUtils.getListSortedByLength();
//        System.out.println(list[0]);
//        System.out.println(list[1]);
//        System.out.println(list[2]);
//        System.out.println(ExpressionUtils.testCount);




//        List<MyBean> list = new ArrayList<>();
//        list.add(new MyBean("2"));
//        list.add(new MyBean("3"));
//        list.add(new MyBean("1"));
//        list.add(new MyBean("7’2/7"));
//        ExpressionUtils.addBean(list,new MyBean("6"));
//        System.out.println(list);
//        System.out.println(ExpressionUtils.checkRepeat(list,"6"));
    }
}