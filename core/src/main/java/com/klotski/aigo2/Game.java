package com.klotski.aigo2;


import com.klotski.logic.MoveStep;
import com.klotski.polygon.Chess;

import java.util.ArrayList;

public class Game {

    Board initialBoard;

    public static ArrayList<MoveStep> gameSolver(ArrayList<Chess> chesses) {
        GameSolver gameSolver = new GameSolver();
        Game game = new Game(chesses);
        gameSolver.solve(game);
        ArrayList<MoveStep> moveSteps = PrintUtility.printBestSolution(game, gameSolver);
        return moveSteps;


    }

    public Game(ArrayList<Chess> chesses) {
        Block[] blocks = new Block[chesses.size()];
        for (int i = 0; i < chesses.size(); i++) {
            Chess chess = chesses.get(i);
            BlockType blockType;
            if (chess.getChessWidth() == 1 && chess.getChessHeight() == 1) {
                blockType = BlockType.SINGLE;
            } else if (chess.getChessWidth() == 2 && chess.getChessHeight() == 2) {
                blockType = BlockType.SQUARE;
            } else if (chess.getChessWidth() == 2 && chess.getChessHeight() == 1) {
                blockType = BlockType.B_HORIZONTAL;
            } else {
                blockType = BlockType.VERTICAL;
            }

            BlockPrototype prototype = new BlockPrototype("aaa", blockType);
            Block block = new Block(prototype, chess.getPosition().getX(), (4 - chess.getPosition().getY() - (chess.getChessHeight() - 1)));
            blocks[i] = block;
        }
        Board board = new Board(blocks);
        this.initialBoard = board;
    }

}
