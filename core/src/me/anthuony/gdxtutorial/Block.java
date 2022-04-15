package me.anthuony.gdxtutorial;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Block {
    int x;
    int y;
    int length;
    int height;
    boolean destroyed = false;

    public Block(int x, int y, int length, int height) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.height = height;
    }

    public void draw(ShapeRenderer renderer) {
        if(destroyed) { return; }
        renderer.rect(x, y, length, height);
    }
}
