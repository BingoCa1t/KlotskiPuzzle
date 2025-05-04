package com.klotski.Scene;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.klotski.Main;
import com.klotski.utils.logger.Logger;

import java.util.Stack;

/**
 * 场景栈切换管理器
 * @author Life_Checkpoint
 */
public class ScreenManager
{
    private KlotskiScene currentScreen;
    private Stack<KlotskiScene> screenStack;
    private Main gameMain;
    public ScreenManager(Main gameMain)
    {
        this.gameMain = gameMain;
        screenStack = new Stack<>();
    }

    /**
     * 场景进入并彻底清理后台场景
     * @param screen 进入场景
     */
    public void setScreenWithClear(KlotskiScene screen)
    {
        if (!screenStack.isEmpty()) clearScreenStack();
        setScreenWithoutSaving(screen);
    }

    /**
     * 场景进入，保持后台栈不变，不将当前场景压入后台
     * @param screen 进入场景
     */
    public void setScreenWithoutSaving(KlotskiScene screen)
    {
        if (currentScreen != null)
        {
            currentScreen.hide();
            currentScreen.dispose();
        }
        currentScreen = screen;
        currentScreen.show();
        gameMain.setScreen(currentScreen);
        for(Actor a : currentScreen.getStage().getActors())
        {
           // a.addAction(Actions.moveTo(10,10,2f));
           a.addAction(Actions.alpha(0,2f));
        }
        Logger.debug("ScreenManager", "Set screen -> " + currentScreen);
    }

    /**
     * 场景进入，将当前场景压入后台
     * @param screen 进入场景
     */
    public void setScreen(KlotskiScene screen) {
        if (currentScreen != null) {
            currentScreen.hide();
            screenStack.add(currentScreen);
        }
        currentScreen = screen;
        currentScreen.show();
        gameMain.setScreen(currentScreen);
        for(Actor a : currentScreen.getStage().getActors())
        {
            a.getColor().set(a.getColor(),0);
            a.addAction(Actions.alpha(1,0.4f));

        }
        Logger.debug("ScreenManager", "Set screen -> " + currentScreen);
    }

    /**
     * 返回上一个场景，当前场景被彻底销毁
     */
    public void returnPreviousScreen() {
        if (screenStack.isEmpty() || currentScreen == null) {
            Logger.error("ScreenManager", "The Previous / Current Screen is not exists");
            return;
        }
        currentScreen.hide();
        currentScreen.dispose();
        currentScreen = screenStack.pop();
        currentScreen.show();
        gameMain.setScreen(currentScreen);
        currentScreen.getStage().addAction(Actions.sequence(
            Actions.fadeIn(2f) ));// 2 秒内渐隐)
        Logger.debug("ScreenManager", "Set screen -> " + currentScreen);
    }

    public KlotskiScene getRootScreen() {
        return screenStack.get(0);
    }

    // 获取当前屏幕
    public KlotskiScene getCurrentScreen() {
        return currentScreen;
    }

    // 清理所有后台场景
    public void clearScreenStack() {
        for(Screen thisScreen : screenStack) thisScreen.dispose();
        screenStack.clear();
    }

    // 彻底清理所有场景
    public void dispose() {
        clearScreenStack();
        if (currentScreen != null) currentScreen.dispose();
    }

    // 执行场景渲染
    public void render() {
        if (currentScreen != null) {
            currentScreen.render(KlotskiScene.UPDATE_TIME_STEP);
        }
    }

    public Stack<KlotskiScene> getScreenStack() {
        return screenStack;
    }
}
