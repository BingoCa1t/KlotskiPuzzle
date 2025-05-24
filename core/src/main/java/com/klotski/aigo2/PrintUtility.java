package com.klotski.aigo2;

import com.klotski.logic.MoveStep;
import com.klotski.logic.Pos;

import java.util.ArrayList;
import java.util.HashMap;

public class PrintUtility {

    private static boolean PRINT_BEST_SOLUTION = false;
    private static boolean PRINT_FINAL_SOLUTION = true;
    private static boolean PRINT_PROPAGATE_SOLUTION = false;
    private static boolean PRINT_FOUND_SOLUTION = false;
    private static boolean PRINT_ADD_SOLUTION = false;
    private static boolean PRINT_TEST_MOVE = false;
    private static boolean SIMPLE_SOLUTION = true;

    protected static void printAddSolution(Board iterator) {
        if (!PRINT_ADD_SOLUTION) return;
        String tempString;
        tempString = "Add best solution: " + iterator.idOfBoardExplored;
        tempString += ", previousBoard = ";

        System.out.println(tempString);
    }

    protected static void printFoundSolution(Board iterator) {
        if (!PRINT_FOUND_SOLUTION) return;
        String tempString;
        tempString = "Found best solution: " + iterator.idOfBoardExplored;
        tempString += ", previousBoard = ";

        System.out.println(tempString);
    }

    static int printSolutionCount = 0;

    static ArrayList<MoveStep> printFinalSolution(Game game, GameSolver gameSolver) {
        if (!PRINT_FINAL_SOLUTION) return null;
        boolean temp = PRINT_BEST_SOLUTION;
        PRINT_BEST_SOLUTION = true;
        ArrayList<MoveStep> moveSteps= printBestSolution(game, gameSolver);
        PRINT_BEST_SOLUTION = temp;
        return moveSteps;
    }


