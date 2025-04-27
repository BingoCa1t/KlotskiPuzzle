package com.klotski;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klotski.assets.AssetsPathManager;
import com.klotski.logger.Logger;
import com.klotski.logic.ChessBoardControl;
import com.klotski.logic.Pos;
import com.klotski.polygon.Chess;
import com.klotski.polygon.ChessBoard;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter
{
    private SpriteBatch batch;
    private Texture image;
    private AssetsPathManager assetsPathManager;
    Chess c1;
    Chess c2;
    Chess c3;
    Chess c4;
    ChessBoard cb2;
    Sprite t1, t2, t3, t4;
    ChessBoard cb;
    Stage stage;
    ChessBoardControl cbc = new ChessBoardControl();
    public AssetsPathManager getAssetsPathManager()
    {
        return assetsPathManager;
    }

    @Override
    public void create()
    {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        /*
        t1 = new Sprite(new Texture("1.png"));
        t2= new Sprite(new Texture("2.png"));
        t3 = new Sprite(new Texture("3.png"));
        t4= new Sprite(new Texture("4.png"));
        t1.setOrigin(0, 0);
        //t1.setSize(160f,160f);

        c1 = new Chess(t1, "曹操", 2, 2);
        c1.setXY(new Pos(0,0));
        c2 = new Chess(t2, "关羽", 2, 1);
        c2.setXY(new Pos(0, 2));
        c3 = new Chess(t3, "张飞", 1, 2);
        c3.setXY(new Pos(2, 0));
        c4 = new Chess(t4, "赵云", 1, 2);
        c4.setXY(new Pos(3, 0));
        //c1.setPosition(20, 20);
        cb = new ChessBoard();
        cb.addChess(c1);
        cb.addChess(c2);
        cb.addChess(c3);
        cb.addChess(c4);

        cb.setPosition(10f, 100f);
        cb.setOrigin(0, 0);
        // 使用默认的构造方法创建舞台, 宽高默认为屏幕宽高
        stage = new Stage();

        // 将 演员 添加到舞台中, 由舞台去更新演员的逻辑和绘制
        stage.addActor(cb);
        cb.setPosition(10f, 100f);
        //TestLogger.Test();

         */

        cbc.load(null,null);

        cb2=cbc.getChessBoard();
        stage = new Stage();
        stage.addActor(cb2);

        /* 事件初始化 */

        // 首先必须注册输入处理器（stage）, 将输入的处理设置给 舞台（Stage 实现了 InputProcessor 接口）
        // 这样舞台才能接收到输入事件, 分发给相应的演员 或 自己处理。
        Gdx.input.setInputProcessor(stage);

        // 给舞台添加输入监听器（包括触屏, 鼠标点击, 键盘按键 的输入）
        //不要同时添加两个监听器，会带来不幸
        //stage.addListener(new MyInputListener());

        // 给演员添加一个 点击 监听器（只包括 手指点击 或 鼠标点击）
        cb2.addListener(new MyInputListener());

        // 只有需要监听输入的舞台/演员才需要添加监听器
        // 如果要移除指定监听器, 可以调用相应的 removeListener(listener) 方法
    }

    @Override
    public void render()
    {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        //cb.draw(batch,0.5f);
        //batch.draw(image, 140, 210);
        stage.act();
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        image.dispose();
    }

    private class SelectChessListener extends ClickListener
    {
        @Override
        public void clicked(InputEvent event, float x,float y)
        {

        }
    }
    private class MyInputListener extends InputListener
    {
        private boolean isDragging = false;
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
        {
            int xx=(int)(x/160f);
            int yy=(int)(y/160f);
            Chess c=cbc.getChess(new Pos(xx,yy));
            if (c == null)
            {
                Logger.debug("Selected chess is null: "+xx+" "+yy);
                if(cbc.getSelectingChess()!=null)
                {
                    cbc.move(cbc.getSelectingChess(),new Pos(xx,yy));
                }
                return true;
            }
            if(c==cbc.getSelectingChess())
            {

            }
            cbc.select(c);
            Logger.debug("Selected chess: "+c.getPosition().getX()+" "+c.getPosition().getY());
            //c.setSelected(true);
            return true;
        }
        @Override
        public boolean mouseMoved (InputEvent event, float x, float y)
        {
            //cbc.mouseMoved(new Pos((int)(x/160f),(int)(y/160f)));
            return true;
        }

        /*
        @Override
        public void touchDragged(InputEvent event,float x,float y,int pointer)
        {
            isDragging = true;
            Logger.debug("Touch Dragged: "+x+" "+y);
            int xx=(int)(x/160f);
            int yy=(int)(y/160f);
            cbc.dragged(new Pos(xx,yy));
        }

         */
        /**
         * 当有键盘按键被按下时调用, 参数 keycode 是被按下的按键的键值,
         * 所有键盘按键的键值常量定义在 com.badlogic.gdx.Input.Keys 类中
         */
        @Override
        public boolean keyDown(InputEvent event, int keycode)
        {
            switch (keycode)
            {
                case Input.Keys.UP:
                {
                    //Gdx.app.log(TAG, "被按下的按键: 方向上键");
                    cb2.move(cb2.getChess10(),new Pos(2,0));
                    System.out.println("移动棋子");
                    break;
                }

                default:
                {
                    //Gdx.app.log(TAG, "其他按键, KeyCode: " + keycode);
                    break;
                }
            }
            return false;
        }
    }
}
