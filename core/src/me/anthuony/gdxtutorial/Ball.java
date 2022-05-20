package me.anthuony.gdxtutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Ball extends Circle {
    float xSpeed;
    float ySpeed;
    Color color = Color.WHITE;
    Intersector.MinimumTranslationVector minimumTranslationVector = new Intersector.MinimumTranslationVector();

    public Ball(float x, float y, float radius, float xSpeed, float ySpeed) {
        super(x, y, radius);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    public void update(float deltaTime) {
        x += xSpeed * deltaTime;
        y += ySpeed * deltaTime;
        if(x <= radius) {
            if(xSpeed < 0)
                xSpeed = -xSpeed;
            Vector2 topLeft = new Vector2(0, Gdx.graphics.getHeight());
            Vector2 bottomLeft = new Vector2(0, 0);
            if(Intersector.intersectSegmentCircle(topLeft, bottomLeft, this, minimumTranslationVector))
                applyMTV();
        }
        else if(x >= (Gdx.graphics.getWidth() - radius)) {
            if(xSpeed > 0)
                xSpeed = -xSpeed;
            Vector2 topRight = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Vector2 bottomRight = new Vector2(Gdx.graphics.getWidth(), 0);
            if(Intersector.intersectSegmentCircle(topRight, bottomRight, this, minimumTranslationVector))
                applyMTV();
        }
        if(y >= (Gdx.graphics.getHeight() - radius)) {
            if(ySpeed > 0)
                ySpeed = -ySpeed;
            Vector2 topLeft = new Vector2(0, Gdx.graphics.getHeight());
            Vector2 topRight = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            if(Intersector.intersectSegmentCircle(topLeft, topRight, this, minimumTranslationVector))
                applyMTV();
        }
        /*else if(y <= radius) {
            if(ySpeed < 0)
                ySpeed = -ySpeed;
            Vector2 bottomLeft = new Vector2(0, 0);
            Vector2 bottomRight = new Vector2(Gdx.graphics.getWidth(), 0);
            if(Intersector.intersectSegmentCircle(bottomLeft, bottomRight, this, minimumTranslationVector))
                applyMTV();
        }*/
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
    public void checkCollision(Ball ball) {
        if(overlaps(ball)) {
            xSpeed = -xSpeed;
            ySpeed = -ySpeed;
        }
    }
    public void checkCollision(Block block) {
        if(!block.destroyed) {
            Side side = collidesWith(block);
            if(side != null) {
                changeDirection(side);
                block.destroyed = true;
                Random r = new Random();
                color = new Color(r.nextFloat() + .2f, r.nextFloat() + .2f, r.nextFloat() + .2f, 1);
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
            Vector2 topLeft = new Vector2(rect.x, rect.y + rect.height);
            Vector2 topRight = new Vector2(rect.x + rect.width, rect.y + rect.height);
            Vector2 bottomLeft = new Vector2(rect.x, rect.y);
            Vector2 bottomRight = new Vector2(rect.x + rect.width, rect.y);


            if(Intersector.intersectSegmentCircle(topLeft, topLeft, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.TOP_LEFT;
            }
            else if(Intersector.intersectSegmentCircle(topRight, topRight, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.TOP_RIGHT;
            }
            else if(Intersector.intersectSegmentCircle(bottomLeft, bottomLeft, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.BOTTOM_LEFT;
            }
            else if(Intersector.intersectSegmentCircle(bottomRight, bottomRight, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.BOTTOM_RIGHT;
            }
            else if(Intersector.intersectSegmentCircle(topLeft, topRight, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.TOP;
            }
            else if(Intersector.intersectSegmentCircle(bottomLeft, bottomRight, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.BOTTOM;
            }
            else if(Intersector.intersectSegmentCircle(topLeft, bottomLeft, this, minimumTranslationVector))
            {
                applyMTV();
                return Side.LEFT;
            }
            else if(Intersector.intersectSegmentCircle(bottomRight, topRight, this, minimumTranslationVector))
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
}
