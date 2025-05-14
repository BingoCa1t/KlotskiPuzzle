package com.klotski.archive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.klotski.logic.LevelStatus;
import com.klotski.logic.MoveStep;

import java.util.Stack;

/**
 * 这个是存储每个关卡的存档
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LevelArchive
{
    //唯一标识符MapID
    private int mapID;
    //步数最少的一次
    private Stack<MoveStep> moveSteps;
    //步数最少的那次对应的用时
    private int seconds=-1;
    //关卡状态
    private LevelStatus levelStatus;
    private int stars=-1;

    public int getStars()
    {
        return stars;
    }

    public void setStars(int stars)
    {
        this.stars = stars;
    }

    public int getMapID()
    {
        return mapID;
    }

    public int getSeconds()
    {
        return seconds;
    }

    public Stack<MoveStep> getMoveSteps()
    {
        return moveSteps;
    }

    public void setMapID(int mapID)
    {
        this.mapID = mapID;
    }

    public void setMoveSteps(Stack<MoveStep> moveSteps)
    {
        this.moveSteps = moveSteps;
    }

    public void setSeconds(int seconds)
    {
        this.seconds = seconds;
    }


    public LevelStatus getLevelStatus()
    {
        return levelStatus;
    }

    public void setLevelStatus(LevelStatus levelStatus)
    {
        this.levelStatus = levelStatus;
    }

    public LevelArchive(LevelArchive levelArchive)
    {
        this.mapID = levelArchive.mapID;
        this.moveSteps = (Stack<MoveStep>) levelArchive.getMoveSteps().clone();
        this.seconds = levelArchive.seconds;
        this.levelStatus = levelArchive.levelStatus;
    }
    public LevelArchive()
    {

    }
}
