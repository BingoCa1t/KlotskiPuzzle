package com.klotski.logic;

import java.util.Objects;

/**
 * 坐标类，记录坐标
 *
 * @author BingoCAT
 */
public class Pos
{
    private int x;
    private int y;
    private int z;

    public int getX()
    {
        return x;
    }
    public void setX(final int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public int getZ()
    {
        return z;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setZ(int z)
    {
        this.z = z;
    }

    public Pos(final int x, final int y, final int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Pos(final int x, final int y)
    {
        this.x = x;
        this.y = y;
        this.z = 0;
    }
    /**
     * 无参构造函数，序列化、反序列化需要
     */
    public Pos()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * 坐标加法
     * @param p1 两个坐标相加
     * @return 结果
     */
    public Pos add(Pos p1)
    {
        return new Pos(this.x + p1.x, this.y + p1.y, this.z + p1.z);
    }

    /**
     * 坐标减法
     * @param p1 减数
     * @return 当前坐标减去p1的结果
     */
    public Pos sub(Pos p1)
    {
        return new Pos(this.x - p1.x, this.y - p1.y, this.z - p1.z);
    }

    /**
     * 判断两个坐标是否相等
     * @param obj 提供的对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj)
    {
        // 如果 obj 类型确实为 Pos 类
        if (obj instanceof Pos anotherPos)
        {
            // 将类型对象转换为 Pos 类
            if (x != anotherPos.x) return false;
            if (y != anotherPos.y) return false;
            if (z != anotherPos.z) return false;
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean isInLine(final Pos pos)
    {
        if(pos.getX() == this.x || pos.getY() == this.y ) return true;
        return false;
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(x, y, z);
    }

    /**
     * toString()
     * @return 返回坐标形式(x,y)
     */
    @Override
    public String toString()
    {
        return "(" + x + ", " + y +  ")";
    }
}
