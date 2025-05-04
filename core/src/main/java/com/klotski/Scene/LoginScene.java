package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.klotski.Main;
import com.klotski.polygon.LoginGroup;
import com.klotski.user.UserManager;

public class LoginScene extends KlotskiScene
{
    private UserManager userManager;
    private LoginGroup loginGroup;
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
    public void login()
    {
        userManager.login(null,null);
    }
    public void loginSucceed()
    {

    }
    public void loginFail()
    {

    }
    @Override
    public void init()
    {
        super.init();
        stage=new Stage();
        loginGroup=new LoginGroup();
        loginGroup.setPosition(700,300);
        stage.addActor(loginGroup);
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void input()
    {

    }

    @Override
    public void draw(float delta)
    {

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,0,1);
        stage.act(delta);
        stage.draw();
    }
    @Override
    public void logic(float delta)
    {

    }
}
