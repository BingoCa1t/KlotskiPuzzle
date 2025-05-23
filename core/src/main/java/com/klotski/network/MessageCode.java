package com.klotski.network;

/**
 * 网络通信代码
 *
 * @author BingoCAT
 */
public enum MessageCode
{
    /**
     * 未知代码
     */
    Unknown("0000|"),
    /**
     * 用户注册相关
     */
    UserRegister("0001|"),
    /**
     * 用户登录相关
     */
    UserLogin("0002|"),
    /**
     * 获取验证码
     */
    UserGetRegisterCode("0003|"),
    /**
     * 登录成功，获取用户信息
     */
    UserGetUserInfo("0004|"),
    /**
     * 发送存档
     */
    SendArchive("0005|"),
    /**
     * 开始观战
     */
    BeginWatch("0010|"),
    /**
     * 结束观战
     */
    EndWatch("0011|"),
    /**
     * 开始游戏
     */
    BeginGame("0040|"),
    /**
     * 结束游戏
     */
    EndGame("0041|"),
    /**
     * 获取在线列表
     */
    OnlineList("0020|"),
    /**
     * 开始观战
     */
    ToWatch("0060|"),
    /**
     * 观战时更新界面
     */
    UpdateWatch("0015|"),
    ToWatch1("0061|");


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
