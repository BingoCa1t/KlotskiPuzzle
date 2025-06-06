package com.klotski.polygon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.klotski.Main;
import com.klotski.Scene.GameMainScene;
import com.klotski.Scene.LevelSelectScene;
import com.klotski.assets.ImageAssets;
import com.klotski.logic.LevelStatus;
import com.klotski.utils.SmartBitmapFont;

import java.util.ArrayList;

public class LevelGroup extends Group
{
    /**
     * 最大15个
     */
    public Main gameMain;
    ArrayList<Integer> levels = new ArrayList<>();
    private int currentLevel = 0;
    private final float levelHW = 140f;
    private final float Y1 = 550f;
    private final float Y2 = 300f;
    private final float Y3 = 50f;
    private final float X1 = 0f;
    private final float X2 = 250f;
    private final float X3 = 500f;
    private final float X4 = 750f;
    private final float X5 = 1000f;
    private LevelSelectScene levelSelectScene;

    public LevelGroup(Main gameMain,LevelSelectScene levelSelectScene)
    {
        super();
        this.setPosition(350, 150);
        this.gameMain = gameMain;
        this.levelSelectScene = levelSelectScene;
        //this.setSize(800, 1600);
    }

    public void addLevel(int mapID, boolean isOpen)
    {
        float s = 0;
        float y=0;
        //LevelActor la = new LevelActor(levelInfo, isOpen,levelInfos.size()+1);
        LevelActor la = new LevelActor(mapID, levels.size(), isOpen,gameMain.getAssetsPathManager());
        int star = 0;
        Image starImage;
        if (gameMain.getUserManager().getArchiveManager() != null)
        {
            star = gameMain.getUserManager().getArchiveManager().getActiveArchive().get(mapID).getStars();
        }
        switch (star)
        {
            case 0:
                starImage = new Image(new TextureRegion(gameMain.getAssetsPathManager().get(ImageAssets.OneStar)));
                starImage.setVisible(false);
                break;
            case 1:
                starImage = new Image(new TextureRegion(gameMain.getAssetsPathManager().get(ImageAssets.OneStar)));
                s = 65f;
                starImage.setScale(0.45f);
                break;
            case 2:
                starImage = new Image(new TextureRegion(gameMain.getAssetsPathManager().get(ImageAssets.TwoStar)));
                starImage.setScale(0.5f);
                break;
            case 3:
                starImage = new Image(new TextureRegion(gameMain.getAssetsPathManager().get(ImageAssets.ThreeStar)));
                starImage.setScale(0.45f);
                s = -45f;
                y = 25f;
                break;
            default:
                starImage = new Image(new TextureRegion(gameMain.getAssetsPathManager().get(ImageAssets.OneStar)));
                starImage.setVisible(false);
                break;

        }
        //starImage.setSize(80,30);

        starImage.setOrigin(starImage.getWidth() / 2, starImage.getHeight() / 2);

        /*图片140f，左下角为坐标原点，第三排y=50，第二排y=300，第一排y=550*/
        switch (levels.size() / 5)
        {
            case 0:
                la.setY(Y1);
                starImage.setY(Y1 + 80-y);
                break;
            case 1:
                la.setY(Y2);
                starImage.setY(Y2 + 80-y);
                break;
            case 2:
                la.setY(Y3);
                starImage.setY(Y3 + 80-y);
                break;
            default:
                la.setY(-10000f);
                starImage.setY(-10000f);
                break;
        }
        switch (levels.size() % 5)
        {
            case 0:
                la.setX(X1);
                starImage.setX(X1 - 60 + s);
                break;
            case 1:
                la.setX(X2);
                starImage.setX(X2 - 60 + s);
                break;
            case 2:
                la.setX(X3);
                starImage.setX(X3 - 60 + s);
                break;
            case 3:
                la.setX(X4);
                starImage.setX(X4 - 60 + s);
                break;
            case 4:
                la.setX(X5);
                starImage.setX(X5 - 60 + s);
                break;
            default:
                la.setX(-10000f);
                starImage.setX(-10000f);
                break;
        }
        //levelInfos.add(levelInfo);
        levels.add(mapID);
        la.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if(!(gameMain.getUserManager().getArchiveManager().getActiveArchive().get(mapID).getMoveSteps()==null)&&!(gameMain.getUserManager().getArchiveManager().getActiveArchive().get(mapID).getMoveSteps().isEmpty())&&gameMain.getUserManager().getArchiveManager().getActiveArchive().get(mapID).getLevelStatus()!= LevelStatus.Succeed && gameMain.getUserManager().getArchiveManager().getActiveArchive().get(mapID).getLevelStatus()!= LevelStatus.UpComing)
                {
                    if(gameMain.getScreenManager().getCurrentScreen() instanceof LevelSelectScene lss)
                    {
                        lss.showDialog(mapID);
                    }
                }
                else {
                    gameMain.getScreenManager().setScreen(new GameMainScene(gameMain, mapID));
                }
            }
        });
        if(!isOpen) la.setTouchable(Touchable.disabled);
        this.addActor(la);
        this.addActor(starImage);
    }

    public ArrayList<Integer> getLevels()
    {
        return levels;
    }

    public int getCurrentLevel()
    {
        return currentLevel;
    }

    public void nextLevel()
    {
        if (currentLevel < levels.size() - 1)
        {
            //gameMain.getScreenManager().setScreen(new GameMainScene(gameMain, levels.get(++currentLevel)));
            ++currentLevel;
            if(!(gameMain.getUserManager().getArchiveManager().getActiveArchive().get(levels.get(currentLevel)).getMoveSteps()==null)&&!(gameMain.getUserManager().getArchiveManager().getActiveArchive().get(levels.get(currentLevel)).getMoveSteps().isEmpty())&&gameMain.getUserManager().getArchiveManager().getActiveArchive().get(levels.get(currentLevel)).getLevelStatus()!= LevelStatus.Succeed&&gameMain.getUserManager().getArchiveManager().getActiveArchive().get(levels.get(currentLevel)).getLevelStatus()!= LevelStatus.UpComing)
            {
                if(gameMain.getScreenManager().getCurrentScreen() instanceof LevelSelectScene lss)
                {
                    lss.showDialog(levels.get(currentLevel));
                }
            }
            else {
                gameMain.getScreenManager().setScreen(new GameMainScene(gameMain, levels.get(currentLevel)));
            }


        } else
        {
            currentLevel = 0;
            if(!(gameMain.getUserManager().getArchiveManager().getActiveArchive().get(levels.get(currentLevel)).getMoveSteps()==null)&&!(gameMain.getUserManager().getArchiveManager().getActiveArchive().get(levels.get(currentLevel)).getMoveSteps().isEmpty())&&gameMain.getUserManager().getArchiveManager().getActiveArchive().get(levels.get(currentLevel)).getLevelStatus()!= LevelStatus.Succeed&&gameMain.getUserManager().getArchiveManager().getActiveArchive().get(levels.get(currentLevel)).getLevelStatus()!= LevelStatus.UpComing)
            {
                if(gameMain.getScreenManager().getCurrentScreen() instanceof LevelSelectScene lss)
                {
                    lss.showDialog(levels.get(currentLevel));
                }
            }
            else {
                gameMain.getScreenManager().setScreen(new GameMainScene(gameMain, levels.get(currentLevel)));
            }

        }
    }

    public void returnLevel()
    {
        gameMain.getScreenManager().setScreen(new GameMainScene(gameMain, levels.get(currentLevel)));
    }

    public void setCurrentLevel(int currentLevel)
    {
        this.currentLevel = currentLevel;
    }
}
