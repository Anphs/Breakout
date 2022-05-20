package me.anthuony.gdxtutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;

import java.util.*;

public class Breakout extends Game {
	private final float BALL_BASE_SPEED = 200;
	private int numBalls = 1;
	
	private ShapeRenderer renderer;
	private Paddle paddle;
	private Array<Ball> balls;
	private Array<Block> blocks;
	private Random r;

	private void reset () {
		while(balls.size < numBalls)
			balls.add(new Ball(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 8, BALL_BASE_SPEED, BALL_BASE_SPEED));

		for(int i = 0; i < balls.size; i++) {
			Ball ball = balls.get(i);
			float angle = (r.nextFloat() - 0.5f) * 2f;
			ball.setX(Gdx.graphics.getWidth() / 2f);
			ball.setY(Gdx.graphics.getHeight() / 4f - i * 32);
			ball.setXSpeed(BALL_BASE_SPEED * angle);
			ball.setYSpeed(BALL_BASE_SPEED + BALL_BASE_SPEED * (i * .2f));
		}

		int rowLength = 9;
		int blockSpacing = (Gdx.graphics.getWidth() * 10) / 640;
		int blockWidth = Gdx.graphics.getWidth() - blockSpacing;
		blockWidth /= rowLength;
		blockWidth -= blockSpacing;
		int blockHeight = blockWidth / 3;
		blocks.clear();
		for(int i = Gdx.graphics.getHeight() / 2; i < Gdx.graphics.getHeight(); i += blockHeight + blockSpacing) {
			for(int j = blockSpacing; j < Gdx.graphics.getWidth(); j += blockWidth + blockSpacing) {
				blocks.add(new Block(j ,i, blockWidth, blockHeight));
			}
		}
	}

	@Override
	public void create () {
		renderer = new ShapeRenderer();
		paddle = new Paddle(Gdx.graphics.getWidth() / 2f, 32, 100, 10);
		balls = new Array<>();
		blocks = new Array<>();
		r = new Random();
		reset();
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.begin(ShapeRenderer.ShapeType.Line);

		paddle.update();
		paddle.draw(renderer);

		for(Ball ball : balls) {
			if(ball.y < ball.radius) {
				reset();
				break;
			}
			ball.update(Gdx.graphics.getDeltaTime());
			ball.checkCollision(paddle);
			for(Iterator<Block> iterator = blocks.iterator(); iterator.hasNext();) {
				Block block = iterator.next();
				ball.checkCollision(block);
				if(block.destroyed)
					iterator.remove();
			}
			for(int i = 0; i < balls.size; i++) {
				Ball otherBall = balls.get(i);
				if(otherBall != ball)
					ball.checkCollision(otherBall);
			}
			
			ball.draw(renderer);
		}
		
		for(Block block : blocks)
			block.draw(renderer);

		if(blocks.isEmpty()) {
			numBalls++;
			reset();
		}

		renderer.end();
	}

}