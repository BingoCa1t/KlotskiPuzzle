package com.klotski.user;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.klotski.Main;
import com.klotski.Scene.KlotskiScene;
import com.klotski.Scene.LoginScene;
import com.klotski.Scene.ScreenManager;
import com.klotski.Scene.StartScene;
import com.klotski.archive.ArchiveManager;
import com.klotski.archive.LevelArchive;
import com.klotski.logic.LevelStatus;
import com.klotski.network.MessageCode;
import com.klotski.network.NetManager;
import com.klotski.network.NetworkMessageObserver;
import com.klotski.utils.json.JsonManager;
import com.klotski.utils.logger.Logger;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

public class UserManager implements NetworkMessageObserver
{
    private Main gameMain;
    private NetManager netManager;
    private JsonManager jsonManager;
    private ScreenManager screenManager;
    private String userInfosRootPath = "D:\\UserInfo\\";
    private UserInfo activeUser;
    private ArchiveManager archiveManager;
    private boolean loggedIn=false;

    public UserManager(Main gameMain ,NetManager netManager, ScreenManager screenManager)
    {
        this.netManager = netManager;
        this.screenManager = screenManager;
        this.jsonManager = new JsonManager();
        this.gameMain = gameMain;
    }

    public void getCode(String email)
    {
        netManager.sendMessage(MessageCode.UserGetRegisterCode,email);

    }

    public int register(String username, String passwordMD5, String email, String code)
    {
        return -1;
    }

    public void login(String email, String passwordMD5)
    {
        netManager.sendMessage(MessageCode.UserLogin.getCode() + email + "|" + passwordMD5);
    }


    public boolean updateUserInfo(UserInfo userInfo)
    {
        //return jsonManager.saveJsonToFile(userInfosRootPath+userInfo.getUserID()+"\\info.usr", userInfo);
        return false;
    }
    public UserInfo DEFAULT()
    {
        UserInfo userInfo = new UserInfo();
        //userInfo.setUserID(0);
        userInfo.setUserName("TEST");
        userInfo.setEmail("wanght2024@mail.sustech.edu.cn");
        LevelArchive levelInfo = new LevelArchive();
        ArrayList<LevelArchive> l=new ArrayList<>();
        levelInfo.setLevelStatus(LevelStatus.UpComing);
        levelInfo.setMapID(1);
        LevelArchive levelInfo2 = new LevelArchive();
        levelInfo2.setLevelStatus(LevelStatus.Closed);
        levelInfo2.setMapID(2);
        l.add(levelInfo2);

        l.add(levelInfo);
        //userInfo.setLevels(l);
        //userInfo.setPasswordMD5("123456");
        userInfo.setRememberPassword(false);
        return userInfo;
    }
    public void UpdateActiveUserInfoFromServer()
    {

    }
    /**
     * 根据userID读取已有用户
     * @param userID
     * @return
     */
    public UserInfo loadUserInfo(int userID)
    {
        try
        {
            // 测试根目录存在性，不存在则创建
            if (!testUserInfosPathWithCreate()) return null;
        }
        catch (Exception e)
        {
            Logger.error("UserManager", e.getMessage());
            return null;
        }
        String userInfoPath = userInfosRootPath + userID + ".usr";
        try
        {
            FileHandle handle = Gdx.files.internal(userInfoPath);
            if (!handle.exists())
            {
                return null;
            }
            UserInfo currentUserInfo = new JsonManager().loadJsonfromFile(userInfoPath, UserInfo.class);
            Logger.debug("UserManager", "User data : " + new JsonManager().getJsonString(currentUserInfo));
            return currentUserInfo;
        }
        catch (Exception e)
        {
            Logger.error("UserManager", e.getMessage());
            return null;
        }
    }
    /**
     * 判定是否为合法用户信息文件
     * @param userInfo 用户信息文件
     * @return 合法性
     */
    public boolean isValidUserInfo(UserInfo userInfo)
    {
        if (
            userInfo.isRememberPassword() == null
               // userInfo.getUserID() == -1 ||
                //userInfo.getSaveArchives() == null ||
                    //userInfo.getPasswordMD5() == null
        ) return false;

        //if (userInfo.getPasswordMD5().isEmpty()) return false;

        return true;
    }
    //定时向服务端发送UserInfo（？如何
    @Override
    public void update(MessageCode code, String message)
    {
        //写完优化成switch case结构

        //登录成功 Login Succeed
        if (code == MessageCode.UserLogin && message.equals("200"))
        {
            KlotskiScene cs = screenManager.getCurrentScreen();
            if (cs instanceof LoginScene loginScene)
            {
                //此时应该显示：“登录成功，正在同步用户数据..."
                loginScene.loginSucceed();
                //接下来是服务端发送用户数据UserInfo，
            }
        }
        //登录失败 Login Fail
        else if(code == MessageCode.UserLogin&&message.equals("404"))
        {

            KlotskiScene cs = screenManager.getCurrentScreen();
            if (cs instanceof LoginScene loginScene)
            {
                loginScene.loginFail();
            }
        }
        //注册时获取验证码
        else if(code == MessageCode.UserGetRegisterCode)
        {
            registerCode=message;
        }
        //登录成功，请求用户信息和存档信息（{userInfo}|||{us
        else if(code == MessageCode.UserGetUserInfo)
        {
            String[] m=message.split(Pattern.quote("|"));
            //activeUser=jsonManager.parseJsonToObject(m[0], UserInfo.class);
            //activeUser=new UserInfo();
            //activeUser.setUserName("");
            //activeUser.setEmail();
            activeUser=jsonManager.parseJsonToObject(message, UserInfo.class);

        }
        else if(code == MessageCode.SendArchive)
        {
            KlotskiScene cs = screenManager.getCurrentScreen();

            if (cs instanceof LoginScene loginScene)
            {
                //获取到了UserInfo
                if(activeUser!=null)
                {
                    String[] m=message.split(Pattern.quote("|"));
                    archiveManager=new ArchiveManager(activeUser,netManager);
                    archiveManager.loadByNetwork(m[0],m[1],m[2],Integer.parseInt(m[3]));
                    Gdx.app.postRunnable(()-> screenManager.setScreen(new StartScene(gameMain,archiveManager)));
                    loggedIn=true;
                }
            }

        }
    }
    private String registerCode="-1";

    public String getRegisterCode()
    {
        return registerCode;
    }

    /**
     * 测试用户配置目录是否存在，若不存在则尝试创建
     *
     * @return 目录存在性，不存在则返回 false，无论是否存在都会进行创建
     */
    public synchronized boolean testUserInfosPathWithCreate()
    {
        try
        {
            // 不存在则创建目录
            if (!Gdx.files.external(userInfosRootPath).exists())
            {
                Logger.warning("UserManager", String.format("User infos directory %s is not exists. It will be created.", userInfosRootPath));
                Gdx.files.external(userInfosRootPath).mkdirs();
                return false;
            }
            return true;
        } catch (Exception e)
        {
            Logger.error("UserManager", String.format("Cannot create user infos directory %s because %s", userInfosRootPath, e.getMessage()));
            return false;
        }
    }
    public void save()
    {

    }

    public UserInfo getActiveUser()
    {
        return activeUser;
    }

    public ArchiveManager getArchiveManager()
    {
         return archiveManager;
    }
}
