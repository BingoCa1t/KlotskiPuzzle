package com.klotski.aigo2;

import com.klotski.logic.MoveStep;
import com.klotski.logic.Pos;

import java.util.ArrayList;
import java.util.HashMap;

public class PrintUtility {





    static int printSolutionCount = 0;



    static ArrayList<MoveStep> printBestSolution(Game game, GameSolver gameSolver) {
        ArrayList<MoveStep> moveSteps = new ArrayList<>();
        Board iterator = game.initialBoard;
        Board finalStep ;
        int stepNumberTillBestSolution = game.initialBoard.stepNumberToSolution;
        HashMap<Block, BlockPrototype> blockNameMapping = new HashMap<Block, BlockPrototype>();
        for (int i = 0; i < game.initialBoard.blocks.length; i++) {
            blockNameMapping.put(game.initialBoard.blocks[i], game.initialBoard.blocks[i].prototype);
        }
        do {
            assert iterator.connectedBoards.size() > 0;
            Board nextBoardBestSolution = null;
            Move moveBestSolution = null;

            if (iterator.stepNumberToSolution > 0) {
                ArrayList<Board> connected = new ArrayList<Board>(iterator.connectedBoards);

                connected.sort(iterator.new StepToSolutionComparator());
                nextBoardBestSolution = connected.get(0);
                if (nextBoardBestSolution.stepNumberToSolution >= iterator.stepNumberToSolution) {
                    nextBoardBestSolution = null;
                    moveBestSolution = null;
                } else {
                    moveBestSolution = iterator.calculcateMove(nextBoardBestSolution);
                    assert moveBestSolution != null;


                    Pos origenalPos = new Pos(moveBestSolution.oldBlock.xPos, 4 - moveBestSolution.oldBlock.yPos - (moveBestSolution.oldBlock.getPrototype().getBlockType().getHeight() - 1));
                    Pos newPos;
                    Pos newPos2;
                    MoveStep moveStep;
                    MoveStep moveStep2;

                    switch (moveBestSolution.moveType) {
                        case UP:
                            newPos = new Pos(origenalPos.getX(), origenalPos.getY() + 1);
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            break;
                        case DOWN:
                            newPos = new Pos(origenalPos.getX(), origenalPos.getY() - 1);
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            break;
                        case LEFT:
                            newPos = new Pos(origenalPos.getX() - 1, origenalPos.getY());
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            break;
                        case RIGHT:
                            newPos = new Pos(origenalPos.getX() + 1, origenalPos.getY());
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            break;
                        case UP2:
                            newPos = new Pos(origenalPos.getX(), origenalPos.getY() + 2);
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            break;
                        case DOWN2:
                            newPos = new Pos(origenalPos.getX(), origenalPos.getY() - 2);
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            break;
                        case LEFT2:
                            newPos = new Pos(origenalPos.getX() - 2, origenalPos.getY());
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            break;
                        case RIGHT2:
                            newPos = new Pos(origenalPos.getX() + 2, origenalPos.getY());
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            break;
                        case UPLEFT:
                            newPos = new Pos(origenalPos.getX(), origenalPos.getY() + 1);
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            newPos2 = new Pos(origenalPos.getX() - 1, origenalPos.getY() + 1);
                            moveStep2 = new MoveStep(newPos, newPos2);
                            moveSteps.add(moveStep2);
                            break;
                        case UPRIGHT:
                            newPos = new Pos(origenalPos.getX(), origenalPos.getY() + 1);
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            newPos2 = new Pos(origenalPos.getX() + 1, origenalPos.getY() + 1);
                            moveStep2 = new MoveStep(newPos, newPos2);
                            moveSteps.add(moveStep2);
                            break;
                        case DOWNLEFT:
                            newPos = new Pos(origenalPos.getX(), origenalPos.getY() - 1);
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            newPos2 = new Pos(origenalPos.getX() - 1, origenalPos.getY() - 1);
                            moveStep2 = new MoveStep(newPos, newPos2);
                            moveSteps.add(moveStep2);
                            break;
                        case DOWNRIGHT:
                            newPos = new Pos(origenalPos.getX(), origenalPos.getY() - 1);
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            newPos2 = new Pos(origenalPos.getX() + 1, origenalPos.getY() - 1);
                            moveStep2 = new MoveStep(newPos, newPos2);
                            moveSteps.add(moveStep2);
                            break;
                        case LEFTUP:
                            newPos = new Pos(origenalPos.getX() - 1, origenalPos.getY());
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            newPos2 = new Pos(origenalPos.getX() - 1, origenalPos.getY() + 1);
                            moveStep2 = new MoveStep(newPos, newPos2);
                            moveSteps.add(moveStep2);
                            break;
                        case LEFTDOWN:
                            newPos = new Pos(origenalPos.getX() - 1, origenalPos.getY());
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            newPos2 = new Pos(origenalPos.getX() - 1, origenalPos.getY() - 1);
                            moveStep2 = new MoveStep(newPos, newPos2);
                            moveSteps.add(moveStep2);
                            break;
                        case RIGHTUP:
                            newPos = new Pos(origenalPos.getX() + 1, origenalPos.getY());
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            newPos2 = new Pos(origenalPos.getX() + 1, origenalPos.getY() + 1);
                            moveStep2 = new MoveStep(newPos, newPos2);
                            moveSteps.add(moveStep2);
                            break;
                        case RIGHTDOWN:
                            newPos = new Pos(origenalPos.getX() + 1, origenalPos.getY());
                            moveStep = new MoveStep(origenalPos, newPos);
                            moveSteps.add(moveStep);
                            newPos2 = new Pos(origenalPos.getX() + 1, origenalPos.getY() - 1);
                            moveStep2 = new MoveStep(newPos, newPos2);
                            moveSteps.add(moveStep2);
                            break;
                        default:
                            break;
                    }
                }
            }
            assert stepNumberTillBestSolution == iterator.stepNumberToSolution;
            if (moveBestSolution != null) {
                blockNameMapping.put(moveBestSolution.newBlock, blockNameMapping.get(moveBestSolution.oldBlock));

            }
            stepNumberTillBestSolution = stepNumberTillBestSolution - 1;
            finalStep = iterator;
            iterator = nextBoardBestSolution;
        } while (iterator != null);

        for (Block block : finalStep.blocks) {
            block.prototype = blockNameMapping.get(block);
        }


        printSolutionCount++;
        return moveSteps;
    }

}
