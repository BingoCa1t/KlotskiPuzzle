package com.klotski.user;

import com.klotski.archive.LevelArchive;
import com.klotski.logic.LevelInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class UserInfo
{
    private Boolean rememberPassword;
    private boolean guest;
    private int userID;
    private String userName;
    private String passwordMD5;
    private String email;
    private ArrayList<LevelArchive> levels;
    //id-levelInfo
    //id为Map文件名
    private HashMap<Integer,LevelInfo> levelInfo1;
    private HashMap<Integer,LevelInfo> levelInfo2;
    private HashMap<Integer,LevelInfo> levelInfo3;
    public ArrayList<LevelArchive> getLevels()
    {
        return levels;
    }

    public int getUserID()
    {
        return userID;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPasswordMD5()
    {
        return passwordMD5;
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

    public void setLevels(ArrayList<LevelArchive> levels)
    {
        this.levels = levels;
    }

    public void setPasswordMD5(String passwordMD5)
    {
        this.passwordMD5 = passwordMD5;
    }

    public void setRememberPassword(boolean rememberPassword)
    {
        this.rememberPassword = rememberPassword;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
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
