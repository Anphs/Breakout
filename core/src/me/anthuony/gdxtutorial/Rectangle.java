package me.anthuony.gdxtutorial;

public abstract class Rectangle {
    int x;
    int y;
    int length;
    int height;

    public Rectangle(int x, int y, int length, int height) {
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
