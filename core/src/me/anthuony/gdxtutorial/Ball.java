package me.anthuony.gdxtutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball {
    int x;
    int y;
    int size;
    int xSpeed;
    int ySpeed;
    Color color = Color.WHITE;

    public Ball(int x, int y, int size, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    public void update(Game game) {
        x += xSpeed;
        y += ySpeed;
        if(x < size|| x > (Gdx.graphics.getWidth() - size)) {
            xSpeed = -xSpeed;
        }
        if(y < size || y > (Gdx.graphics.getHeight() - size)) {
            ySpeed = -ySpeed;
        }
    }
    public void draw(ShapeRenderer renderer) {
        renderer.setColor(color);
        renderer.circle(x, y, size);
    }
    public void checkCollision(Paddle paddle) {
        if(collidesWith(paddle)) {
            color = Color.WHITE;
            ySpeed = -ySpeed;
        }
        else{
            color = Color.WHITE;
        }
    }
    public void checkCollision(Block block) {
        if(!block.destroyed && collidesWith(block)) {
            ySpeed = -ySpeed;
            block.destroyed = true;
        }
    }
    private boolean collidesWith(Paddle paddle) {
        if(paddle.x - paddle.length/2f < x + size && paddle.x + paddle.length/2f > x - size && paddle.y - paddle.height/2f < y + size && paddle.y + paddle.height/2f > y - size)
        {
            return true;
        }
        return false;
    }

    private boolean collidesWith(Block block) {
        if(block.x - block.length/2f < x + size && block.x + block.length/2f > x - size && block.y - block.height/2f < y + size && block.y + block.height/2f > y - size)
        {
            return true;
        }
        return false;
    }
}
