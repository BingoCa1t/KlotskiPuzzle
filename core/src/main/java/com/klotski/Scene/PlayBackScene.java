package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klotski.Main;
import com.klotski.archive.LevelArchive;
import com.klotski.assets.ImageAssets;
import com.klotski.logic.ChessBoardControl;
import com.klotski.logic.MoveStep;
import com.klotski.map.MapData;
import com.klotski.utils.ScheduledTask;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class PlayBackScene extends KlotskiScene
{
    /** 按钮组 */
    private Group group;
    private ImageButton backButton;
    private ImageButton backKButton;
    private ImageButton nextButton;
    private ImageButton nextTButton;
    private ImageButton playButton;
    private Drawable playDrawable;
    private Drawable pauseDrawable;
    /** 游戏存档 */
    private LevelArchive levelArchive;
    /** 棋盘控制器 */
    private ChessBoardControl cbc;
    /** 地图数据 */
    private MapData mapData;
    /** 关卡mapID */
    private int mapID;
    /** 弹出的MoveStep */
    private Stack<MoveStep> backSteps=new Stack<>();
    /** 自动播放定时任务 */
    ScheduledTask autoPlay=new ScheduledTask(200,1300, TimeUnit.MILLISECONDS);
    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄
     */
    public PlayBackScene(Main gameMain, LevelArchive levelArchive)
    {
        super(gameMain);
        this.levelArchive = levelArchive;
        this.mapID = levelArchive.getMapID();
    }
    @Override
    public void init()
    {
        super.init();
        // 棋步记录表
        Table dataTable = new Table();
        group = new Group();
        //初始化棋盘控制器ChessBoardControl
        cbc = new ChessBoardControl(gameMain, dataTable);
        //从地图管理器获取地图
        mapData = gameMain.getMapDataManager().getMapDataList().get(mapID);
        //载入存档
        cbc.loadPlayback(mapData, levelArchive);
        //将棋盘恢复到初始状态，同时存储步数
        while(cbc.getSteps()>0)
        {
            backSteps.push(cbc.moveBack());
            cbc.getChessBoard().getChildren().forEach(Actor::clearActions);
        }
        cbc.getChessBoard().setPosition(100,100);
        //自动播放任务
        autoPlay.setTask(()->
            {
                Gdx.app.postRunnable(() ->
                {
                    if(!backSteps.isEmpty())
                    {
                        cbc.move(backSteps.pop());
                    }
                    else {
                        isPlaying=false;
                    }
                });
            }
        );
        autoPlay.start();
        autoPlay.pause();
        /*
        播放：开始定时任务，点击后图标切换为”暂停“，同时禁用其他按钮
        暂停：暂停定时任务，点击后图标切换为“播放”，同时启用其他按钮
        向左：后退一步
        向右：前进一步
        快进：到达终局
        快退：到达开始处
         */
        backButton=new ImageButton(new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.pbBackButton)));
        backKButton=new ImageButton(new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.pbBackKButton)));
        nextButton=new ImageButton(new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.pbNextButton)));
        nextTButton=new ImageButton(new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.pbNextTButton)));
        playDrawable=new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.pbPlayButton));
        ImageButton.ImageButtonStyle playStyle=new ImageButton.ImageButtonStyle();
        playStyle.imageDown = playDrawable;
        playStyle.imageChecked = playDrawable;
        playButton=new ImageButton(new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.pbPlayButton)));
        pauseDrawable=new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.pbPauseButton));
        group.addActor(backButton);
        group.addActor(backKButton);
        group.addActor(nextButton);
        group.addActor(nextTButton);
        group.addActor(playButton);
        backButton.setSize(100,100);
        backKButton.setSize(100,100);
        nextButton.setSize(100,100);
        nextTButton.setSize(100,100);
        playButton.setSize(100,100);
        backButton.setPosition(0,100);
        backKButton.setPosition(200,100);
        playButton.setPosition(400,100);
        nextButton.setPosition(600,100);
        nextTButton.setPosition(800,100);
        group.setPosition(800,0);

        backButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent e, float x, float y)
            {
                if(cbc.getSteps()>0) backSteps.push(cbc.moveBack());

            }
        });
        backKButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent e, float x, float y)
            {
                while(cbc.getSteps()>0)
                {
                    backSteps.push(cbc.moveBack());
                }
            }
        });
        nextButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent e, float x, float y)
            {
                if(!backSteps.isEmpty())cbc.move(backSteps.pop());

            }
        });
        nextTButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent e, float x, float y)
            {
                while(!backSteps.isEmpty())
                {
                    cbc.move(backSteps.pop());
                }
            }
        });
        playButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent e, float x, float y)
            {
                if(!isPlaying)
                {
                   if(!backSteps.isEmpty())
                   {
                        autoPlay.resume();
                   }
                }
                else
                {
                    autoPlay.pause();
                }
                isPlaying =!isPlaying;
            }
        });
        stage.addActor(group);
        stage.addActor(cbc.getChessBoard());
    }
    private boolean isPlaying=false;
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




}
