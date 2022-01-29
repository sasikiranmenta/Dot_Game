package com.sasi.dotgame.service;

import com.sasi.dotgame.constants.Connection;
import com.sasi.dotgame.model.Dot;
import com.sasi.dotgame.model.Dots;

import java.io.IOException;

import static com.sasi.dotgame.constants.Connection.*;
import static com.sasi.dotgame.constants.Constants.*;
import static java.lang.Math.abs;
import static java.lang.Math.ceil;

public class Service {

    public static void clearConsole() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
        Process startProcess = pb.inheritIO().start();
        startProcess.waitFor();
    }

    public static void println(String x) {
        System.out.println(x);
    }

    public static void print(String x) {
        System.out.print(x);
    }

    public static int setSpace(int row, int column) {
        return Integer.toString(row * column).length();
    }

    public static void initializeBox(Dot[][] box) {
        int index = 1;
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < box[0].length; j++) {
                box[i][j] = new Dot(index);
                index++;
            }
        }
    }

    public static void printDotsInAThread(int millis) {
        Thread r = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.print(".");
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            println("\n");

        });
        r.start();
    }

    public static Connection connectDots(Dots dots, int dot1, int dot2) {
        Dot firstDot = getDot(dots, dot1);
        Dot secondDot = getDot(dots, dot2);
        Connection relationStatus = setRelationIfPossible(dots, firstDot, secondDot);
        if (relationStatus == CONNECTEDNOW) {
            println("Connected " + dot1 + " -> " + dot2);
        } else if (relationStatus == CONNECTIONNOTPOSSIBLE) {
            println("com.sasi.dotgame.constants.Connection not possible for " + dot1 + " ->  " + dot2);
        } else if (relationStatus == ALREADYCONNECTED) {
            println(dot1 + " ->  " + dot2 + " are already connected");
        }
        return relationStatus;
    }

    public static int checkSquareFormation(Dots dots, int dot1, int dot2) {
        Dot firstDot = getDot(dots, dot1);
        Dot secondDot = getDot(dots, dot2);
        Connection relationInfo = getRelation(firstDot, secondDot);

        int squaresFormed = 0;

        if (relationInfo == Connection.ROWRELATED) {
            if (firstDot.getUp() != null && firstDot.getUp().getRight() != null && firstDot.getUp().getRight() == secondDot.getUp()) {
                squaresFormed++;
            }
            if (firstDot.getDown() != null && firstDot.getDown().getRight() != null && firstDot.getDown().getRight() == secondDot.getDown()) {
                squaresFormed++;
            }
        } else {
            if (firstDot.getLeft() != null && firstDot.getLeft().getDown() != null && firstDot.getLeft().getDown() == secondDot.getLeft()) {
                squaresFormed++;
            }
            if (firstDot.getRight() != null && firstDot.getRight().getDown() != null && firstDot.getRight().getDown() == secondDot.getRight()) {
                squaresFormed++;
            }
        }
        return squaresFormed;
    }


    public static void printBox(Dots dots) {
        int currMaxDigit = 1;
        int spaces = dots.getMaxSpaces();
        for (int i = 0; i < dots.getBox().length; i++) {
            for (int j = 0; j < dots.getBox()[0].length; j++) {
                int index = dots.getBox()[i][j].getValue();
                if (dots.getBox()[i][j].getRight() != null) {
                    print(index + HYPHEN.repeat(spaces));
                } else {
                    print(index + SPACE.repeat(spaces));
                }
                getDot(dots, index);
                index++;
                if (Integer.toString(index).length() > currMaxDigit) {
                    currMaxDigit = Integer.toString(index).length();
                    spaces--;
                }
            }
            println("");
            for (int j = 0; j < dots.getBox()[0].length; j++) {
                if (dots.getBox()[i][j].getDown() != null) {
                    print(PIPE + SPACE.repeat(dots.getMaxSpaces()));
                } else {
                    print(SPACE + SPACE.repeat(dots.getMaxSpaces()));
                }
            }
            println("");
        }
    }

    private static Connection getRelation(Dot firstDot, Dot secondDot) {
        if (firstDot.getRight() == secondDot || firstDot.getLeft() == secondDot) {
            return ROWRELATED;
        }
        return COLUMNRELATED;
    }

    private static Connection setRelationIfPossible(Dots dots, Dot firstDot, Dot secondDot) {

        Connection rowConnectStatus = setRowConnection(dots, firstDot, secondDot);
        Connection columnConnectStatus = setColumnConnection(dots, firstDot, secondDot);
        if (rowConnectStatus == columnConnectStatus && rowConnectStatus == CONNECTIONNOTPOSSIBLE) {
            return CONNECTIONNOTPOSSIBLE;
        }
        if (rowConnectStatus == CONNECTEDNOW || columnConnectStatus == CONNECTEDNOW) {
            return CONNECTEDNOW;
        }
        if (rowConnectStatus == ALREADYCONNECTED || columnConnectStatus == ALREADYCONNECTED) {
            return ALREADYCONNECTED;
        }
        return CONNECTIONNOTPOSSIBLE;
    }

    private static Connection setColumnConnection(Dots dots, Dot firstDot, Dot secondDot) {
        int firstDotCol = getCol(dots, firstDot.getValue());
        int secondDotCol = getCol(dots, secondDot.getValue());
        if (abs(firstDotCol - secondDotCol) == 1 && abs(getRow(dots, firstDot.getValue()) - getRow(dots, secondDot.getValue())) == 0) {
            if (firstDot.getRight() == null && secondDot.getLeft() == null) {
                firstDot.setRight(secondDot);
                secondDot.setLeft(firstDot);
                return CONNECTEDNOW;
            } else {
                return ALREADYCONNECTED;
            }
        }

        return CONNECTIONNOTPOSSIBLE;
    }

    private static Connection setRowConnection(Dots dots, Dot firstDot, Dot secondDot) {
        int firstDotRow = getRow(dots, firstDot.getValue());
        int secondDotRow = (int) ceil((double) secondDot.getValue() / dots.getColSize()) - 1;
        if (abs(firstDotRow - secondDotRow) == 1 && abs(getCol(dots, firstDot.getValue()) - getCol(dots, secondDot.getValue())) == 0) {
            if (secondDot.getUp() == null && firstDot.getDown() == null) {
                secondDot.setUp(firstDot);
                firstDot.setDown(secondDot);
                return CONNECTEDNOW;
            } else {
                return ALREADYCONNECTED;
            }
        }
        return CONNECTIONNOTPOSSIBLE;
    }

    private static Dot getDot(Dots dots, int index) {
        int rowindex = getRow(dots, index);
        int colindex = getCol(dots, index);
        return dots.getBox()[rowindex][colindex];
    }

    private static int getCol(Dots dots, int index) {
        int colIndex = (index % dots.getColSize()) - 1;
        if (colIndex < 0) {
            colIndex = dots.getColSize() - 1;
        }
        return colIndex;
    }

    private static int getRow(Dots dots, int index) {
        return (int) ceil((double) index / dots.getColSize()) - 1;
    }

}
