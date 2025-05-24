package com.klotski.aigo2;

import java.math.BigInteger;

public class BlockPrototype {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name = "aaa";
	public int width;

    public void setHeight(int height) {
        this.height = height;
    }

    public int height;

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public BlockType blockType;


	public BlockPrototype(String name, BlockType blockType){
		this.name = name;
		this.blockType = blockType;
		switch (blockType) {
			case SQUARE:
				width = 2;
				height = 2;
				break;
			case B_HORIZONTAL:
				width = 2;
				height = 1;
				break;
			case VERTICAL:
				width = 1;
				height = 2;
				break;
			case SINGLE:
				width = 1;
				height = 1;
				break;
		}
	}
}
