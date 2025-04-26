package com.klotski.logic;

import java.util.Objects;

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
    }
    public Pos add(Pos p1)
    {
        return new Pos(this.x + p1.x, this.y + p1.y, this.z + p1.z);
    }
    public Pos sub(Pos p1)
    {
        return new Pos(this.x - p1.x, this.y - p1.y, this.z - p1.z);
    }
    @Override
    public boolean equals(Object obj) {
        // 如果 obj 类型确实为 Pos 类
        if (obj instanceof Pos) {
            // 将类型对象转换为 Pos 类
            Pos anotherPos = (Pos) obj;

            if (x != anotherPos.x) return false;
            if (y != anotherPos.y) return false;
            if (z != anotherPos.z) return false;
            return true;
        } else {
            // 如果类型不是 Pos 类，这两个对象肯定不一样
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
