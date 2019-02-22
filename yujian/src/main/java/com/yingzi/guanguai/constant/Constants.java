package com.yingzi.guanguai.constant;

public enum Constants {
    APPID("wx85b7d684388d6d28", 0), SECRET("7f650df0ee70da790e71e1cd38b1c616", 1), CACHE("sessionCache", 2),SUCESS("sucess",4),FAIL("fail",5),REG("\\w+@(\\w+\\.){1,3}\\w+",6);
    String value;
    int code;

    Constants(String value, int code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
