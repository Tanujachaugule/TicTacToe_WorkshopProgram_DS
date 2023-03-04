package com.workshopProblem;

import java.util.Random;

public class TicTacToe {
    public static void main(String[] args) {
        System.out.println("Welcome to Tic Tac Toe Game");
        System.out.println();
         final PlayingTTT play = new PlayingTTT();
         final String[][] board = new String[3][3];
        play.settingBoard(board);
        String firstPlay = " ";
        if(play.decidingToss()){
            firstPlay = firstPlay+"player";
        }else{
            firstPlay = firstPlay+"computer";
        }
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
}

