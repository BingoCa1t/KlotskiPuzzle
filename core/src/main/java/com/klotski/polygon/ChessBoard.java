package com.klotski.polygon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.klotski.utils.logger.Logger;
import com.klotski.logic.ChessBoardArray;
import com.klotski.logic.Pos;

import java.util.ArrayList;

/**
 * 前端，负责绘制棋盘和棋子
 *
 * @author BingoCAT
 */
public class ChessBoard extends Group
{
    //ChessBoardArray为后端，负责维护数组
    //Chess只有X和Y坐标（整数），由ChessBoard负责转换成像素坐标
    /** 音效 */
    private Sound selectedSound;
    private Sound moveSound;
    //棋盘默认宽度
    private final int DEFAULT_WIDTH = 4;
    //棋盘默认高度
    private final int DEFAULT_HEIGHT = 5;
    //暂时无用，以后可能会用到
    private Chess virtualChess;
    private Chess selectedChess;
    //chess数组
    private ArrayList<Chess> chesses = new ArrayList<>();

    public ArrayList<Chess> getChesses()
    {
        return chesses;
    }

    public Chess getVirtualChess()
    {
        return virtualChess;
    }

    public ChessBoard()
    {
        super();
        moveSound= Gdx.audio.newSound(Gdx.files.internal("music/move.mp3"));
        selectedSound=Gdx.audio.newSound(Gdx.files.internal("music/select.mp3"));
    }

    @Override
    public void addActor(Actor actor)
    {
        super.addActor(actor);
    }

    /**
     * 添加棋子，addActor的同时把棋子加入Arraylist
     *
     * @param chess 添加的棋子
     */
    public void addChess(Chess chess)
    {

        addActor(chess);
        chesses.add(chess);
    }
    public void addChessArray(ArrayList<Chess> chesses)
    {
        for(Chess chess : chesses)
            addChess(chess);
    }
    /**
     * 删除棋子
     *
     * @param chess 要删除的棋子
     */
    public void deleteChess(Chess chess)
    {
        if (!chesses.contains(chess))
        {
            Logger.warning("Chess does not exist:" + chess.toString());
            return;
        }
        chesses.remove(chess);
        removeActor(chess);
        //getChildren().removeValue(chess,false);
    }

    /**
     * 移动棋子的绘制动作
     *
     * @param chess 要移动的棋子
     * @param p     目标位置
     */
    public void move(Chess chess, Pos p)
    {
        // 获取一个 MoveTo 动作, 0.5秒内移动到目标位置
        MoveToAction action = Actions.moveTo(p.getX() * Chess.squareHW, p.getY() * Chess.squareHW, 0.5F, Interpolation.smoother);
        // 将动作附加在演员身上, 执行动作
        chess.addAction(action);
        moveSound.play();
        chess.setXYWithoutChangingState(p);

    }

    /**
     * 选中棋子的绘制动作
     *
     * @param chess 选中的棋子
     */
    public void select(Chess chess)
    {
        if(selectedChess != null&&selectedChess.equals(chess))
        {
            chess.disSelect();
            selectedChess = null;
            return;
        }
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
        selectedSound.play();
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
        this.virtualChess.getColor().a = 0.3f;
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
        //不能像底下那样手动处理，Group父类的draw会按照Group的Position来作为绘制Actor原点
        /*
        for(Chess chess : chesses)
        {
            //chess.setPosition(chess.getX()*60, chess.getY()*60);
            chess.draw(batch, parentAlpha)
        }

         */
    }
}
