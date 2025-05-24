package com.klotski.logic;

/**
 * 棋步记录类，包括起点和终点
 *
 * @author BingoCAT
 */
public class MoveStep
{
    /**
     * 起点
     */
    public Pos origin;
    /**
     * 终点
     */
    public Pos destination;

    /**
     * 默认构造函数
     * @param origin 起点
     * @param destination 终点
     */
    public MoveStep(Pos origin, Pos destination)
    {
        this.origin = origin;
        this.destination = destination;
    }
    /**
     * 无参构造函数，jackson序列化、反序列化需要
     */
    public MoveStep()
    {

    }
}
