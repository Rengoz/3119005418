package com.lzk.Exception;

public class TooShortException extends Exception{
    public TooShortException() {}
    public TooShortException(String errorTxt) {super(errorTxt);}
}
