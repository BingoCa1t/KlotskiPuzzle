package com.klotski.logic;

import com.klotski.map.MapData;

public class LevelInfo
{
    private int levelID;
    private MapData mapData;
    private boolean solved=false;
    private int solveTime;
    private int stars;

    public int getLevelID()
    {
        return levelID;
    }

    public int getSolveTime()
    {
        return solveTime;
    }

    public int getStars()
    {
        return stars;
    }

    public MapData getMapData()
    {
        return mapData;
    }

    public void setLevelID(int levelID)
    {
        this.levelID = levelID;
    }

    public void setMapData(MapData mapData)
    {
        this.mapData = mapData;
    }

    public void setSolved(boolean solved)
    {
        this.solved = solved;
    }

    public void setSolveTime(int solveTime)
    {
        this.solveTime = solveTime;
    }

    public void setStars(int stars)
    {
        this.stars = stars;
    }
}
