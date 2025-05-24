package com.klotski.aigo2;


import com.klotski.logic.MoveStep;
import com.klotski.polygon.Chess;

import java.util.ArrayList;

public class Game {

	Board initialBoard;
	String name;


	public static ArrayList<MoveStep> gameSolver(ArrayList<Chess> chesses) {
		long startTime = System.nanoTime();
		GameSolver gameSolver = new GameSolver();
		Game game =  new Game(chesses) ;
		gameSolver.solve(game);
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		System.out.println("<html><body><font face=Courier>");
		ArrayList<MoveStep> moveSteps = PrintUtility.printFinalSolution(game, gameSolver);
		System.out.println("Run time " + ((float)duration)/1000000000.0 +" seconds <br></font></body></html>");
        return moveSteps;



	}

	public Game(String name, Board initialBoard) {
		this.name = name;
		this.initialBoard = initialBoard;
		initialBoard.stepNumberToInitialNode = 0;

	}

public  Game (ArrayList<Chess> chesses) {
	Block[] blocks = new Block[chesses.size()];
    for (int i = 0; i < chesses.size(); i++) {
        Chess chess = chesses.get(i);
        BlockType blockType=null;
        if(chess.getChessWidth()==1&&chess.getChessHeight()==1){
            blockType=BlockType.SINGLE;
        }else if(chess.getChessWidth()==2&&chess.getChessHeight()==2){
            blockType=BlockType.SQUARE;
        } else if (chess.getChessWidth()==2&&chess.getChessHeight()==1) {
            blockType=BlockType.B_HORIZONTAL;
        }else{
            blockType=BlockType.VERTICAL;
        }

        BlockPrototype prototype = new BlockPrototype("aaa",blockType);
        Block block = new Block(prototype, chess.getPosition().getX(), (4-chess.getPosition().getY()-(chess.getChessHeight()-1)));
        blocks[i] = block;
    }
    Board board = new Board(blocks);
    this.initialBoard = board;
}

	private static Game initGame四将连关() {
		// 四将连关
		Block[] blocks = new Block[10];

		blocks[0] = new Block(new BlockPrototype("曹操", BlockType.SQUARE), 0, 0);
		blocks[1] = new Block(new BlockPrototype("关羽", BlockType.B_HORIZONTAL), 2, 0);
		blocks[2] = new Block(new BlockPrototype("黄忠", BlockType.B_HORIZONTAL), 2, 1);
		blocks[3] = new Block(new BlockPrototype("张飞", BlockType.VERTICAL), 0, 2);
		blocks[4] = new Block(new BlockPrototype("赵云", BlockType.VERTICAL), 1, 2);
		blocks[5] = new Block(new BlockPrototype("马超", BlockType.B_HORIZONTAL), 2, 2);
		blocks[6] = new Block(new BlockPrototype("卒1", BlockType.SINGLE), 0, 4);
		blocks[7] = new Block(new BlockPrototype("卒2", BlockType.SINGLE), 2, 3);
		blocks[8] = new Block(new BlockPrototype("卒3", BlockType.SINGLE), 3, 3);
		blocks[9] = new Block(new BlockPrototype("卒4", BlockType.SINGLE), 3, 4);
		Board boardConfig = new Board(blocks);
		Game game = new Game("四将连关", boardConfig);
		return game;

	}


	private static Game initGameTest() {
		Block[] blocks = new Block[10];

		blocks[0] = new Block(new BlockPrototype("曹操", BlockType.SQUARE), 1, 1);
		blocks[3] = new Block(new BlockPrototype("张飞", BlockType.VERTICAL), 0, 2);
		blocks[4] = new Block(new BlockPrototype("赵云", BlockType.VERTICAL), 3, 3);
		blocks[6] = new Block(new BlockPrototype("卒1", BlockType.SINGLE), 0, 0);
		blocks[7] = new Block(new BlockPrototype("卒2", BlockType.SINGLE), 0, 1);
		blocks[8] = new Block(new BlockPrototype("卒3", BlockType.SINGLE), 3, 0);
		blocks[9] = new Block(new BlockPrototype("卒4", BlockType.SINGLE), 3, 1);
		blocks[10] = new Block(new BlockPrototype("卒5", BlockType.SINGLE), 0, 4);
		blocks[11] = new Block(new BlockPrototype("卒6", BlockType.SINGLE), 1, 3);
		blocks[12] = new Block(new BlockPrototype("卒7", BlockType.SINGLE), 1, 4);
		blocks[13] = new Block(new BlockPrototype("卒8", BlockType.SINGLE), 2, 3);
		blocks[14] = new Block(new BlockPrototype("卒9", BlockType.SINGLE), 2, 4);

		Board boardConfig = new Board(blocks);
		Game game = new Game("Test", boardConfig);
		return game;

	}

	private static Game initGame横刀立马() {
		// 横刀立马
		Block[] blocks = new Block[10];

		blocks[3] = new Block(new BlockPrototype("张飞", BlockType.VERTICAL), 0, 0);
		blocks[0] = new Block(new BlockPrototype("曹操", BlockType.SQUARE), 1, 0);
		blocks[4] = new Block(new BlockPrototype("赵云", BlockType.VERTICAL), 3, 0);
		blocks[2] = new Block(new BlockPrototype("黄忠", BlockType.VERTICAL), 0, 2);
		blocks[1] = new Block(new BlockPrototype("关羽", BlockType.B_HORIZONTAL), 1, 2);
		blocks[5] = new Block(new BlockPrototype("马超", BlockType.VERTICAL), 3, 2);
		blocks[6] = new Block(new BlockPrototype("卒1", BlockType.SINGLE), 0, 4);
		blocks[7] = new Block(new BlockPrototype("卒2", BlockType.SINGLE), 1, 3);
		blocks[8] = new Block(new BlockPrototype("卒3", BlockType.SINGLE), 2, 3);
		blocks[9] = new Block(new BlockPrototype("卒4", BlockType.SINGLE), 3, 4);
		Board boardConfig = new Board(blocks);
		Game game = new Game("横刀立马", boardConfig);
		return game;

	}




}
