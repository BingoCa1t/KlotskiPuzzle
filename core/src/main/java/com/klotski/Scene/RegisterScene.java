package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.klotski.Main;
import com.klotski.utils.SmartBitmapFont;

public class RegisterScene extends KlotskiScene
{
    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄Q
     */
    Image registImage;
    TextButton getCodeButton;
    TextButton registerButton;
    public RegisterScene(Main gameMain)
    {
        super(gameMain);
    }
    @Override
    public void init()
    {
        super.init();
        registImage=new Image(new TextureRegion(new Texture("regist.png")));
        TextButton.TextButtonStyle style=new TextButton.TextButtonStyle();
        style.font=new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("fonts\\Gabriola.ttf")),40);
        style.fontColor= Color.WHITE;
        style.disabledFontColor=Color.GRAY;
        getCodeButton=new TextButton("Get Code", style);
        registerButton=new TextButton("Register", style);
        stage.addActor(registImage);
        

    }
    @Override
    public void input()
    {

    }

    @Override
    public void draw(float delta)
    {
        stage.draw();
    }

    @Override
    public void logic(float delta)
    {

    }
}
