package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klotski.Main;
import com.klotski.archive.LevelArchive;
import com.klotski.assets.AssetsPathManager;
import com.klotski.assets.ImageAssets;
import com.klotski.logic.ChessBoardControl;
import com.klotski.logic.MoveStep;
import com.klotski.map.MapData;
import com.klotski.polygon.StarProgress;
import com.klotski.polygon.TimerW;
import com.klotski.utils.ScheduledTask;
import com.klotski.utils.SmartBitmapFont;

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
    /** 是否为障碍模式 */
    private boolean isObstacle=false;
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
        if(mapData.getMapType()==2) isObstacle=true;
        //载入存档
        cbc.loadPlayback(mapData, levelArchive);

        //进度条
        StarProgress starProgress = new StarProgress(mapData.getGrades()[0], mapData.getGrades()[1], mapData.getGrades()[2],gameMain.getAssetsPathManager());
        starProgress.setPosition(830, 650);
        starProgress.setStep(levelArchive.getMoveSteps().size());

        //步数信息
        BitmapFont font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")), 75);
        Label.LabelStyle ls = new Label.LabelStyle();
        ls.font = font;
        ls.fontColor = Color.WHITE;
        Label stepLabel = new Label(String.format("%02d",levelArchive.getMoveSteps().size()), ls);
        stepLabel.setPosition(1200, 800);
        if(isObstacle) stepLabel.setPosition(1200,600);
        //计时器
        TimerW tw = new TimerW();
        tw.setPosition(850, 750);
        tw.setTime(cbc.getSecond());
        if(isObstacle) tw.setPosition(1170,750);

        // 步数列表功能
        Label.LabelStyle ls2 = new Label.LabelStyle();
        ls2.font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")), 40);
        ls2.fontColor = Color.WHITE;
        // 创建主容器
        Table mainTable = new Table();
        mainTable.setFillParent(false);
        dataTable.align(Align.topLeft);
        dataTable.pad(10);
        // 创建表头表格
        Table headerTable = new Table();
        headerTable.align(Align.left);
        headerTable.pad(10);
        // 添加表头
        Label label1 = new Label("棋步记录", ls2);
        label1.setAlignment(Align.center);
        headerTable.add(label1).width(300).pad(5);
        headerTable.row();
        ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle();
        ScrollPane scrollPane = new ScrollPane(dataTable, scrollStyle);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFillParent(false); // 填充整个父容器
        mainTable.setPosition(1414, 47);
        mainTable.setSize(450, 860);
        // 将表头和滚动面板添加到主表格
        mainTable.add(headerTable).fillX().row();
        mainTable.add(scrollPane).expand().fill().row();

        Image background =new Image(gameMain.getAssetsPathManager().get(ImageAssets.GameMainBackground));
        background.setSize(1920,1080);
        //几部分组件的半透明灰色圆角矩形背景

        Image stepBackground =new Image(gameMain.getAssetsPathManager().get(ImageAssets.GameMainStepBackground));
        stepBackground.setPosition(826,635);
        if(isObstacle)
        {
            stepBackground=new Image(gameMain.getAssetsPathManager().get(ImageAssets.ObstacleStepBackground));
            stepBackground.setPosition(1150,614);
        }
        Image recordBackground =new Image(gameMain.getAssetsPathManager().get(ImageAssets.GameMainRecordBackground));
        recordBackground.setPosition(1414,47);
        Image buttonBackground =new Image(gameMain.getAssetsPathManager().get(ImageAssets.pbButtonBackground));
        buttonBackground.setPosition(1150,47);
        if(!isObstacle) buttonBackground.setVisible(false);
        //将棋盘恢复到初始状态，同时存储步数
        while(cbc.getSteps()>0)
        {
            backSteps.push(cbc.moveBack());
            cbc.getChessBoard().getChildren().forEach(Actor::clearActions);
        }
        cbc.getChessBoard().setPosition(100,50);
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
                        autoPlay.pause();
                        backKButton.setTouchable(Touchable.enabled);
                        nextTButton.setTouchable(Touchable.enabled);
                        nextButton.setTouchable(Touchable.enabled);
                        backButton.setTouchable(Touchable.enabled);
                        playButton.getStyle().imageChecked=new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.pbPlayButton));
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
        playStyle.imageUp = playDrawable;
        playButton=new ImageButton(new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.pbPlayButton)));
        pauseDrawable=new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.pbPauseButton));

        backButton.setSize(80,80);
        backKButton.setSize(80,80);
        nextButton.setSize(80,80);
        nextTButton.setSize(80,80);
        playButton.setSize(80,80);
        backKButton.setPosition(0,0);
        backButton.setPosition(70,0);
        playButton.setPosition(140,0);
        nextButton.setPosition(210,0);
        nextTButton.setPosition(280,0);
        group.setPosition(825,400);
        if(isObstacle)
        {
            group.setPosition(1200,150);
            backKButton.setPosition(0,0);
            backButton.setPosition(0,70);
            playButton.setPosition(0,140);
            nextButton.setPosition(0,210);
            nextTButton.setPosition(0,280);
            starProgress.setVisible(false);
            stepLabel.setPosition(1230,670);
            tw.setPosition(1170,750);
        }
        stage.addActor(background);
        stage.addActor(stepBackground);
        stage.addActor(recordBackground);
        stage.addActor(buttonBackground);
        stage.addActor(starProgress);
        stage.addActor(tw);
        stage.addActor(mainTable);
        stage.addActor(stepLabel);
        group.addActor(backButton);
        group.addActor(backKButton);
        group.addActor(nextButton);
        group.addActor(nextTButton);
        group.addActor(playButton);



        Label titleLabel;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")), 80);
        labelStyle.fontColor = Color.WHITE;
        titleLabel = new Label("回放-"+mapData.getMapName(), labelStyle);
        titleLabel.setPosition(640, 970);

        //返回按钮 Back Button
        Button.ButtonStyle backbs = new Button.ButtonStyle();
        backbs.up = new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.GameMainBackButton));
        Button backButtonn = new Button(backbs);
        backButtonn.setPosition(50, 950);
        backButtonn.setSize(100, 100);
        backButtonn.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                gameMain.getScreenManager().returnPreviousScreen();
            }
        });

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
                        playButton.getStyle().imageUp=new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.pbPauseButton));
                        nextButton.setTouchable(Touchable.disabled);
                        nextTButton.setTouchable(Touchable.disabled);
                        backButton.setTouchable(Touchable.disabled);
                        backKButton.setTouchable(Touchable.disabled);
                   }
                   else
                   {
                       playButton.getStyle().imageUp=new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.pbPlayButton));
                       nextButton.setTouchable(Touchable.enabled);
                       nextTButton.setTouchable(Touchable.enabled);
                       backButton.setTouchable(Touchable.enabled);
                       backKButton.setTouchable(Touchable.enabled);
                   }
                }
                else
                {
                    autoPlay.pause();
                    playButton.getStyle().imageUp=new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.pbPlayButton));
                    nextButton.setTouchable(Touchable.enabled);
                    nextTButton.setTouchable(Touchable.enabled);
                    backButton.setTouchable(Touchable.enabled);
                    backKButton.setTouchable(Touchable.enabled);
                }
                isPlaying =!isPlaying;
            }
        });
        stage.addActor(stepBackground);
        stage.addActor(recordBackground);
        stage.addActor(titleLabel);
        stage.addActor(group);
        stage.addActor(cbc.getChessBoard());
        stage.addActor(starProgress);
        stage.addActor(tw);
        stage.addActor(mainTable);
        stage.addActor(backButtonn);
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
