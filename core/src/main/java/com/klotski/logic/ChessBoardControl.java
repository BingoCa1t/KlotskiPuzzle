package com.klotski.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.klotski.map.MapData;
import com.klotski.polygon.Chess;
import com.klotski.polygon.ChessBoard;
import com.klotski.utils.logger.Logger;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 控制器，负责控制前后端
 *
 * @author BingoCAT
 */
public class ChessBoardControl
{

    Stack<MoveStep> moveSteps = new Stack<>();
    /**前端*/
    private ChessBoard chessBoard;
    /**后端*/
    private ChessBoardArray chessBoardArray;
    //当前棋盘上选中的棋子
    private Chess selectingChess;
    private Chess mainChess;
    private ArrayList<Pos> exit;
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
     * 测试时候使用的。默认构造棋盘
     */
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
        chessBoardArray = new ChessBoardArray(chessBoard.getChesses(), 4, 5);
        chessBoard.setPosition(100, 100);
    }

    /**
     * 加载棋盘
     *
     */
    public void load(MapData mapData)
    {
        chessBoard=new ChessBoard();
        Image background;
        background = new Image(new Sprite(new Texture("background.png")));
        background.setPosition(-10, -10);
        background.setHeight(5 * Chess.squareHW + 20);
        background.setWidth(4 * Chess.squareHW + 20);
        chessBoard.addActor(background);
        chessBoard.addChessArray(mapData.getChesses());
        chessBoard.setPosition(100, 100);
        chessBoardArray = new ChessBoardArray(chessBoard.getChesses(), mapData.getWidth(), mapData.getHeight());


        //loadDefault();
    }

    /**
     * 移动棋子
     *
     * @param chess 棋子
     * @param p     目标坐标
     */
    public void move(Chess chess, Pos p)
    {
        Pos pp=new Pos(p.getX(),p.getY());

        if (chessBoardArray.isChessCanMove(chess, pp))
        {
            moveSteps.push(new MoveStep(chess.getPosition(), pp, chess));
            Logger.debug(chess.toString() + " Move to" + pp.toString());
            chessBoard.move(chess, pp);

        }
        else
        {
            Logger.debug(chess.toString() + " Illegal Movement" + pp.toString());
        }
    }
    public int getSteps()
    {
        return moveSteps.size();
    }
    public void moveBack()
    {
        if (!moveSteps.isEmpty())
        {
            MoveStep ms = moveSteps.pop();
            move(ms.chess, ms.origin);
            moveSteps.pop();
        }
    }

    /**
     * 获取指定坐标的棋子
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
    public boolean isWin()
    {

        return false;
    }
    public void restart()
    {
        while(!moveSteps.isEmpty())
        {
            moveBack();
        }
    }
}
