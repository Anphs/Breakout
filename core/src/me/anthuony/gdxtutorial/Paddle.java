package me.anthuony.gdxtutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle extends Rectangle {
    Color color = Color.WHITE;

    public Paddle(float x, float y, float length, float height) {
        super(x, y, length, height);
    }

    public void update() {
        x = Gdx.input.getX();
        x = clamp(x, length/2f + 0.01f, Gdx.graphics.getWidth() - length/2f);
        //y = Gdx.graphics.getHeight() - Gdx.input.getY();
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.rect(x - (length / 2f), y - (height / 2f), length, height);
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
