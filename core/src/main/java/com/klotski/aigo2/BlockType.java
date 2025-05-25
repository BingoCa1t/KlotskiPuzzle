package com.klotski.aigo2;

public enum BlockType {
    SQUARE, B_HORIZONTAL, VERTICAL, SINGLE;

    public int getHeight() {
        return switch (this)
        {
            case SQUARE, VERTICAL -> 2;
            case B_HORIZONTAL, SINGLE -> 1;
        };
    }
}
