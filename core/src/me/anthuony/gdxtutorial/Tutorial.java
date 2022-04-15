package me.anthuony.gdxtutorial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;

import java.util.*;

public class Tutorial extends ApplicationAdapter {
	ShapeRenderer renderer;
	Ball ball;
	List<Ball> balls = new ArrayList<>();
	Random r = new Random();

	@Override
	public void create () {
		renderer = new ShapeRenderer();
		ball = new Ball(150, 200, 70, 15, 5);
		for(int i = 0; i < 1000; i++) {
			balls.add(new Ball(
					r.nextInt(Gdx.graphics.getWidth()),
					r.nextInt(Gdx.graphics.getHeight()),
					r.nextInt(3),
					r.nextInt(10) + 1,
					r.nextInt(10) + 1
			));
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		for(Ball ball : balls) {
			ball.update();
			ball.draw(renderer);
		}
		renderer.end();
	}
}