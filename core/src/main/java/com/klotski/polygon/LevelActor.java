package com.klotski.polygon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.klotski.logic.LevelInfo;
import com.klotski.user.UserInfo;

public class LevelActor extends Actor
{
    private TextureRegion region;
    private int levelID;
    private LevelInfo levelInfo;
    private int stars=0;
    private boolean isOpen;
    public LevelActor(LevelInfo levelInfo,boolean isOpen,int order)
    {
        super();
        this.levelInfo = levelInfo;
        this.levelID=levelInfo.getLevelID();
        this.stars=levelInfo.getStars();
        this.isOpen=isOpen;
        region=new TextureRegion(new Texture(Gdx.files.internal("levelclosed.png")));
        //将图片素材按照棋子大小缩放
        setScale(140f / region.getRegionWidth(), 140f / this.region.getRegionHeight());
        //这里的size是图片素材的大小，并非棋子显示的大小，棋子显示大小=图片素材大小*缩放比
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }
    public void enter()
    {

    }
    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        // 如果 region 为 null 或者 演员不可见, 则直接不绘制
        if (region == null || !isVisible())
        {
            return;
        }
        super.draw(batch, parentAlpha);

        /*
         * 绘制纹理区域
         * 将演员中的 位置(position, 即 X, Y 坐标), 缩放和旋转支点(origin), 宽高尺寸, 缩放比, 旋转角度 应用到绘制中,
         * 最终 batch 会将综合结果绘制到屏幕上
         */
        batch.setColor(1.0f, 1.0f, 1.0f, 1f);
        batch.draw(
            region,
            getX(), getY(),
            getOriginX(), getOriginY(),
            getWidth(), getHeight(),
            getScaleX(), getScaleY(),
            getRotation()
        );
    }
}
