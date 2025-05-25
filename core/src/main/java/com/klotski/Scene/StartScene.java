package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.klotski.Main;
import com.klotski.archive.ArchiveManager;
import com.klotski.archive.LevelArchive;
import com.klotski.assets.ImageAssets;
import com.klotski.music.MusicManager;
import com.klotski.network.MessageCode;
import com.klotski.network.NetworkMessageObserver;
import com.klotski.polygon.UserInfoGroup;
import com.klotski.user.UserInfo;
import com.klotski.utils.json.JsonManager;
import com.klotski.utils.logger.Logger;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 获取到UserInfo的第一个Scene
 * 由UserManager初始化
 * 此为登录之后的Root Scene，将ArchiveManager在这里初始化
 * 游戏开始界面，选择模式、设置等
 *
 * @author BingoCAT
 */
public class StartScene extends KlotskiScene implements NetworkMessageObserver
{
    //决定把ArchiveManager交由此处管理，不放在Main里了
    //不过其初始化仍然由UserManager负责（毕竟由他登录）
    //由UserManager负责？

    //还是由UserManager负责比较豪
    private ArchiveManager archiveManager;
    private UserInfo userInfo;
    private Button tempentry;
    private ScreenManager screenManager;
    // 按钮 弹起 状态的纹理
    private Texture upTexture;
    private Group userGroup;
    // 按钮 按下 状态的纹理
    private Texture downTexture;

    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄
     */
    public StartScene(Main gameMain, ArchiveManager archiveManager)
    {
        super(gameMain);
        screenManager = gameMain.getScreenManager();
        userInfo = gameMain.getUserManager().getActiveUser();
        this.archiveManager = archiveManager;
        userGroup = new Group();
    }
    @Override
    public void show()
    {
        super.show();
    }
    @Override
    public void init()
    {
        super.init();
        gameMain.getMusicManager().play(MusicManager.MusicAudio.MainBGM,true);
        gameMain.getNetManager().addObserver(this);
        //背景图片 Background Image
        Image background = new Image(gameMain.getAssetsPathManager().get(ImageAssets.StartSceneBackground));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setPosition(0, 0);

        //用户信息组 User Information Group
        UserInfoGroup userInfoGroup = new UserInfoGroup(userInfo);
        userInfoGroup.setPosition(20,980);

        //曹操Spine Object组

        //玩法组

        Button.ButtonStyle sStyle = new Button.ButtonStyle();
        sStyle.up = new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.StartButton));
        Button startButton = new Button(sStyle);
        startButton.setPosition(300, 50);
        startButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                ArrayList<Integer> tem = new ArrayList<>();
                tem.add(1);
                tem.add(2);
                tem.add(3);
                tem.add(4);
                tem.add(5);
                /*
                tem.add(2);
                tem.add(4);
                tem.add(3);
                tem.add(5);
                tem.add(2);
                 */
                screenManager.setScreen(new LevelSelectScene(gameMain, tem));
            }
        });


        Button.ButtonStyle ssStyle = new Button.ButtonStyle();
        ssStyle.up = new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.StartButton));
        Button sstartButton = new Button(sStyle);
        sstartButton.setPosition(650, 50);
        sstartButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                ArrayList<Integer> tem = new ArrayList<>();
                tem.add(21);
                tem.add(22);
                tem.add(23);
                tem.add(24);
                tem.add(25);
                screenManager.setScreen(new LevelSelectScene(gameMain, tem));
            }
        });

        Button.ButtonStyle sssStyle = new Button.ButtonStyle();
        sssStyle.up = new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.StartButton));
        Button ssstartButton = new Button(sStyle);
        ssstartButton.setPosition(1000, 50);
        ssstartButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                ArrayList<Integer> tem = new ArrayList<>();
                tem.add(41);
                screenManager.setScreen(new LevelSelectScene(gameMain, tem));
            }
        });


        Button.ButtonStyle watchStyle = new Button.ButtonStyle();
        watchStyle.up = new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.WatchButton));
        Button watchButton = new Button(watchStyle);
        watchButton.setPosition(1350, 50);
        watchButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                gameMain.getScreenManager().setScreen(new WatchScene(gameMain));
            }
        });
        //用户头像组

        stage.addActor(background);
        stage.addActor(userInfoGroup);
        stage.addActor(startButton);
        stage.addActor(watchButton);
        stage.addActor(sstartButton);
        stage.addActor(ssstartButton);
    }

    @Override
    public void input()
    {

    }

    @Override
    public void draw(float delta)
    {
        stage.draw();
    }

    @Override
    public void logic(float delta)
    {

    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render(delta);
    }
    //此为临时方法
    LevelArchive levelArchive;
    JsonManager j=new JsonManager();
    @Override
    public void update(MessageCode code, String message)
    {
        String[] str=message.split(Pattern.quote("|"));
        if(code==MessageCode.UpdateWatch)
        {
            levelArchive=j.parseJsonToObject(str[1],LevelArchive.class);
        }
    }
}
