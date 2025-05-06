package com.klotski.map;

import com.klotski.Main;
import com.klotski.utils.json.JsonManager;

import java.util.ArrayList;

public class MapDataManager
{
    private Main gameMain;
    private JsonManager jsonManager;
    private ArrayList<MapData> mapDataList1;
    //测试需要，先只用Map1

    public ArrayList<MapData> getMapDataList1()
    {
        return mapDataList1;
    }

    public MapDataManager(Main gameMain)
    {
        mapDataList1 = new ArrayList<>();
        this.gameMain = gameMain;
        this.jsonManager = new JsonManager();
    }
    public void load()
    {
        for(int i = 1; i < 15; i++)
        {
            mapDataList1.add(jsonManager.loadJsonfromFile("D:\\Map\\"+i+".map", MapData.class));
        }
    }
}
