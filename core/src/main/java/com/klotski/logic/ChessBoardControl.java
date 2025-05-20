package com.klotski.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.klotski.Main;
import com.klotski.Scene.GameMainScene;
import com.klotski.archive.ArchiveManager;
import com.klotski.archive.LevelArchive;
import com.klotski.map.MapData;
import com.klotski.network.MessageCode;
import com.klotski.polygon.Chess;
import com.klotski.polygon.ChessBoard;
import com.klotski.utils.json.JsonManager;
import com.klotski.utils.logger.Logger;

import java.util.*;

/**
 * 控制器，负责控制前后端
 *
 * @author BingoCAT
 */
public class ChessBoardControl
{
    private JsonManager jsonManager;
    private boolean isLoadArchive=true;
    private boolean isWatch=false;
    private Main gameMain;
    private ArchiveManager archiveManager;
    private LevelArchive levelArchive=new LevelArchive();
    private Stack<MoveStep> moveSteps = new Stack<>();
    private int second=0;
    private MapData mapData;
    /**前端*/
    private ChessBoard chessBoard;
    /**后端*/
    private ChessBoardArray chessBoardArray;
    //当前棋盘上选中的棋子
    private Chess selectingChess;
    private Chess mainChess;
    private ArrayList<Pos> exits;

    public ChessBoardControl(Main gameMain)
    {
        this.archiveManager = gameMain.getUserManager().getArchiveManager();
        this.gameMain = gameMain;
        this.jsonManager=new JsonManager();
    }
    //获取棋盘
    public ChessBoard getChessBoard()
    {
        return chessBoard;
    }

    //获取选中的棋子
    public Chess getSelectingChess()
    {
        return selectingChess;
    }

