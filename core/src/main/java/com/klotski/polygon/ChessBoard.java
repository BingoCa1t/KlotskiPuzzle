package com.klotski.polygon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.klotski.logic.ChessBoardArray;
import com.klotski.logic.Pos;

import java.util.ArrayList;

public class ChessBoard extends Group
{
    // private HashMap<Chess,String> chessBoardImagePath;

    //前后端分离（？
    //此为前端，只负责绘制UI
    //ChessBoardArray为后端，负责维护数组
    //先不写逻辑，把图画出来先
    //先写一个draw()方法，把图画出来
    //Chess只有X和Y坐标（整数），由ChessBoard负责转换成像素坐标
    //控制器？
    //private int[][] chessBoardArray;
    private final int DEFAULT_WIDTH = 4;
    private final int DEFAULT_HEIGHT = 5;
    private ChessBoardArray boardArray;
    private Chess virtualChess;
    private Image background = new Image(new Texture("background.png"));
    private ArrayList<Chess> chesses = new ArrayList<>();

    public ArrayList<Chess> getChesses()
    {
        return chesses;
    }

    public Chess getVirtualChess()
    {
        return virtualChess;
    }

    public void init()
    {
        //chessBoardImagePath=new HashMap<>();
        //chesses=new ArrayList<>();

    }

    public ChessBoard()
    {
        super();
        //将图片素材按照棋子大小缩放
        background.setScale(DEFAULT_WIDTH * 160f / background.getImageWidth(), DEFAULT_HEIGHT * 160f / background.getImageHeight());
        addActor(background);
        //这里的size是图片素材的大小，并非棋子显示的大小，棋子显示大小=图片素材大小*缩放比
        //setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }

    @Override
    public void addActor(Actor actor)
    {
        super.addActor(actor);
    }

    public void addChess(Chess chess)
    {
        addActor(chess);
        chesses.add(chess);
    }

    public void deleteChess(Chess chess)
    {
        chesses.remove(chess);
        removeActor(chess);
    }

    /**
     * 移动棋子的绘制动作
     *
     * @param chess 要移动的棋子
     * @param p     目标位置
     */
    public void move(Chess chess, Pos p)
    {
        // 获取一个 MoveTo 动作, 0.8秒内移动到目标位置
        MoveToAction action = Actions.moveTo(p.getX() * 160, p.getY() * 160, 0.8F, Interpolation.smooth);

        // 将动作附加在演员身上, 执行动作
        chess.addAction(action);
    }

    /**
     * 选中棋子的绘制动作
     *
     * @param chess 选中的棋子
     */
    public void select(Chess chess)
    {
        //单击触发
        if (!chesses.contains(chess))
        {
            return;
        }

        for (Chess chess2 : chesses)
        {
            chess2.disSelect();
        }
        chess.select();
    }

    /// 临时方法，测试移动动画
    public Chess getChess10()
    {
        return chesses.get(9);
    }

    public void dragged(Chess c, Pos p)
    {
        c.setXY(p);

        //setVirtualChess(c);
    }

    private void setVirtualChess(Chess virtualChess, Pos p)
    {
        if (virtualChess != null)
        {
            this.removeActor(this.virtualChess);
        }
        this.virtualChess = new Chess(virtualChess);

        this.virtualChess.setXY(p);
        this.virtualChess.getColor().a=0.3f;
        this.addActor(this.virtualChess);


    }

    public void mouseMoved(Chess selectingChess, Pos p)
    {
        setVirtualChess(selectingChess, p);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

        super.draw(batch, parentAlpha);
        //super.draw(batch, parentAlpha);
        //画每一个棋子
        //****这里以后加上要先画棋盘
        //不能像底下那样手动处理，Group父类的draw会按照Group的Position来作为绘制Actor原点
        /*
        for(Chess chess : chesses)
        {
            //chess.setPosition(chess.getX()*60, chess.getY()*60);
            chess.draw(batch, parentAlpha);
        }

         */
    }
}
