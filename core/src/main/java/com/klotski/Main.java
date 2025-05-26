package com.klotski;

import com.badlogic.gdx.Game;
import com.klotski.Scene.*;
import com.klotski.assets.AssetsPathManager;
import com.klotski.assets.MusicAssets;
import com.klotski.map.MapDataManager;
import com.klotski.music.MusicManager;
import com.klotski.network.NetManager;
import com.klotski.network.NetworkMessageObserver;
import com.klotski.settings.SettingManager;
import com.klotski.user.UserInfo;
import com.klotski.user.UserManager;
import com.klotski.utils.json.JsonManager;
import com.klotski.utils.logger.Logger;
import com.klotski.utils.reedSolomon.RSDecoder;
import com.klotski.utils.reedSolomon.RSEncoder;

import java.io.IOException;
import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game
{
    private UserManager userManager;
    private NetManager netManager;
    private MusicManager musicManager;
    private ScreenManager screenManager;
    private MapDataManager mapDataManager;
    private AssetsPathManager assetsPathManager;
    private SettingManager settingManager;
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

    public Main(SettingManager settingManager)
    {
        super();
        this.settingManager = settingManager;
    }

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
        //screenManager.setScreen(new SettingScene(this));

            String s=RSEncoder.encoder("test123456TEST123456");
            System.out.println(s);
            System.out.println(RSDecoder.decoder(s));


    }

    @Override
    public void render()
    {
        super.render();
    }

    @Override
    public void dispose()
    {
        super.dispose();
        netManagerThread.interrupt();
        netManager.close();
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

    public SettingManager getSettingManager()
    {
        return settingManager;
    }

    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
    }
    public void restartNetManager()
    {


        NetManager newNet=new NetManager("124.71.34.129",12345);
        for(NetworkMessageObserver nmo : netManager.getObservers())
        {
            newNet.addObserver(nmo);
        }
        netManager.stopThread();
        netManagerThread.interrupt();
        netManager.close();
        netManager=newNet;
        netManagerThread=new Thread(netManager);
        netManagerThread.setDaemon(true);
        netManagerThread.start();
    }

}
