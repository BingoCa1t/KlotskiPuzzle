package com.klotski.logic;

import com.klotski.polygon.Chess;

import java.util.HashMap;

/**
 * 负责棋盘的后端运算，维护棋盘数组
 * @author BingoCAT
 */
public class ChessBoardArray
{
    //目前提供两种存储方式：int[]数组和HashMap
    private int[][] chessBoard;
    private HashMap<Chess, Pos> chessPositions;

    public ChessBoardArray(int[][] array)
    {
        this.chessBoard = array;
    }

    public boolean addChess(Chess chess)
    {
        return false;
    }

    public boolean move(Chess chess)
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

    }
    public boolean isPosEmpty(Pos p)
    {
        return true;
    }
}


