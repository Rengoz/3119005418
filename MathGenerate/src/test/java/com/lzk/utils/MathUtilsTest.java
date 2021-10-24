package com.lzk.utils;

import junit.framework.TestCase;

import java.util.Random;

public class MathUtilsTest extends TestCase {

    public void testGetNum() {
        MathUtils.setNumRange(10);
        for (int i = 0;i<10;i++){
            System.out.println(MathUtils.getNum());
        }
    }

    public void testGetOperation() {
        for (int i = 0;i<10;i++){
            System.out.println(MathUtils.getOperation());
        }
    }
}