package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
import com.klotski.polygon.TimerW;
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
    private SpriteBatch batch;
   // private Stage stage;
    private ChessBoardControl cbc;
    private boolean isInBackMenu;
    private LocalDateTime startTime;
    private MapData mapData;
    private boolean isInAI = false;
    //private Main game;
    private Label timeLabel;
    private Label stepLabel;
    private BitmapFont font;
    private LevelInfo levelInfo;
    private Image background;
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
    private int seconds;
    private TimerW tw;
    @Override
    public void init()
    {
        super.init();
        background = new Image(new Texture("selectLevelBackground.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);
        cbc = new ChessBoardControl();
        mapData = gameMain.getMapDataManager().getMapDataList1().get(levelInfo.getMapID());
        cbc.load(mapData);
        stage.addActor(cbc.getChessBoard());
        cbc.getChessBoard().addListener(new MyInputListener());
        font=new BitmapFont(Gdx.files.internal("huawenzhongsong.fnt"));
        Label.LabelStyle ls=new Label.LabelStyle();
        ls.font = font;
        ls.fontColor = Color.WHITE;
        timeLabel=new Label("00:00", ls);
        timeLabel.setPosition(850,800);
        timeLabel.setFontScale(0.7f);
        stepLabel=new Label("00", ls);
        stepLabel.setPosition(1200,800);
        stepLabel.setFontScale(0.7f);
        startTime = LocalDateTime.now();
        //stage.addActor(timeLabel);
        stage.addActor(stepLabel);
        stage.addListener(new ChessBoardListener());
        tw=new TimerW();
        tw.setPosition(850,800);
        stage.addActor(tw);
        Timer.schedule(new Timer.Task()
        {
            @Override
            public void run()
            {
                tw.addSecond();
            }
        },0f,1f);
        Button.ButtonStyle rbs = new Button.ButtonStyle();
        rbs.up=new TextureRegionDrawable(new TextureRegion(new Texture("restart.png")));
        rbs.down=new TextureRegionDrawable(new TextureRegion(new Texture("restart.png")));
        Button.ButtonStyle ubs = new Button.ButtonStyle();
        ubs.up=new TextureRegionDrawable(new TextureRegion(new Texture("undo.png")));
        Button restartButton =new Button(rbs);
        Button undoButton =new Button(ubs);
        restartButton.setPosition(850,400);
        restartButton.setSize(200,100);
        restartButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                cbc.restart();
                stepLabel.setText("00");
            }
        });
        undoButton.setPosition(1200,400);
        undoButton.setSize(200,100);

        stage.addActor(restartButton);
        stage.addActor(undoButton);
        undoButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                cbc.moveBack();
                stepLabel.setText(cbc.getSteps());
            }
        });
    }

    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄Q
     */
    public GameMainScene(Main gameMain,LevelInfo levelInfo)
    {

        super(gameMain);
        this.levelInfo = levelInfo;
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
}
