package com.klotski.map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.klotski.logic.Pos;
import com.klotski.map.serialize.MapDataSerializer;
import com.klotski.polygon.Chess;

import java.util.ArrayList;

@JsonSerialize(using= MapDataSerializer.class)
public class MapData
{
    /** 地图ID，此为唯一标识符 */
    private int mapID;
    /** 地图类型，分为不限时和倒计时等*/
    private int mapType;
    /** 地图名字 */
    private String mapName;
    /** 关卡得分，比如10步以内3颗星，15步以内两颗星，20步以内一颗星，则数组应为[10,15,20] */
    private int[] grades;
    /** 棋盘宽度*/
    private int width;
    /** 棋盘高度 */
    private int height;
    /** 棋子数组 */
    private ArrayList<Chess> chesses;
    /** 棋盘出口 */
    private ArrayList<Pos> exit;
    /** 主棋子（曹操）下标 */
    private int mainIndex;

    public ArrayList<Chess> getChesses()
    {
        return chesses;
    }

    public ArrayList<Pos> getExit()
    {
        return exit;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public int[] getGrades()
    {
        return grades;
    }

    public String getMapName()
    {
        return mapName;
    }

    public void setChesses(ArrayList<Chess> chesses)
    {
        this.chesses = chesses;
    }

    public void setExit(ArrayList<Pos> exit)
    {
        this.exit = exit;
    }

    public void setGrades(int[] grades)
    {
        this.grades = grades;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getMapType()
    {
        return mapType;
    }

    public void setMapType(int mapType)
    {
        this.mapType = mapType;
    }

    public int getMapID()
    {
        return mapID;
    }

    public void setMapID(int mapID)
    {
        this.mapID = mapID;
    }

    public int getMainIndex()
    {
        return mainIndex;
    }

    public void setMainIndex(int mainIndex)
    {
        this.mainIndex = mainIndex;
    }
    public MapData()
    {

    }

    /**
     * 复制mapData
     * @param mapData 要复制的mapData
     */
    public MapData(MapData mapData)
    {

        this.mapID=mapData.mapID;
        this.mapType=mapData.mapType;
        this.mapName=new String(mapData.mapName);
        this.grades=mapData.grades.clone();
        this.width=mapData.width;
        this.height=mapData.height;
        this.exit=new ArrayList<>();
        for(Pos p:mapData.exit)
        {
            this.exit.add(new Pos(p.getX(),p.getY()));
        }
        this.mainIndex=mapData.mainIndex;
        this.chesses=new ArrayList<>();
        for(Chess c:mapData.chesses)
        {
            Chess copy=new Chess(c,true);
            this.chesses.add(copy);
        }

    }
}

