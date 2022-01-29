package com.sasi.dotgame.model;

public class Dot {
    private Dot up;
    private Dot down;
    private Dot left;
    private Dot right;
    private int value;

    public Dot(int value) {
        this.value = value;
    }

    public Dot getUp() {
        return up;
    }

    public void setUp(Dot up) {
        this.up = up;
    }

    public Dot getDown() {
        return down;
    }

    public void setDown(Dot down) {
        this.down = down;
    }

    public Dot getLeft() {
        return left;
    }

    public void setLeft(Dot left) {
        this.left = left;
    }

    public Dot getRight() {
        return right;
    }

    public void setRight(Dot right) {
        this.right = right;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
