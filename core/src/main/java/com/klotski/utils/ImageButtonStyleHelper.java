package com.klotski.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ImageButtonStyleHelper
{
    // 基于单张图片创建ImageButton样式
    public static ImageButton.ImageButtonStyle createFromTexture(Texture texture) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();

        // 正常状态
        TextureRegionDrawable normal = new TextureRegionDrawable(new TextureRegion(texture));
        style.imageUp = normal;

        // 按下状态 - 默认为原图的深色版本
        TextureRegionDrawable pressed = new TextureRegionDrawable(new TextureRegion(texture));
        pressed.tint(Color.DARK_GRAY);
        style.imageDown = pressed;

        // 悬停状态 - 默认为原图的浅色版本
        TextureRegionDrawable over = new TextureRegionDrawable(new TextureRegion(texture));
        over.tint(Color.LIGHT_GRAY);
        style.imageOver = over;

        // 选中状态 - 默认为原图的高亮版本
        TextureRegionDrawable checked = new TextureRegionDrawable(new TextureRegion(texture));
        checked.tint(Color.YELLOW);
        style.imageChecked = checked;

        // 禁用状态 - 默认为原图的半透明版本
        TextureRegionDrawable disabled = new TextureRegionDrawable(new TextureRegion(texture));
        disabled.tint(new Color(1, 1, 1, 0.5f));
        style.imageDisabled = disabled;

        return style;
    }
}
