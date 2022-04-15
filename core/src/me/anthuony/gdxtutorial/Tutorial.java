package me.anthuony.gdxtutorial;

import com.badlogic.gdx.ApplicationAdapter;
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


	@Override
	public void create () {
		renderer = new ShapeRenderer();
		ball = new Ball(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 8, 2, 2);
		paddle = new Paddle(Gdx.input.getX(), 32, 100, 10);

		int blockWidth = 60;
		int blockHeight = 20;
		for(int i = Gdx.graphics.getHeight() / 2; i < Gdx.graphics.getHeight(); i += blockHeight + 10) {
			for(int j = 10; j < Gdx.graphics.getWidth(); j += blockWidth + 10) {
				blocks.add(new Block(j ,i, blockWidth, blockHeight));
			}
		}

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.begin(ShapeRenderer.ShapeType.Line);

		ball.update(this);
		ball.checkCollision(paddle);
		ball.draw(renderer);

		paddle.update();
		paddle.draw(renderer);

		for(Block block: blocks) {
			block.draw(renderer);
			ball.checkCollision(block);
		}

		renderer.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

}