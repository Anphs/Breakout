package me.anphs.breakout;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import java.util.*;

public class Breakout extends Game {
	private static final float BALL_BASE_SPEED = 300;
	private static final int PADDLE_WIDTH = 100;
	private int numBalls = 1;
	
	private ShapeRenderer renderer;
	private Paddle paddle;
	private Random r;
	private Array<Ball> balls;
	private Pool<Ball> ballPool;
	private Array<Block> blocks;
	private Pool<Block> blockPool;
	private Sound ballSound, paddleSound, wallSound, blockSound, loseSound;

	private void clearBlocks() {
		for (Array.ArrayIterator<Block> iterator = blocks.iterator(); iterator.hasNext(); )
		{
			Block block = iterator.next();
			iterator.remove();
			blockPool.free(block);
		}
	}
	
	private void reset () {
		while(balls.size < numBalls) {
			Ball ball = ballPool.obtain();
			ball.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 8, BALL_BASE_SPEED, BALL_BASE_SPEED);
			balls.add(ball);
		}

		for(int i = 0; i < balls.size; i++) {
			Ball ball = balls.get(i);
			float angle = (r.nextFloat() - 0.5f) * 2f;
			ball.setX(Gdx.graphics.getWidth() / 2f);
			ball.setY(paddle.getY() + paddle.height + ball.radius + i * ball.radius * 2.5f);
			ball.setXSpeed(BALL_BASE_SPEED * angle);
			ball.setYSpeed(BALL_BASE_SPEED + BALL_BASE_SPEED * (i * .2f));
		}

		int rowLength = 9;
		int blockSpacing = (Gdx.graphics.getWidth() * 10) / 640;
		int blockWidth = Gdx.graphics.getWidth() - blockSpacing;
		blockWidth /= rowLength;
		blockWidth -= blockSpacing;
		int blockHeight = blockWidth / 3;
		clearBlocks();
		for(int i = Gdx.graphics.getHeight() / 2; i < Gdx.graphics.getHeight() * .8f; i += blockHeight + blockSpacing) {
			for(int j = blockSpacing; j < Gdx.graphics.getWidth(); j += blockWidth + blockSpacing) {
				Block block = blockPool.obtain();
				block.init(j ,i, blockWidth, blockHeight);
				blocks.add(block);
			}
		}
	}

	@Override
	public void create () {
		ballSound = Gdx.audio.newSound(Gdx.files.internal("ball.wav"));
		paddleSound = Gdx.audio.newSound(Gdx.files.internal("paddle.wav"));
		wallSound = Gdx.audio.newSound(Gdx.files.internal("wall.wav"));
		blockSound = Gdx.audio.newSound(Gdx.files.internal("block.wav"));
		loseSound = Gdx.audio.newSound(Gdx.files.internal("lose.wav"));
		
		renderer = new ShapeRenderer();
		paddle = new Paddle(Gdx.graphics.getWidth() / 2f, 32, PADDLE_WIDTH, 10);
		r = new Random();
		balls = new Array<>();
		blocks = new Array<>();
		ballPool = Pools.get(Ball.class);
		blockPool = Pools.get(Block.class);
		reset();
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.begin(ShapeRenderer.ShapeType.Line);

		paddle.update();
		paddle.draw(renderer);

		for(int i = 0; i < balls.size; i++) {
			Ball ball = balls.get(i);
			if(ball.y < ball.radius) {
				loseSound.play(1.0f);
				reset();
				break;
			}
			if(ball.checkWallCollision())
				wallSound.play(1.0f);
			ball.update(Gdx.graphics.getDeltaTime());
			if(ball.checkCollision(paddle))
				paddleSound.play(1.0f);
			for(Iterator<Block> iterator = blocks.iterator(); iterator.hasNext();) {
				Block block = iterator.next();
				ball.checkCollision(block);
				if(block.destroyed) {
					blockSound.play(1.0f);
					iterator.remove();
					blockPool.free(block);
				}
			}
			for(int j = i + 1; j < balls.size; j++)
				if(ball.checkCollision(balls.get(j)))
					ballSound.play(1.0f);
			
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
	
	@Override
	public void dispose()
	{
		super.dispose();
	}
}