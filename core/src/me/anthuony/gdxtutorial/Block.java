package me.anthuony.gdxtutorial;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Block extends Rectangle {

    boolean destroyed = false;

    public Block(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public void draw(ShapeRenderer renderer) {
        if(destroyed) { return; }
        renderer.setColor(Color.WHITE);
        renderer.rect(x, y, width, height);
    }
}
