package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.klotski.Main;
import com.klotski.logic.ChessBoardControl;
import com.klotski.logic.LevelInfo;
import com.klotski.logic.Pos;
import com.klotski.map.MapData;
import com.klotski.polygon.Chess;
import com.klotski.polygon.SettleGroup;
import com.klotski.polygon.StarProgress;
import com.klotski.polygon.TimerW;
import com.klotski.utils.SmartBitmapFont;
import com.klotski.utils.logger.Logger;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 游戏核心界面
 *
 * @author BingoCAT
 */
public class GameMainScene extends KlotskiScene
{
    private int mapID;
    private ShapeRenderer shapeRenderer;
    private StarProgress starProgress;
    // 长方形的宽度和高度变量
    private float rectangleWidth = 200;
    private float rectangleHeight = 100;
    // 圆角的半径
    private float cornerRadius = 20;
    private SpriteBatch batch;
   // private Stage stage;
    private ChessBoardControl cbc;
    private boolean isInBackMenu;
    private LocalDateTime startTime;
    private MapData mapData;
    private boolean isInAI = false;
    //private Main game;
    //private Label timeLabel;
    private Label stepLabel;
    private BitmapFont font;
    //private LevelInfo levelInfo;
    private Image background;
    private Label titleLabel;
    /**
     * 测试时候的默认MapData
     *
     * @return 返回的默认MapData
     */
    private MapData Default()
    {
        MapData mapData = new MapData();

        Chess c1, c2, c3, c4, c5, c6, c7, c8, c9, c10;
        Sprite t1, t2, t3, t4;
        t1 = new Sprite(new Texture("Caoc.png"));
        t2 = new Sprite(new Texture("Caoc.png"));
        t3 = new Sprite(new Texture("Caoc.png"));
        t4 = new Sprite(new Texture("Caoc.png"));
        t1.setOrigin(0, 0);
        //t1.setSize(160f,160f);

        ArrayList<Chess> chesses = new ArrayList<>();
        c1 = new Chess("Caoc.png", "曹操", 2, 2);
        c1.setXY(new Pos(1, 2));
        c2 = new Chess("Caoc.png", "关羽", 2, 1);
        c2.setXY(new Pos(1, 4));
        c3 = new Chess("Caoc.png", "张飞", 1, 2);
        c3.setXY(new Pos(3, 0));
        c4 = new Chess("Caoc.png", "赵云", 1, 2);
        c4.setXY(new Pos(0, 2));
        c5 = new Chess("Caoc.png", "黄忠", 1, 2);
        c5.setXY(new Pos(3, 2));
        c6 = new Chess("Caoc.png", "马超", 1, 2);
        c6.setXY(new Pos(0, 0));
        c7 = new Chess("Caoc.png", "卒", 1, 1);
        c7.setXY(new Pos(0, 4));
        c8 = new Chess("Caoc.png", "卒", 1, 1);
        c8.setXY(new Pos(1, 1));
        c9 = new Chess("Caoc.png", "卒", 1, 1);
        c9.setXY(new Pos(3, 4));
        c10 = new Chess("Caoc.png", "卒", 1, 1);
        c10.setXY(new Pos(2, 1));

        chesses.add(c1);
        chesses.add(c2);
        chesses.add(c3);
        chesses.add(c4);
        chesses.add(c5);
        chesses.add(c6);
        chesses.add(c7);
        chesses.add(c8);
        chesses.add(c9);
        chesses.add(c10);
        mapData.setChesses(chesses);
        mapData.setWidth(4);
        mapData.setHeight(5);
        mapData.setGrades(new int[]{5, 10, 15});
        mapData.setExit(new ArrayList<>()
        {{
            add(new Pos(1, 0));
            add(new Pos(2, 0));
        }});
        mapData.setMapName("Default");
        return mapData;
    }
    private int second=0;
    private TimerW tw;
    @Override
    public void init()
    {
        super.init();

        //棋盘控制器ChessBoardControl
        cbc = new ChessBoardControl(gameMain.getUserManager().getArchiveManager());
        mapData = gameMain.getMapDataManager().getMapDataList().get(mapID);
        cbc.load(mapData);
        cbc.getChessBoard().setPosition(100,50);
        cbc.getChessBoard().addListener(new MyInputListener());

        //星星进度条
        starProgress = new StarProgress(mapData.getGrades()[0], mapData.getGrades()[1], mapData.getGrades()[2]);
        starProgress.setPosition(830,650);

        //背景
        background = new Image(new Texture("selectLevelBackground.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        //标题Label
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("furore.ttf")),80);
        labelStyle.fontColor = Color.WHITE;
        titleLabel=new Label(mapData.getMapName(),labelStyle);
        titleLabel.setPosition(700,970);

        font=new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")),75);
        Label.LabelStyle ls=new Label.LabelStyle();
        ls.font = font;
        ls.fontColor = Color.WHITE;
        stepLabel=new Label("00", ls);
        stepLabel.setPosition(1200,800);
        //startTime = LocalDateTime.now();
        //stage.addActor(timeLabel);

        stage.addListener(new ChessBoardListener());
        //计时器
        tw=new TimerW();
        tw.setPosition(850,750);

        Timer.schedule(new Timer.Task()
        {
            @Override
            public void run()
            {

                cbc.addSecond();
                tw.setTime(cbc.getSecond());
            }
        },1f,1f);

        //重置按钮 Restart Button
        Button.ButtonStyle rbs = new Button.ButtonStyle();
        rbs.up=new TextureRegionDrawable(new TextureRegion(new Texture("restart.png")));
        rbs.down=new TextureRegionDrawable(new TextureRegion(new Texture("restart.png")));
        Button restartButton =new Button(rbs);
        restartButton.setPosition(830,500);
        restartButton.setSize(200,100);
        restartButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                cbc.restart();
                stepLabel.setText("00");
                tw.reset();

                starProgress.setStep(cbc.getSteps());
            }
        });

        //撤销按钮 Undo Button
        Button.ButtonStyle ubs = new Button.ButtonStyle();
        ubs.up=new TextureRegionDrawable(new TextureRegion(new Texture("undo.png")));
        Button undoButton =new Button(ubs);
        undoButton.setPosition(1100,500);
        undoButton.setSize(200,100);
        undoButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                cbc.moveBack();
                starProgress.setStep(cbc.getSteps());
                stepLabel.setText(String.format("%02d",cbc.getSteps()));
            }
        });

        //提示按钮 Hint Button
        Button.ButtonStyle hbs = new Button.ButtonStyle();
        hbs.up=new TextureRegionDrawable(new TextureRegion(new Texture("hint.png")));
        Button hintButton =new Button(hbs);
        hintButton.setPosition(830,350);
        hintButton.setSize(200,100);
        hintButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                //还没搞
            }
        });

        //上移按钮 Up Button
        Button.ButtonStyle upbs = new Button.ButtonStyle();
        upbs.up=new TextureRegionDrawable(new TextureRegion(new Texture("gameMainButton\\upButton.png")));
        Button upButton =new Button(upbs);
        upButton.setPosition(1000,200);
        upButton.setSize(120,120);
        upButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                move(new Pos(0,1));
            }
        });

        //下移按钮 Down Button
        Button.ButtonStyle downbs = new Button.ButtonStyle();
        downbs.up=new TextureRegionDrawable(new TextureRegion(new Texture("gameMainButton\\downButton.png")));
        Button downButton =new Button(downbs);
        downButton.setPosition(1000,60);
        downButton.setSize(120,120);
        downButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                move(new Pos(0,-1));
            }
        });

        //左移按钮 Left Button
        Button.ButtonStyle leftbs = new Button.ButtonStyle();
        leftbs.up=new TextureRegionDrawable(new TextureRegion(new Texture("gameMainButton\\leftButton.png")));
        Button leftButton =new Button(leftbs);
        leftButton.setPosition(860,60);
        leftButton.setSize(120,120);
        leftButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                move(new Pos(-1,0));
            }
        });

        //右移按钮 Right Button
        Button.ButtonStyle rightbs = new Button.ButtonStyle();
        rightbs.up=new TextureRegionDrawable(new TextureRegion(new Texture("gameMainButton\\rightButton.png")));
        Button rightButton =new Button(rightbs);
        rightButton.setPosition(1140,60);
        rightButton.setSize(120,120);
        rightButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                move(new Pos(1,0));
            }
        });

        //返回按钮 Back Button
        Button.ButtonStyle backbs = new Button.ButtonStyle();
        backbs.up=new TextureRegionDrawable(new TextureRegion(new Texture("gameMainButton\\backButton.png")));
        Button backButton =new Button(backbs);
        backButton.setPosition(50,950);
        backButton.setSize(100,100);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                gameMain.getScreenManager().returnPreviousScreen();
            }
        });


        //stage.addActor(background);
        stage.addActor(tw);
        stage.addActor(starProgress);
        stage.addActor(titleLabel);
        stage.addActor(cbc.getChessBoard());
        stage.addActor(stepLabel);
        stage.addActor(restartButton);
        stage.addActor(undoButton);
        stage.addActor(hintButton);
        stage.addActor(upButton);
        stage.addActor(downButton);
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(backButton);
        //
    }

    /**
     * 提供给四个按钮的方法，向指定方向移动
     * @param pos 单位方向的向量坐标
     */
    public void move(Pos pos)
    {
        if(cbc.getSelectingChess()!=null)
        {
            Pos p=cbc.getSelectingChess().getPosition().add(pos);
            if(p.getX()<0||p.getY()<0||p.getX()>cbc.getBoardWidth()-1||p.getY()>cbc.getBoardHeight()-1)
            {
                return;
            }
            cbc.move(cbc.getSelectingChess(), cbc.getSelectingChess().getPosition().add(pos));
            starProgress.setStep(cbc.getSteps());
            stepLabel.setText(String.format("%02d", cbc.getSteps()));
        }
    }
    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄Q
     */
    public GameMainScene(Main gameMain,int mapID)
    {

        super(gameMain);
        this.mapID=mapID;
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
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render(delta);
    }


    public void setMapData(MapData mapData)
    {
        this.mapData = mapData;
    }

    public MapData getMapData()
    {
        return mapData;
    }



    private class MyInputListener extends InputListener
    {
        private boolean isDragging = false;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
        {
            int xx = (int) (x / 160f);
            int yy = (int) (y / 160f);
            Chess c = cbc.getChess(new Pos(xx, yy));
            if (c == null)
            {
                Logger.debug("Selected chess is null: " + xx + " " + yy);
                if (cbc.getSelectingChess() != null)
                {
                    Pos pp = new Pos(xx, yy);
                    if (cbc.getSelectingChess().getChessWidth() > 1 || cbc.getSelectingChess().getChessHeight() > 1)
                    {
                        pp.setX((pp.getX() - cbc.getSelectingChess().getPosition().getX() > 0) ? pp.getX() - cbc.getSelectingChess().getChessWidth() + 1 : pp.getX());
                        pp.setY((pp.getY() - cbc.getSelectingChess().getPosition().getY() > 0) ? pp.getY() - cbc.getSelectingChess().getChessHeight() + 1 : pp.getY());
                    }
                    cbc.move(cbc.getSelectingChess(), pp);
                    stepLabel.setText(String.format("%02d", cbc.getSteps()));
                    starProgress.setStep(cbc.getSteps());
                }
                return true;
            }
            if (c == cbc.getSelectingChess())
            {
                c.disSelect();
            }
            cbc.select(c);
            Logger.debug("Selected chess: " + c.getPosition().getX() + " " + c.getPosition().getY());
            //c.setSelected(true);
            return true;
        }

        @Override
        public boolean mouseMoved(InputEvent event, float x, float y)
        {
            //cbc.mouseMoved(new Pos((int)(x/160f),(int)(y/160f)));
            return true;
        }

        /*
        @Override
        public void touchDragged(InputEvent event,float x,float y,int pointer)
        {
            isDragging = true;
            Logger.debug("Touch Dragged: "+x+" "+y);
            int xx=(int)(x/160f);
            int yy=(int)(y/160f);
            cbc.dragged(new Pos(xx,yy));
        }
         */
        /**
         * 当有键盘按键被按下时调用, 参数 keycode 是被按下的按键的键值,
         * 所有键盘按键的键值常量定义在 com.badlogic.gdx.Input.Keys 类中
         */
    }

    private class ChessBoardListener extends InputListener
    {
        @Override
        public boolean keyDown(InputEvent event, int keycode)
        {
            // Logger.debug("keyDown: "+keycode);
            switch (keycode)
            {
                case Input.Keys.UP:
                {

                    //Gdx.app.log(TAG, "被按下的按键: 方向上键");
                    // cb2.move(cb2.getChess10(),new Pos(2,0));
                    // System.out.println("移动棋子");
                    break;
                }
                case Input.Keys.DOWN:
                {
                    Logger.debug("move back");
                    cbc.moveBack();
                    stepLabel.setText(String.format("%02d", cbc.getSteps()));
                    break;
                }
                case Input.Keys.LEFT:
                {

                    break;
                }
                default:
                {
                    //Gdx.app.log(TAG, "其他按键, KeyCode: " + keycode);
                    break;
                }
            }
            return false;
        }
    }
    public void refreshWidget()
    {
        starProgress.setStep(cbc.getSteps());
        stepLabel.setText(String.format("%02d", cbc.getSteps()));
        tw.setTime(cbc.getSecond());
    }
}
