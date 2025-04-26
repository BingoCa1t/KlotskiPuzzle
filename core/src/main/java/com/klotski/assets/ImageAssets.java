package com.klotski.assets;

/**
 * 图像资源枚举
 */
public enum ImageAssets
{
    AboutButton("img/button/about.png");

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
