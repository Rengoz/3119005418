package com.lzk.domain;
import java.util.ArrayList;
import java.util.List;

public class MyBean{
    public String result;

    public List<MyBean> list;//中间结果相同时往下一个链表查询

    public MyBean(String result) {
        this.result = result;
        this.list = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "result='" + result + '\'';
    }
}
