package com.klotski;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klotski.Scene.GameMainScene;
import com.klotski.assets.AssetsPathManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game
{
    private SpriteBatch batch;
    private Texture image;
    private Stage stage;
    private GameMainScene gms;
    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");

        gms = new GameMainScene(this);
        gms.show();
        setScreen(gms);

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render();
    }







    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    public AssetsPathManager getAssetsPathManager()
    {
        return null;
    }
}
