package com.klotski.user;

import com.klotski.archive.LevelArchive;
import com.klotski.logic.LevelInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class UserInfo
{
    private Boolean rememberPassword;
    private boolean guest;
    //private int userID;
    private String userName;
    private String email;
    //private ArrayList<LevelArchive> levels;

    //id-levelInfo
    //id为Map文件名
    //如何按顺序读取levelInfo？

    //修改逻辑吧。载入关卡流程：
    //在程序开始时，由MapDataManager读取MapData，MapID为唯一标识符，提供HashMap<Integer(MapID),MapData>
    //在用户登录成功时，首先会向服务器请求存档，随后在本地存储一份（为了防止不合题意）
    //在用户登录成功时，由ArchiveManager读取Archive，MapID为唯一标识符，提供HashMap<Integer(MapID),LevelArchive> activeArchive
    //Archive1 2 3也交由ArchiveManager管理，提供setActiveArchive切换存档
    //由LevelSelectScene类里内置（或者由文件读取）的LevelOrder-MapID映射表（List<Integer>)，读取这两个数据绘制前端
    //LevelArchive存储以下数据：mapID唯一标识符，步数最少的一次Stack<MoveStep>，步数最少时用时最少的time、关卡状态levelStatus
    //time默认=-1
/*
    private LinkedHashMap<Integer,LevelInfo> levelInfo1;
    private HashMap<Integer,LevelInfo> levelInfo2;
    private HashMap<Integer,LevelInfo> levelInfo3;

 */




    public String getEmail()
    {
        return email;
    }
    public String getUserName()
    {
        return userName;
    }

    public boolean isGuest()
    {
        return guest;
    }

    public void setGuest(boolean guest)
    {
        this.guest = guest;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
    public void setRememberPassword(boolean rememberPassword)
    {
        this.rememberPassword = rememberPassword;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public Boolean isRememberPassword()
    {
        return rememberPassword;
    }
}
