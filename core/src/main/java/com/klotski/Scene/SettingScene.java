package com.klotski.Scene;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klotski.Main;
import com.klotski.assets.ImageAssets;
import com.klotski.polygon.SettingGroup;

public class SettingScene extends KlotskiScene
{
    private SettingGroup group;
    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄
     */
    public SettingScene(Main gameMain)
    {
        super(gameMain);
    }
    @Override
    public void init()
    {
        super.init();
        ImageButton backButton=new ImageButton(new TextureRegionDrawable(gameMain.getAssetsPathManager().get(ImageAssets.RegisterBackButton)));
        backButton.setPosition(100,900);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                gameMain.getScreenManager().returnPreviousScreen();
            }
        });
        group = new SettingGroup(gameMain.getSettingManager());
        stage.addActor(group);
        group.setPosition(200, 200);
        stage.addActor(backButton);
    }
    @Override
    public void input()
    {

    }

    @Override
    public void draw(float delta)
    {
stage.act(delta);
stage.draw();
    }

    @Override
    public void logic(float delta)
    {

    }
    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(0, 0, 0, 0);
        super.render(delta);
    }
}
