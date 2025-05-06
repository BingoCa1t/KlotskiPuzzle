package com.klotski;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klotski.Scene.GameMainScene;
import com.klotski.Scene.LoginScene;
import com.klotski.Scene.ScreenManager;
import com.klotski.archive.ArchiveManager;
import com.klotski.assets.AssetsPathManager;
import com.klotski.logic.LevelInfo;
import com.klotski.map.MapData;
import com.klotski.map.MapDataManager;
import com.klotski.network.NetManager;
import com.klotski.Scene.StartScene;
import com.klotski.user.UserInfo;
import com.klotski.user.UserManager;
import com.klotski.utils.json.JsonManager;
import com.klotski.utils.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game
{
    private UserManager userManager;
    private UserInfo userInfo;
    private NetManager netManager;

    private ScreenManager screenManager;
    private JsonManager jsonManager;
    private MapDataManager mapDataManager;
    private HashMap<Integer,LevelInfo> levelInfoMap;
    private SpriteBatch batch;
    private Texture image;
    private ArchiveManager archiveManager;
   // private Stage stage;
    private GameMainScene gms;
    private LoginScene loginScene;
    private HashMap<Integer, LevelInfo> levelInfos;
    /*
    先在主程序里加载所有MapData
    然后再初始化levelInfo
    MapData分三种：闯关，倒计时，自定义
    levelInfo用来存档
    levelInfo里的MapData仅用于选择关卡界面
    于是可以把SaveArchive写进levelInfo（？
    那levelInfo的初始化有两个部分
    MapData和LevelArchive
    在用户登录（or guest）之后初始化LevelInfo
     */
    @Override
    public void create()
    {
        netManager=new NetManager("127.0.0.1",12345);
        // 创建一个新线程并启动
        Thread netManagerThread = new Thread(netManager);

        netManagerThread.start();

        jsonManager=new JsonManager();
        Logger.debug("JSON manager");
        screenManager=new ScreenManager(this);
        Logger.debug("Screen manager");
        userManager=new UserManager(netManager,screenManager);
        Logger.debug("User manager");
        userInfo=userManager.DEFAULT();
        Logger.debug("User info");
        //userManager.updateUserInfo(userInfo);
        netManager.addObserver(userManager);
        Logger.debug("User info update");
        mapDataManager=new MapDataManager(this);
        Logger.debug("Map manager");
        //加载所有MapData
        mapDataManager.load();
        Logger.debug("Map manager loaded");
        archiveManager=new ArchiveManager(userInfo);
        Logger.debug("Archive manager");
        archiveManager.load();
        Logger.debug("Archive manager loaded");
        //加载所有LevelArchive
        //archiveManager=new ArchiveManager(userManager.loadUserInfo(0));
        //加载LevelInfo
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        loginScene=new LoginScene(this);
        //gms = new GameMainScene(this);
        //ObjectMapper mapper = new ObjectMapper();
        //String s=mapper.writeValueAsString(gms.getMapData())
        //gms.init();
       // MapData m=jsonManager.parseJsonToObject(jsonManager.getJsonString(gms.getMapData()),MapData.class);
        //jsonManager.saveJsonToFile("D:\\Map\\1.map",gms.getMapData());
        //loginScene.show();
        //screenManager.setScreen(new StartScene(this));
        screenManager.setScreen(loginScene);

    }

    @Override
    public void render()
    {
        //ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render();
    }







    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    public AssetsPathManager getAssetsPathManager()
    {
        return null;
    }

    public UserManager getUserManager()
    {
        return userManager;
    }

    public ScreenManager getScreenManager()
    {
        return screenManager;
    }

    public JsonManager getJsonManager()
    {
        return jsonManager;
    }

    public ArchiveManager getArchiveManager()
    {
        return archiveManager;
    }

    public MapDataManager getMapDataManager()
    {
        return mapDataManager;
    }

    public UserInfo getUserInfo()
    {
        return userInfo;
    }
}
