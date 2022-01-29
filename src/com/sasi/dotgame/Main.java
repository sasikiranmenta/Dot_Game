package com.sasi.dotgame;

import com.sasi.dotgame.constants.Connection;
import com.sasi.dotgame.model.Dot;
import com.sasi.dotgame.model.Dots;

import java.io.IOException;
import java.util.Scanner;

import static com.sasi.dotgame.constants.Connection.CONNECTEDNOW;
import static com.sasi.dotgame.constants.Constants.PLAYER1;
import static com.sasi.dotgame.constants.Constants.PLAYER2;
import static com.sasi.dotgame.service.Service.*;


public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        int player1 = 0;
        int player2 = 0;
        boolean one = true;
        Dots dots = new Dots();
        Scanner sc = new Scanner(System.in);
        println("Enter rows and columns of the box to be drawn respectively");

        dots.setRowSize(sc.nextInt());
        dots.setColSize(sc.nextInt());
        dots.setBox(new Dot[dots.getRowSize()][dots.getColSize()]);
        initializeBox(dots.getBox());
        dots.setMaxBoxes((dots.getColSize() - 1) * (dots.getRowSize() - 1));
        dots.setMaxSpaces(setSpace(dots.getRowSize(), dots.getColSize()));

        String player;

        while (dots.getMaxBoxes() != player1 + player2) {

            player = one ? PLAYER1 : PLAYER2;
            printDotsInAThread(99);
            Thread.sleep(1500);
            println("");
            println("Player1 score: " + player1);
            println("Player2 score: " + player2);
            println("");
            printBox(dots);
            println(player + " turn");
            println("");
            println("Enter the dot numbers to connect");
            int dot1 = sc.nextInt();
            int dot2 = sc.nextInt();
            if (dot1 > dot2) {
                int temp = dot1;
                dot1 = dot2;
                dot2 = temp;
            }
            clearConsole();

            printDotsInAThread(198);
            Thread.sleep(2000);
            Connection connectionStatus = connectDots(dots, dot1, dot2);
            if (connectionStatus == CONNECTEDNOW) {
                int squareFormed = checkSquareFormation(dots, dot1, dot2);
                if (squareFormed > 0) {
                    println("HOLA! Squares formed: " + squareFormed);
                    if (one) {
                        player1 += squareFormed;

                    } else {
                        player2 += squareFormed;
                    }
                } else {
                    one = !one;
                }
            } else {
                one = !one;
            }

        }
        println("Game Completed");
        println(player1 > player2 ? "Player1 won" : "Player2 won");
    }
}
