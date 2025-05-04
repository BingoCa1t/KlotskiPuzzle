package com.klotski.archive;

import com.badlogic.gdx.Gdx;
import com.klotski.logic.LevelInfo;
import com.klotski.user.UserInfo;

import java.util.HashMap;

public class ArchiveManager
{
    //存档内容：过关情况+当前关存档
    private UserInfo userInfo;

    public ArchiveManager(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }
    public void load()
    {
        if(Gdx.files.external(String.valueOf(userInfo.getUserID())).exists())
        {

        }
        else
        {
            init();
        }
    }
    public void init()
    {


    }
}
