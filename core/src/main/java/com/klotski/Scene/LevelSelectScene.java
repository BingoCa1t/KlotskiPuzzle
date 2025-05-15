package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klotski.Main;
import com.klotski.archive.ArchiveManager;
import com.klotski.logic.LevelInfo;
import com.klotski.polygon.LevelActor;
import com.klotski.polygon.LevelGroup;
import com.klotski.utils.logger.Logger;

import java.util.ArrayList;

public class LevelSelectScene extends KlotskiScene
{
    private String title="";
    private LevelGroup lg;
    private Image background;
    private Image selectLevelText;
    private ArchiveManager archiveManager;
    private ArrayList<Integer> mapIDs;
    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄Q
     */
    public LevelSelectScene(Main gameMain,ArrayList<Integer> mapIDs)
    {
        super(gameMain);
        this.mapIDs = mapIDs;
        stage=new Stage();
    }
    @Override
    public void init()
    {
        //背景图片 Background
        background=new Image(new TextureRegion(new Texture(Gdx.files.internal("selectLevelBackground.png"))));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //上方文字 "Select Level"
        selectLevelText=new Image(new TextureRegion(new Texture(Gdx.files.internal("selectLevelText.png"))));
        selectLevelText.setScale(0.6f);
        selectLevelText.setPosition(550,900);

        //关卡选择组
        lg = new LevelGroup(gameMain);
        for(int i : mapIDs)
        {
            lg.addLevel(i,true);
        }
        /*
        li=new LevelInfo();
        li.setMapID(0);
        lg.addLevel(li,true);
        LevelInfo li2=new LevelInfo();
        li2.setMapID(1);
        lg.addLevel(li2,true);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        lg.addLevel(li,true);
        lg.addLevel(li,false);
        lg.addLevel(li,false);
        lg.addLevel(li,false);
        lg.addLevel(li,false);
        lg.addLevel(li,false);
        lg.addLevel(li,false);
        lg.addLevel(li,false);
        lg.addLevel(li,false);

         */
        /*
        lg.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
               //Logger.debug("Clicked:"+event.getListenerActor());
                Actor actor = event.getListenerActor();
                if(actor instanceof LevelActor la)
                {
                    int levelID=la.getMapID();
                    gameMain.getScreenManager().setScreen(new GameMainScene(gameMain,la.getLevelInfo()));
                }

            }
        });
         */
        stage.addActor(background);
        stage.addActor(selectLevelText);
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
    public void nextLevel()
    {
        lg.nextLevel();
    }
    public void returnLevel()
    {
        lg.returnLevel();
    }

}
