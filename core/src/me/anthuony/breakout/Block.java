package me.anthuony.breakout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Pool;

public class Block extends Rectangle implements Pool.Poolable
{

    boolean destroyed;

    public Block() { super(); destroyed = false; }
    
    public void init(float x, float y, float width, float height) {
        super.init(x, y, width, height);
    }

    public void draw(ShapeRenderer renderer) {
        if(destroyed) { return; }
        renderer.setColor(Color.WHITE);
        renderer.rect(x, y, width, height);
    }
    
    @Override
    public void reset()
    {
        super.reset();
        destroyed = false;
    }
}
