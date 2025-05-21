package com.klotski.polygon;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * 回放时的控制按钮
 * @author BingoCAT
 */
public class PlayBackGroup extends Group
{
    /*
播放：开始定时任务，点击后图标切换为”暂停“，同时禁用其他按钮
暂停：暂停定时任务，点击后图标切换为“播放”，同时启用其他按钮
向左：后退一步
向右：前进一步
快进：到达终局
快退：到达开始处
 */
    private ImageButton backButton;
    private ImageButton backKButton;
    private ImageButton nextButton;
    private ImageButton nextTButton;
    private ImageButton playButton;
    private Drawable pauseDrawable;
    public PlayBackGroup(Drawable play,Drawable back,Drawable backK,Drawable next,Drawable nextT,Drawable pause)
    {
        super();
        backButton=new ImageButton(back);
        backKButton=new ImageButton(backK);
        nextButton=new ImageButton(next);
        nextTButton=new ImageButton(nextT);
        playButton=new ImageButton(play);
        pauseDrawable=pause;
    }
    public void setBackButtonClick(ClickListener listener)
    {
        backButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent e, float x, float y)
            {
                listener.clicked(e,x,y);

            }
        });
    }
}
