package me.anthuony.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import java.util.Random;

public class Ball extends Circle implements Pool.Poolable
{
    private float xSpeed;
    private float ySpeed;
    private final Color color = new Color(1, 1, 1, 1);
    private final Intersector.MinimumTranslationVector minimumTranslationVector = new Intersector.MinimumTranslationVector();
    private static final Random r = new Random();
    Vector2 screenTopLeft, screenTopRight, screenBottomRight, screenBottomLeft;
    
    public Ball() {
        screenTopLeft = new Vector2(0, Gdx.graphics.getHeight());
        screenTopRight = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screenBottomRight = new Vector2(Gdx.graphics.getWidth(), 0);
        screenBottomLeft = new Vector2(0, 0);
    }
    
    public void update(float deltaTime) {
        x += xSpeed * deltaTime;
        y += ySpeed * deltaTime;
        
        if(Intersector.intersectSegmentCircle(screenTopLeft, screenBottomLeft, this, minimumTranslationVector)) {
            applyMTV();
            if(xSpeed < 0)
                xSpeed = -xSpeed;
        }
        else if(Intersector.intersectSegmentCircle(screenTopRight, screenBottomRight, this, minimumTranslationVector)) {
            applyMTV();
            if(xSpeed > 0)
                xSpeed = -xSpeed;
        }
        if(Intersector.intersectSegmentCircle(screenTopLeft, screenTopRight, this, minimumTranslationVector)) {
            applyMTV();
            if(ySpeed > 0)
                ySpeed = -ySpeed;
        }
    }
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.circle(x, y, radius);
    }
    public void checkCollision(Paddle paddle) {
        Side side = collidesWith(paddle);
        if(side != null) {
            changeDirection(side);
        }
    }
    public void checkCollision(Ball otherBall) {
        if(overlaps(otherBall)) {
            if(xSpeed / otherBall.xSpeed < 0) {
                xSpeed = -xSpeed;
                otherBall.xSpeed = -otherBall.xSpeed;
            }
            if(ySpeed / otherBall.ySpeed < 0) {
                ySpeed = -ySpeed;
                otherBall.ySpeed = -otherBall.ySpeed;
            }
        }
    }
    public void checkCollision(Block block) {
        if(!block.destroyed) {
            Side side = collidesWith(block);
            if(side != null) {
                changeDirection(side);
                block.destroyed = true;
                color.set(r.nextFloat() + .2f, r.nextFloat() + .2f, r.nextFloat() + .2f, 1);
                if(xSpeed > 0) {
                    xSpeed += 5;
                }
                else
                {
                    xSpeed -= 5;
                }
                if(ySpeed > 0) {
                    ySpeed += 5;
                }
                else
                {
                    ySpeed -= 5;
                }
            }
        }
    }
    public void changeDirection(Side side) {
        if(side == Side.LEFT || side == Side.TOP_LEFT || side == Side.BOTTOM_LEFT) {
            if(xSpeed > 0) {
                xSpeed = -xSpeed;
            }
        }
        else if(side == Side.RIGHT || side == Side.TOP_RIGHT || side == Side.BOTTOM_RIGHT) {
            if(xSpeed < 0) {
                xSpeed = -xSpeed;
            }
        }
        if(side == Side.TOP || side == Side.TOP_RIGHT || side == Side.TOP_LEFT) {
            if(ySpeed < 0) {
                ySpeed = -ySpeed;
            }
        }
        else if(side == Side.BOTTOM || side == Side.BOTTOM_RIGHT || side == Side.BOTTOM_LEFT) {
            if(ySpeed > 0) {
                ySpeed = -ySpeed;
            }
        }
        else {
            xSpeed = -xSpeed;
            ySpeed = -ySpeed;
        }
    }
    private Side collidesWith(Rectangle rect) {
        if(Intersector.overlaps(this, rect)) {
            if(Intersector.intersectSegmentCircle(rect.topLeft, rect.topLeft, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.TOP_LEFT;
            }
            else if(Intersector.intersectSegmentCircle(rect.topRight, rect.topRight, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.TOP_RIGHT;
            }
            else if(Intersector.intersectSegmentCircle(rect.bottomLeft, rect.bottomLeft, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.BOTTOM_LEFT;
            }
            else if(Intersector.intersectSegmentCircle(rect.bottomRight, rect.bottomRight, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.BOTTOM_RIGHT;
            }
            else if(Intersector.intersectSegmentCircle(rect.topLeft, rect.topRight, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.TOP;
            }
            else if(Intersector.intersectSegmentCircle(rect.bottomLeft, rect.bottomRight, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.BOTTOM;
            }
            else if(Intersector.intersectSegmentCircle(rect.topLeft, rect.bottomLeft, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.LEFT;
            }
            else if(Intersector.intersectSegmentCircle(rect.bottomRight, rect.topRight, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.RIGHT;
            }
        }
        return null;
    }
    private void applyMTV() {
        Vector2 angle = minimumTranslationVector.normal.rotateDeg(180);
        float distance = minimumTranslationVector.depth;
        this.x += distance * Math.cos(angle.angleRad());
        this.y += distance * Math.sin(angle.angleRad());
    }
    
    public void setXSpeed(float xSpeed)
    {
        this.xSpeed = xSpeed;
    }
    
    public void setYSpeed(float ySpeed)
    {
        this.ySpeed = ySpeed;
    }
    
    public void init(float x, float y, float radius, float xSpeed, float ySpeed) {
        this.setX(x);
        this.setY(y);
        this.setRadius(radius);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    
    @Override
    public void reset()
    {
        this.setX(0);
        this.setY(0);
        this.setRadius(0);
        this.setXSpeed(0);
        this.setYSpeed(0);
        this.color.set(1, 1, 1, 1);
    }
}
