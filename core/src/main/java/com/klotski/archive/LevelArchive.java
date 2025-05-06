package com.klotski.archive;

import com.klotski.logic.LevelStatus;
import com.klotski.logic.MoveStep;

import java.util.Stack;

/**
 * 这个是存储每个关卡的存档
 */
public class LevelArchive
{
    private int levelID;
    private Stack<MoveStep> moveSteps;
    private int seconds=-1;
    private LevelStatus levelStatus;

    public int getLevelID()
    {
        return levelID;
    }

    public int getSeconds()
    {
        return seconds;
    }

    public Stack<MoveStep> getMoveSteps()
    {
        return moveSteps;
    }

    public void setLevelID(int levelID)
    {
        this.levelID = levelID;
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
        this.levelID = levelArchive.levelID;
        this.moveSteps = (Stack<MoveStep>) levelArchive.getMoveSteps().clone();
        this.seconds = levelArchive.seconds;
        this.levelStatus = levelArchive.levelStatus;
    }
    public LevelArchive()
    {

    }
}
