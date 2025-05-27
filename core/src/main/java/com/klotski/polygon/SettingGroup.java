package com.klotski.polygon;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.klotski.settings.SettingManager;
import com.klotski.utils.SmartBitmapFont;

/**
 * 设置面板组，包含图形和音频设置选项
 */
public class SettingGroup extends Group {
    private final Skin skin;
    private final CheckBox usingMipmapCheckBox;
    private final CheckBox vSyncCheckBox;
    private final ButtonGroup<CheckBox> msaaButtonGroup;
    private final Slider mainVolumeSlider;
    private final Slider musicVolumeSlider;
    private final Slider soundVolumeSlider;
    private final Label mainVolumeValueLabel;
    private final Label musicVolumeValueLabel;
    private final Label soundVolumeValueLabel;

    private SettingManager settingManager;

    // 存储设置值的变量
    private boolean usingMipmap ;
    private boolean vSync ;
    private int msaaLevel = 0;
    private float mainVolume = 0.8f;
    private float musicVolume = 0.8f;
    private float soundVolume = 0.8f;

    private static final float PADDING = 10;
    private static final float LABEL_WIDTH = 150;
    private static final float CONTROL_WIDTH = 400;
    private static final float VALUE_LABEL_WIDTH = 60;
    private static final float ROW_HEIGHT = 100;
    private static final float START_X = 500;
    private static final float START_Y = 500;
    Label usingMipmapLabel;
    Label vSyncLabel;
    Label msaaLabel;
    Label mainVolumeLabel;
    Label musicVolumeLabel;
    Label soundVolumeLabel;

