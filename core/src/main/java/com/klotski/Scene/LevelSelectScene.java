package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klotski.Main;
import com.klotski.logic.LevelInfo;
import com.klotski.polygon.LevelGroup;

public class LevelSelectScene extends KlotskiScene
{
    private String title="";
    private LevelInfo li;
    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄Q
     */
    public LevelSelectScene(Main gameMain)
    {
        super(gameMain);

        stage=new Stage();


    }
    @Override
    public void init()
    {
        LevelGroup lg=new LevelGroup();
        li=new LevelInfo();
        li.setMapData(null);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        stage.addActor(lg);
    }
    @Override
    public void input()
    {

    }

    @Override
    public void draw(float delta)
    {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void logic(float delta)
    {

    }
}
