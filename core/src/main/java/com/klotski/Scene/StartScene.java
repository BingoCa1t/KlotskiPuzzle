package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.klotski.Main;
import com.klotski.utils.logger.Logger;

/**
 * 游戏开始界面，选择模式、设置等
 */
public class StartScene extends KlotskiScene
{
    private Button tempentry;
    private ScreenManager screenManager;
    // 按钮 弹起 状态的纹理
    private Texture upTexture;

    // 按钮 按下 状态的纹理
    private Texture downTexture;
    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄Q
     */
    public StartScene(Main gameMain)
    {

        super(gameMain);
        screenManager =gameMain.getScreenManager();
    }
    @Override
    public void init()
    {
        super.init();

        upTexture = new Texture(Gdx.files.internal("login.png"));
        downTexture = new Texture(Gdx.files.internal("login.png"));
        Button.ButtonStyle style = new Button.ButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域
        style.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(downTexture));

        tempentry=new Button(style);
        tempentry.setPosition(500,500);
        tempentry.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                screenManager.setScreen(new LevelSelectScene(gameMain));
            }
        });
        stage.addActor(tempentry);
    }
    @Override
    public void input()
    {

    }

    @Override
    public void draw(float delta)
    {

    }

    @Override
    public void logic(float delta)
    {

    }
    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,0,1);
        stage.act(delta);
        stage.draw();

    }
}
