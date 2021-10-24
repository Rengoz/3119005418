package com.lzk.myException;

public class NegativeNumberException extends Throwable{
    @Override
    public String getMessage() {
        return "计算过程不能出现负数";
    }
}
