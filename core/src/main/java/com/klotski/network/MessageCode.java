package com.klotski.network;

public enum MessageCode
{
    UserRegister("0001|"),
    UserLogin("0002|");
    private final String code;

    MessageCode(String code)
    {
        this.code = code;
    }
}
