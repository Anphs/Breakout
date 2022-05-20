package me.anthuony.gdxtutorial;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

public class Block extends Rectangle implements Pool.Poolable
{

    boolean destroyed;

    public Block() { super(); destroyed = false; }
    
    public void init(float x, float y, float width, float height) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
    }

    public void draw(ShapeRenderer renderer) {
        if(destroyed) { return; }
        renderer.setColor(Color.WHITE);
        renderer.rect(x, y, width, height);
    }
    
    @Override
    public void reset()
    {
        this.setX(0);
        this.setY(0);
        this.setWidth(0);
        this.setHeight(0);
        destroyed = false;
    }
}
