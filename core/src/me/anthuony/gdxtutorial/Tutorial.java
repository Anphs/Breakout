package me.anthuony.gdxtutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;

import java.util.*;

public class Tutorial extends Game {
	ShapeRenderer renderer;
	Ball ball;
	Paddle paddle;
	List<Block> blocks = new ArrayList<>();
	float ballBaseSpeed = 1;

	public void reset () {
		ball = new Ball(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 4f, 8, ballBaseSpeed, ballBaseSpeed);

		int blockWidth = 60;
		int blockHeight = 20;
		for(int i = Gdx.graphics.getHeight() / 2; i < Gdx.graphics.getHeight(); i += blockHeight + 10) {
			for(int j = 10 + blockWidth / 2; j < Gdx.graphics.getWidth(); j += blockWidth + 10) {
				blocks.add(new Block(j ,i, blockWidth, blockHeight));
			}
		}
	}

	@Override
	public void create () {
		renderer = new ShapeRenderer();
		ball = new Ball(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 4f, 8, ballBaseSpeed, ballBaseSpeed);
		paddle = new Paddle(Gdx.graphics.getWidth() / 2, 32, 100, 10);

		int blockWidth = 60;
		int blockHeight = 20;
		for(int i = Gdx.graphics.getHeight() / 2; i < Gdx.graphics.getHeight(); i += blockHeight + 10) {
			for(int j = 10 + blockWidth / 2; j < Gdx.graphics.getWidth(); j += blockWidth + 10) {
				blocks.add(new Block(j ,i, blockWidth, blockHeight));
			}
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.begin(ShapeRenderer.ShapeType.Line);

		ball.update(Gdx.graphics.getDeltaTime());
		ball.checkCollision(paddle);
		ball.draw(renderer);

		paddle.update();
		paddle.draw(renderer);

		for(int i = blocks.size()-1; i >= 0; i--) {
			Block block = blocks.get(i);
			ball.checkCollision(block);
			if(block.destroyed) {
				blocks.remove(block);
			}
			else {
				block.draw(renderer);
			}
		}

		if(ball.y < ball.size || blocks.isEmpty()) {
			reset();
		}

		renderer.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

}