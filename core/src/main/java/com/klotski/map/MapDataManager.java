package com.klotski.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.klotski.Main;
import com.klotski.utils.json.JsonManager;
import com.klotski.utils.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 管理全局的地图数据
 *
 * @author BingoCAT
 */
public class MapDataManager
{
    private Main gameMain;
    /** JSON管理器 */
    private JsonManager jsonManager;
    /** mapID -> MapData的表*/
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
    /** 加载所有地图数据 */
    public void load()
    {
        for(int i = 1; i < 15; i++)
        {
            FileHandle mapFile = Gdx.files.internal("mapData/"+i+".map");
            if(mapFile.exists())
            {
                String mapString = mapFile.readString();
                MapData mapData = jsonManager.parseJsonToObject(mapString, MapData.class);
                if (mapData != null)
                {
                    mapData.setMapID(i);
                    mapDataList.put(i, mapData);
                    Logger.info("Map Manager","Loaded map "+i+" from "+mapFile);
                }
                else
                {
                    Logger.warning("Map Manager","Cannot Load map "+i+" from "+mapFile);
                }
            }
        }
    }
}
