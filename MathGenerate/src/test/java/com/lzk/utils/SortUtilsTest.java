package com.lzk.utils;

import org.junit.Test;

import java.util.ArrayList;

public class SortUtilsTest {
    @Test
    public void testInsertSort() {
        ArrayList list = new ArrayList();
        list.add(1);
        SortUtils.insertSort(list);
    }
}
