package com.klotski;

import com.badlogic.gdx.Game;
import com.klotski.Scene.*;
import com.klotski.assets.AssetsPathManager;
import com.klotski.assets.MusicAssets;
import com.klotski.map.MapDataManager;
import com.klotski.music.MusicManager;
import com.klotski.network.NetManager;
import com.klotski.user.UserInfo;
import com.klotski.user.UserManager;
import com.klotski.utils.json.JsonManager;
import com.klotski.utils.logger.Logger;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game
{
    private UserManager userManager;
    private NetManager netManager;
    private MusicManager musicManager;
    private ScreenManager screenManager;
    private JsonManager jsonManager;
    private MapDataManager mapDataManager;
    private AssetsPathManager assetsPathManager;
    private Thread netManagerThread;
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
        assetsPathManager=new AssetsPathManager(this);
        assetsPathManager.preloadAllAssets();
        assetsPathManager.startAssetsLoading();
        netManager=new NetManager("124.71.34.129",12345);
        netManagerThread = new Thread(netManager);
        netManagerThread.setDaemon(true);
        netManagerThread.start();
        jsonManager=new JsonManager();
        screenManager=new ScreenManager(this);
        userManager=new UserManager(this,netManager,screenManager);
        netManager.addObserver(userManager);
        mapDataManager=new MapDataManager(this);
        //加载所有MapData
        mapDataManager.load();
        musicManager=new MusicManager(this);
        musicManager.loadMusic(MusicManager.MusicAudio.GameMusic, MusicAssets.GameMusic);
        musicManager.loadMusic(MusicManager.MusicAudio.MainBGM, MusicAssets.MainBGM);
        screenManager.setScreen(new WelcomeScene(this));
    }

    @Override
    public void render()
    {
        //ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();

    }

    public AssetsPathManager getAssetsPathManager()
    {
        return assetsPathManager;
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


    public MapDataManager getMapDataManager()
    {
        return mapDataManager;
    }

    public NetManager getNetManager()
    {
        return netManager;
    }

    public MusicManager getMusicManager()
    {
        return musicManager;
    }
}
