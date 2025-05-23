package com.klotski.polygon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.klotski.utils.SmartBitmapFont;

public class SettleGroup extends Group
{
    private int stars=0;
    private String time="00:00";
    private int step=0;
    Image image;
    Button backButton;
    Button homeButton;
    Button nextButton;
    Button returnButton;
    public SettleGroup(int stars,String time,int step)
    {
        super();
        this.stars=stars;
        this.time=time;
        this.step=step;
        switch(this.stars)
        {
            case 0:
                image=new Image(new TextureRegion(new Texture(Gdx.files.internal("settle\\0s.png"))));
                break;
            case 1:
                image=new Image(new TextureRegion(new Texture(Gdx.files.internal("settle\\1s.png"))));
                break;
            case 2:
                image=new Image(new TextureRegion(new Texture(Gdx.files.internal("settle\\2s.png"))));
                break;
            case 3:
                image=new Image(new TextureRegion(new Texture(Gdx.files.internal("settle\\3s.png"))));
                break;
            default:
                image=new Image(new TextureRegion(new Texture(Gdx.files.internal("settle\\3s.png"))));
                break;
        }
        image.setSize(600,600);
        image.setPosition(20,20);
        int hw=90;
        //返回按钮 Back Button
        Button.ButtonStyle backbs=new Button.ButtonStyle();
        backbs.up=new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settle\\backButton.png"))));
        backButton=new Button(backbs);
        backButton.setSize(hw,hw);
        backButton.setPosition(130,5);

        //主页按钮 Home Button
        Button.ButtonStyle homebs=new Button.ButtonStyle();
        homebs.up=new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settle\\homeButton.png"))));
        homeButton=new Button(homebs);
        homeButton.setSize(hw,hw);
        homeButton.setPosition(230,5);

        //下一关按钮 Next Button
        Button.ButtonStyle nextbs=new Button.ButtonStyle();
        nextbs.up=new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settle\\nextButton.png"))));
        nextButton=new Button(nextbs);
        nextButton.setSize(hw,hw);
        nextButton.setPosition(330,5);

        //重来按钮 Return Button
        Button.ButtonStyle rebs=new Button.ButtonStyle();
        rebs.up=new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settle\\returnButton.png"))));
        returnButton=new Button(rebs);
        returnButton.setSize(hw,hw);
        returnButton.setPosition(430,5);

        //用时 Time Label
        Label.LabelStyle st=new Label.LabelStyle();
        st.font=new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")),60);
        Label timeLabel=new Label(time,st);
        timeLabel.setPosition(150,125);

        //步数 Step Label
        Label stepLabel=new Label(String.valueOf(step),st);
        stepLabel.setPosition(450,129);
        //回放
        Label.LabelStyle st2=new Label.LabelStyle();
        st2.font=new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")),60);

        playbackLabel = new Label("查看回放",st2);

        playbackLabel.setPosition(200,-100);
        addActor(image);
        addActor(backButton);
        addActor(homeButton);
        addActor(nextButton);
        addActor(returnButton);
        addActor(playbackLabel);
        addActor(timeLabel);
        addActor(stepLabel);
    }
    Label playbackLabel;
    public boolean addBackListener(EventListener listener)
    {
        return backButton.addListener(listener);
    }
    public boolean addHomeListener(EventListener listener)
    {
        return homeButton.addListener(listener);
    }
    public boolean addNextListener(EventListener listener)
    {
        return nextButton.addListener(listener);
    }
    public boolean addReturnListener(EventListener listener)
    {
        return returnButton.addListener(listener);
    }
    public boolean addPlaybackListener(EventListener listener)
    {
        return playbackLabel.addListener(listener);
    }

}
