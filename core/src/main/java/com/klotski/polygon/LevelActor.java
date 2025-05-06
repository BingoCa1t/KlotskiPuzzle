package com.klotski.polygon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.klotski.logic.LevelInfo;
import com.klotski.user.UserInfo;
import com.klotski.utils.logger.Logger;

import java.nio.ByteBuffer;

public class LevelActor extends Actor
{
    private TextureRegion region;
    private int levelID;
    private LevelInfo levelInfo;
    private int stars=0;
    private boolean isOpen;
    //private Button button;
    private int order;
    public int getLevelID()
    {
        return levelID;
    }

    public void setLevelID(int levelID)
    {
        this.levelID = levelID;
    }

    public LevelInfo getLevelInfo()
    {
        return levelInfo;
    }

    public LevelActor(LevelInfo levelInfo, boolean isOpen, int order)
    {
        super();
        Button.ButtonStyle style = new Button.ButtonStyle();
        if(isOpen)
        {
            style.up=new TextureRegionDrawable(new Texture(Gdx.files.internal("level"+String.valueOf(order)+".png")));
            Pixmap pixmap = new Pixmap(Gdx.files.internal("level1.png"));
            Pixmap p1=createPressedPixmap(pixmap);
            style.down=new TextureRegionDrawable(new Texture(p1));
        }
        else{
            style.up=new TextureRegionDrawable(new Texture(Gdx.files.internal("levelclosed.png")));
            Pixmap pixmap = new Pixmap(Gdx.files.internal("levelclosed.png"));
            Pixmap p1=createPressedPixmap(pixmap);
            style.down=new TextureRegionDrawable(new Texture(p1));
        }
        this.order=order;

        //this.button=new Button(style);
        this.levelInfo = levelInfo;
        this.levelID=levelInfo.getLevelID();
        this.stars=levelInfo.getStars();
        this.isOpen=isOpen;
        if(!isOpen)
        {
            region = new TextureRegion(new Texture(Gdx.files.internal("levelclosed.png")));
        }
        else {
            region = new TextureRegion(new Texture(Gdx.files.internal("level"+String.valueOf(order)+".png")));
        }
        //将图片素材按照棋子大小缩放
        setScale(140f / region.getRegionWidth(), 140f / this.region.getRegionHeight());

        //这里的size是图片素材的大小，并非棋子显示的大小，棋子显示大小=图片素材大小*缩放比
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }
    public void enter()
    {

    }

    private Pixmap createPressedPixmap(Pixmap normalPixmap) {
        Pixmap pressedPixmap = new Pixmap(normalPixmap.getWidth(), normalPixmap.getHeight(), normalPixmap.getFormat());
        for (int x = 0; x < normalPixmap.getWidth(); x++) {
            for (int y = 0; y < normalPixmap.getHeight(); y++) {
                int color = normalPixmap.getPixel(x, y);
                int r = (color >> 24) & 0xff;
                int g = (color >> 16) & 0xff;
                int b = (color >> 8) & 0xff;
                int a = color & 0xff;

                // 颜色加深
                r = (int) (r * 0.8);
                g = (int) (g * 0.8);
                b = (int) (b * 0.8);

                int newColor = (r << 24) | (g << 16) | (b << 8) | a;
                pressedPixmap.drawPixel(x, y, newColor);
            }
        }
        return pressedPixmap;
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
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
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
