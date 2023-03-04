package com.workshopProblem;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        System.out.println("Welcome to Tic Tac Toe Game");
        System.out.println();
        final PlayingTTT play = new PlayingTTT();
        Scanner sc = new Scanner(System.in);
        final String[][] board = new String[3][3];
        play.settingBoard(board);
        String player;
        String cpu;
        if (play.decidingToss()) {
            System.out.println("Enter a character between X or O");
            player = sc.next();
            if (player == "X") {
                cpu = "O";
            } else {
                cpu = "X";
            }
        } else {
            if (play.decidingToss()) {
                cpu = "O";
                player = "X";
            } else {
                cpu = "X";
                player = "O";
            }
        }
        sc.close();
    }
}
class PlayingTTT {
    public void settingBoard(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = ". ";
            }
        }
    }
    public boolean decidingToss(){
        final Random r = new Random();
        return r.nextBoolean();
    }
    public void displayBoard(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
}