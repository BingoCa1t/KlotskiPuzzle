package com.klotski.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * 从单个纹理自动生成 ImageButtonStyle 的工具类
 */
public class ImageButtonStyleHelper
{


    public static Button.ButtonStyle createButtonStyle(Texture texture)
    {
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(texture));

        // 正常状态
        TextureRegionDrawable upDrawable = drawable;

        // 按下状态 - 使用缩放的纹理
        TextureRegionDrawable downDrawable = createScaledDrawable(drawable, 0.95f);

        // 悬停状态 - 使用着色的纹理
        TextureRegionDrawable overDrawable = createTintedDrawable(drawable, new Color(0.9f, 0.9f, 0.9f, 1f));

        // 禁用状态 - 使用着色的纹理
        TextureRegionDrawable disabledDrawable = createTintedDrawable(drawable, new Color(0.5f, 0.5f, 0.5f, 0.7f));

        // 创建并配置样式
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = upDrawable;
        style.down = downDrawable;
        style.over = overDrawable;
        style.disabled = disabledDrawable;

        // 设置图片样式
        style.imageUp = upDrawable;
        style.imageDown = downDrawable;
        style.imageOver = overDrawable;
        style.imageDisabled = disabledDrawable;

        return style;
    }
    /**
     * 从单个纹理创建 ImageButtonStyle
     *
     * @param texture         基础纹理
     * @param scaleDownFactor 按下状态缩放因子
     * @param hoverTint       悬停状态色调
     * @param disabledTint    禁用状态色调
     * @return 生成的 ImageButtonStyle
     */

    public static ImageButton.ImageButtonStyle createStyleFromTexture(
        Texture texture,
        float scaleDownFactor,
        Color hoverTint,
        Color disabledTint
    )
    {
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(texture));

        // 正常状态
        TextureRegionDrawable upDrawable = drawable;

        // 按下状态 - 使用缩放的纹理
        TextureRegionDrawable downDrawable = createScaledDrawable(drawable, scaleDownFactor);

        // 悬停状态 - 使用着色的纹理
        TextureRegionDrawable overDrawable = createTintedDrawable(drawable, hoverTint);

        // 禁用状态 - 使用着色的纹理
        TextureRegionDrawable disabledDrawable = createTintedDrawable(drawable, disabledTint);

        // 创建并配置样式
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = upDrawable;
        style.down = downDrawable;
        style.over = overDrawable;
        style.disabled = disabledDrawable;

        // 设置图片样式
        style.imageUp = upDrawable;
        style.imageDown = downDrawable;
        style.imageOver = overDrawable;
        style.imageDisabled = disabledDrawable;

        return style;
    }

    /**
     * 创建缩放后的 Drawable
     */
    private static TextureRegionDrawable createScaledDrawable(
        TextureRegionDrawable original,
        float scaleFactor
    )
    {
        TextureRegion region = original.getRegion();
        int scaledWidth = (int) (region.getRegionWidth() * scaleFactor);
        int scaledHeight = (int) (region.getRegionHeight() * scaleFactor);
        int offsetX = (region.getRegionWidth() - scaledWidth) / 2;
        int offsetY = (region.getRegionHeight() - scaledHeight) / 2;

        TextureRegion scaledRegion = new TextureRegion(
            region.getTexture(),
            region.getRegionX() + offsetX,
            region.getRegionY() + offsetY,
            scaledWidth,
            scaledHeight
        );

        return new TextureRegionDrawable(scaledRegion);
    }

    /**
     * 创建着色后的 Drawable
     */
    private static TextureRegionDrawable createTintedDrawable(
        TextureRegionDrawable original,
        Color tint
    )
    {
        // 创建一个新的 TextureRegionDrawable 实例
        TextureRegionDrawable copy = new TextureRegionDrawable(original);

        TintedDrawable tintedDrawable = new TintedDrawable(copy, tint);
        return tintedDrawable;
    }

    /**
     * 内部类：用于应用色调的 Drawable
     */
    private static class TintedDrawable extends TextureRegionDrawable
    {
        private final Color tint;

        public TintedDrawable(TextureRegionDrawable drawable, Color tint)
        {
            super(drawable);
            this.tint = new Color(tint);
        }

        @Override
        public void draw(Batch batch, float x, float y, float width, float height)
        {
            Color oldColor = batch.getColor().cpy();
            batch.setColor(tint);
            super.draw(batch, x, y, width, height);
            batch.setColor(oldColor);
        }
    }
}
