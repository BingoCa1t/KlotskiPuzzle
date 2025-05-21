package com.klotski.Scene;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.klotski.Main;
import com.klotski.archive.LevelArchive;
import com.klotski.logic.ChessBoardControl;
import com.klotski.logic.MoveStep;
import com.klotski.map.MapData;
import com.klotski.utils.ScheduledTask;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class PlayBackScene extends KlotskiScene
{
    private LevelArchive levelArchive;
    /** 棋盘控制器 */
    private ChessBoardControl cbc;
    /** 地图数据 */
    private MapData mapData;
    /** 关卡mapID */
    private int mapID;
    /** 弹出的MoveStep */
    private Stack<MoveStep> backSteps=new Stack<>();
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
        // 棋步记录表
        Table dataTable = new Table();
        //初始化棋盘控制器ChessBoardControl
        cbc = new ChessBoardControl(gameMain, dataTable);
        //从地图管理器获取地图
        mapData = gameMain.getMapDataManager().getMapDataList().get(mapID);
        //载入存档
        cbc.load(mapData, levelArchive, true);
        //将棋盘恢复到初始状态，同时存储步数
        while(cbc.getSteps()>0)
        {
            backSteps.push(cbc.moveBack());
        }
        cbc.getChessBoard().setPosition(100,100);

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
    ScheduledTask s=new ScheduledTask(0,1, TimeUnit.SECONDS);
    public void autoPlay(int deltaTime)
    {

        s.setTask(()->{
            if(backSteps.size()>0)
            {
                cbc.move(backSteps.pop());
            }
        });
    }
}
