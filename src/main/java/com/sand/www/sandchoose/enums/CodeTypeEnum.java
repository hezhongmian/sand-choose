package com.sand.www.sandchoose.enums;

public enum CodeTypeEnum {

    KUAISAN(1, "1-6"),
    SAND(2, "0-9"),
    PAILIESAN(3, "0-9"),
    PAILIEWU(4, "0-9");

    private int code;
    private String value;

    CodeTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
