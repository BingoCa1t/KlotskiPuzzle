package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klotski.Main;
import com.klotski.polygon.LoginGroup;
import com.klotski.user.UserInfo;
import com.klotski.user.UserManager;
import com.klotski.utils.logger.Logger;

public class LoginScene extends KlotskiScene
{
    private UserManager userManager;
    private LoginGroup loginGroup;
    private String email="";
    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄Q
     */
    public LoginScene(Main gameMain)
    {
        super(gameMain);
        this.userManager=gameMain.getUserManager();

    }

    public LoginScene(Main gameMain,String email)
    {
        super(gameMain);
        this.userManager=gameMain.getUserManager();
        if(email==null) return;
        this.email=email;

    }
    public void login(String email, String password)
    {
        userManager.login(email, password);
    }
    public void loginSucceed()
    {
        //UserInfo userInfo=userManager.getActiveUser();
        //gameMain.getScreenManager().setScreen(new StartScene(gameMain));
        Logger.info("Login Succeed");
    }
    public void loginFail()
    {
        Logger.info("Login Fail");
    }
    @Override
    public void init()
    {
        super.init();
        stage=new Stage();
        loginGroup=new LoginGroup(email);
        loginGroup.setPosition(700,300);
        stage.addActor(loginGroup);

        Button.ButtonStyle buttonStyle=new Button.ButtonStyle();
        buttonStyle.up=new TextureRegionDrawable(new Texture("login.png"));
        buttonStyle.down=new TextureRegionDrawable(new Texture("login.png"));

        Button loginButton=new Button(buttonStyle);
        loginButton.setPosition(850,300);
        loginButton.setTransform(true);
        loginButton.setScale(0.3f);
        loginButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if(!(loginGroup.getPasswordTextField().getText().isEmpty() || loginGroup.getUsernameTextField().getText().isEmpty()))
                {
                    login(loginGroup.getUsernameTextField().getText(),loginGroup.getPasswordTextField().getText());
                    Logger.debug("Login"+loginGroup.getUsernameTextField().getText()+loginGroup.getPasswordTextField().getText());
                }
            }
        });
        stage.addActor(loginButton);

        //Gdx.input.setInputProcessor(stage);
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
    public void render(float delta)
    {
        ScreenUtils.clear(0,0,0,1);
        super.render(delta);
    }
    @Override
    public void logic(float delta)
    {

    }
}
