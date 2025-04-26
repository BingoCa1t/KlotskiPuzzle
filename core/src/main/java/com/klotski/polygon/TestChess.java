package com.klotski.polygon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TestChess
{
    public static void test()
    {
        TextureRegion t1=new TextureRegion(new Texture("chess.png"),160,160);
        Chess c1=new Chess(t1,"曹操",2,2);
        ChessBoard cb=new ChessBoard();
        cb.addChess(c1);
    }
}
