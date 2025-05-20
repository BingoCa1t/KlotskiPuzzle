package com.klotski.assets;

/**
 * 图像资源枚举
 */
public enum ImageAssets
{
    // 曹操
    cc("chess\\cc.png"),
    ccS("chess\\ccS.png"),
    // 关羽
    gyH("chess\\gyH.png"),
    gyW("chess\\gyW.png"),
    gyHS("chess\\gyHS.png"),
    gyWS("chess\\gyWS.png"),
    // 赵云
    zyH("chess\\zyH.png"),
    zyW("chess\\zyW.png"),
    zyHS("chess\\zyHS.png"),
    zyWS("chess\\zyWS.png"),
    // 黄忠
    hzH("chess\\hzH.png"),
    hzW("chess\\hzW.png"),
    hzHS("chess\\hzHS.png"),
    hzWS("chess\\hzWS.png"),
    // 马超
    mcH("chess\\mcH.png"),
    mcW("chess\\mcW.png"),
    mcHS("chess\\mcHS.png"),
    mcWS("chess\\mcWS.png"),
    // 卒
    zu("chess\\zu.png"),
    zuS("chess\\zuS.png"),
    // 张飞
    zfH("chess\\zfH.png"),
    zfW("chess\\zfW.png"),
    zfHS("chess\\zfHS.png"),
    zfWS("chess\\zfWS.png");
    private final String alias;

    ImageAssets(String alias)
    {
        this.alias = alias;
    }

    public String getAlias()
    {
        return alias;
    }
    }