    public SettingGroup(SettingManager settingManager)
    {
        // 创建自定义皮肤
        this.skin = createCustomSkin();
        usingMipmap=settingManager.gameSettings.graphics.isMipmap();
        vSync=settingManager.gameSettings.graphics.isVsync();
        msaaLevel=settingManager.gameSettings.graphics.msaa;
        mainVolume=settingManager.gameSettings.sound.masterVolume;
        musicVolume=settingManager.gameSettings.sound.musicVolume;
        soundVolume=settingManager.gameSettings.sound.effectsVolume;

        // 创建使用Mipmap选项

        usingMipmapLabel = new Label("Using Mipmap", skin);
        usingMipmapCheckBox = new CheckBox("", skin);
        usingMipmapCheckBox.setChecked(usingMipmap);
        usingMipmapCheckBox.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                settingManager.gameSettings.graphics.mipmap=usingMipmapCheckBox.isChecked();
            }
        });

        // 创建vSync选项

        vSyncLabel = new Label("vSync", skin);
        vSyncCheckBox = new CheckBox("", skin);
        vSyncCheckBox.setChecked(vSync);
        vSyncCheckBox.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                settingManager.gameSettings.graphics.vsync=vSyncCheckBox.isChecked();
            }
        });

        // 创建MSAA选项（使用CheckBox和ButtonGroup实现单选功能）

        msaaLabel = new Label("MSAA", skin);
        msaaButtonGroup = new ButtonGroup<>();
        msaaButtonGroup.setMaxCheckCount(1);
        msaaButtonGroup.setMinCheckCount(1);
        msaaButtonGroup.setUncheckLast(true);


        CheckBox msaa0Button = new CheckBox("0", skin);
        CheckBox msaa2Button = new CheckBox("2", skin);
        CheckBox msaa4Button = new CheckBox("4", skin);
        CheckBox msaa8Button = new CheckBox("8", skin);
        CheckBox msaa16Button = new CheckBox("16", skin);

        msaaButtonGroup.add(msaa0Button, msaa2Button, msaa4Button, msaa8Button, msaa16Button);
        msaa8Button.setChecked(true); // 默认选择0


        // 为MSAA按钮添加监听器
        ChangeListener msaaListener = new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                if (msaa0Button.isChecked()) msaaLevel = 0;
                else if (msaa2Button.isChecked()) msaaLevel = 2;
                else if (msaa4Button.isChecked()) msaaLevel = 4;
                else if (msaa8Button.isChecked()) msaaLevel = 8;
                else if (msaa16Button.isChecked()) msaaLevel = 16;

                settingManager.gameSettings.graphics.msaa=msaaLevel;
            }
        };

        msaa0Button.addListener(msaaListener);
        msaa2Button.addListener(msaaListener);
        msaa4Button.addListener(msaaListener);
        msaa8Button.addListener(msaaListener);
        msaa16Button.addListener(msaaListener);

        // 创建主音量滑动条

        mainVolumeLabel = new Label("Main Volume", skin);
        mainVolumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        mainVolumeSlider.setWidth(600);
        mainVolumeSlider.setValue(mainVolume);
        mainVolumeValueLabel = new Label(String.format("%.0f%%", mainVolume * 100), skin);

        mainVolumeSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                mainVolume = mainVolumeSlider.getValue();
                mainVolumeValueLabel.setText(String.format("%.0f%%", mainVolume * 100));
                settingManager.gameSettings.sound.masterVolume=mainVolume;
            }
        });

        // 创建音乐音量滑动条

        musicVolumeLabel = new Label("Music Volume", skin);
        musicVolumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        musicVolumeSlider.setWidth(600);
        musicVolumeSlider.setValue(musicVolume);
        musicVolumeValueLabel = new Label(String.format("%.0f%%", musicVolume * 100), skin);

        musicVolumeSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                musicVolume = musicVolumeSlider.getValue();
                musicVolumeValueLabel.setText(String.format("%.0f%%", musicVolume * 100));
                settingManager.gameSettings.sound.musicVolume=musicVolume;
            }
        });

        // 创建音效音量滑动条

        soundVolumeLabel = new Label("Sound Volume", skin);
        soundVolumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        soundVolumeSlider.setWidth(600);
        soundVolumeSlider.setValue(soundVolume);
        soundVolumeValueLabel = new Label(String.format("%.0f%%", soundVolume * 100), skin);

        soundVolumeSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                soundVolume = soundVolumeSlider.getValue();
                soundVolumeValueLabel.setText(String.format("%.0f%%", soundVolume * 100));
                settingManager.gameSettings.sound.effectsVolume=soundVolume;
            }
        });

        positionControls(msaa0Button, msaa2Button, msaa4Button, msaa8Button, msaa16Button);

        // 添加所有控件到组
        addActor(usingMipmapLabel);
        addActor(usingMipmapCheckBox);
        addActor(vSyncLabel);
        addActor(vSyncCheckBox);
        addActor(msaaLabel);
        addActor(msaa0Button);
        addActor(msaa2Button);
        addActor(msaa4Button);
        addActor(msaa8Button);
        addActor(msaa16Button);
        addActor(mainVolumeLabel);
        addActor(mainVolumeSlider);
        addActor(mainVolumeValueLabel);
        addActor(musicVolumeLabel);
        addActor(musicVolumeSlider);
        addActor(musicVolumeValueLabel);
        addActor(soundVolumeLabel);
        addActor(soundVolumeSlider);
        addActor(soundVolumeValueLabel);

        // 设置面板大小和位置
        setSize(600, 400);
        setPosition((1920 - getWidth()) / 2, (1080 - getHeight()) / 2);
    }

    /**
     * 创建自定义皮肤，不使用uiskin.json
     */
    private Skin createCustomSkin() {
        Skin skin = new Skin();

        // 创建字体

        BitmapFont font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")),60);
        skin.add("default", font);

        // 创建白色纹理用于UI元素
        Texture whiteTexture = new Texture(Gdx.files.internal("gameMainScene/white.png"));
        skin.add("white", whiteTexture);

        // 创建复选框样式
        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.font = font;

        Pixmap offPixmap = new Pixmap(30, 30, Pixmap.Format.RGBA8888);
        offPixmap.setColor(0, 0, 0, 0); // 透明颜色
        offPixmap.fill(); // 填充透明背景
        offPixmap.setColor(1, 1, 1, 1); // 白色
        offPixmap.drawRectangle(0, 0, 30, 30); // 绘制白色边框
        Texture offTexture = new Texture(offPixmap);
        offPixmap.dispose();

        TextureRegionDrawable offDrawable = new TextureRegionDrawable(new TextureRegion(offTexture));
        offDrawable.setMinWidth(30);
        offDrawable.setMinHeight(30);
        checkBoxStyle.checkboxOff = offDrawable;

        // 选中状态 - 白色边框 + 白色矩形
        Pixmap onPixmap = new Pixmap(30, 30, Pixmap.Format.RGBA8888);
        onPixmap.setColor(Color.WHITE);
        onPixmap.drawRectangle(0, 0, 30, 30); // 外边框

        // 绘制白色小矩形
        onPixmap.fillRectangle(8, 8, 16, 16); // 居中的16x16白色矩形

        Texture onTexture = new Texture(onPixmap);
        onPixmap.dispose();

        TextureRegionDrawable onDrawable = new TextureRegionDrawable(new TextureRegion(onTexture));
        checkBoxStyle.checkboxOn = onDrawable;
        skin.add("default", checkBoxStyle);

        // 创建滑动条样式
        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = new TextureRegionDrawable(new TextureRegion(whiteTexture));
        sliderStyle.background.setMinWidth(500);
        sliderStyle.background.setMinHeight(10);
        sliderStyle.knob = new TextureRegionDrawable(new TextureRegion(whiteTexture));
        sliderStyle.knob.setMinWidth(20);
        sliderStyle.knob.setMinHeight(60);
        skin.add("default-horizontal", sliderStyle);

        // 创建标签样式
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);

        return skin;
    }

    /**
     * 获取当前设置
     */
    public boolean isUsingMipmap() {
        return usingMipmap;
    }

    public boolean isVSyncEnabled() {
        return vSync;
    }

    public int getMsaaLevel() {
        return msaaLevel;
    }

    public float getMainVolume() {
        return mainVolume;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    /**
     * 释放资源
     */
    public void dispose() {
        skin.dispose();
    }
    private void positionControls(CheckBox msaa0Button, CheckBox msaa2Button,
                                  CheckBox msaa4Button, CheckBox msaa8Button, CheckBox msaa16Button)
        {
        float currentY = START_Y;

        // 设置Using Mipmap控件位置
        usingMipmapLabel.setPosition(START_X, currentY, Align.right);
        usingMipmapCheckBox.setPosition(START_X + LABEL_WIDTH + PADDING, currentY, Align.left);
        currentY -= ROW_HEIGHT;

        // 设置vSync控件位置
        vSyncLabel.setPosition(START_X, currentY, Align.right);
        vSyncCheckBox.setPosition(START_X + LABEL_WIDTH + PADDING, currentY, Align.left);
        currentY -= ROW_HEIGHT;

        // 设置MSAA控件位置
        msaaLabel.setPosition(START_X, currentY, Align.right);
        float msaaX = START_X + LABEL_WIDTH + PADDING;
        msaa0Button.setPosition(msaaX, currentY, Align.left);
        msaa2Button.setPosition(msaaX + 80, currentY, Align.left);
        msaa4Button.setPosition(msaaX + 210, currentY, Align.left);
        msaa8Button.setPosition(msaaX + 340, currentY, Align.left);
        msaa16Button.setPosition(msaaX + 470, currentY, Align.left);
        currentY -= ROW_HEIGHT;

        // 设置主音量控件位置
        mainVolumeLabel.setPosition(START_X, currentY, Align.right);
        mainVolumeSlider.setPosition(START_X + LABEL_WIDTH + PADDING, currentY, Align.left);
        mainVolumeValueLabel.setPosition(START_X + LABEL_WIDTH + PADDING + CONTROL_WIDTH + PADDING+200,
            currentY, Align.left);
        currentY -= ROW_HEIGHT;

        // 设置音乐音量控件位置
        musicVolumeLabel.setPosition(START_X, currentY, Align.right);
        musicVolumeSlider.setPosition(START_X + LABEL_WIDTH + PADDING, currentY, Align.left);
        musicVolumeValueLabel.setPosition(START_X + LABEL_WIDTH + PADDING + CONTROL_WIDTH + PADDING+200,
            currentY, Align.left);
        currentY -= ROW_HEIGHT;

        // 设置音效音量控件位置
        soundVolumeLabel.setPosition(START_X, currentY, Align.right);
        soundVolumeSlider.setPosition(START_X + LABEL_WIDTH + PADDING, currentY, Align.left);
        soundVolumeValueLabel.setPosition(START_X + LABEL_WIDTH + PADDING + CONTROL_WIDTH + PADDING+200,
            currentY, Align.left);
    }
}
