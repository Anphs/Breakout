package me.anthuony.gdxtutorial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;

import java.util.*;

public class Tutorial extends ApplicationAdapter {
	ShapeRenderer renderer;
	Ball ball;
	Paddle paddle;


	@Override
	public void create () {
		renderer = new ShapeRenderer();
		ball = new Ball(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 30, 5, 5);
		paddle = new Paddle(Gdx.input.getX(), 32, 100, 10);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.begin(ShapeRenderer.ShapeType.Filled);

		ball.update();
		ball.checkCollision(paddle);
		ball.draw(renderer);

		paddle.update();
		paddle.draw(renderer);

		renderer.end();
	}
}