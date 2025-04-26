package com.klotski.polygon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.klotski.logic.Pos;

/**
 * 棋子类，继承自Actor
 * @author BingoCAT
 */
public class Chess extends Actor
{

    //自定义里面的宽度、高度、坐标都是如（3,3）这样的，Actor基类本身的才是像素绘制的坐标
    private TextureRegion region;//绘制区域
    private Pos position;//棋子实时XY坐标，如(0,0))
    private String chessName;//棋子名字，暂时无用，以后也许做个性化的时候会用到
    private final int chessWidth;//棋子宽度，初始化之后不可改变
    private final int chessHeight;//棋子高度，初始化之后不可改变
    private final int DEFAULT_CHESS_WIDTH = 1;
    private final int DEFAULT_CHESS_HEIGHT = 1;
    //private ChessBoard chessBoard;
    //暂时保留X和Y坐标，之后将使用Pos类的position表示坐标
    private int chessX;//棋子左下角相对于棋盘左下角的X坐标
    private int chessY;//棋子左下角相对于棋盘左下角的Y坐标
    private final float squareHW = 160f;//棋盘一格为160像素，在这里统一管理大小，方便以后修改
    private boolean isSelected = false;

    public void setSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }
    public boolean getSelected()
    {
        return isSelected;
    }
    public TextureRegion getRegion()
    {
        return region;
    }
    public int getChessWidth()
    {
        return chessWidth;
    }

    public int getChessHeight()
    {
        return chessHeight;
    }

    public void setChessX(int chessX)
    {
        this.chessX = chessX;
    }

    public int getChessX()
    {
        return chessX;
    }

    public void setChessY(int chessY)
    {
        this.chessY = chessY;
    }

    public int getChessY()
    {
        return chessY;
    }

    public Pos getPosition()
    {
        return position;
    }

    public void setXY(Pos position)
    {
        this.position = position;
        this.setPosition(position.getX() * squareHW,position.getY() * squareHW);
        this.setOrigin(0,0);
    }

    /**
     * 初始化棋子
     * @param chessName   棋子名称，例如：“曹操”
     * @param chessHeight 棋子高度，例如：棋子“曹操”高度是2
     * @param chessWidth  棋子宽度，例如：棋子“曹操”宽度是2
     * @param region      绘制区域
     */
    public Chess(TextureRegion region, String chessName, int chessWidth, int chessHeight)
    {
        super();
        this.region = region;
        this.chessName = chessName;
        this.chessWidth = chessWidth;
        this.chessHeight = chessHeight;

        //将图片素材按照棋子大小缩放
        setScale(squareHW * chessWidth / region.getRegionWidth(), squareHW * chessHeight / this.region.getRegionHeight());
        //这里的size是图片素材的大小，并非棋子显示的大小，棋子显示大小=图片素材大小*缩放比
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }
    public Chess(Chess chess)
    {
        super();
        this.region = chess.getRegion();
        this.chessWidth = chess.getChessWidth();
        this.chessHeight = chess.getChessHeight();
        setScale(squareHW * chessWidth / region.getRegionWidth(), squareHW * chessHeight / this.region.getRegionHeight());
        //这里的size是图片素材的大小，并非棋子显示的大小，棋子显示大小=图片素材大小*缩放比
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }
    /**
     * 修改棋子贴图
     * @param region 贴图
     */
    public void setRegion(TextureRegion region)
    {
        this.region = region;
        // 重新设置纹理区域后, 需要重新设置宽高
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
        //将图片素材按照棋子大小缩放
        setScale(squareHW * chessWidth / region.getRegionWidth(), squareHW * chessHeight / this.region.getRegionHeight());

    }

    public void select()
    {
        this.setRegion(new TextureRegion(new Texture("CaocSelected.png")));
        isSelected=true;
    }
    public void disSelect()
    {
        this.setRegion(new TextureRegion(new Texture("Caoc.png")));
        isSelected=false;
    }
    @Override
    public void act(float delta)
    {
        super.act(delta);
    }
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    @Override
    public void draw(Batch batch, float parentAlpha)
    {
      //  super.draw(batch, parentAlpha);

        // 如果 region 为 null 或者 演员不可见, 则直接不绘制
        if (region == null || !isVisible())
        {
            return;
        }

        /*
        if(isSelected)
        {


            shapeRenderer.setAutoShapeType(true);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            shapeRenderer.setColor(1, 1, 1, 1);
            shapeRenderer.rect(getX(), getY(), getWidth()*getScaleX(), getHeight()*getScaleY());
            shapeRenderer.end();
        }

         */
		/* 这里选择一个较为复杂的绘制方法进行绘制
		batch.draw(
				region,
				x, y,
				originX, originY,
				width, height,
				scaleX, scaleY,
				rotation
		);*/
        /*
         * 绘制纹理区域
         * 将演员中的 位置(position, 即 X, Y 坐标), 缩放和旋转支点(origin), 宽高尺寸, 缩放比, 旋转角度 应用到绘制中,
         * 最终 batch 会将综合结果绘制到屏幕上
         */

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
