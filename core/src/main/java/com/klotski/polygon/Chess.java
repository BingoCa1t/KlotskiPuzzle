package com.klotski.polygon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.klotski.Main;
import com.klotski.assets.AssetsPathManager;
import com.klotski.assets.ImageAssets;
import com.klotski.logic.Pos;

/**
 * 棋子类，继承自Actor
 * 请注意：此类的构造方法仅给字段赋值，读取绘制区域要执行init()方法
 * @author BingoCAT
 */
public class Chess extends Actor
{
    /*
    关于棋子贴图的处理思路产生变动
    后期会使用AssetManager，放弃自定义贴图的处理方式，改成提供标识符，由AssetManager负责读取对应的所有贴图
    imagePath字段应为棋子中文名的前两个字母小写，比如 曹操：cc 关羽：gy
    如果是正方形棋子（卒和曹操），则命名为（cc.png 和 ccS.png 、 zu.png 和 zuS.png）
    如果是长方形棋子，则1*2时，命名为**H（gyH.png 和 gyHS.png）  2*1 命名为**W（gyS.png 和 gyWS.png）
    读取时采用：
    1、如果是曹操（cc）和卒（zu），则直接读取唯一的贴图
    2、如果是其他棋子，则根据长宽来决定贴图
    */

    /*自定义里面的宽度、高度、坐标都是如（3,3）这样的，Actor基类本身的才是像素绘制的坐标*/
    private int ID;
    private String imagePath;
    private String selectedImagePath;
    private TextureRegion disSelectedImage;
    private TextureRegion selectedImage;
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

    private boolean isMovable = true;


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
     * 初始化棋子（已弃用）
     *
     * @param chessName   棋子名称，例如：“曹操”
     * @param chessHeight 棋子高度，例如：棋子“曹操”高度是2
     * @param chessWidth  棋
     * @param region      绘制区域
     */
    public Chess(TextureRegion region, String chessName, int chessWidth, int chessHeight)
    {
        super();
        this.region = region;
        this.chessName = chessName;
        this.chessWidth = chessWidth;
        this.chessHeight = chessHeight;
        this.particles = new Array<>();
        this.isExploding = false;

        //将图片素材按照棋子大小缩放
        setScale(squareHW * chessWidth / region.getRegionWidth(), squareHW * chessHeight / this.region.getRegionHeight());
        //这里的size是图片素材的大小，并非棋子显示的大小，棋子显示大小=图片素材大小*缩放比
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }

    /**
     * 初始化棋子,这里只会给几个字段赋值，不会读取绘制区域
     * 路径命名方式：取棋子名字前两个字母
     * @param imagePath
     * @param chessName 棋子名称，例如：“曹操”
     * @param chessWidth 棋子宽度，例如：棋子“曹操”宽度是2
     * @param chessHeight 棋子高度，例如：棋子“曹操”高度是2
     */
    public Chess(String imagePath, String chessName, int chessWidth, int chessHeight)
    {
        super();
        this.imagePath =imagePath;
        this.chessName = chessName;
        this.chessWidth = chessWidth;
        this.chessHeight = chessHeight;
        this.particles = new Array<>();
        this.isExploding = false;
    }

    /**
     * 读取绘制区域，完成棋子初始化
     * @param apm 资源管理器
     */
    public void init(AssetsPathManager apm)
    {
        String s1=imagePath;
        String s2="";
        String s3="S";
        if(chessHeight>chessWidth) s2="H";
        else if(chessWidth>chessHeight) s2="W";
        this.disSelectedImage=new TextureRegion(apm.get(ImageAssets.valueOf(s1+s2)));
        this.selectedImage=new TextureRegion(apm.get(ImageAssets.valueOf(s1+s2+s3)));
        this.region=this.disSelectedImage;
        if(this.chessName.equals("fixed")) this.setMovable(false);
        setOrigin(this.getWidth()/2, this.getHeight()/2);
        //将图片素材按照棋子大小缩放
        setScale((squareHW * chessWidth-5) / region.getRegionWidth(), (squareHW * chessHeight-5) / this.region.getRegionHeight());
        //这里的size是图片素材的大小，并非棋子显示的大小，棋子显示大小=图片素材大小*缩放比
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
        particleTexture=new TextureRegion(createParticleTexture(16));

    }

    /**
     * 创建chess的副本
     *
     * @param chess 新的副本
     */
    public Chess(Chess chess)
    {
        super();
        this.imagePath=chess.getImagePath();
        this.chessName=chess.getChessName();
        this.chessWidth=chess.getChessWidth();
        this.chessHeight=chess.getChessHeight();
        this.isMovable=chess.isMovable();
        this.particles=new Array<>();
    }

    /**
     * 在读取地图的时候
     * @param chess
     * @param isArchive
     */
    public Chess(Chess chess,boolean isArchive)
    {
        super();
        this.imagePath = chess.imagePath;
        this.chessWidth = chess.getChessWidth();
        this.chessHeight = chess.getChessHeight();
        this.setXY(chess.getPosition());
        this.chessName = chess.getChessName();
        this.particles=new Array<>();
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
        setScale((squareHW * chessWidth-5) / region.getRegionWidth(), (squareHW * chessHeight-5) / this.region.getRegionHeight());

    }

