package com.klotski.aigo2;

public enum BlockType {
    SQUARE, B_HORIZONTAL, VERTICAL, SINGLE;

    public int getHeight() {
        switch (this) {
            case SQUARE:
                return 2;

            case B_HORIZONTAL:
                return 1;
            case VERTICAL:
                return 2;
            case SINGLE:
                return 1;
        }
        return -1;
    }
}
