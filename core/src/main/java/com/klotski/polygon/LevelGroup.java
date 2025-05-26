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

    public LevelGroup(Main gameMain)
    {
        super();
        this.setPosition(350, 150);
        this.gameMain = gameMain;
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
                if(!(gameMain.getUserManager().getArchiveManager().getActiveArchive().get(mapID).getMoveSteps()==null)&&!(gameMain.getUserManager().getArchiveManager().getActiveArchive().get(mapID).getMoveSteps().isEmpty()))
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
            gameMain.getScreenManager().setScreen(new GameMainScene(gameMain, levels.get(++currentLevel)));

        } else
        {
            gameMain.getScreenManager().setScreen(new GameMainScene(gameMain, levels.getFirst()));
            currentLevel = 0;
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
    /*
    private Skin customSkin;
    private TextButton yesButton, noButton;
    private Label resultLabel;
    private boolean dialogResult = false;
    private boolean dialogVisible = false;
    private void createCustomSkin() {
        customSkin = new Skin();

        // 创建简单的白色纹理作为基础
        Texture whiteTexture = gameMain.getAssetsPathManager().get(ImageAssets.White);
        customSkin.add("white", whiteTexture);

        // 创建字体

        customSkin.add("default-font", new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")),60));

        // 创建按钮样式
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(customSkin.getRegion("white"))
            .tint(new Color(0.4f, 0.4f, 0.4f, 1));
        buttonStyle.down = new TextureRegionDrawable(customSkin.getRegion("white"))
            .tint(new Color(0.6f, 0.6f, 0.6f, 1));
        buttonStyle.checked = new TextureRegionDrawable(customSkin.getRegion("white"))
            .tint(new Color(0.6f, 0.6f, 0.6f, 1));
        buttonStyle.over = new TextureRegionDrawable(customSkin.getRegion("white"))
            .tint(new Color(0.5f, 0.5f, 0.5f, 1));
        buttonStyle.font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")),60);
        customSkin.add("default", buttonStyle);

        // 创建标签样式
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")),60);
        labelStyle.fontColor = Color.BLUE;
        customSkin.add("default", labelStyle);

        // 创建对话框样式
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")),60);
        windowStyle.background = new TextureRegionDrawable(customSkin.getRegion("white"))
            .tint(new Color(0.3f, 0.3f, 0.3f, 0.9f));
        windowStyle.titleFontColor = Color.WHITE;
        customSkin.add("default", windowStyle);
    }
    private Boolean isLoadArchive;
    private void showLoadSaveDialog(int mapID) {
        // 创建对话框
        final Dialog dialog = new Dialog("载入存档", customSkin) {
            @Override
            protected void result(Object object) {
                currentLevel = levels.indexOf(mapID);
                dialogResult = (Boolean) object;
                isLoadArchive = dialogResult;
                dialogVisible = false;
                if(Boolean.TRUE.equals(isLoadArchive))
                {
                    gameMain.getScreenManager().setScreen(new GameMainScene(gameMain, mapID,true));
                }
                else if(Boolean.FALSE.equals(isLoadArchive))
                {
                    gameMain.getScreenManager().setScreen(new GameMainScene(gameMain, mapID,false));
                }

            }
        };
        dialog.setSize(400, 300);
        dialog.setPosition(getStage().getWidth() / 2 - dialog.getWidth() / 2,
            getStage().getHeight() / 2 - dialog.getHeight() / 2);
        dialog.setMovable(false);
        dialog.setModal(true);
        // 添加文本内容
        dialog.text("是否载入存档？");

        // 添加按钮
        yesButton = new TextButton("是", customSkin);
        noButton = new TextButton("否", customSkin);

        // 使用 pad 方法设置按钮之间的间距
        dialog.button(yesButton, true).padRight(20);
        dialog.button(noButton, false);

        // 显示对话框
        dialog.show(this.getStage());
        dialog.toFront();
        dialogVisible = true;
    }


     */
}