    /**
     * 棋子被选中
     */
    public void select()
    {
        this.setRegion(selectedImage);
        isSelected = true;
    }

    /**
     * 棋子被取消选中
     */
    public void disSelect()
    {
        this.setRegion(disSelectedImage);
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
        // 如果没有爆炸，正常绘制Actor
        if (!isExploding) {
            batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
            batch.draw(region,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation());
            batch.setColor(1, 1, 1, 1); // 重置颜色
        }

        // 更新并绘制粒子（如果正在爆炸）
        if (!particles.isEmpty()) {
            for (int i = particles.size - 1; i >= 0; i--) {
                Particle p = particles.get(i);
                p.update(Gdx.graphics.getDeltaTime());
                if (!p.isAlive()) {
                    particles.removeIndex(i);
                }
            }

            // 绘制所有粒子
            batch.setColor(1, 1, 1, parentAlpha);
            for (Particle p : particles) {
                batch.setColor(1, 1, 1, p.alpha * parentAlpha);
                batch.draw(particleTexture,
                    p.position.x - p.size / 2,
                    p.position.y - p.size / 2,
                    p.size, p.size);
            }
            batch.setColor(1, 1, 1, 1); // 重置颜色
        }
        /*
         * 绘制纹理区域
         * 将演员中的 位置(position, 即 X, Y 坐标), 缩放和旋转支点(origin), 宽高尺寸, 缩放比, 旋转角度 应用到绘制中,
         * 最终 batch 会将综合结果绘制到屏幕上
         */

    }
    public void explode() {
        if (isExploding) return;

        isExploding = true;
         // 隐藏原始Actor

        // 将Actor分割成粒子
        int particleCount = 100;
        float particleSize = Math.max(getWidth()*getScaleX(), getHeight()*getScaleY()) / 10f;

        // 获取Actor在舞台上的位置
        float stageX = getX();
        float stageY = getY();

        for (int i = 0; i < particleCount; i++) {
            // 在Actor区域内随机位置生成粒子
            float x = stageX + MathUtils.random(0, getWidth()*getScaleX());
            float y = stageY + MathUtils.random(0, getHeight()*getScaleY());

            particles.add(new Particle(x, y, particleSize));
        }
    }
    public boolean isExplosionFinished()
    {
      return isExploding && particles.isEmpty();
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

    public String getSelectedImagePath()
    {
        return selectedImagePath;
    }

    public void setSelectedImagePath(String selectedImagePath)
    {
        this.selectedImagePath = selectedImagePath;
    }

    public TextureRegion getDisSelectedImage()
    {
        return disSelectedImage;
    }

    public void setDisSelectedImage(TextureRegion disSelectedImage)
    {
        this.disSelectedImage = disSelectedImage;
    }

    public TextureRegion getSelectedImage()
    {
        return selectedImage;
    }

    public void setSelectedImage(TextureRegion selectedImage)
    {
        this.selectedImage = selectedImage;
    }

    public boolean isMovable()
    {
        return isMovable;
    }

    public void setMovable(boolean movable)
    {
        isMovable = movable;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Chess chess)
        {
            if(this.chessName.equals(chess.getChessName())&&this.chessWidth==chess.getChessWidth()&&this.chessHeight==chess.getChessHeight()&&this.getPosition().equals(chess.getPosition()))
                return true;
        }
        return false;
    }

    private TextureRegion particleTexture;

    // 粒子数组
    private Array<Particle> particles;
    private boolean isExploding;

    // 粒子类
    private class Particle
    {
        Vector2 position;
        Vector2 velocity;
        float size;
        float alpha;
        float life;
        float maxLife;

        public Particle(float x, float y, float size)
        {
            this.position = new Vector2(x, y);
            this.size = size;
            this.alpha = 1f;
            this.maxLife = MathUtils.random(0.5f, 1.5f);
            this.life = 0f;

            // 随机速度向量，使粒子四散
            float angle = MathUtils.random(0, 360);
            float speed = MathUtils.random(50, 200);
            this.velocity = new Vector2(
                MathUtils.cosDeg(angle) * speed,
                MathUtils.sinDeg(angle) * speed
            );
        }

        public void update(float deltaTime) {
            life += deltaTime;
            alpha = 1f - (life / maxLife);

            // 更新位置
            position.add(velocity.x * deltaTime, velocity.y * deltaTime);

            // 添加重力效果
            velocity.y -= 100 * deltaTime;
        }

        public boolean isAlive() {
            return life < maxLife;
        }
    }
    private static Texture createParticleTexture(int size) {
        com.badlogic.gdx.graphics.Pixmap pixmap = new com.badlogic.gdx.graphics.Pixmap(size, size, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);

        // 创建一个白色圆点
        pixmap.setColor(1, 1, 1, 1); // 设置为白色
        pixmap.fillCircle(size / 2, size / 2, size / 2); // 填充圆形

        Texture texture = new Texture(pixmap);
        pixmap.dispose(); // 释放Pixmap资源

        return texture;
    }
}
