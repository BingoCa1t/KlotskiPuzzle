package com.klotski.polygon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.klotski.user.UserInfo;
import com.klotski.utils.SmartBitmapFont;

public class UserInfoGroup extends Group
{
    private UserInfo userInfo;
    private Button headButton;
    private Label nameLabel;
    private Label eMailLabel;
    public UserInfoGroup(UserInfo u)
    {
        super();
        userInfo = u;

        //头像
        Button.ButtonStyle style2 = new Button.ButtonStyle();
        style2.up=new TextureRegionDrawable(new Texture(Gdx.files.internal("headImage.jpg")));
        headButton = new Button(style2);
        headButton.setSize(60,60);
        headButton.setPosition(0,0);

        //昵称 Name Label
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")),40);
        style.fontColor = Color.WHITE;
        nameLabel = new Label(userInfo.getUserName(), style);
        nameLabel.setPosition(30,60);

        //邮箱 E-mail Label
        eMailLabel = new Label(userInfo.getEmail(), style);
        eMailLabel.setPosition(30,20);

        addActor(headButton);
        addActor(nameLabel);
        addActor(eMailLabel);
    }
}
