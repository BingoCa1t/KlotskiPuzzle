package com.klotski.logic;

import com.klotski.polygon.Chess;

public class MoveStep
{
    public Pos origin;
    public Pos destination;

    public MoveStep(Pos origin, Pos destination)
    {
        this.origin = origin;
        this.destination = destination;
    }
    //jackson序列化、反序列化需要
    public MoveStep()
    {

    }
}
