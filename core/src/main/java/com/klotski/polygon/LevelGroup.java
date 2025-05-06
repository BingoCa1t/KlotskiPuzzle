package com.klotski.polygon;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.klotski.Main;
import com.klotski.Scene.GameMainScene;
import com.klotski.logic.LevelInfo;
import com.klotski.utils.logger.Logger;

import java.util.ArrayList;

public class LevelGroup extends Group
{
    /**
     * 最大15个
     */
    public Main gameMain;
    ArrayList<LevelInfo> levelInfos = new ArrayList<>();
    private final float levelHW = 140f;
    private final float Y1 = 550f;
    private final float Y2 = 300f;
    private final float Y3 = 50f;
    private final float X1 = 0f;
    private final float X2 = 250f;
    private final float X3 = 500f;
    private final float X4 = 750f;
    private final float X5 = 1000f;

    public LevelGroup(Main gameMain)
    {
        super();
        this.setPosition(350, 150);
        this.gameMain = gameMain;
        //this.setSize(800, 1600);
    }

    public void addLevel(LevelInfo levelInfo, boolean isOpen)
    {
        LevelActor la = new LevelActor(levelInfo, isOpen,levelInfos.size()+1);

        /*图片140f，左下角为坐标原点，第三排y=50，第二排y=300，第一排y=550*/
        switch (levelInfos.size() / 5)
        {
            case 0:
                la.setY(Y1);
                break;
            case 1:
                la.setY(Y2);
                break;
            case 2:
                la.setY(Y3);
                break;
            default:
                la.setY(-10000f);
                break;
        }
        switch (levelInfos.size() % 5)
        {
            case 0:
                la.setX(X1);
                break;
            case 1:
                la.setX(X2);
                break;
            case 2:
                la.setX(X3);
                break;
            case 3:
                la.setX(X4);
                break;
            case 4:
                la.setX(X5);
                break;
            default:
                la.setX(-10000f);
                break;
        }
        levelInfos.add(levelInfo);
        la.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Logger.debug("Clicked:"+event.getListenerActor());
                Actor actor = event.getListenerActor();
                if(actor instanceof LevelActor la)
                {
                    int levelID=la.getLevelID();
                    gameMain.getScreenManager().setScreen(new GameMainScene(gameMain,la.getLevelInfo()));
                }

            }
        });
        this.addActor(la);
    }
}
