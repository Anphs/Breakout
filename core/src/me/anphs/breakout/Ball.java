package me.anphs.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import java.util.Random;

public class Ball extends Circle implements Pool.Poolable
{
    private final Vector2 velocity;
    private final Color color = new Color(1, 1, 1, 1);
    private final Intersector.MinimumTranslationVector minimumTranslationVector = new Intersector.MinimumTranslationVector();
    private static final Random r = new Random();
    Vector2 screenTopLeft, screenTopRight, screenBottomRight, screenBottomLeft;
    
    public Ball() {
        velocity = new Vector2(0, 0);
        screenTopLeft = new Vector2(0, Gdx.graphics.getHeight());
        screenTopRight = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screenBottomRight = new Vector2(Gdx.graphics.getWidth(), 0);
        screenBottomLeft = new Vector2(0, 0);
    }
    
    public void update(float deltaTime) {
        x += velocity.x * deltaTime;
        y += velocity.y * deltaTime;
    }
    public boolean checkWallCollision() {
        if(Intersector.intersectSegmentCircle(screenTopLeft, screenBottomLeft, this, minimumTranslationVector)) {
            applyMTV(Side.RIGHT);
            if(velocity.x < 0)
                velocity.x = -velocity.x;
            return true;
        }
        else if(Intersector.intersectSegmentCircle(screenTopRight, screenBottomRight, this, minimumTranslationVector)) {
            applyMTV(Side.LEFT);
            if(velocity.x > 0)
                velocity.x = -velocity.x;
            return true;
        }
        if(Intersector.intersectSegmentCircle(screenTopLeft, screenTopRight, this, minimumTranslationVector)) {
            applyMTV(Side.BOTTOM);
            if(velocity.y > 0)
                velocity.y = -velocity.y;
            return true;
        }
        return false;
    }
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.circle(x, y, radius);
    }
    public boolean checkCollision(Paddle paddle) {
        Side side = collidesWith(paddle);
        if(side != null) {
            if(side == Side.TOP || side == Side.TOP_RIGHT || side == Side.TOP_LEFT) {
                float newAngle = MathUtils.atan2(y - (paddle.y + paddle.height/2f), x - (paddle.x + paddle.width/2f)) * (180 / MathUtils.PI);
                velocity.setAngleDeg(MathUtils.clamp(newAngle, 30, 150));
            }
            else {
                changeDirection(side);
            }
            return true;
        }
        return false;
    }
    public boolean checkCollision(Ball otherBall) {
        if(overlaps(otherBall)) {
            if(velocity.x / otherBall.velocity.x < 0) {
                velocity.x = -velocity.x;
                otherBall.velocity.x = -otherBall.velocity.x;
            }
            if(velocity.y / otherBall.velocity.y < 0) {
                velocity.y = -velocity.y;
                otherBall.velocity.y = -otherBall.velocity.y;
            }
            return true;
        }
        return false;
    }
    public void checkCollision(Block block) {
        if(!block.destroyed) {
            Side side = collidesWith(block);
            if(side != null) {
                changeDirection(side);
                block.destroyed = true;
                color.set(r.nextFloat() + .2f, r.nextFloat() + .2f, r.nextFloat() + .2f, 1);
                if(velocity.x > 0) {
                    velocity.x += 10;
                }
                else
                {
                    velocity.x -= 10;
                }
                if(velocity.y > 0) {
                    velocity.y += 3;
                }
                else
                {
                    velocity.y -= 3;
                }
            }
        }
    }
    public void changeDirection(Side side) {
        if(side == Side.LEFT || side == Side.TOP_LEFT || side == Side.BOTTOM_LEFT) {
            if(velocity.x > 0) {
                velocity.x = -velocity.x;
            }
        }
        else if(side == Side.RIGHT || side == Side.TOP_RIGHT || side == Side.BOTTOM_RIGHT) {
            if(velocity.x < 0) {
                velocity.x = -velocity.x;
            }
        }
        if(side == Side.TOP || side == Side.TOP_RIGHT || side == Side.TOP_LEFT) {
            if(velocity.y < 0) {
                velocity.y = -velocity.y;
            }
        }
        else if(side == Side.BOTTOM || side == Side.BOTTOM_RIGHT || side == Side.BOTTOM_LEFT) {
            if(velocity.y > 0) {
                velocity.y = -velocity.y;
            }
        }
        else {
            velocity.x = -velocity.x;
            velocity.y = -velocity.y;
        }
    }
    private Side collidesWith(Rectangle rect) {
        if(Intersector.overlaps(this, rect)) {
            if(Intersector.intersectSegmentCircle(rect.topLeft, rect.topLeft, this, minimumTranslationVector))
            {
                applyMTV(Side.TOP_LEFT);
                return Side.TOP_LEFT;
            }
            else if(Intersector.intersectSegmentCircle(rect.topRight, rect.topRight, this, minimumTranslationVector))
            {
                applyMTV(Side.TOP_RIGHT);
                return Side.TOP_RIGHT;
            }
            else if(Intersector.intersectSegmentCircle(rect.bottomLeft, rect.bottomLeft, this, minimumTranslationVector))
            {
                applyMTV(Side.BOTTOM_LEFT);
                return Side.BOTTOM_LEFT;
            }
            else if(Intersector.intersectSegmentCircle(rect.bottomRight, rect.bottomRight, this, minimumTranslationVector))
            {
                applyMTV(Side.BOTTOM_RIGHT);
                return Side.BOTTOM_RIGHT;
            }
            else if(Intersector.intersectSegmentCircle(rect.topLeft, rect.topRight, this, minimumTranslationVector))
            {
                applyMTV(Side.TOP);
                return Side.TOP;
            }
            else if(Intersector.intersectSegmentCircle(rect.bottomLeft, rect.bottomRight, this, minimumTranslationVector))
            {
                applyMTV(Side.BOTTOM);
                return Side.BOTTOM;
            }
            else if(Intersector.intersectSegmentCircle(rect.topLeft, rect.bottomLeft, this, minimumTranslationVector))
            {
                applyMTV(Side.LEFT);
                return Side.LEFT;
            }
            else if(Intersector.intersectSegmentCircle(rect.bottomRight, rect.topRight, this, minimumTranslationVector))
            {
                applyMTV(Side.RIGHT);
                return Side.RIGHT;
            }
        }
        return null;
    }
    private void applyMTV(Side side) {
        Vector2 angle = minimumTranslationVector.normal;
        float distance = minimumTranslationVector.depth;
        if(side == Side.LEFT || side == Side.TOP_LEFT || side == Side.BOTTOM_LEFT) {
            if(!(angle.angleDeg() >= 90 && angle.angleDeg() <= 270))
                angle.rotateDeg(180);
        }
        else if(side == Side.RIGHT || side == Side.TOP_RIGHT || side == Side.BOTTOM_RIGHT) {
            if(angle.angleDeg() >= 90 && angle.angleDeg() <= 270)
                angle.rotateDeg(180);
        }
        if(side == Side.TOP || side == Side.TOP_RIGHT || side == Side.TOP_LEFT) {
            if(angle.angleDeg() >= 180)
            {
                angle.rotateDeg(180);
                if(side == Side.TOP_LEFT)
                    angle.rotateDeg(90);
                else if(side == Side.TOP_RIGHT)
                    angle.rotateDeg(-90);
            }
        }
        else if(side == Side.BOTTOM || side == Side.BOTTOM_RIGHT || side == Side.BOTTOM_LEFT) {
            if(angle.angleDeg() <= 180)
            {
                angle.rotateDeg(180);
                if(side == Side.BOTTOM_LEFT)
                    angle.rotateDeg(-90);
                else if(side == Side.BOTTOM_RIGHT)
                    angle.rotateDeg(90);
            }
        }
        this.x += distance * Math.cos(angle.angleRad());
        this.y += distance * Math.sin(angle.angleRad());
    }
    
    public void setXSpeed(float xSpeed)
    {
        this.velocity.x = xSpeed;
    }
    
    public void setYSpeed(float ySpeed)
    {
        this.velocity.y = ySpeed;
    }
    
    public void init(float x, float y, float radius, float xSpeed, float ySpeed) {
        this.setX(x);
        this.setY(y);
        this.setRadius(radius);
        this.velocity.x = xSpeed;
        this.velocity.y = ySpeed;
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
