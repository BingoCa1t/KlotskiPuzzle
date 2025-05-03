package com.klotski.assets;

/**
 * 图像资源枚举
 */
public enum ImageAssets
{
    AboutButton("img/button/about.png"),
    Caocao("Caoc.png|CaocSelected.png"),
    Guanyu("Guanyu.png|GuanyuSelected.png"),
    HuangZong("HuangZong.png|HuangZongSelected.png"),
    ZhaoYun("ZhaoYun.png|ZhaoYunSelected.png");
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
