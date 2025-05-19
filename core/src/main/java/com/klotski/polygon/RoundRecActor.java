package com.klotski.polygon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RoundRecActor extends Actor
{
    /**
     * 3:右边的星星
     * 2：中间的星星
     * 1：左边的星星
     */
    private final float MAX_WIDTH=484;
    private final float WIDTH3=390;
    private final float WIDTH2=245;
    private final float WIDTH1=100;
    private final float WIDTH0=28;
    private float rectangleWidth = MAX_WIDTH;
    private float rectangleHeight = 40;
    private float cornerRadius = 14;
    private ShapeRenderer shapeRenderer=new ShapeRenderer();
    private float X=0;
    private float Y=0;
    private int steps=0;
    private int S1;
    private int S2;
    private int S3;
    private int S4;
    private float unit0;
    private float unit1;
    private float unit2;
    private float unit3;

    public void setSteps(int steps)
    {
        this.steps=steps;
        if(steps<S1)
        {
            rectangleWidth=MAX_WIDTH-unit3*steps;
        }
        else if(steps<S2)
        {
            rectangleWidth=WIDTH3-unit2*(steps-S1);
        }
        else if(steps<S3)
        {
            rectangleWidth=WIDTH2-unit1*(steps-S2);
        }
        else if(steps<S4)
        {
            rectangleWidth=WIDTH1-unit0*(steps-S3);
        }
        else
        {
            rectangleWidth=WIDTH0;
        }


    }

    public RoundRecActor(int S1, int S2, int S3)
    {
        super();
        //S1<S2<S3<S4
        this.S1=S1;
        this.S2=S2;
        this.S3=S3;
        this.S4=S3+10;
        this.unit3=(MAX_WIDTH-WIDTH3)/S1;
        this.unit2=(WIDTH3-WIDTH2)/(S2-S1);
        this.unit1=(WIDTH2-WIDTH1)/(S3-S2);
        this.unit0=(WIDTH1-WIDTH0)/(S4-S3);


    }
    public void init()
    {

    }
    @Override
    public void draw(Batch batch, float parentAlpha)
    {

        super.draw(batch, parentAlpha);
        X=this.getParent().getX()+6;
        Y=this.getParent().getY()+7;
        batch.end();
        //OpenGL的一个特性：永远不要同时改变状态。所以在ShapeRender.begin()方法前加上batch.end()方法
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);

        // 绘制中间的矩形部分
        shapeRenderer.rect(X + cornerRadius, Y + cornerRadius, rectangleWidth - 2 * cornerRadius, rectangleHeight - 2 * cornerRadius);

        // 绘制四个边的矩形部分
        shapeRenderer.rect(X + cornerRadius, Y, rectangleWidth - 2 * cornerRadius, cornerRadius);
        shapeRenderer.rect(X + cornerRadius, Y + rectangleHeight - cornerRadius, rectangleWidth - 2 * cornerRadius, cornerRadius);
        shapeRenderer.rect(X, Y + cornerRadius, cornerRadius, rectangleHeight - 2 * cornerRadius);
        shapeRenderer.rect(X + rectangleWidth - cornerRadius, Y + cornerRadius, cornerRadius, rectangleHeight - 2 * cornerRadius);

        // 绘制四个圆角
        shapeRenderer.arc(X + cornerRadius, Y + cornerRadius, cornerRadius, 180, 90);
        shapeRenderer.arc(X + rectangleWidth - cornerRadius, Y + cornerRadius, cornerRadius, 270, 90);
        shapeRenderer.arc(X + cornerRadius, Y + rectangleHeight - cornerRadius, cornerRadius, 90, 90);
        shapeRenderer.arc(X + rectangleWidth - cornerRadius, Y + rectangleHeight - cornerRadius, cornerRadius, 0, 90);

        shapeRenderer.end();
        batch.begin();
    }

    public void setS1(int s1)
    {
        S1 = s1;
    }

    public void setS2(int s2)
    {
        S2 = s2;
    }

    public void setS3(int s3)
    {
        S3 = s3;
    }
}
