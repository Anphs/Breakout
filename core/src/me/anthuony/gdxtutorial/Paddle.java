package me.anthuony.gdxtutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Paddle extends Rectangle {
    Color color = Color.WHITE;

    public Paddle(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public void update() {
        x = Gdx.input.getX() - width/2f;
        x = clamp(x, 0.01f, Gdx.graphics.getWidth() - width);
        //y = Gdx.graphics.getHeight() - Gdx.input.getY() - height/2f;
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.rect(x, y, width, height);
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
