package com.klotski.archive;

import com.badlogic.gdx.Gdx;
import com.klotski.logic.LevelStatus;
import com.klotski.user.UserInfo;
import com.klotski.utils.json.JsonManager;
import com.klotski.utils.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * 大存档，每个用户有三个存档
 * 取得用户信息后，负责读取小存档，并生成HashMap<levelID,levelArchive>
 */
public class ArchiveManager
{
    //存档内容：过关情况+当前关存档
    private UserInfo userInfo;
    private JsonManager jsonManager;
    private HashMap<Integer,LevelArchive> archive1;
    private HashMap<Integer,LevelArchive> archive2;
    private HashMap<Integer,LevelArchive> archive3;
    private HashMap<Integer,LevelArchive> activeArchive;
    private String userInfoPath;
    public ArchiveManager(UserInfo userInfo)
    {
        this.userInfo = userInfo;
        jsonManager = new JsonManager();
        archive1 = new HashMap<>();
        archive2 = new HashMap<>();
        archive3 =new HashMap<>();
        userInfoPath = "D:\\UserInfo\\"+userInfo.getUserID()+"\\";
        activeArchive = archive1;
    }
    public void setActiveArchive(int n)
    {
        switch (n)
        {
            case 1:
                activeArchive = archive1;
                break;
                case 2:
                    activeArchive = archive2;
                    break;
                    case 3:
                        activeArchive = archive3;
                        break;
                        default:
                            activeArchive = archive1;
                            break;
        }
    }
    public void load()
    {
        if(Gdx.files.external(userInfoPath+"\\archive\\1.ar").exists())
        {
            archive1=jsonManager.loadJsonfromFile(userInfoPath+"\\archive\\1.ar",HashMap.class);
            archive2=jsonManager.loadJsonfromFile(userInfoPath+"\\archive\\2.ar",HashMap.class);
            archive3=jsonManager.loadJsonfromFile(userInfoPath+"\\archive\\3.ar",HashMap.class);
        }
        else
        {
            Logger.debug("Load Default");
            init();
        }
    }
    public void init()
    {
        for(int i=1; i<40; i++)
        {
            LevelArchive archive = new LevelArchive();
            archive.setLevelStatus(LevelStatus.UpComing);
            archive.setMoveSteps(new Stack<>());
            archive.setLevelID(i);
            archive.setSeconds(-1);
            archive1.put(i, archive);
            archive2.put(i, archive);
            archive3.put(i, archive);
            save();
        }
    }
    public void save()
    {
        jsonManager.saveJsonToFile(userInfoPath+"\\archive\\1.ar", archive1);
        jsonManager.saveJsonToFile(userInfoPath+"\\archive\\2.ar", archive2);
        jsonManager.saveJsonToFile(userInfoPath+"\\archive\\3.ar", archive3);
    }
    public void change(int index, LevelArchive archive)
    {
        //传递的是副本
        activeArchive.replace(index, new LevelArchive(archive));
    }

}
