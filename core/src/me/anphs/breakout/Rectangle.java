package me.anphs.breakout;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

class Rectangle extends com.badlogic.gdx.math.Rectangle implements Pool.Poolable
{
    public Vector2 topLeft, topRight, bottomLeft, bottomRight;
    
    public Rectangle() {
        super();
        initCorners();
    }
    
    public Rectangle(float x, float y, float width, float height) {
        super(x, y, width, height);
        initCorners();
    }
    
    private void initCorners() {
        this.topLeft = new Vector2(x, y + height);
        this.topRight = new Vector2(x + width, y + height);
        this.bottomLeft = new Vector2(x, y);
        this.bottomRight = new Vector2(x + width, y);
    }
    
    public void updateCorners() {
        this.topLeft.set(x, y + height);
        this.topRight.set(x + width, y + height);
        this.bottomLeft.set(x, y);
        this.bottomRight.set(x + width, y);
    }
    
    public void init(float x, float y, float width, float height) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        updateCorners();
    }
    
    @Override
    public void reset()
    {
        this.setX(0);
        this.setY(0);
        this.setWidth(0);
        this.setHeight(0);
        updateCorners();
    }
    
    public Rectangle setX(float x) {
        this.x = x;
        updateCorners();
        return this;
    }
    
    public Rectangle setY(float y) {
        this.y = y;
        updateCorners();
        return this;
    }
}

enum Side {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT,
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
}
