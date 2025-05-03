package com.klotski.logic;

import com.klotski.polygon.Chess;

public class MoveStep
{
    public Pos origin;
    public Pos destination;
    public Chess chess;

    public MoveStep(Pos origin, Pos destination, Chess chess)
    {
        this.origin = origin;
        this.destination = destination;
        this.chess = chess;
    }
}
