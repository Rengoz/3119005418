package com.lzk.myException;

public class ZeroDivException extends Throwable{
    @Override
    public String getMessage() {
        return "分母不能为0";
    }
}
