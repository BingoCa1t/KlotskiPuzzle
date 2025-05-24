package com.klotski.Scene;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klotski.Main;
import com.klotski.logic.ChessBoardControl;
import com.klotski.logic.Pos;
import com.klotski.polygon.Chess;
import com.klotski.polygon.ChessBoard;
import com.klotski.utils.logger.Logger;

public class ObstacleGameScene extends KlotskiScene
{
    private ChessBoardControl cbc;
    /**
     * 基类初始化，需要传入 gameMain
     *
     * @param gameMain 全局句柄
     */
    public ObstacleGameScene(Main gameMain)
    {
        super(gameMain);
    }
    @Override
    public void init()
    {
        super.init();
        cbc=new ChessBoardControl(gameMain);
        cbc.load(gameMain.getMapDataManager().getMapDataList().get(6));
        stage.addActor(cbc.getChessBoard());
        cbc.getChessBoard().setPosition(100,40);
        stage.addListener(new ChessBoardListener());
        cbc.getChessBoard().addListener(new MyInputListener());
    }
    @Override
    public void input()
    {

    }

    @Override
    public void draw(float delta)
    {
        stage.draw();
    }

    @Override
    public void logic(float delta)
    {

    }
    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render(delta);
    }
    /**
     * 鼠标选中棋子并移动
     */
    private class MyInputListener extends InputListener
    {
        private boolean isDragging = false;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
        {
            int xx = (int) (x / 160f);
            int yy = (int) (y / 160f);

            Chess c = cbc.getChess(new Pos(xx, yy));

            if (c == null)
            {

                Logger.debug("Selected chess is null: " + xx + " " + yy);
                if (cbc.getSelectingChess() != null && cbc.getSelectingChess().isMovable())
                {
                    Pos pp = new Pos(xx, yy);
                    if (cbc.getSelectingChess().getChessWidth() > 1 || cbc.getSelectingChess().getChessHeight() > 1)
                    {
                        pp.setX((pp.getX() - cbc.getSelectingChess().getPosition().getX() > 0) ? pp.getX() - cbc.getSelectingChess().getChessWidth() + 1 : pp.getX());
                        pp.setY((pp.getY() - cbc.getSelectingChess().getPosition().getY() > 0) ? pp.getY() - cbc.getSelectingChess().getChessHeight() + 1 : pp.getY());
                    }
                    cbc.move(cbc.getSelectingChess(), pp);
                    //stepLabel.setText(String.format("%02d", cbc.getSteps()));

                }
                return true;
            }
            if (c == cbc.getSelectingChess())
            {
                c.disSelect();
            }

            cbc.select(c);
            Logger.debug("Selected chess: " + c.getPosition().getX() + " " + c.getPosition().getY());
            //c.setSelected(true);
            return true;
        }

        @Override
        public boolean mouseMoved(InputEvent event, float x, float y)
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
    }
    /**
     * 提供给四个按钮的方法，向指定方向移动
     *
     * @param pos 单位方向的向量坐标
     */
    public void move(Pos pos)
    {
        if (cbc.getSelectingChess() != null)
        {
            Pos p = cbc.getSelectingChess().getPosition().add(pos);
            if (p.getX() < 0 || p.getY() < 0 || p.getX() > cbc.getBoardWidth() - 1 || p.getY() > cbc.getBoardHeight() - 1)
            {
                return;
            }
            cbc.move(cbc.getSelectingChess(), cbc.getSelectingChess().getPosition().add(pos));
           // stepLabel.setText(String.format("%02d", cbc.getSteps()));
        }
    }
    /**
     * 键盘控制棋子移动
     */
    private class ChessBoardListener extends InputListener
    {
        @Override
        public boolean keyDown(InputEvent event, int keycode)
        {
            // Logger.debug("keyDown: "+keycode);
            switch (keycode)
            {
                case Input.Keys.UP:
                    move(new Pos(0, 1));
                    break;
                case Input.Keys.DOWN:
                    move(new Pos(0, -1));
                    break;
                case Input.Keys.LEFT:
                    move(new Pos(-1, 0));
                    break;
                case Input.Keys.RIGHT:
                    move(new Pos(1, 0));
                    break;
                    case Input.Keys.W:
                        if(cbc.getSelectingChess()!=null)
                        {
                            cbc.getSelectingChess().explode();
                            //cbc.deleteChess(cbc.getSelectingChess());
                        }
                        break;
                default:
                    break;
            }
            return false;
        }
    }
}
