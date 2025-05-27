package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klotski.Main;
import com.klotski.archive.ArchiveManager;
import com.klotski.assets.ImageAssets;
import com.klotski.logic.LevelStatus;
import com.klotski.polygon.LevelGroup;
import com.klotski.utils.ImageButtonStyleHelper;
import com.klotski.utils.SmartBitmapFont;

import java.util.ArrayList;

public class LevelSelectScene extends KlotskiScene
{
    private String title="";
    private LevelGroup lg;
    private Image background;
    private Image selectLevelText;
    private ArchiveManager archiveManager;
    private ArrayList<Integer> mapIDs;
    private ImageButton backButton;
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
        this.archiveManager=gameMain.getUserManager().getArchiveManager();
    }
    Group g;
    Button yesButton;
    Button noButton;
    @Override
    public void init()
    {
        // 背景图片 Background
        background=new Image(new TextureRegion(gameMain.getAssetsPathManager().get(ImageAssets.LevelSelectBackground)));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // 上方文字 "Select Level"
        selectLevelText=new Image(new TextureRegion(gameMain.getAssetsPathManager().get(ImageAssets.SelectLevelText)));
        selectLevelText.setScale(0.6f);
        selectLevelText.setPosition(550,900);

        // 关卡选择组
        lg = new LevelGroup(gameMain,this);
        for(int i : mapIDs)
        {
            lg.addLevel(i,archiveManager.getActiveArchive().get(i).getLevelStatus()!=LevelStatus.Closed);
        }

        //返回按钮
        ImageButton.ImageButtonStyle style = ImageButtonStyleHelper.createStyleFromTexture(
            gameMain.getAssetsPathManager().get(ImageAssets.GameMainBackButton),
            0.9f,
            new Color(0.9f, 0.9f, 0.9f, 1f),
            new Color(0.5f, 0.5f, 0.5f, 0.7f)
        );
        backButton=new ImageButton(style);
        backButton.setPosition(100,950);
        backButton.setSize(100,100);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                gameMain.getScreenManager().returnPreviousScreen();
            }
        });

        g = new Group();
        Image dialog=new Image(gameMain.getAssetsPathManager().get(ImageAssets.IsLoadArchiveDialog));
        dialog.setSize(500,500);
        g.addActor(dialog);
        Button.ButtonStyle style2 = ImageButtonStyleHelper.createButtonStyle(gameMain.getAssetsPathManager().get(ImageAssets.YES));

        yesButton = new Button(style2);
        Button.ButtonStyle style3 = ImageButtonStyleHelper.createButtonStyle(gameMain.getAssetsPathManager().get(ImageAssets.NO));

        noButton = new Button(style3);
        yesButton.setPosition(100,95);
        noButton.setPosition(350,95);
        g.addActor(yesButton);
        g.addActor(noButton);
        g.setVisible(false);
        g.setPosition(700,200);
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
        stage.addActor(backButton);
        stage.addActor(g);

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
    @Override
    public void show()
    {
        super.show();
        stage=new Stage();
        int currentLevelID=lg.getCurrentLevel();
        init();
        Gdx.input.setInputProcessor(stage);
        lg.setCurrentLevel(currentLevelID);
    }
    public void showDialog(int mapID)
    {

        //yesButton.clearListeners();
        yesButton.setTouchable(Touchable.enabled);
        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                gameMain.getScreenManager().setScreen(new GameMainScene(gameMain, mapID,true));
                g.setVisible(false);
            }
        });
        //noButton.clearListeners();
        noButton.setTouchable(Touchable.enabled);
        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                gameMain.getScreenManager().setScreen(new GameMainScene(gameMain, mapID,false));
                g.setVisible(false);
            }
        });
        g.setVisible(true);
    }


}
