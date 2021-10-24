package com.lzk.domain;

import java.util.Stack;

public class Progress {
    public Progress(int length, Stack<String> progress) {
        this.progress = progress;
        this.length = progress.size();
    }
    public Progress() {}

    int length;
    Stack<String> progress;
}
