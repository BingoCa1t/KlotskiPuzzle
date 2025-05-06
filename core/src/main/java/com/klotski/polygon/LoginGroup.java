package com.klotski.polygon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.klotski.utils.SmartBitmapFont;

public class LoginGroup extends Group
{
    private TextField usernameTextField;
    private TextField passwordTextField;
    // 文本框背景纹理
    private Texture bgTexture;
    public static final int TEXT_FIELD_WIDTH = 450;
    public static final int TEXT_FIELD_HEIGHT = 50;
    // 文本框中的光标纹理
    private Texture cursorTexture;
    // 位图字体
    private BitmapFont bitmapFont;
    private Image cursorImage;
    private Button loginButton;
    public LoginGroup()
    {
        super();
        cursorImage = new Image(new Texture(Gdx.files.internal("zuhe2.png")));
        cursorImage.setPosition(-300,0);
        bgTexture=new Texture("field.png");
        bitmapFont=new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")),40);
        TextField.TextFieldStyle textFieldStyle=new TextField.TextFieldStyle();
        textFieldStyle.font = bitmapFont;
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.cursor=new TextureRegionDrawable(createCursorTexture());
        Color selectionColor = new Color(0.2f, 0.2f, 0.8f, 0.5f);

        //textFieldStyle.selection = new TextureRegionDrawable(new TextureRegion(selectionColor));
        textFieldStyle.selection=new TextureRegionDrawable(createSelected());
        //textFieldStyle.background=new TextureRegionDrawable(new TextureRegion(bgTexture));
        usernameTextField =new TextField("username", textFieldStyle);
        passwordTextField =new TextField("", textFieldStyle);

        usernameTextField =new TextField("", textFieldStyle);
        usernameTextField.setSize(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        passwordTextField.setSize(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);

        // 设置文本框的位置
        usernameTextField.setPosition(90, 420);
        passwordTextField.setPosition(90, 230);
        // 用于显示密码的文本框, 需要将文本框设置为密码模式
        passwordTextField.setPasswordMode(true);
        // 显示密码时用 * 号代替密码字符
        passwordTextField.setPasswordCharacter('*');
        loginButton = new Button(new TextureRegionDrawable(bgTexture));
        addActor(cursorImage);
        addActor(usernameTextField);
        addActor(passwordTextField);


    }
    /**
     * 创建文本框中的光标纹理
     */
    private Texture createCursorTexture() {
        Pixmap pixmap = new Pixmap(3, TEXT_FIELD_HEIGHT - 4, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
    private Texture createSelected() {
        Pixmap pixmap = new Pixmap(3, TEXT_FIELD_HEIGHT - 4, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.5f, 0.5f, 1, 0.3f);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    public TextField getPasswordTextField()
    {
        return passwordTextField;
    }

    public TextField getUsernameTextField()
    {
        return usernameTextField;
    }
}
