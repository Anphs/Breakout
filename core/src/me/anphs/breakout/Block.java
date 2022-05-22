package me.anphs.breakout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Pool;

public class Block extends Rectangle implements Pool.Poolable
{

    public boolean destroyed;
    private final Color color;

    public Block() { super(); destroyed = false; color = new Color(1, 1,1, 1); }
    
    public void init(float x, float y, float width, float height) {
        super.init(x, y, width, height);
        color.set(0, Math.max((y % 11)/11f, .5f), Math.max((y % 13)/13f, .5f), 1);
    }

    public void draw(ShapeRenderer renderer) {
        if(destroyed) { return; }
        renderer.setColor(color);
        renderer.rect(x, y, width, height);
    }
    
    @Override
    public void reset()
    {
        super.reset();
        destroyed = false;
        color.set(1, 1, 1, 1);
    }
}
