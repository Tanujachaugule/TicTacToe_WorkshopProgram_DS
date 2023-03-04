package com.workshopProblem;

public class TicTacToe {
    public static void main(String[] args) {
        System.out.println("Welcome to Tic Tac Toe Game");
        System.out.println();
        PlayingTTT play = new PlayingTTT();
        String[][] board = new String[3][3];
        play.settingBoard(board);
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
}

