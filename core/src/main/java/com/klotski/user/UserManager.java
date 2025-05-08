package com.klotski.user;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.klotski.Scene.KlotskiScene;
import com.klotski.Scene.LoginScene;
import com.klotski.Scene.ScreenManager;
import com.klotski.archive.LevelArchive;
import com.klotski.logic.LevelInfo;
import com.klotski.logic.LevelStatus;
import com.klotski.network.MessageCode;
import com.klotski.network.NetManager;
import com.klotski.network.NetworkMessageObserver;
import com.klotski.utils.json.JsonManager;
import com.klotski.utils.logger.Logger;

import java.util.ArrayList;
import java.util.Stack;

public class UserManager implements NetworkMessageObserver
{

    private NetManager netManager;
    private JsonManager jsonManager;
    private ScreenManager screenManager;
    private String userInfosRootPath = "D:\\UserInfo\\";
    private UserInfo activeUser;

    public UserManager(NetManager netManager, ScreenManager screenManager)
    {
        this.netManager = netManager;
        this.screenManager = screenManager;
        this.jsonManager = new JsonManager();
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
        return jsonManager.saveJsonToFile(userInfosRootPath+userInfo.getUserID()+"\\info.usr", userInfo);
    }
    public UserInfo DEFAULT()
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserID(0);
        userInfo.setUserName("TEST");
        userInfo.setEmail("TEST@TEST.com");
        LevelArchive levelInfo = new LevelArchive();
        ArrayList<LevelArchive> l=new ArrayList<>();
        levelInfo.setLevelStatus(LevelStatus.UpComing);
        levelInfo.setLevelID(0);
        levelInfo.setMoveSteps(new Stack<>());

        l.add(levelInfo);
        userInfo.setLevels(l);
        userInfo.setPasswordMD5("123456");
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
    public boolean isValidUserInfo(UserInfo userInfo) {
        if (
            userInfo.isRememberPassword() == null ||
                userInfo.getUserID() == -1 ||
                //userInfo.getSaveArchives() == null ||
                    userInfo.getPasswordMD5() == null
        ) return false;

        if (userInfo.getPasswordMD5().isEmpty()) return false;

        return true;
    }
    @Override
    public void update(MessageCode code, String message)
    {
        if (code == MessageCode.UserLogin && message.equals("200"))
        {
            KlotskiScene cs = screenManager.getCurrentScreen();
            if (cs instanceof LoginScene loginScene)
            {
                //此时应该显示：“登录成功，正在同步用户数据..."
                loginScene.loginSucceed();
            }
        }
        if(code == MessageCode.UserLogin&&message.equals("404"))
        {
            KlotskiScene cs = screenManager.getCurrentScreen();
            if (cs instanceof LoginScene loginScene)
            {
                loginScene.loginFail();
            }
        }
        if(code == MessageCode.UserGetRegisterCode)
        {
            registerCode=message;
        }
        if(code == MessageCode.UserGetUserInfo)
        {
            activeUser=jsonManager.parseJsonToObject(message, UserInfo.class);
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
}
