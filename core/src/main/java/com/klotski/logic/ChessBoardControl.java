package com.klotski.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.klotski.Main;
import com.klotski.Scene.GameMainScene;
import com.klotski.aigo2.Game;
import com.klotski.aigo2.Move;
import com.klotski.archive.ArchiveManager;
import com.klotski.archive.LevelArchive;
import com.klotski.assets.AssetsPathManager;
import com.klotski.assets.ImageAssets;
import com.klotski.map.MapData;
import com.klotski.network.MessageCode;
import com.klotski.polygon.Chess;
import com.klotski.polygon.ChessBoard;
import com.klotski.utils.SmartBitmapFont;
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
    /** 棋步记录表的Label数组 */
    ArrayList<Label> dataLabels = new ArrayList<>();
    /** 传入的前端棋步记录表，如果不传入则弃用*/
    private Table dataTable;
    /** JSON处理器 */
    private JsonManager jsonManager;
    /**用户是否选择载入存档 */
    private boolean isLoadArchive = true;
    /** 是否处于观战模式 */
    private boolean isWatch = false;
    /** 游戏主句柄*/
    private Main gameMain;
    /** 存档管理器 */
    private ArchiveManager archiveManager;
    /** 当前关卡的存档 */
    private LevelArchive levelArchive = new LevelArchive();
    /** 步数记录栈 */
    private Stack<MoveStep> moveSteps = new Stack<>();
    /** 棋局进行时间 */
    private int second = 0;
    /** 关卡地图数据 */
    private MapData mapData;
    /** 前端 */
    private ChessBoard chessBoard;
    /** 后端 */
    private ChessBoardArray chessBoardArray;
    /** 当前棋盘上选中的棋子 */
    private Chess selectingChess;
    /** 棋盘出口 */
    private ArrayList<Pos> exits;
    /** 是否处于回放状态 */
    private boolean isPlayback = false;
    /** 实时计算的AI下一步（Hint）*/
    ArrayList<MoveStep> hints=new ArrayList<>();
    private boolean enableHint = true;
    @Deprecated
    public ChessBoardControl(Main gameMain)
    {
        this.archiveManager = gameMain.getUserManager().getArchiveManager();
        this.gameMain = gameMain;
        this.jsonManager = new JsonManager();
        dataTable = new Table();
    }

    public ChessBoardControl(Main gameMain, Table dataTable)
    {
        this.archiveManager = gameMain.getUserManager().getArchiveManager();
        this.gameMain = gameMain;
        this.jsonManager = new JsonManager();
        this.dataTable = dataTable;
    }
    public ChessBoardControl(Main gameMain, Table dataTable,boolean isLoadArchive,boolean enableHint)
    {
        this.archiveManager = gameMain.getUserManager().getArchiveManager();
        this.gameMain = gameMain;
        this.jsonManager = new JsonManager();
        this.dataTable = dataTable;
        this.isLoadArchive = isLoadArchive;
        this.enableHint = enableHint;
    }

    /** 获取棋盘 */
    public ChessBoard getChessBoard()
    {
        return chessBoard;
    }

    /** 获取选中的棋子 */
    public Chess getSelectingChess()
    {
        return selectingChess;
    }

    /**
     * 在回放界面载入棋盘
     *
     * @param mapData      地图数据
     * @param levelArchive 存档数据
     */
    public void loadPlayback(MapData mapData, LevelArchive levelArchive)
    {
        isPlayback = true;
        this.mapData = new MapData(mapData);
        this.levelArchive = new LevelArchive(levelArchive);
        this.second = levelArchive.getSeconds();
        chessBoard = new ChessBoard(gameMain.getSettingManager());
        Image background;
        Image chessBoardImage;

        chessBoardImage = new Image(gameMain.getAssetsPathManager().get(ImageAssets.ChessBoardFrame));
        if(mapData.getMapType()==2) chessBoardImage=new Image(gameMain.getAssetsPathManager().get(ImageAssets.ObstacleChessBoardFrame));
        chessBoardImage.setPosition(-16, -16);
        background = new Image(gameMain.getAssetsPathManager().get(ImageAssets.ChessBoardBackground));
        background.setPosition(-10, -10);
        background.setHeight(mapData.getHeight() * Chess.squareHW + 20);
        background.setWidth(mapData.getWidth() * Chess.squareHW + 20);
        chessBoard.addActor(background);
        chessBoard.addActor(chessBoardImage);
        for (Chess c : this.mapData.getChesses())
        {
            c.init(gameMain.getAssetsPathManager());
        }
        chessBoard.addChessArray(this.mapData.getChesses());
        chessBoard.setPosition(100, 100);
        exits = mapData.getExit();
        chessBoardArray = new ChessBoardArray(chessBoard.getChesses(), mapData.getWidth(), mapData.getHeight(), exits, mapData.getMainIndex());

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
            if (getChessByPosition(moveStep.origin) == null)
            {
                levelArchive.setMoveSteps(moveSteps);
            }
            moveInArchive(getChessByPosition(moveStep.origin), moveStep.destination);
            //不要重复添加
            //moveSteps.push(moveStep);
        }
        levelArchive.setMoveSteps(moveSteps);
    }

    /**
     * 加载棋盘（更新了加载存档）
     * 此方法从存档管理器里加载存档
     *
     * @param mapData 地图数据
     */
    public void load(MapData mapData)
    {
        load(mapData, archiveManager.getActiveArchive().get(mapData.getMapID()), false);
        //loadDefault();
    }

    /**
     * 载入存档
     *
     * @param mapData 地图数据
     * @param levelArchive 存档
     * @param isWatch 是否为观战模式
     */
    public void load(MapData mapData, LevelArchive levelArchive, boolean isWatch)
    {
        this.isWatch = isWatch;
        //创建MapData的副本，不要更改MapDataManager里的数据
        this.mapData = new MapData(mapData);
        this.levelArchive = levelArchive;

        //后补（算了其实不需要）
        /*if(levelArchive==null)
        {
            levelArchive=new LevelArchive();
        }
         */
        //archiveManager.getActiveArchive().replace(mapData.getMapID(),levelArchive);
        chessBoard = new ChessBoard(gameMain.getSettingManager());
        Image background;
        Image chessBoardImage;
        chessBoardImage = new Image(gameMain.getAssetsPathManager().get(ImageAssets.ChessBoardFrame));
        if(mapData.getMapType()==2) chessBoardImage=new Image(gameMain.getAssetsPathManager().get(ImageAssets.ObstacleChessBoardFrame));
        chessBoardImage.setPosition(-16, -16);
        background = new Image(gameMain.getAssetsPathManager().get(ImageAssets.ChessBoardBackground));
        background.setPosition(-10, -10);
        background.setHeight(mapData.getHeight() * Chess.squareHW + 20);
        background.setWidth(mapData.getWidth() * Chess.squareHW + 20);
        chessBoard.addActor(background);
        chessBoard.addActor(chessBoardImage);
        for (Chess c : this.mapData.getChesses())
        {
            c.init(gameMain.getAssetsPathManager());
        }
        chessBoard.addChessArray(this.mapData.getChesses());
        chessBoard.setPosition(100, 100);
        exits = mapData.getExit();
        chessBoardArray = new ChessBoardArray(chessBoard.getChesses(), mapData.getWidth(), mapData.getHeight(), exits, mapData.getMainIndex());
        //如果存档是null或empty，则将存档的moveSteps设置为当前的moveSteps
        if (levelArchive.getMoveSteps() == null || levelArchive.getMoveSteps().isEmpty() || levelArchive.getLevelStatus() == LevelStatus.Succeed)
        {
            levelArchive.setMoveSteps(moveSteps);
        }
        if(!isLoadArchive)
        {
            levelArchive.setMoveSteps(moveSteps);
            levelArchive.setLevelStatus(LevelStatus.UpComing);
            levelArchive.setSeconds(-1);
        }
        //如果存在存档，且用户选择载入存档<br>（逻辑后补）</br>，则载入存档
        else if (this.isWatch || isLoadArchive)
        {
            try
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
                    if (getChessByPosition(moveStep.origin) == null)
                    {
                        levelArchive.setMoveSteps(moveSteps);
                    }
                    moveInArchive(getChessByPosition(moveStep.origin), moveStep.destination);
                    //不要重复添加
                    //moveSteps.push(moveStep);
                }
                levelArchive.setMoveSteps(moveSteps);
                this.second = levelArchive.getSeconds();
            } catch (Exception e)
            {
                Logger.warning("Chess archive load error: ", e.getMessage() + ", will not load archive");
                moveSteps.clear();
                chessBoard = new ChessBoard(gameMain.getSettingManager());
                Image background2;
                Image chessBoardImage2;
                chessBoardImage2 = new Image(gameMain.getAssetsPathManager().get(ImageAssets.ChessBoardFrame));
                if(mapData.getMapType()==2) chessBoardImage=new Image(gameMain.getAssetsPathManager().get(ImageAssets.ObstacleChessBoardFrame));
                chessBoardImage2.setPosition(-16, -16);
                background2 = new Image(gameMain.getAssetsPathManager().get(ImageAssets.ChessBoardBackground));
                background2.setPosition(-10, -10);
                background2.setHeight(5 * Chess.squareHW + 20);
                background2.setWidth(4 * Chess.squareHW + 20);
                chessBoard.addActor(background2);
                chessBoard.addActor(chessBoardImage2);
                for (Chess c : this.mapData.getChesses())
                {
                    c.init(gameMain.getAssetsPathManager());
                }
                chessBoard.addChessArray(this.mapData.getChesses());
                chessBoard.setPosition(100, 100);
                exits = mapData.getExit();
                chessBoardArray = new ChessBoardArray(chessBoard.getChesses(), mapData.getWidth(), mapData.getHeight(), exits, mapData.getMainIndex());
                levelArchive.setMoveSteps(moveSteps);
            }

        }
        if (!isWatch)
        {
            if (!gameMain.getUserManager().getActiveUser().isGuest())
            {
                gameMain.getNetManager().sendMessage(MessageCode.UpdateWatch, gameMain.getUserManager().getActiveUser().getEmail(), jsonManager.getJsonString(levelArchive), moveSteps.isEmpty() ? "0" : jsonManager.getJsonString(moveSteps.peek()));
            }
        }

    }

    /**
     * 获得呈现在棋步记录表里的字符串
     *
     * @param delta 移动的向量
     * @param chess 移动的棋子
     * @return 返回的字符串
     */
    public String getStepsString(Pos delta, Chess chess)
    {
        if(delta.equals(new Pos(0,0))) return "炸毁了一个障碍物";
        String direction = "";
        int num = 0;
        if (delta.getX() == 0)
        {
            if (delta.getY() > 0)
            {
                direction = "上";
            } else
            {
                direction = "下";
            }
            num = Math.abs(delta.getY());
        } else
        {
            if (delta.getX() > 0)
            {
                direction = "右";
            } else
            {
                direction = "左";
            }
            num = Math.abs(delta.getX());
        }
        return (String.format("棋子 [%s] 向 %s 移动了 %s 格", chess.getChessName(), direction, num));
    }

    /**
     * 移动棋子
     *
     * @param moveStep 棋步记录
     * @return 移动是否成功
     */
    public boolean move(MoveStep moveStep)
    {
        return move(getChessByPosition(moveStep.origin), moveStep.destination);
    }

    /**
     * 移动棋子
     *
     * @param chess 棋子
     * @param p     目标坐标
     */
    public boolean move(Chess chess, Pos p)
    {
        return move(chess, p, false);
    }

    /**
     * 移动棋子
     *
     * @param chess  要移动的棋子
     * @param p      目标坐标
     * @param isBack 是否是悔棋（差别在于向服务器发送的内容）
     * @return 移动是否成功
     */
    public boolean move(Chess chess, Pos p, boolean isBack)
    {
        Pos pp = new Pos(p.getX(), p.getY());
        if (chess == null)
        {
            return false;
        }
        if(chess.getPosition().equals(pp))
        {
            if(!chess.isAppear() && !isBack)
                return false;
            if(!chess.isAppear() && isBack)
            {
                chess.setAppear(true);
                chessBoardArray.init();
                return true;
            }
            chess.explode();
            deleteChess(chess);

            moveSteps.push(new MoveStep(chess.getPosition(), pp));
            BitmapFont font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")), 30);
            Label.LabelStyle ls = new Label.LabelStyle();
            ls.font = font;
            ls.fontColor = Color.WHITE;
            Label l = new Label(getStepsString(pp.sub(chess.getPosition()), chess), ls);
            dataTable.add(l).width(300).pad(5);
            dataLabels.add(l);
            dataTable.row();
            levelArchive.setLevelStatus(LevelStatus.InProgress);
            Logger.debug("Boom a obstacle");
            levelArchive.setSeconds(second);
            if (!isWatch && !isPlayback && !gameMain.getUserManager().getActiveUser().isGuest())
            {
                //存档
                archiveManager.saveByNetwork();
                //所有客户端一直向服务器发送LevelArchive和最近一次移动：0015|email|{LevelArchive}|{MoveStep}
                gameMain.getNetManager().sendMessage(MessageCode.UpdateWatch, gameMain.getUserManager().getActiveUser().getEmail(), jsonManager.getJsonString(levelArchive), moveSteps.isEmpty() ? "0" : jsonManager.getJsonString(moveSteps.peek()), isBack ? "1" : "0");
            }


            return true;
        }
        if (chessBoardArray.isChessCanMove(chess, pp))
        {
            if (!isBack)
            {
                moveSteps.push(new MoveStep(chess.getPosition(), pp));
            }

            //stepsData.add(getStepsString(pp,chess));
            BitmapFont font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")), 30);
            Label.LabelStyle ls = new Label.LabelStyle();
            ls.font = font;
            ls.fontColor = Color.WHITE;
            Label l = new Label(getStepsString(pp.sub(chess.getPosition()), chess), ls);
            dataTable.add(l).width(300).pad(5);
            dataLabels.add(l);
            dataTable.row();
            levelArchive.setLevelStatus(LevelStatus.InProgress);
            Logger.debug(chess.toString() + " Move to" + pp.toString());
            levelArchive.setSeconds(second);
            if (!isWatch && !isPlayback &&!gameMain.getUserManager().getActiveUser().isGuest())
            {
                //存档
                archiveManager.saveByNetwork();
                //所有客户端一直向服务器发送LevelArchive和最近一次移动：0015|email|{LevelArchive}|{MoveStep}
                gameMain.getNetManager().sendMessage(MessageCode.UpdateWatch, gameMain.getUserManager().getActiveUser().getEmail(), jsonManager.getJsonString(levelArchive), moveSteps.isEmpty() ? "0" : jsonManager.getJsonString(moveSteps.peek()), isBack ? "1" : "0");
            } else
            {

            }
            chessBoard.move(chess, pp);
            if(enableHint) hints=Game.gameSolver(getChessBoard().getChesses());

            if (!isPlayback && isWin())
            {

                chessBoard.setTouchable(Touchable.disabled);
                if(gameMain.getScreenManager().getCurrentScreen() instanceof GameMainScene gms)
                {
                    gms.stopInput();
                }
                Logger.debug("Win");
                int steps = moveSteps.size();
                int star;
                if (steps <= mapData.getGrades()[0]) star = 3;
                else if (steps <= mapData.getGrades()[1]) star = 2;
                else if (steps <= mapData.getGrades()[2]) star = 1;
                else star = 0;
                if (!isWatch)
                {
                    levelArchive.setLevelStatus(LevelStatus.Succeed);
                    LevelArchive levelArchive2 = archiveManager.getActiveArchive().get(levelArchive.getMapID()+1);
                    if(levelArchive2 != null && levelArchive2.getLevelStatus() == LevelStatus.Closed)
                    {
                        levelArchive2.setLevelStatus(LevelStatus.UpComing);
                    }
                    //levelArchive.setMoveSteps(levelArchive.getMoveSteps().size() < steps ? levelArchive.getMoveSteps() : moveSteps);
                    levelArchive.setStars(star);
                    levelArchive.setSeconds(second);
                    archiveManager.saveByNetwork();
                }

                if (gameMain.getScreenManager().getCurrentScreen() instanceof GameMainScene gms)
                {
                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run()
                        {
                            MoveToAction action = Actions.moveTo((p.getX()) * Chess.squareHW, (p.getY() - 2) * Chess.squareHW, 1.3f, Interpolation.smoother);
                            AlphaAction a = Actions.fadeOut(0.8f, Interpolation.smoother);
                            chess.addAction(Actions.parallel(action, a));
                        }
                    },0.7f);

                    final int finalStar = star;
                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run()
                        {
                            gms.settle(finalStar, second, moveSteps.size());

                        }
                    }, 1.5f);
                }
            }
            return true;
        } else
        {
            Logger.debug(chess.toString() + " Illegal Movement" + pp.toString());
            return false;
        }
    }

    /**
     * 读取存档时的移动棋子
     *
     * @param chess 要移动的棋子
     * @param p     目标位置
     */
    public void moveInArchive(Chess chess, Pos p)
    {
        Pos pp = new Pos(p.getX(), p.getY());

        if(chess.getPosition().equals(pp))
        {
            deleteChess(chess);
            moveSteps.push(new MoveStep(chess.getPosition(), pp));
            BitmapFont font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")), 30);
            Label.LabelStyle ls = new Label.LabelStyle();
            ls.font = font;
            ls.fontColor = Color.WHITE;
            Label l = new Label(getStepsString(pp.sub(chess.getPosition()), chess), ls);
            dataTable.add(l).width(300).pad(5);
            dataLabels.add(l);
            dataTable.row();
            Logger.debug("Boom a obstacle");
            return;
        }
        if (chessBoardArray.isChessCanMove(chess, pp))
        {
            moveSteps.push(new MoveStep(chess.getPosition(), pp));
            //stepsData.add(getStepsString(pp.sub(chess.getPosition()),chess));
            BitmapFont font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")), 30);
            Label.LabelStyle ls = new Label.LabelStyle();
            ls.font = font;
            ls.fontColor = Color.WHITE;
            Label l = new Label(getStepsString(pp.sub(chess.getPosition()), chess), ls);
            dataTable.add(l).width(300).pad(5);
            dataLabels.add(l);
            dataTable.row();
            Logger.debug(chess.toString() + " Move to" + pp.toString());
            chessBoard.move(chess, pp);
        } else
        {
            Logger.debug(chess.toString() + " Illegal Movement" + pp.toString());
        }
    }

    /**
     * 刷新dataTable里面的内容
     */
    public void refreshDataTable()
    {
        dataTable.clearChildren(true);
        for (Label l : dataLabels)
        {
            dataTable.add(l).width(300).pad(5);
            dataTable.row();
        }

    }

    /**
     * 返回步数
     *
     * @return 返回步数记录栈的长度
     */
    public int getSteps()
    {
        return moveSteps.size();
    }

    /**
     * 回退，弹出移动记录栈最顶层的元素并反向移动
     *
     * @return 被弹出的元素
     */
    public MoveStep moveBack()
    {
        if (!moveSteps.isEmpty())
        {
            MoveStep ms = moveSteps.pop();
            if(ms.destination.equals(ms.origin))
            {
                //getChessByPosition(ms.destination).setAppear(true);
                move(getChessByPosition(ms.destination), ms.origin, true);
                dataLabels.removeLast();
                refreshDataTable();
                return ms;
            }
            move(getChessByPosition(ms.destination), ms.origin, true);
            dataLabels.removeLast();
            dataLabels.removeLast();
            refreshDataTable();
            return ms;
        }
        return null;
    }

    /**
     * 获取指定坐标的棋子（判断点击某个棋子用）
     * 包括棋子覆盖的位置
     *
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
        if (chess == null ) return;

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
     *
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
        while (!moveSteps.isEmpty())
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
     *
     * @param p 棋子的左下角坐标
     * @return Chess，如找不到则返回null
     */
    public Chess getChessByPosition(Pos p)
    {
        for (Chess c : chessBoard.getChesses())
        {
            if (c.getPosition().getX() == p.getX() && c.getPosition().getY() == p.getY())
            {
                return c;
            }
        }
        return null;
    }

    /**
     * 获取棋局进行时间
     *
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

    public void deleteChess(Chess chess)
    {
        //mapData.getChesses().remove(chess);
        chess.setAppear(false);
        //chessBoard.deleteChess(chess);
        chessBoardArray.deleteChess(chess);
    }
    public ArrayList<MoveStep> getHints()
    {
        return hints;
    }
    public void calculateHints()
    {
        if(enableHint) hints=Game.gameSolver(getChessBoard().getChesses());
    }
    public Chess getMainChess()
    {
        return chessBoard.getChesses().get(mapData.getMainIndex());
    }
}
