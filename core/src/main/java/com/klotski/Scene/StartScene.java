package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    }

    @Override
    public void init()
    {
        gameMain.getNetManager().addObserver(this);
        super.init();
        //背景图片 Background Image
        Image background = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("startScene\\startBackGround.png")))));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setPosition(0, 0);

        //用户信息组 User Information Group
        UserInfoGroup userInfoGroup = new UserInfoGroup(userInfo);

        //曹操Spine Object组

        //玩法组

        Button.ButtonStyle sStyle = new Button.ButtonStyle();
        sStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("startScene\\startButton.png"))));
        Button startButton = new Button(sStyle);
        startButton.setPosition(1000, 50);
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
                screenManager.setScreen(new LevelSelectScene(gameMain, tem));
            }
        });
        Button.ButtonStyle watchStyle = new Button.ButtonStyle();
        watchStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("startScene\\watchButton.png"))));
        Button watchButton = new Button(watchStyle);
        watchButton.setPosition(1350, 50);
        watchButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                gameMain.getScreenManager().setScreen(new GameMainScene(gameMain,levelArchive,true,"wanght2024@mail.sustech.edu.cn"));
            }
        });
        //用户头像组

        upTexture = new Texture(Gdx.files.internal("login.png"));
        downTexture = new Texture(Gdx.files.internal("login.png"));
        Button.ButtonStyle style = new Button.ButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域
        style.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(downTexture));


        tempentry = new Button(style);
        tempentry.setPosition(500, 500);
        tempentry.addListener(new ClickListener()
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
                screenManager.setScreen(new LevelSelectScene(gameMain, tem));
            }
        });
        stage.addActor(background);
        stage.addActor(startButton);
        stage.addActor(watchButton);
        //stage.addActor(tempentry);

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
        //ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
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
