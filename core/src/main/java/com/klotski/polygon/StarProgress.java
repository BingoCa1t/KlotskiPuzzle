package com.klotski.polygon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.klotski.assets.AssetsPathManager;
import com.klotski.assets.ImageAssets;
import com.klotski.utils.SmartBitmapFont;

public class StarProgress extends Group
{
    private Image rec;
    private Image star1;
    private Image star2;
    private Image star3;
    private Label numberLabel1;
    private Label numberLabel2;
    private Label numberLabel3;

    ShapeRenderer shapeRenderer;
    RoundRecActor recActor;
    BitmapFont font;

    public void setStep(int steps)
    {
        recActor.setSteps(steps);
    }
    public StarProgress(int n1, int n2, int n3, AssetsPathManager assetsPathManager)
    {
        super();
        shapeRenderer = new ShapeRenderer();
        recActor = new RoundRecActor(n1, n2, n3);
        rec = new Image(assetsPathManager.get(ImageAssets.GameMainStepRectangle));

        //三颗星星 Star Image
        star1 = new Image(assetsPathManager.get(ImageAssets.OneStarInGame));
        star2 = new Image(assetsPathManager.get(ImageAssets.OneStarInGame));
        star3 = new Image(assetsPathManager.get(ImageAssets.OneStarInGame));
        star1.setPosition(80, 5);
        star2.setPosition(225, 5);
        star3.setPosition(370, 5);

        //三个数字 NumberLabel
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("LeagueGothic-Regular.ttf")), 45);
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        numberLabel1 = new Label(String.valueOf(n3), labelStyle);
        numberLabel2 = new Label(String.valueOf(n2), labelStyle);
        numberLabel3 = new Label(String.valueOf(n1), labelStyle);
        numberLabel1.setPosition(85, 44);
        numberLabel2.setPosition(235, 44);
        numberLabel3.setPosition(385, 44);

        addActor(rec);
        addActor(recActor);
        addActor(star1);
        addActor(star2);
        addActor(star3);
        addActor(numberLabel1);
        addActor(numberLabel2);
        addActor(numberLabel3);

        recActor.init();
        recActor.setPosition(0, 0);
    }

    @Override
    public void draw(Batch batch,float parentAlpha)
    {
        super.draw(batch, parentAlpha);



    }

}
