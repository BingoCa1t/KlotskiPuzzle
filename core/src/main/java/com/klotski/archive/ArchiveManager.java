package com.klotski.archive;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.core.type.TypeReference;
import com.klotski.logic.LevelStatus;
import com.klotski.network.MessageCode;
import com.klotski.network.NetManager;
import com.klotski.user.UserInfo;
import com.klotski.utils.json.JsonManager;
import com.klotski.utils.logger.Logger;

import java.util.HashMap;
import java.util.Stack;

/**
 * 大存档，每个用户有三个存档
 * 取得用户信息后，负责读取小存档，并生成HashMap<MapID,levelArchive>
 * @author BingoCAT
 */
public class ArchiveManager
{

    //存档内容：过关情况+当前关存档
    private UserInfo userInfo;
    private String eMail;
    private JsonManager jsonManager;
    private HashMap<Integer,LevelArchive> archive1;
    private HashMap<Integer,LevelArchive> archive2;
    private HashMap<Integer,LevelArchive> archive3;
    private HashMap<Integer,LevelArchive> activeArchive;
    private String userInfoPath;
    private NetManager netManager;
    private int activeN=1;

    /**
     * 初始化存档管理器
     * @param userInfo 用户信息
     * @param netManager Main里的网络管理器
     */
    public ArchiveManager(UserInfo userInfo,NetManager netManager)
    {
        this.userInfo = userInfo;
        eMail = userInfo.getEmail();
        this.netManager = netManager;
        jsonManager = new JsonManager();
        archive1 = new HashMap<>();
        archive2 = new HashMap<>();
        archive3 =new HashMap<>();
        //userInfoPath = "D:\\UserInfo\\"+userInfo.getUserID()+"\\";
        activeArchive = archive1;
    }

    /**
     * 设置当前存档（1-3）
     * @param n 存档编号，超出则默认是1
     */
    public void setActiveArchive(int n)
    {
        switch (n)
        {
            case 1:
                activeArchive = archive1;
                activeN = 1;
                break;
                case 2:
                    activeArchive = archive2;
                    activeN = 2;
                    break;
                    case 3:
                        activeArchive = archive3;
                        activeN = 3;
                        break;
                        default:
                            activeArchive = archive1;
                            activeN = 1;
                            break;
        }
    }

    /**
     * 从本地文件加载（已弃用）
     */
    public void load()
    {
        /*
        if(Gdx.files.external(userInfoPath+"\\archive\\1.ar").exists()||Gdx.files.external(userInfoPath+"\\archive\\2.ar").exists()||Gdx.files.external(userInfoPath+"\\archive\\3.ar").exists())
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

         */
    }

    /**
     * 从服务器加载用户存档（默认）
     * 每次登录时，服务端会向客户端发送存档数据
     * 而后每进行一次移动，都会向服务器更新存档
     * @param json1 存档1
     * @param json2 存档2
     * @param json3 存档3
     * @param active 当前存档编号
     */
    public void loadByNetwork(String json1,String json2,String json3,int active)
    {
        TypeReference<HashMap<Integer, LevelArchive>> ref = new TypeReference<HashMap<Integer, LevelArchive>>() {};
        archive1=jsonManager.parseJsonToObject(json1, ref);
        archive2=jsonManager.parseJsonToObject(json2,ref);
        archive3=jsonManager.parseJsonToObject(json3,ref);
        switch(active)
        {
            case 1:
                activeArchive=archive1;
                activeN = 1;
                break;
                case 2:
                    activeArchive=archive2;
                    activeN = 2;
                    break;
                    case 3:
                        activeArchive=archive3;
                        activeN = 3;
                        break;
                        default:
                            activeArchive=archive1;
                            activeN = 1;
                            break;
        }
    }

    /**
     * 向服务器存档
     */
    public void saveByNetwork()
    {
        //String json0=jsonManager.getJsonString(userInfo);
        String json1=jsonManager.getJsonString(archive1);
        String json2=jsonManager.getJsonString(archive2);
        String json3=jsonManager.getJsonString(archive3);
        netManager.sendMessage(MessageCode.SendArchive,userInfo.getEmail()+"|",json1,json2,json3,String.valueOf(activeN));
    }
    /*
    public void init()
    {
        for(int i=1; i<60; i++)
        {
            LevelArchive archive = new LevelArchive();
            archive.setLevelStatus(LevelStatus.Closed);
            //archive.setMoveSteps(new Stack<>());
            archive.setMapID(i);
            archive.setSeconds(-1);
            archive1.put(i, archive);
            archive2.put(i, archive);
            archive3.put(i, archive);

        }
        archive1.get(1).setLevelStatus(LevelStatus.UpComing);
        archive2.get(1).setLevelStatus(LevelStatus.UpComing);
        archive3.get(1).setLevelStatus(LevelStatus.UpComing);
        activeArchive=archive1;
        String json1=jsonManager.getJsonString(archive1);
        String json2=jsonManager.getJsonString(archive2);
        String json3=jsonManager.getJsonString(archive3);
        //String json4=jsonManager.getJsonString(userInfo);

        netManager.sendMessage(MessageCode.SendArchive,"wanght2024@mail.sustech.edu.cn"+"|"+json1+"|"+json2+"|"+json3);

    }

     */

    /**
     * 向本地存档（暂时弃用）
     */
    public void save()
    {
        jsonManager.saveJsonToFile(userInfoPath+"\\archive\\1.ar", archive1);
        jsonManager.saveJsonToFile(userInfoPath+"\\archive\\2.ar", archive2);
        jsonManager.saveJsonToFile(userInfoPath+"\\archive\\3.ar", archive3);
    }
    /*
    public void change(int index, LevelArchive archive)
    {
        //传递的是副本
        activeArchive.replace(index, new LevelArchive(archive));
    }

     */

    /**
     * 返回当前存档
     * @return 当前存档
     */
    public HashMap<Integer, LevelArchive> getActiveArchive()
    {
        return activeArchive;
    }
    public void DEFAULT()
    {

    }
}