    static ArrayList<MoveStep> printBestSolution(Game game, GameSolver gameSolver) {
        ArrayList<MoveStep> moveSteps = new ArrayList<>();
        if (!PRINT_BEST_SOLUTION) return null;
        String tempString = game.name + " Best Solution #" + printSolutionCount;
        System.out.println(tempString + "<br>");
        System.out.println("<table bgcolor=#333333>");
        Board iterator = game.initialBoard;
        Board finalStep = null;
        int stepNumberTillBestSolution = game.initialBoard.stepNumberToSolution;
        HashMap<Block, BlockPrototype> blockNameMapping = new HashMap<Block, BlockPrototype>();
        for (int i = 0; i < game.initialBoard.blocks.length; i++) {
            blockNameMapping.put(game.initialBoard.blocks[i], game.initialBoard.blocks[i].prototype);
        }
        do {
            assert iterator.connectedBoards.size() > 0;
            Board nextBoardBestSolution = null;
            Move moveBestSolution = null;
            tempString = "";
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
                    if (blockNameMapping.get(moveBestSolution.oldBlock) == null) {
                        System.out.println("test");
                    }
                    int stepNumber = game.initialBoard.stepNumberToSolution - iterator.stepNumberToSolution + 1;
                    if (stepNumber % 5 == 1) tempString = "<tr>";
                    tempString += "<td bgcolor=#ffffff>";
                        tempString += stepNumber + ": Board #" + iterator.idOfBoardExplored
                            + "->" + ((BlockPrototype) blockNameMapping.get(moveBestSolution.oldBlock)).name + "(" + moveBestSolution.oldBlock.hashString() + ") " + moveBestSolution.moveType;
                        Pos origenalPos = new Pos(moveBestSolution.oldBlock.xPos, 4 - moveBestSolution.oldBlock.yPos - ( moveBestSolution.oldBlock.getPrototype().getBlockType().getHeight()-1));
                        Pos newPos = new Pos();
                        Pos newPos2 = new Pos();
                       MoveStep moveStep;
                       MoveStep moveStep2 ;

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
                                newPos = new Pos(origenalPos.getX() , origenalPos.getY() + 1);
                                moveStep = new MoveStep(origenalPos, newPos);
                                moveSteps.add(moveStep);
                                newPos2 = new Pos(origenalPos.getX() - 1, origenalPos.getY()+1);
                                moveStep2 = new MoveStep(newPos, newPos2);
                                moveSteps.add(moveStep2);
                                break;
                            case UPRIGHT:
                                newPos = new Pos(origenalPos.getX(), origenalPos.getY() + 1);
                                moveStep = new MoveStep(origenalPos, newPos);
                                moveSteps.add(moveStep);
                                newPos2 = new Pos(origenalPos.getX() + 1, origenalPos.getY()+1);
                                moveStep2 = new MoveStep(newPos, newPos2);
                                moveSteps.add(moveStep2);
                                break;
                            case DOWNLEFT:
                                newPos = new Pos(origenalPos.getX(), origenalPos.getY() - 1);
                                moveStep = new MoveStep(origenalPos, newPos);
                                moveSteps.add(moveStep);
                                newPos2 = new Pos(origenalPos.getX() - 1, origenalPos.getY()-1);
                                moveStep2 = new MoveStep(newPos, newPos2);
                                moveSteps.add(moveStep2);
                                break;
                            case DOWNRIGHT:
                                newPos = new Pos(origenalPos.getX(), origenalPos.getY() - 1);
                                moveStep = new MoveStep(origenalPos, newPos);
                                moveSteps.add(moveStep);
                                newPos2 = new Pos(origenalPos.getX() + 1, origenalPos.getY()-1);
                                moveStep2 = new MoveStep(newPos, newPos2);
                                moveSteps.add(moveStep2);
                                break;
                            case LEFTUP:
                                newPos = new Pos(origenalPos.getX() - 1, origenalPos.getY());
                                moveStep = new MoveStep(origenalPos, newPos);
                                moveSteps.add(moveStep);
                                newPos2 = new Pos(origenalPos.getX() - 1, origenalPos.getY()+1);
                                moveStep2 = new MoveStep(newPos, newPos2);
                                moveSteps.add(moveStep2);
                                break;
                            case LEFTDOWN:
                                newPos = new Pos(origenalPos.getX() - 1, origenalPos.getY());
                                moveStep = new MoveStep(origenalPos, newPos);
                                moveSteps.add(moveStep);
                                newPos2 = new Pos(origenalPos.getX() - 1, origenalPos.getY()-1);
                                moveStep2 = new MoveStep(newPos, newPos2);
                                moveSteps.add(moveStep2);
                                break;
                            case RIGHTUP:
                                newPos = new Pos(origenalPos.getX() + 1, origenalPos.getY());
                                moveStep = new MoveStep(origenalPos, newPos);
                                moveSteps.add(moveStep);
                                newPos2 = new Pos(origenalPos.getX() + 1, origenalPos.getY()+1);
                                moveStep2 = new MoveStep(newPos, newPos2);
                                moveSteps.add(moveStep2);
                                break;
                            case RIGHTDOWN:
                                newPos = new Pos(origenalPos.getX() + 1, origenalPos.getY());
                                moveStep = new MoveStep(origenalPos, newPos);
                                moveSteps.add(moveStep);
                                newPos2 = new Pos(origenalPos.getX() + 1, origenalPos.getY()-1);
                                moveStep2 = new MoveStep(newPos, newPos2);
                                moveSteps.add(moveStep2);
                                break;
                            default:
                                break;
                        }



                    tempString += "</td>";
                    if (stepNumber % 5 == 0) tempString += "</tr>";
                }
                if (!SIMPLE_SOLUTION) tempString += " (" + PrintUtility.getBoardInfo(iterator) + ")";
            }
            System.out.println(tempString);
            assert stepNumberTillBestSolution == iterator.stepNumberToSolution;
            if (moveBestSolution != null) {
                blockNameMapping.put(moveBestSolution.newBlock, blockNameMapping.get(moveBestSolution.oldBlock));

            }
            stepNumberTillBestSolution = stepNumberTillBestSolution - 1;
            finalStep = iterator;
            iterator = nextBoardBestSolution;
        } while (iterator != null);
        if (game.initialBoard.stepNumberToSolution % 5 != 0) {
            for (int i = game.initialBoard.stepNumberToSolution % 5; i < 5; i++) {
                System.out.println("<td bgcolor=#dddddd>&nbsp;</td>");
            }
        }
        System.out.println("</table><br>");
        System.out.println("<table><tr><td>");
        printBoardLayout(game.initialBoard);
        System.out.println("</td><td width=50 align=center valign=center><font size=20>&#x2192;</font></td><td>");
        for (Block block : finalStep.blocks) {
            block.prototype = blockNameMapping.get(block);
        }
        printBoardLayout(finalStep);
        System.out.println("</td></tr></table>");
        tempString = game.name + " " + gameSolver.getClass().getSimpleName() + " End Best Solution #" + printSolutionCount;
        System.out.println(tempString + "<br>");
        System.out.println("boardConfigsExplored.size=" + gameSolver.boardConfigsExplored.size() + "<br>");
        printSolutionCount++;
        return moveSteps;
    }


    static String getBoardInfo(Board boardConfig) {
        String tempString = "#" + boardConfig.idOfBoardExplored + ", stepNumber=" + boardConfig.stepNumberToInitialNode + ", stepNumberTillBestSolution = " + boardConfig.stepNumberToSolution;
        tempString += ", hashString=" + boardConfig.hashString();
        tempString += ", isBorderLineNode=" + boardConfig.isBorderLineNode;
        return tempString;
    }


    public static void printNoUpdate(GameSolver gameSolver, Board boardConfig, Board connectedBoard) {
        if (!PRINT_PROPAGATE_SOLUTION) return;
        String tempString = "No Update for " + PrintUtility.getBoardInfo(boardConfig) + " from" + PrintUtility.getBoardInfo(connectedBoard);
        System.out.println(tempString);
    }

    public static void printUpdateSolution(GameSolver gameSolver, Board boardConfig, Board connectedBoard) {
        if (!PRINT_PROPAGATE_SOLUTION) return;
        String tempString = "Update for " + PrintUtility.getBoardInfo(boardConfig) + " from " + PrintUtility.getBoardInfo(connectedBoard);
        System.out.println(tempString);
    }

    public static void printPropagateSolution(Board existingSolution) {
        if (!PRINT_PROPAGATE_SOLUTION) return;
        System.out.println("Propagate the best solution for #" + existingSolution.idOfBoardExplored + "...");
    }

    public static void printTestMove(Board boardConfig, int i, Block oldBlock, MoveType moveType, Board nextBoardInTest) {
        if (!PRINT_TEST_MOVE) return;
        System.out.println("#" + boardConfig.stepNumberToInitialNode + "." + i + "." + moveType + "->" + oldBlock.prototype.name + " " + moveType + "->" + nextBoardInTest.stepNumberToInitialNode + "(" + PrintUtility.getBoardInfo(nextBoardInTest) + ")");
    }

    public static void printReverseTestMove() {
        if (!PRINT_TEST_MOVE) return;
        System.out.println("Reverse");
    }


    public static void printBoardLayout(Board boardConfig) {
        String tempString = "";
        System.out.println("<table bgcolor=#333333>");
        for (int i = 0; i <= Board.MAXYPOS; i++) {
            tempString = "<tr>";
            for (int j = 0; j <= Board.MAXXPOS; j++) {
                boolean isEmpty = true;
                for (int k = 0; k < boardConfig.blocks.length; k++) {
                    if (j >= boardConfig.blocks[k].xPos && j < boardConfig.blocks[k].xPos + boardConfig.blocks[k].prototype.width
                        && i >= boardConfig.blocks[k].yPos && i < boardConfig.blocks[k].yPos + boardConfig.blocks[k].prototype.height) {
                        isEmpty = false;
                        if (i == boardConfig.blocks[k].yPos && j == boardConfig.blocks[k].xPos) {
                            switch (boardConfig.blocks[k].prototype.blockType) {
                                case SQUARE:
                                    tempString += "<td colspan=2 rowspan=2 width=70 height=70 align=center valign=cener bgcolor=#dddddd>" + boardConfig.blocks[k].prototype.name + "</td>";
                                    break;
                                case B_HORIZONTAL:
                                    tempString += "<td colspan=2 width=70 height=35 align=center valign=cener bgcolor=#dddddd>" + boardConfig.blocks[k].prototype.name + "</td>";
                                    break;
                                case VERTICAL:
                                    tempString += "<td rowspan=2 width=35 height=70 align=center valign=cener bgcolor=#dddddd>" + boardConfig.blocks[k].prototype.name.substring(0, 1) + "<br>" + boardConfig.blocks[k].prototype.name.substring(1) + "</td>";
                                    break;
                                case SINGLE:
                                    tempString += "<td width=35 height=35 align=center valign=cener bgcolor=#dddddd>" + boardConfig.blocks[k].prototype.name + "</td>";
                                    break;
                            }
                        }
                        break; // break out of k
                    }
                }
                if (isEmpty) {
                    tempString += "<td width=35 height=35 align=center valign=cener bgcolor=#ffffff>&nbsp;</td>";
                }

            }
            tempString += "</tr>";
            System.out.println(tempString);
        }
        System.out.println("</table><br>");

    }


}
