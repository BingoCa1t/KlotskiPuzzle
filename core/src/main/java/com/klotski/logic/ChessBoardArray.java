package com.klotski.logic;

import com.klotski.polygon.Chess;
import com.klotski.utils.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 负责棋盘的后端运算，维护棋盘数组
 *
 * @author BingoCAT
 */
public class ChessBoardArray
{
    //目前提供两种存储方式：int[]数组和HashMap
    private int width;
    private int height;
    //牢记：左下角是（0,0）
    private int[][] chessBoard;
    private ArrayList<Chess> chessPositions;
    private Chess mainChess;
    private ArrayList<Pos> exits;

    public ChessBoardArray(int[][] array)
    {
        this.chessBoard = array;
    }

    public ChessBoardArray(ArrayList<Chess> chessPositions, int width, int height,ArrayList<Pos> exits,int mainIndex)
    {
        this.chessPositions = chessPositions;
        this.width = width;
        this.height = height;
        this.exits = exits;
        this.mainChess = chessPositions.get(mainIndex);
        init();
    }

    public void init()
    {
        //-1代表边界
        //0代表空位
        //1代表有棋子
        chessBoard = new int[width + 2][height + 2];
        for (int x = 0; x < width + 2; x++)
        {
            for (int y = 0; y < height + 2; y++)
            {
                if (x == 0 || y == 0 || x == width + 1 || y == height + 1)
                    chessBoard[x][y] = -1;
            }
        }
        for (Chess c : chessPositions)
        {
            if(!c.isAppear()) continue;
            Pos p = c.getPosition();
            for (int x = 1; x < c.getChessWidth() + 1; x++)
            {
                for (int y = 1; y < c.getChessHeight() + 1; y++)
                {
                    chessBoard[p.getX() + x][p.getY() + y] = 1;
                }
            }
        }
    }


    public boolean isChessCanMove(Chess chess, Pos destination)
    {
        Pos origin = chess.getPosition();
        if (!origin.isInLine(destination)) return false;
        //先删除棋子
        for (int x = 0; x < chess.getChessWidth(); x++)
        {
            for (int y = 0; y < chess.getChessHeight(); y++)
            {
                chessBoard[1 + origin.getX() + x][1 + origin.getY() + y] = 0;
            }
        }
        /*保证终点处为空，且路径上为空
        1、向正方向（右、上）移动
        起点（x0，y0），终点处（x，y），棋子（x+a,y+b)，
        也就是（x0,y0),(x+a,y+b)为两个角的矩形空白即可
        2、向负方向（左、下）移动
        起点（x0，y0），终点处（x，y）
        也就是（x，y），（x0+a，y0+b）两个角的矩形空白即可
         */
        if (destination.sub(origin).getX() >= 0 && destination.sub(origin).getY() >= 0)
        {
            for (int x = origin.getX()+1; x <= chess.getChessWidth() + destination.getX(); x++)
            {
                for (int y = origin.getY()+1; y <= chess.getChessHeight() + destination.getY(); y++)
                {
                    if (chessBoard[x][y] == 1||chessBoard[x][y] == -1)
                    {
                        for (int xa = 0; xa < chess.getChessWidth(); xa++)
                        {
                            for (int yb = 0; yb < chess.getChessHeight(); yb++)
                            {
                                chessBoard[1 + origin.getX() + xa][1 + origin.getY() + yb] = 1;
                            }
                        }
                        return false;
                    }
                }
            }
        }
        else
        {
            for (int x = destination.getX()+1; x <= chess.getChessWidth() + origin.getX(); x++)
            {
                for (int y = destination.getY()+1; y <= chess.getChessHeight() + origin.getY(); y++)
                {
                    if (chessBoard[x][y] == 1)
                    {
                        for (int xa = 0; xa < chess.getChessWidth(); xa++)
                        {
                            for (int yb = 0; yb < chess.getChessHeight(); yb++)
                            {
                                chessBoard[1 + origin.getX() + xa][1 + origin.getY() + yb] = 1;
                            }
                        }
                        return false;
                    }
                }
            }
        }


            /*
        for(int x = 0; x < Math.max(chess.getChessWidth(),destination.getX()- origin.getX()); x++)
        {
            for(int y = 0; y < Math.max(chess.getChessHeight(),destination.getY()- origin.getY()); y++)
            {
                if(chessBoard[1+destination.getX()+x][1+destination.getY()+y]==1)
                {
                    for(int xa = 0; xa < chess.getChessWidth(); xa++)
                    {
                        for(int yb = 0; yb < chess.getChessHeight(); yb++)
                        {
                            chessBoard[1+origin.getX()+xa][1+origin.getY()+yb]=1;
                        }
                    }
                    return false;
                }

            }
        }

        */
        for (int xa = 0; xa < chess.getChessWidth(); xa++)
        {
            for (int yb = 0; yb < chess.getChessHeight(); yb++)
            {
                chessBoard[1 + destination.getX() + xa][1 + destination.getY() + yb] = 1;
            }
        }
        return true;
    }

    public boolean addChess(Chess chess)
    {
        return false;
    }

    public int[][] getChessBoard()
    {
        return chessBoard;
    }

    public void setChessBoard(int[][] chessBoard)
    {
        this.chessBoard = chessBoard;
    }

    public void deleteChess(Chess chess)
    {
        //重新初始化即可
        init();
    }

    public boolean isPosEmpty(Pos p)
    {
        return true;
    }
    public boolean isWin()
    {
        //假设exit都是按照顺序排列的，且至少有两个格子长度
        if(exits.getFirst().getX()==0&&exits.get(1).getX()==0)
        {
            if(mainChess.getPosition().equals(exits.get(0))) return true;
        }
        if(exits.getFirst().getY()==0&&exits.get(1).getY()==0)
        {
            if(mainChess.getPosition().equals(exits.get(0))) return true;
        }
        //Logger.debug("Win");
        return false;
    }
    public int getBoardWidth()
    {
        return width;
    }
    public int getBoradHeight()
    {
        return height;
    }
}


