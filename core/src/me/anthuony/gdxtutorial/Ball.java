package me.anthuony.gdxtutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class Ball {
    float x;
    float y;
    float size;
    float xSpeed;
    float ySpeed;
    Color color = Color.WHITE;

    public Ball(float x, float y, float size, float xSpeed, float ySpeed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    public void update() {
        x += xSpeed;
        y += ySpeed;
        if(x < size|| x > (Gdx.graphics.getWidth() - size)) {
            xSpeed = -xSpeed;
        }
        if(y > (Gdx.graphics.getHeight() - size)) {
            ySpeed = -ySpeed;
        }
    }
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.circle(x, y, size);
    }
    public void checkCollision(Paddle paddle) {
        Side side = collidesWith(paddle);
        if(side != null) {
            changeDirection(side);
        }
        else{
            //color = Color.WHITE;
        }
    }
    public void checkCollision(Block block) {
        if(!block.destroyed) {
            Side side = collidesWith(block);
            if(side != null) {
                changeDirection(side);
                block.destroyed = true;
                Random r = new Random();
                color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1);
                if(xSpeed > 0) {
                    xSpeed += 0.05;
                }
                else
                {
                    xSpeed -= 0.05;
                }
                if(ySpeed > 0) {
                    ySpeed += 0.05;
                }
                else
                {
                    ySpeed -= 0.05;
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
        x += xSpeed;
        y += ySpeed;
    }
    private Side collidesWith(Rectangle rect) {
        boolean greaterThanLeftSide = rect.x - rect.length/2f <= x + size;
        boolean lessThanRightSide = rect.x + rect.length/2f >= x - size;
        boolean greaterThanBottom = rect.y - rect.height/2f <= y + size;
        boolean lessThanTop = rect.y + rect.height/2f >= y - size;
        if(greaterThanLeftSide && lessThanRightSide && greaterThanBottom && lessThanTop)
        {
            if(rect.x - rect.length/2f >= x + size) {
                if(rect.y + rect.height/2f <= y)
                {
                    //color = Color.PINK;
                    return Side.TOP_LEFT;
                }
                else if(rect.y - rect.height/2f >= y)
                {
                    //color = Color.SKY;
                    return Side.BOTTOM_LEFT;
                }
                //color = Color.YELLOW;
                return Side.LEFT;
            }
            else if(rect.x + rect.length/2f <= x - size)
            {
                if(rect.y + rect.height/2f <= y)
                {
                    //color = Color.BROWN;
                    return Side.TOP_RIGHT;
                }
                else if(rect.y - rect.height/2f >= y)
                {
                    //color = Color.CHARTREUSE;
                    return Side.BOTTOM_RIGHT;
                }
                //color = Color.ORANGE;
                return Side.RIGHT;
            }
            else if(rect.y + rect.height/2f <= y)
            {
                //color = Color.GREEN;
                return Side.TOP;
            }
            else if(rect.y - rect.height/2f >= y)
            {
                //color = Color.RED;
                return Side.BOTTOM;
            }
        }
        return null;
    }
}
