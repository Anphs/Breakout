package me.anthuony.gdxtutorial;

public abstract class Rectangle {
    float x;
    float y;
    float length;
    float height;

    public Rectangle(float x, float y, float length, float height) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.height = height;
    }
}

enum Side {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT,
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
}
