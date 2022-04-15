package me.anthuony.gdxtutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle {
    int x;
    int y;
    int length;
    int height;
    Color color = Color.WHITE;

    public Paddle(int x, int y, int length, int height) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.height = height;
    }

    public void update() {
        x = Gdx.input.getX();
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.rect(x - (length / 2f), y - (height / 2f), length, height);
    }
}
