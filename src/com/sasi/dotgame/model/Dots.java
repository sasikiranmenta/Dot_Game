package com.sasi.dotgame.model;

public class Dots {
    private Integer maxSpaces = 0;
    private Dot[][] box;
    private int rowSize;
    private int colSize;
    private int maxBoxes;

    public Integer getMaxSpaces() {
        return maxSpaces;
    }

    public void setMaxSpaces(Integer maxSpaces) {
        this.maxSpaces = maxSpaces;
    }

    public Dot[][] getBox() {
        return box;
    }

    public void setBox(Dot[][] box) {
        this.box = box;
    }

    public int getRowSize() {
        return rowSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }

    public int getMaxBoxes() {
        return maxBoxes;
    }

    public void setMaxBoxes(int maxBoxes) {
        this.maxBoxes = maxBoxes;
    }
}
