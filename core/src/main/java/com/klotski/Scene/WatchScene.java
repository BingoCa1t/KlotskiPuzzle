package com.klotski.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fasterxml.jackson.core.type.TypeReference;
import com.klotski.Main;
import com.klotski.archive.LevelArchive;
import com.klotski.network.MessageCode;
import com.klotski.network.NetworkMessageObserver;
import com.klotski.utils.SmartBitmapFont;
import com.klotski.utils.json.JsonManager;
import com.klotski.utils.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class WatchScene extends KlotskiScene implements NetworkMessageObserver
{
    //不结构化了，直接String
    private ArrayList<String> onlineUsers;
    private JsonManager jsonManager;
    private Map<String,LevelArchive> userArchives;
    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄
     */
    public WatchScene(Main gameMain)
    {
        super(gameMain);
        jsonManager = new JsonManager();
        stage = new Stage();
        userArchives = new ConcurrentHashMap<String, LevelArchive>();
        /*
        /*
        Timer.schedule(new Timer.Task()
        {
            @Override
            public void run()
            {
                //向服务端请求在线列表
                gameMain.getNetManager().sendMessage("0020|");
            }
        }, 0, 5);


        //ScrollPane scrollPane=new ScrollPane();
       // stage = new Stage(new ScreenViewport());
        //Gdx.input.setInputProcessor(stage);

        // 创建默认皮肤

        // 创建表格
        Table table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.topLeft);
        table.pad(10);

        // 添加表头
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font=new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")),30);
        labelStyle.fontColor=Color.WHITE;
        table.add(new Label("用户名",labelStyle)).width(150).pad(5);
        table.add(new Label("邮箱", labelStyle)).width(250).pad(5);
        table.add(new Label("在线状态", labelStyle)).width(100).pad(5);
        table.row();

        // 添加分隔线
        Table separator = new Table();
        separator.setBackground(new TextureRegionDrawable(new Texture("startScene/startBackGround.png")));
        table.add(separator).height(1).colspan(3).fillX().padBottom(5);
        table.row();

        // 添加示例数据
        for (int i = 1; i <= 20; i++) {
            table.add(new Label("用户" + i, labelStyle)).pad(5);
            table.add(new Label("user" + i + "@example.com", labelStyle)).pad(5);

            // 添加在线状态按钮
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")),30);
            textButtonStyle.fontColor=Color.BLACK;
            TextButton statusBtn = new TextButton(i % 3 == 0 ? "离线" : "在线",textButtonStyle);
            statusBtn.setColor(i % 3 == 0 ? 1f : 0f, i % 3 == 0 ? 0f : 1f, 0f, 1f);
            final int index = i;
            statusBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                    TextButton btn = (TextButton) actor;
                    if (btn.getText().toString().equals("在线")) {
                        btn.setText("离线");
                        btn.setColor(1f, 0f, 0f, 1f);
                    } else {
                        btn.setText("在线");
                        btn.setColor(0f, 1f, 0f, 1f);
                    }
                    Gdx.app.log("状态变更", "用户" + index + "状态变更为: " + btn.getText());
                }
            });
            table.add(statusBtn).pad(5);

            table.row();
        }
        table.setSize(stage.getWidth(), stage.getHeight());
        // 创建滚动面板
        ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle();
        scrollStyle.background=new TextureRegionDrawable(new Texture("startScene\\startBackGround.png"));
        scrollStyle.hScroll=new TextureRegionDrawable(new Texture("startScene\\startBackGround.png"));
        scrollStyle.vScroll=new TextureRegionDrawable(new Texture("startScene\\startBackGround.png"));
        scrollStyle.corner=new TextureRegionDrawable(new Texture("startScene\\startBackGround.png"));

        ScrollPane scrollPane = new ScrollPane(table, scrollStyle);

        scrollPane.setSize(1920,1080);

        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFillParent(true);
        Table container = new Table();
        container.setFillParent(true);
        container.add(scrollPane).expand();
        // 将滚动面板添加到舞台
        stage.addActor(container);
        */

        //stage.addActor(table);
        //Logger.debug("WatchScene", "舞台初始化完成，宽度: " + stage.getWidth() + " 高度: " + stage.getHeight());
    }
    Table dataTable;
    Label.LabelStyle labelStyle;
    @Override
    public void init()
    {
        // 创建皮肤
        super.init();
        Timer.schedule(new Timer.Task()
        {
            @Override
            public void run()
            {
                //向服务端请求在线列表
                gameMain.getNetManager().sendMessage("0020|");
            }
        }, 0, 5);


        //ScrollPane scrollPane=new ScrollPane();
        // stage = new Stage(new ScreenViewport());
        //Gdx.input.setInputProcessor(stage);

        // 创建默认皮肤

        // 创建表格
        // 设置带有视口的舞台
        //stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // 创建主容器
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        // 创建表头表格
        Table headerTable = new Table();
        headerTable.align(Align.left);
        headerTable.pad(10);

        // 创建数据表格

        dataTable = new Table();
        dataTable.align(Align.topLeft);
        dataTable.pad(10);

        // 定义标签样式

        labelStyle = new Label.LabelStyle();
        labelStyle.font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")), 60);
        labelStyle.fontColor = Color.WHITE;

        // 添加表头
        headerTable.add(new Label("用户名", labelStyle)).width(150).pad(5);
        headerTable.add(new Label("邮箱", labelStyle)).width(250).pad(5);
        headerTable.add(new Label("在线状态", labelStyle)).width(100).pad(5);
        headerTable.row();

        // 添加分隔线到表头
        Table separator = new Table();
        separator.setBackground(new TextureRegionDrawable(new Texture("startScene/startBackGround.png")));
        headerTable.add(separator).height(1).colspan(3).fillX().padBottom(5);
        headerTable.row();

        /*
        // 复制表头到数据表格（仅用于布局对齐）
        dataTable.add(new Label("用户名", labelStyle)).width(150).pad(5);
        dataTable.add(new Label("邮箱", labelStyle)).width(250).pad(5);
        dataTable.add(new Label("在线状态", labelStyle)).width(100).pad(5);
        dataTable.row();

         */

        // 添加分隔线到数据表格
        /*
        Table dataSeparator = new Table();
        dataSeparator.setBackground(new TextureRegionDrawable(new Texture("startScene/startBackGround.png")));
        dataTable.add(dataSeparator).height(1).colspan(3).fillX().padBottom(5);
        dataTable.row();

         */

        // 添加示例数据
        for (int i = 1; i <= 20; i++)
        {

        }

        // 创建滚动面板，只包含数据表格
        ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle();
       // scrollStyle.background = new TextureRegionDrawable(new Texture("startScene/startBackGround.png"));

        ScrollPane scrollPane = new ScrollPane(dataTable, scrollStyle);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFillParent(false); // 不要填充整个父容器

        // 将表头和滚动面板添加到主表格
        mainTable.add(headerTable).fillX().row();
        mainTable.add(scrollPane).expand().fill();

        // 将主表格添加到舞台
        stage.addActor(mainTable);
        mainTable.setFillParent(false);
        mainTable.setPosition(150,100);
        mainTable.setSize(1600,800);

        // 注册场景变更监听器
       // stage.addActor(scrollPane);
    }

    @Override
    public void input()
    {

    }

    @Override
    public void draw(float delta)
    {
        //stage.act(delta);
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        stage.draw();
    }

    @Override
    public void logic(float delta)
    {

    }

    @Override
    public void update(MessageCode code, String message)
    {
        if (code == MessageCode.OnlineList)
        {
            TypeReference<ArrayList<String>> ref = new TypeReference<ArrayList<String>>()
            {
            };
            onlineUsers = jsonManager.parseJsonToObject(message, ref);
            if(dataTable == null) dataTable = new Table();
            dataTable.clear();

            //添加到表格
            //添加单击事件

        }
        JsonManager j=new JsonManager();
        if(code==MessageCode.UpdateWatch)
        {
            String[] str=message.split(Pattern.quote("|"));
            levelArchive=j.parseJsonToObject(str[1],LevelArchive.class);
        }
        if (code == MessageCode.ToWatch1)
        {
            LevelArchive levelArchive = jsonManager.parseJsonToObject(message, LevelArchive.class);
            gameMain.getScreenManager().setScreen(new GameMainScene(gameMain, levelArchive, true, ""));
        }



    }
    LevelArchive levelArchive;
    private void addData(java.util.List<String> infos)
    {
        for(String info : infos)
        {
            //list.add(entry.getKey()+"|"+entry.getValue().name+"|"+status)
            String[] i=info.split(Pattern.quote("|"));
            dataTable.add(new Label(i[1], labelStyle)).pad(5);
            dataTable.add(new Label(i[0], labelStyle)).pad(5);
            // 添加在线状态按钮
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = new SmartBitmapFont(new FreeTypeFontGenerator(Gdx.files.internal("STZHONGS.TTF")), 30);
            textButtonStyle.fontColor = Color.WHITE;

            TextButton statusBtn;
            switch(i[2])
            {
                case "0":
                    statusBtn=new TextButton("Online", textButtonStyle);
                    break;
                    case "1":
                        statusBtn=new TextButton("Watching", textButtonStyle);
                        break;
                        case "2":
                            statusBtn=new TextButton("Playing", textButtonStyle);
                            statusBtn.addListener(new ClickListener()
                            {
                                @Override
                                public void clicked(InputEvent e, float x, float y)
                                {

                                }
                            });
                            break;
                            default:
                                statusBtn=new TextButton("Online", textButtonStyle);
                                break;
            }
            //statusBtn.setColor(i % 3 == 0 ? 1f : 0f, i % 3 == 0 ? 0f : 1f, 0f, 1f);



            dataTable.add(statusBtn).pad(5);
            dataTable.row();
        }

    }
}