    /**
     * 测试时候使用的。默认构造棋盘（已弃用）
     */
/*
    private void loadDefault()
    {
        chessBoard = new ChessBoard();

        Chess c1, c2, c3, c4, c5, c6, c7, c8, c9, c10;
        Sprite t1, t2, t3, t4;
        t1 = new Sprite(new Texture("Caoc.png"));
        t2 = new Sprite(new Texture("Caoc.png"));
        t3 = new Sprite(new Texture("Caoc.png"));
        t4 = new Sprite(new Texture("Caoc.png"));
        t1.setOrigin(0, 0);
        //t1.setSize(160f,160f);
        Image background;

        background = new Image(new Sprite(new Texture("background.png")));
        background.setPosition(-10, -10);
        background.setHeight(5 * Chess.squareHW + 20);
        background.setWidth(4 * Chess.squareHW + 20);


        c1 = new Chess(t1, "曹操", 2, 2);
        c1.setXY(new Pos(1, 2));
        c2 = new Chess(t2, "关羽", 2, 1);
        c2.setXY(new Pos(1, 4));
        c3 = new Chess(t3, "张飞", 1, 2);
        c3.setXY(new Pos(3, 0));
        c4 = new Chess(t4, "赵云", 1, 2);
        c4.setXY(new Pos(0, 2));
        c5 = new Chess(t1, "黄忠", 1, 2);
        c5.setXY(new Pos(3, 2));
        c6 = new Chess(t2, "马超", 1, 2);
        c6.setXY(new Pos(0, 0));
        c7 = new Chess(t3, "卒", 1, 1);
        c7.setXY(new Pos(0, 4));
        c8 = new Chess(t3, "卒", 1, 1);
        c8.setXY(new Pos(1, 1));
        c9 = new Chess(t3, "卒", 1, 1);
        c9.setXY(new Pos(3, 4));
        c10 = new Chess(t3, "卒", 1, 1);
        c10.setXY(new Pos(2, 1));
        chessBoard = new ChessBoard();
        chessBoard.addActor(background);

        chessBoard.addChess(c1);
        chessBoard.addChess(c2);
        chessBoard.addChess(c3);
        chessBoard.addChess(c4);
        chessBoard.addChess(c5);
        chessBoard.addChess(c6);
        chessBoard.addChess(c7);
        chessBoard.addChess(c8);
        chessBoard.addChess(c9);
        chessBoard.addChess(c10);
        chessBoardArray = new ChessBoardArray(chessBoard.getChesses(), 4, 5,null,0);
        chessBoard.setPosition(100, 100);
    }
*/
    /**
     * 加载棋盘（更新了加载存档）
     * 此方法从存档管理器里加载存档
     * @param mapData 地图数据
     */
    public void load(MapData mapData)
    {
        load(mapData,archiveManager.getActiveArchive().get(mapData.getMapID()),false);
        //loadDefault();
    }
    public void load(MapData mapData,LevelArchive levelArchive,boolean isWatch)
    {
        this.isWatch=isWatch;
        //创建MapData的副本，不要更改MapDataManager里的数据
        this.mapData=new MapData(mapData);
        this.levelArchive=levelArchive;
        this.second=levelArchive.getSeconds();
        //后补（算了其实不需要）
        /*if(levelArchive==null)
        {
            levelArchive=new LevelArchive();
        }
         */


        //archiveManager.getActiveArchive().replace(mapData.getMapID(),levelArchive);
        chessBoard=new ChessBoard();
        Image background;
        Image chessBoardImage;
        chessBoardImage = new Image(new Sprite(new Texture("chessBoard.png")));
        chessBoardImage.setPosition(-16, -16);
        background = new Image(new Sprite(new Texture("background.png")));
        background.setPosition(-10, -10);
        background.setHeight(5 * Chess.squareHW + 20);
        background.setWidth(4 * Chess.squareHW + 20);
        chessBoard.addActor(background);
        chessBoard.addActor(chessBoardImage);
        for(Chess c:this.mapData.getChesses())
        {
            c.init(gameMain.getAssetsPathManager());
        }
        chessBoard.addChessArray(this.mapData.getChesses());
        chessBoard.setPosition(100, 100);
        exits=mapData.getExit();
        chessBoardArray = new ChessBoardArray(chessBoard.getChesses(), mapData.getWidth(), mapData.getHeight(),exits, mapData.getMainIndex());
        //如果存档是null或empty，则将存档的moveSteps设置为当前的moveSteps
        if(levelArchive.getMoveSteps()==null||levelArchive.getMoveSteps().isEmpty()||levelArchive.getLevelStatus()==LevelStatus.Succeed)
        {
            levelArchive.setMoveSteps(moveSteps);
        }
        //如果存在存档，且用户选择载入存档<br>（逻辑后补）</br>，则载入存档
        else if(this.isWatch||isLoadArchive)
        {
            //存档里的移动记录栈
            Stack<MoveStep> s = levelArchive.getMoveSteps();
            //栈后进先出，所以使用新栈，将顺序反转后再弹出
            Stack<MoveStep> s2 = new Stack<>();
            while (!s.isEmpty())
            {
                s2.push(s.pop()); // 将栈中的元素弹出并添加到新栈中
            }
            while (!s2.isEmpty())
            {
                MoveStep moveStep = s2.pop();
                //移动棋子
                moveInArchive(getChessByPosition(moveStep.origin),moveStep.destination);
                //不要重复添加
                //moveSteps.push(moveStep);
            }
            levelArchive.setMoveSteps(moveSteps);

        }
        gameMain.getNetManager().sendMessage(MessageCode.UpdateWatch,gameMain.getUserManager().getActiveUser().getEmail(),jsonManager.getJsonString(levelArchive),moveSteps.isEmpty()?"0":jsonManager.getJsonString(moveSteps.peek()));

    }
    /**
     * 移动棋子
     *
     * @param chess 棋子
     * @param p     目标坐标
     */
    public boolean move(Chess chess, Pos p)
    {
        Pos pp=new Pos(p.getX(),p.getY());
        if(chess==null)
        {
            return false;
        }
        if (chessBoardArray.isChessCanMove(chess, pp))
        {
            moveSteps.push(new MoveStep(chess.getPosition(), pp));
            levelArchive.setLevelStatus(LevelStatus.InProgress);
            Logger.debug(chess.toString() + " Move to" + pp.toString());
            levelArchive.setSeconds(second);
            if(!isWatch)
            {
                //存档
                archiveManager.saveByNetwork();
                //所有客户端一直向服务器发送LevelArchive和最近一次移动：0015|email|{LevelArchive}|{MoveStep}
                gameMain.getNetManager().sendMessage(MessageCode.UpdateWatch,gameMain.getUserManager().getActiveUser().getEmail(),jsonManager.getJsonString(levelArchive),jsonManager.getJsonString(moveSteps.peek()));
            }
            else
            {

            }
            chessBoard.move(chess, pp);

            if(isWin())
            {
                chess.clearActions();
                Logger.debug("Win");
                int steps = moveSteps.size();
                int star = 0;
                if (steps <= mapData.getGrades()[0]) star = 3;
                else if (steps <= mapData.getGrades()[1]) star = 2;
                else if (steps <= mapData.getGrades()[2]) star = 1;
                else star = 0;
                if(!isWatch)
                {
                    levelArchive.setLevelStatus(LevelStatus.Succeed);
                    levelArchive.setMoveSteps(levelArchive.getMoveSteps().size() < steps ? levelArchive.getMoveSteps() : moveSteps);
                    levelArchive.setStars(star);
                    levelArchive.setSeconds(second);
                    archiveManager.saveByNetwork();
                }

                if(gameMain.getScreenManager().getCurrentScreen() instanceof GameMainScene gms)
                {
                    MoveToAction action = Actions.moveTo((p.getX()) * Chess.squareHW, (p.getY()-2) * Chess.squareHW, 1.3f, Interpolation.smoother);
                    AlphaAction a= Actions.fadeOut(1.3f, Interpolation.smoother);
                    chess.addAction(Actions.parallel(action, a));
                    int finalStar = star;
                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run()
                        {
                            gms.settle(finalStar,second,moveSteps.size());
                        }
                    },1.3f);
                }
            }
            return true;
        }
        else
        {
            Logger.debug(chess.toString() + " Illegal Movement" + pp.toString());
            return false;
        }
    }
    public void moveInArchive(Chess chess, Pos p)
    {
        Pos pp=new Pos(p.getX(),p.getY());

        if (chessBoardArray.isChessCanMove(chess, pp))
        {
            moveSteps.push(new MoveStep(chess.getPosition(), pp));
            Logger.debug(chess.toString() + " Move to" + pp.toString());
            chessBoard.move(chess, pp);
        }
        else
        {
            Logger.debug(chess.toString() + " Illegal Movement" + pp.toString());
        }
    }

    /**
     * 返回步数
     * @return 返回步数记录栈的长度
     */
    public int getSteps()
    {
        return moveSteps.size();
    }

    /**
     * 回退，弹出移动记录栈最顶层的元素并反向移动
     */
    public void moveBack()
    {
        if (!moveSteps.isEmpty())
        {
            MoveStep ms = moveSteps.pop();
            move(getChessByPosition(ms.destination), ms.origin);
            moveSteps.pop();
        }
    }

    /**
     * 获取指定坐标的棋子（判断点击某个棋子用）
     *包括棋子覆盖的位置
     * @param p 给定坐标
     * @return 位置上的棋子
     */
    public Chess getChess(Pos p)
    {
        for (Actor c : chessBoard.getChesses())
        {
            if (c instanceof Chess cc)
            {
                if (cc.getPosition().getX() <= p.getX() && cc.getPosition().getX() + cc.getChessWidth() > p.getX() && cc.getPosition().getY() <= p.getY() && cc.getPosition().getY() + cc.getChessHeight() > p.getY())
                    return cc;
            }
        }
        return null;
    }

    /**
     * 选中棋子
     *
     * @param chess 被选中的棋子
     */
    public void select(Chess chess)
    {
        if (chess == null) return;
        chessBoard.select(chess);
        selectingChess = chess;
    }

    public void dragged(Pos p)
    {
        if (selectingChess == null) return;
        chessBoard.dragged(selectingChess, p);

    }

    public void mouseMoved(Pos p)
    {
        if (selectingChess == null) return;
        chessBoard.mouseMoved(selectingChess, p);
    }

    /**
     * 判断当前是否胜利
     * @return true为胜利
     */
    public boolean isWin()
    {
        return chessBoardArray.isWin();

    }

    /**
     * 重置棋局（将记录栈全部弹出）并重置时间
     */
    public void restart()
    {
        while(!moveSteps.isEmpty())
        {
            moveBack();
        }
        setSecond(0);
    }
    public int getBoardWidth()
    {
        return chessBoardArray.getBoardWidth();
    }
    public int getBoardHeight()
    {
        return chessBoardArray.getBoradHeight();
    }

    /**
     * 读取存档用，因不能直接序列化Chess，则只记录MoveSteps中的origin和destination
     * 只匹配棋子左下角坐标
     * @param p 棋子的左下角坐标
     * @return Chess，如找不到则返回null
     */
    public Chess getChessByPosition(Pos p)
    {
        for(Chess c : chessBoard.getChesses())
        {
            if(c.getPosition().getX() == p.getX() && c.getPosition().getY() == p.getY())
            {
                return c;
            }
        }
        return null;
    }

    /**
     * 获取棋局进行时间
     * @return 已进行时间
     */
    public int getSecond()
    {
        return second;
    }

    public void setSecond(int second)
    {
        this.second = second;
    }

    /**
     * 每秒时间+1
     */
    public void addSecond()
    {
        second++;
    }
}
