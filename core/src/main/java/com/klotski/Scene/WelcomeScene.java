package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.klotski.Main;
import com.badlogic.gdx.video.VideoPlayer;
import com.klotski.utils.ImageButtonStyleHelper;
import com.klotski.utils.logger.Logger;

import java.io.FileNotFoundException;

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
    public WelcomeScene(Main gameMain)
    {
        super(gameMain);
    }
    @Override
    public void init()
    {
        super.init();

        gameMain.getAssetsPathManager().startAssetsLoading();
        //临时用几个贴图代替一下
        ImageButton.ImageButtonStyle rstyle=ImageButtonStyleHelper.createFromTexture(new Texture(Gdx.files.internal("Caoc.png")));
        registerButton=new ImageButton(rstyle);
        registerButton.setPosition(200,200);
        registerButton.setSize(100,100);
        registerButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent e, float x, float y)
           {
               gameMain.getScreenManager().setScreen(new RegisterScene(gameMain));
           }
        });
        ImageButton.ImageButtonStyle lstyle=ImageButtonStyleHelper.createFromTexture(new Texture(Gdx.files.internal("Caoc.png")));
        loginButton=new ImageButton(lstyle);
        loginButton.setPosition(500,200);
        loginButton.setSize(100,100);
        loginButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y)
            {
                gameMain.getScreenManager().setScreen(new LoginScene(gameMain));
            }
        });
        ImageButton.ImageButtonStyle gstyle=ImageButtonStyleHelper.createFromTexture(new Texture(Gdx.files.internal("Caoc.png")));
        guestButton=new ImageButton(gstyle);
        guestButton.setPosition(800,200);
        guestButton.setSize(100,100);
        guestButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y)
            {
                //gameMain.getScreenManager().setScreen(new StartScene(gameMain));
            }
        });
        stage.addActor(registerButton);
        stage.addActor(loginButton);
        stage.addActor(guestButton);
        /*
        player = VideoPlayerCreator.createVideoPlayer();
        player.setLooping(true);
        try
        {
            player.load(Gdx.files.internal(""));
            player.play();
        } catch (FileNotFoundException e)
        {
            Logger.error(e.getMessage());
        }

         */

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
}
