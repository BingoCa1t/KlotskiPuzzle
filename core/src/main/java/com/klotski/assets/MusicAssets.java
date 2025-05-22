package com.klotski.assets;

/**
 * 音乐资源枚举
 */
public enum MusicAssets
{
    GameMusic("music\\gamemusic.mp3"),
    MainBGM("music\\mainbgm.mp3"),
    ;

    private final String alias;

    MusicAssets(String alias)
    {
        this.alias = alias;
    }

    public String getAlias()
    {
        return alias;
    }
}
