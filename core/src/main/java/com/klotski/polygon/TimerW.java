package com.klotski.polygon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.klotski.utils.SmartBitmapFont;

public class TimerW extends Group
{
    private int minutes=0;
    private int seconds=0;
    private Label label1;
    private Label label2;
    public TimerW()
    {
        super();
        init();
    }
    public void init()
    {
        Label.LabelStyle ls=new Label.LabelStyle();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("DS-DIGI-1.ttf"));
        ls.font = new SmartBitmapFont(generator,160);
        label1 = new Label("0", ls);
        Label.LabelStyle ls2=new Label.LabelStyle();
        ls2.font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("DS-DIGI-1.ttf")),80);


        label2 = new Label(":00", ls2);
        addActor(label1);
        addActor(label2);
        label1.setPosition(0,0);
        label1.setFontScale(1f);
        label2.setPosition(90,20);
        label2.setFontScale(1f);
    }
    public void addSecond()
    {
        if(seconds!=59)
        {
            seconds++;
            label2.setText(String.format(": %02d",seconds));
        }
        else
        {
            seconds=0;
            minutes++;
            label2.setText(String.format(": %02d",seconds));
            label1.setText(minutes);
        }
    }
    public void reset()
    {
        minutes=0;
        seconds=0;
    }
}
