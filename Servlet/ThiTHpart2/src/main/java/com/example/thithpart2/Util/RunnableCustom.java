package com.example.thithpart2.Util;

import java.util.Map;

public class RunnableCustom implements Runnable {
    protected String fieldName;
    protected String value ;
    protected String message;
    protected Map<String, String> errors;
    public void setValue(String value) {
        this.value = value;
    }
    @Override
    public void run() {

    }
}
