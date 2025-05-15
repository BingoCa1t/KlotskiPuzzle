package com.klotski.polygon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.klotski.logic.Pos;

/**
 * 棋子类，继承自Actor
 *
 * @author BingoCAT
 */
public class Chess extends Actor
{
    /*自定义里面的宽度、高度、坐标都是如（3,3）这样的，Actor基类本身的才是像素绘制的坐标*/
    private int ID;
    private String imagePath;
    /**
     * 绘制区域
     */
    private TextureRegion region;
    //棋子实时XY坐标，如(0,0))
    private Pos position;
    //棋子名字，暂时无用，以后也许做个性化的时候会用到
    private String chessName;
    //棋子宽度，初始化之后不可改变
    private final int chessWidth;
    //棋子高度，初始化之后不可改变
    private final int chessHeight;
    //棋子默认宽度
    private final int DEFAULT_CHESS_WIDTH = 1;
    //棋子默认高度
    private final int DEFAULT_CHESS_HEIGHT = 1;
    //棋盘一格为160像素，在这里统一管理大小，方便以后修改
    public static final float squareHW = 170f;
    //棋子是否被选中
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

    public Pos getPosition()
    {
        return position;
    }
    public String getChessName()
    {
        return chessName;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    /**
     * 修改自定义Pos坐标的同时修改组件状态
     *
     * @param position 自定义Pos位置
     */
    public void setXY(Pos position)
    {
        this.position = position;
        this.setPosition(position.getX() * squareHW, position.getY() * squareHW);
    }

    /**
     * 只修改自定义的Pos坐标，不修改组件状态
     *
     * @param position 自定义的Pos坐标
     */
    public void setXYWithoutChangingState(Pos position)
    {
        this.position = position;
    }

    /**
     * 重写父类方法，x、y为像素坐标，同时也会修改自定义的Pos坐标
     *
     * @param x 像素x坐标
     * @param y 像素y坐标
     */
    @Override
    public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        this.position = new Pos((int) (x / squareHW), (int) (y / squareHW));
    }

    /**
     * 初始化棋子
     *
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
    public Chess(String imagePath, String chessName, int chessWidth, int chessHeight)
    {
        super();
        this.imagePath = imagePath;
        this.region=new TextureRegion(new Texture(imagePath));
        this.chessName = chessName;
        this.chessWidth = chessWidth;
        this.chessHeight = chessHeight;
        setOrigin(this.getWidth()/2, this.getHeight()/2);
        //将图片素材按照棋子大小缩放
        setScale((squareHW * chessWidth-5) / region.getRegionWidth(), (squareHW * chessHeight-5) / this.region.getRegionHeight());
        //这里的size是图片素材的大小，并非棋子显示的大小，棋子显示大小=图片素材大小*缩放比
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }

    /**
     * 创建chess的副本
     *
     * @param chess 新的副本
     */
    public Chess(Chess chess)
    {
        super();
        this.region = chess.getRegion();
        this.chessWidth = chess.getChessWidth();
        this.chessHeight = chess.getChessHeight();
        setScale((squareHW * chessWidth-5) / region.getRegionWidth(), (squareHW * chessHeight-5) / this.region.getRegionHeight());
        //这里的size是图片素材的大小，并非棋子显示的大小，棋子显示大小=图片素材大小*缩放比
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }
    public Chess(Chess chess,boolean isArchive)
    {
        super();
        this.imagePath = chess.imagePath;
        this.region=new TextureRegion(new Texture(imagePath));
        this.chessWidth = chess.getChessWidth();
        this.chessHeight = chess.getChessHeight();
        this.setXY(chess.getPosition());
        this.chessName = chess.getChessName();
        setScale((squareHW * chessWidth-5) / region.getRegionWidth(), (squareHW * chessHeight-5) / this.region.getRegionHeight());
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }

    /**
     * 修改棋子贴图
     *
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

    /**
     * 棋子被选中
     */
    public void select()
    {
        this.setRegion(new TextureRegion(new Texture("CaocSelected.png")));
        isSelected = true;
    }

    /**
     * 棋子被取消选中
     */
    public void disSelect()
    {
        this.setRegion(new TextureRegion(new Texture("Caoc.png")));
        isSelected = false;
    }

    @Override
    public void act(float delta)
    {
        super.act(delta);
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

    /**
     * Chess类的toString方法
     *
     * @return 返回格式"Chess [chessName= ,chessWidth= ,chessHeight= ,chessPosition= ,]"
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Chess [");
        builder.append("chessName=");
        builder.append(chessName);
        builder.append(", chessWidth=");
        builder.append(chessWidth);
        builder.append(", chessHeight=");
        builder.append(chessHeight);
        builder.append(", chessPosition=");
        builder.append(getPosition().toString());
        builder.append("]");
        return builder.toString();
    }
}
