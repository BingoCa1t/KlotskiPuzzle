package com.klotski.network;

public enum MessageCode
{
    Unknown("0000|"),
    UserRegister("0001|"),
    UserLogin("0002|"),
    UserGetRegisterCode("0003|"),
    UserGetUserInfo("0004|");

    private final String code;

    public String getCode()
    {
        return code;
    }

    MessageCode(String code)
    {
        this.code = code;
    }

    public static MessageCode fromCode(String code)
    {
        for (MessageCode messageCode : MessageCode.values())
        {
            if (messageCode.getCode().equals(code))
            {
                return messageCode;
            }
        }
        return MessageCode.Unknown;
    }
    }
