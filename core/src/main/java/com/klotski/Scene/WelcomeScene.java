package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.klotski.Main;
import com.badlogic.gdx.video.VideoPlayer;
import com.klotski.utils.logger.Logger;

import java.io.FileNotFoundException;

/**
 * 欢迎界面，登录、注册or游客模式
 */
public class WelcomeScene extends KlotskiScene
{
    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄Q
     */
    VideoPlayer player;
    public WelcomeScene(Main gameMain)
    {
        super(gameMain);

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
}
