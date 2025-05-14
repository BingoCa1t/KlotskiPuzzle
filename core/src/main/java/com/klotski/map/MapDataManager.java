package com.klotski.map;

import com.klotski.Main;
import com.klotski.utils.json.JsonManager;

import java.util.ArrayList;
import java.util.HashMap;

public class MapDataManager
{
    private Main gameMain;
    private JsonManager jsonManager;
    private HashMap<Integer,MapData> mapDataList;


    public HashMap<Integer,MapData> getMapDataList()
    {
        return mapDataList;
    }

    public MapDataManager(Main gameMain)
    {
        mapDataList = new HashMap<>();
        this.gameMain = gameMain;
        this.jsonManager = new JsonManager();
    }
    public void load()
    {
        for(int i = 1; i < 15; i++)
        {
            MapData mapData = jsonManager.loadJsonfromFile("D:\\Map\\"+i+".map", MapData.class);
            if(mapData != null)
                mapData.setMapID(i);
            mapDataList.put(i,mapData);
        }
    }
}
