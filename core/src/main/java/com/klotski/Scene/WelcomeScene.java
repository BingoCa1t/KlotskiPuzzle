package com.klotski.Scene;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.badlogic.gdx.video.scenes.scene2d.VideoActor;
import com.klotski.Main;
import com.badlogic.gdx.video.VideoPlayer;
import com.klotski.utils.ImageButtonStyleHelper;
import com.klotski.utils.logger.Logger;

import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * 欢迎界面，登录、注册or游客模式
 * 背景为三国演义相关部分视频
 * 先显示加载素材及进度条
 * 加载完成后，进度条消失并显示按钮
 * @author BingoCAT
 */
public class WelcomeScene extends KlotskiScene
{
    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄
     */
    VideoPlayer player;
    private ImageButton registerButton;
    private ImageButton loginButton;
    private ImageButton guestButton;
    private Image back;
    public WelcomeScene(Main gameMain)
    {
        super(gameMain);
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
        back=new Image(new Texture(Gdx.files.internal("welcomeScene/back.png")));
        back.setPosition(250,200);
        gameMain.getAssetsPathManager().startAssetsLoading();

        ImageButton.ImageButtonStyle rstyle=ImageButtonStyleHelper.createFromTexture(new Texture(Gdx.files.internal("welcomeScene/regist.png")));
        registerButton=new ImageButton(rstyle);
        registerButton.setPosition(300,200);
        registerButton.setSize(300,150);
        registerButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent e, float x, float y)
           {
               gameMain.getScreenManager().setScreen(new RegisterScene(gameMain));
           }
        });
        ImageButton.ImageButtonStyle lstyle=ImageButtonStyleHelper.createFromTexture(new Texture(Gdx.files.internal("welcomeScene/login.png")));
        loginButton=new ImageButton(lstyle);
        loginButton.setPosition(700,200);
        loginButton.setSize(300,150);
        loginButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y)
            {
                gameMain.getScreenManager().setScreen(new LoginScene(gameMain));
            }
        });
        ImageButton.ImageButtonStyle gstyle=ImageButtonStyleHelper.createFromTexture(new Texture(Gdx.files.internal("welcomeScene/guest.png")));
        guestButton=new ImageButton(gstyle);
        guestButton.setPosition(1100,200);
        guestButton.setSize(300,150);
        guestButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y)
            {
                //gameMain.getScreenManager().setScreen(new StartScene(gameMain));
            }
        });

        player = VideoPlayerCreator.createVideoPlayer();
        player.setLooping(true);
        try
        {
            player.load(Gdx.files.getFileHandle("D:\\FFOutput\\1.webm", Files.FileType.Absolute));
            player.setVolume(0);
            VideoActor v=new VideoActor(player);
            v.setSize(1920,1080);
            v.setPosition(0,0);

            stage.addActor(v);
            player.play();
        } catch (FileNotFoundException e)
        {
            Logger.error(e.getMessage());
        }
        stage.addActor(back);

        stage.addActor(registerButton);
        stage.addActor(loginButton);
        stage.addActor(guestButton);





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
        ScreenUtils.clear(0, 0, 0, 1);
        super.render(delta);
    }
    @Override
    public void dispose()
    {
        super.dispose();
        player.dispose();
    }
}
