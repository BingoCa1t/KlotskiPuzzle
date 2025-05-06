package com.klotski.logic;

import com.klotski.map.MapData;

public class LevelInfo
{
    private int levelID;
    private int mapID;
    private int maxStars=-1;
    private int minSolveTime=-1;
    private LevelStatus levelStatus;
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

    public int getMapID()
    {
        return mapID;
    }

    public void setLevelID(int levelID)
    {
        this.levelID = levelID;
    }

    public void setMapID(int mapID)
    {
        this.mapID = mapID;
    }

    public void setLevelStatus(LevelStatus levelStatus)
    {
        this.levelStatus = levelStatus;
    }

    public void setMaxStars(int maxStars)
    {
        this.maxStars = maxStars;
    }

    public void setMinSolveTime(int minSolveTime)
    {
        this.minSolveTime = minSolveTime;
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
