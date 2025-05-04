package com.klotski.archive;

import com.klotski.logic.MoveStep;

import java.util.Stack;

/**
 * 这个是存储每个关卡的存档
 */
public class LevelArchive
{
    private int levelID;
    private Stack<MoveStep> moveSteps;
    private int seconds;

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
}
