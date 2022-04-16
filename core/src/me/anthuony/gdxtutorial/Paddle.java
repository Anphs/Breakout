package me.anthuony.gdxtutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle extends Rectangle {
    Color color = Color.WHITE;

    public Paddle(int x, int y, int length, int height) {
        super(x, y, length, height);
    }

    public void update() {
        x = Gdx.input.getX();
        //y = Gdx.graphics.getHeight() - Gdx.input.getY();
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.rect(x - (length / 2f), y - (height / 2f), length, height);
    }
}
