package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.klotski.Main;
import com.klotski.network.MessageCode;
import com.klotski.network.NetworkMessageObserver;
import com.klotski.utils.SmartBitmapFont;

import java.util.TimerTask;
import java.util.regex.Pattern;

public class RegisterScene extends KlotskiScene implements NetworkMessageObserver
{
    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄Q
     */
    // 文本框背景纹理
    //private Texture bgTexture;
    public static final int TEXT_FIELD_WIDTH = 500;
    public static final int TEXT_FIELD_HEIGHT = 50;
    // 位图字体
    private BitmapFont bitmapFont;
    private boolean isCountingDown = false;
    Image registImage;
    TextButton getCodeButton;
    TextButton registerButton;
    TextField usernameTextField;
    TextField emailTextField;
    TextField passwordTextField;
    TextField verifyCodeField;
    Label infoLabel;

    public RegisterScene(Main gameMain)
    {
        super(gameMain);
    }

    @Override
    public void init()
    {
        super.init();
        // 初始化信息标签

        // 初始化文本框样式
        bitmapFont = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")), 40);
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = bitmapFont;
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.cursor = new TextureRegionDrawable(createCursorTexture());
        textFieldStyle.selection = new TextureRegionDrawable(createSelected());

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.WHITE;
        infoLabel = new Label("", labelStyle);
        infoLabel.setPosition(1400,400);
        // 初始化文本框
        usernameTextField = new TextField("", textFieldStyle);
        emailTextField = new TextField("", textFieldStyle);
        passwordTextField = new TextField("", textFieldStyle);
        verifyCodeField = new TextField("", textFieldStyle);
        // 文本框设置大小
        usernameTextField.setSize(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        emailTextField.setSize(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        passwordTextField.setSize(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        verifyCodeField.setSize(TEXT_FIELD_WIDTH / 2, TEXT_FIELD_HEIGHT);
        // 设置文本框的位置
        usernameTextField.setPosition(810, 780);
        emailTextField.setPosition(810, 645);
        passwordTextField.setPosition(810, 510);
        verifyCodeField.setPosition(810, 370);
        // 背景图片
        registImage = new Image(new TextureRegion(new Texture("registerScene\\RegisterScene.png")));
        registImage.setPosition(400, 200);
        // 两个按钮的初始化
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("fonts\\Gabriola.ttf")), 80);
        style.fontColor = Color.WHITE;
        style.disabledFontColor = Color.GRAY;
        getCodeButton = new TextButton("Get Code", style);
        getCodeButton.setPosition(1200, 350);
        getCodeButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                if (!isCountingDown)
                {
                    if (isValidEmail(emailTextField.getText()))
                    {
                        gameMain.getUserManager().getCode(emailTextField.getText());
                        startCountdown();
                    } else
                    {
                        infoLabel.setText("Invalid email address");
                    }
                }
            }
        });
        registerButton = new TextButton("Register", style);
        registerButton.setPosition(900, 200);
        registerButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                if (usernameTextField.getText().isEmpty())
                {
                    infoLabel.setText("Please enter username");
                } else if (emailTextField.getText().isEmpty())
                {
                    infoLabel.setText("Please enter email");
                } else if (passwordTextField.getText().isEmpty())
                {
                    infoLabel.setText("Please enter password");
                } else if (verifyCodeField.getText().isEmpty())
                {
                    infoLabel.setText("Please enter verify code");
                } else
                {
                    gameMain.getUserManager().register(usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), verifyCodeField.getText());
                }
            }
        });
        stage.addActor(registImage);
        stage.addActor(getCodeButton);
        stage.addActor(registerButton);
        stage.addActor(usernameTextField);
        stage.addActor(emailTextField);
        stage.addActor(passwordTextField);
        stage.addActor(verifyCodeField);
        stage.addActor(infoLabel);


    }

    private float countdownTimer = 0;

    private void startCountdown()
    {
        countdownTimer = 60;
        isCountingDown = true;
        getCodeButton.setDisabled(true);
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
        ScreenUtils.clear(0, 0, 0, 1);
        super.render(delta);
        // 更新倒计时
        if (isCountingDown)
        {
            countdownTimer -= Gdx.graphics.getDeltaTime();
            if (countdownTimer <= 0)
            {
                countdownTimer = 0;
                isCountingDown = false;
                getCodeButton.setText("Get Code");
                getCodeButton.setDisabled(false);
            } else
            {
                getCodeButton.setText("Again(" + (int) countdownTimer + ")");
            }
        }
    }

    @Override
    public void update(MessageCode code, String message)
    {

    }

    private Texture createCursorTexture()
    {
        Pixmap pixmap = new Pixmap(3, TEXT_FIELD_HEIGHT - 4, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private Texture createSelected()
    {
        Pixmap pixmap = new Pixmap(3, TEXT_FIELD_HEIGHT - 4, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.5f, 0.5f, 1, 0.3f);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    public static boolean isValidEmail(String email)
    {
        if (email == null || email.trim().isEmpty())
        {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public void registrySucceed()
    {
        // 临时屏蔽输入
        Gdx.input.setInputProcessor(new InputAdapter()
        {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button)
            {
                return true; // 消费事件，不传递给其他处理器
            }
            @Override
            public boolean keyDown(int keycode)
            {
                return true;
            }
        });
        infoLabel.setText("Registry Succeed, Jump to the login interface in 2 seconds");
        Timer.schedule(new Timer.Task()
        {
            @Override
            public void run()
            {
                gameMain.getScreenManager().setScreenWithoutSaving(new LoginScene(gameMain));
            }
        }, 2);
    }

    public void registryFail(String message)
    {
        infoLabel.setText(message);
    }
}
