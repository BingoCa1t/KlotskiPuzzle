package com.klotski.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.klotski.polygon.Chess;
import com.klotski.polygon.ChessBoard;

public class ChessBoardControl
{
    private ChessBoard chessBoard;
    private ChessBoardArray chessBoardArray;
    private Chess selectingChess;
    public ChessBoard getChessBoard()
    {
        return chessBoard;
    }
    //private Stage stage;
    private void loadDefault()
    {
        chessBoard = new ChessBoard();
        chessBoardArray=new ChessBoardArray(null);
        Chess c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;
        Sprite t1,t2,t3,t4;
        t1 = new Sprite(new Texture("Caoc.png"));
        t2= new Sprite(new Texture("Caoc.png"));
        t3 = new Sprite(new Texture("Caoc.png"));
        t4= new Sprite(new Texture("Caoc.png"));
        t1.setOrigin(0, 0);
        //t1.setSize(160f,160f);

        c1 = new Chess(t1, "曹操", 2, 2);
        c1.setXY(new Pos(1,2));
        c2 = new Chess(t2, "关羽", 2, 1);
        c2.setXY(new Pos(1, 4));
        c3 = new Chess(t3, "张飞", 1, 2);
        c3.setXY(new Pos(3, 0));
        c4 = new Chess(t4, "赵云", 1, 2);
        c4.setXY(new Pos(0, 2));
        c5=new Chess(t1,"黄总",1,2);
        c5.setXY(new Pos(3, 2));
        c6=new Chess(t2,"马超",1,2);
        c6.setXY(new Pos(0, 0));
        c7=new Chess(t3,"卒",1,1);
        c7.setXY(new Pos(0, 4));
        c8=new Chess(t3,"卒",1,1);
        c8.setXY(new Pos(1, 1));

        c9=new Chess(t3,"卒",1,1);
        c9.setXY(new Pos(3, 4));
        c10=new Chess(t3,"卒",1,1);
        c10.setXY(new Pos(2, 1));
        //c1.setPosition(20, 20);
        chessBoard = new ChessBoard();
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
        chessBoard.setPosition(100,100);
       // chessBoard.setOrigin(0, 0);
    }
    public void load(ChessBoard board, ChessBoardArray boardArray)
    {
        this.chessBoard = board;
        this.chessBoardArray = boardArray;
        loadDefault();
    }
    public void move(Chess chess, Pos p)
    {
        chessBoard.move(chess, p);
    }
    //public void
    public Chess getChess(Pos p)
    {
        for(Actor c:chessBoard.getChesses())
        {
            if(c instanceof Chess cc)
            {
                if(cc.getPosition().getX()<=p.getX() && cc.getPosition().getX()+cc.getChessWidth()> p.getX()&& cc.getPosition().getY()<=p.getY()&&cc.getPosition().getY()+cc.getChessHeight()> p.getY())
                     return cc;
            }
        }
        return null;
    }
    public void select(Chess chess)
    {
        if (chess == null)
        {
            return;
        }
        chessBoard.select(chess);
        selectingChess = chess;
    }
    public void dragged(Pos p)
    {
        if(selectingChess==null) return;
        chessBoard.dragged(selectingChess,p);

    }
    public void mouseMoved(Pos p)
    {
        if(selectingChess==null) return;
        chessBoard.mouseMoved(selectingChess,p);
    }
}
