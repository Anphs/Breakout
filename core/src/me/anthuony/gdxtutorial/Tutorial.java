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
		Random r = new Random();
		float angle = (float) ((r.nextFloat() * (Math.PI/2f)) + (Math.PI/4f));
		ball = new Ball(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 4f, 8, ballBaseSpeed * (float)Math.cos(angle), ballBaseSpeed);

		int rowLength = 9;
		int blockSpacing = (Gdx.graphics.getWidth() * 10) / 640;
		int blockWidth = Gdx.graphics.getWidth() - blockSpacing;
		blockWidth /= rowLength;
		blockWidth -= blockSpacing;
		int blockHeight = blockWidth / 3;
		blocks.clear();
		for(int i = Gdx.graphics.getHeight() / 2; i < Gdx.graphics.getHeight(); i += blockHeight + blockSpacing) {
			for(int j = blockSpacing + blockWidth / 2; j < Gdx.graphics.getWidth(); j += blockWidth + blockSpacing) {
				blocks.add(new Block(j ,i, blockWidth, blockHeight));
			}
		}
	}

	@Override
	public void create () {
		renderer = new ShapeRenderer();
		paddle = new Paddle(Gdx.graphics.getWidth() / 2f, 32, 100, 10);

		reset();
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
		for(Iterator<Block> iterator = blocks.iterator(); iterator.hasNext(); ) {
			Block block = iterator.next();
			ball.checkCollision(block);
			if(block.destroyed) { iterator.remove(); }
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