package com.workshopProblem;

import java.util.*;

public class TicTacToe {
    public static void main(String[] args) {
        System.out.println("Welcome to Tic Tac Toe Game");
        System.out.println();
        String[][] board = new String[3][3];

        PlayingTTT play = new PlayingTTT();

        Scanner sc = new Scanner(System.in);
        playingTheGame(board, play, sc);

        sc.close();
    }
    public static void playingTheGame(String[][] board, PlayingTTT play, Scanner sc) {

        List<Integer> PlayerPosition = new ArrayList<>();
        List<Integer> cpuPosition = new ArrayList<>();
        List<Integer> occupiedPosition = new ArrayList<>();
        play.settingBoard(board);

        boolean toss = play.decidingToss();

        String[] SymbolArray = new String[2];
        SymbolArray = play.choosingSymbol(toss, sc, SymbolArray);
        String playerSymbol = SymbolArray[0];
        String cpuSymbol = SymbolArray[1];

        boolean playerResult = false;
        boolean cpuResult = false;
        do {

            if (toss) {

                play.displayBoard(board);

                // calling a function to get cell index from the user.
                int index = checkingIfPresent(sc, occupiedPosition);

                occupiedPosition.add(index);
                PlayerPosition.add(index);

                // calling a function for setting the symbol at the given index on the board.
                board = play.settingSymbol(board, playerSymbol, index);

                // calling a function to check for the winning condition.
                playerResult = play.checkForWin(PlayerPosition);
                if (playerResult) {
                    System.out.println("\nPlayer Wins");
                    break;
                }
                toss = false;

            } else {
                // calling a function to get cell index from the cpu.
                int index = cpuMove(occupiedPosition, PlayerPosition, cpuPosition);

                cpuPosition.add(index);
                occupiedPosition.add(index);

                // calling a function for setting the symbol at the given index on the board.
                board = play.settingSymbol(board, cpuSymbol, index);

                // calling a function to check for the winning condition.
                cpuResult = play.checkForWin(cpuPosition);
                if (cpuResult) {
                    System.out.println("\ncpu Wins");
                    break;
                }
                toss = true;
            }

            // checking for draw.
            if (play.checkForDraw(board)) {
                System.out.println("\nIt's a draw");
                break;
            }
        } while (playerResult == false && cpuResult == false);

        play.displayBoard(board);
    }
    private static int checkingIfPresent(Scanner sc, List<Integer> occupiedPosition) {
        System.out.println("enter a the position you want to place your symbol, between 1-9");
        int index = sc.nextInt();
        while (occupiedPosition.contains(index)) {
            System.out.println("enter a different position " + index + " is already present");
            index = sc.nextInt();
        }
        return index;
    }
    private static int cpuMove(List<Integer> occupiedPosition, List<Integer> playerPosition,
                               List<Integer> cpuPosition) {

        int index = 0;
        List<Integer> random = new ArrayList<>();
        List<Integer> corner = new ArrayList<>(Arrays.asList(1, 3, 7, 9));
        List<Integer> side = new ArrayList<>(Arrays.asList(2, 4, 6, 8));

        int firstIndex = index;

        // calling a function to get a winning chance.
        index = possibleBestPosition(cpuPosition, occupiedPosition, index);
        if (firstIndex != index) {
            return index;
        }

        // calling a function to stop my opponent from winning.
        index = possibleBestPosition(playerPosition, occupiedPosition, index);
        if (firstIndex != index) {
            return index;
        }

        // calling a function to get a corner index if not filled.
        while (occupiedPosition.containsAll(corner) == false) {
            index = possiblePosition(occupiedPosition, random, index, corner);
            if (firstIndex != index) {
                return index;
            }
        }

        if (occupiedPosition.contains(5) == false) {
            return 5;
        }

        // calling a function to get a corner index if not filled.
        while (occupiedPosition.containsAll(corner) == false) {
            index = possiblePosition(occupiedPosition, random, index, side);
            if (firstIndex != index) {
                return index;
            }
        }
        return index;
    }

    private static int generateRandom(int maxRandom) {
        Random r = new Random();
        return r.nextInt(maxRandom);
    }
    private static int possiblePosition(List<Integer> occupiedPosition, List<Integer> random, int index,
                                        List<Integer> toCompare) {

        random.clear();

        while (random.size() <= 4) {
            int r = generateRandom(4);
            while (random.contains(r)) {
                r = generateRandom(4);
            }
            if (occupiedPosition.contains(toCompare.get(r))) {
                random.add(r);
                break;
            } else {
                return toCompare.get(r);
            }
        }
        return index;
    }
    private static int possibleBestPosition(List<Integer> position, List<Integer> occupiedPosition, int index) {
        int[][] winning = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 }, { 1, 5, 9 },
                { 3, 5, 7 } };

        // variables to calculate index and count the number of matching element.
        int k = 0;
        List<Integer> unMatched = new ArrayList<>();

        while (k < 8) {
            int matchCount = 0;
            unMatched.clear();
            for (int i = 0; i < winning[k].length; i++) {
                if (occupiedPosition.contains(winning[k][i])) {
                    if (position.contains(winning[k][i])) {
                        matchCount++;
                    }
                } else {
                    unMatched.add(winning[k][i]);
                }
            }
            if (matchCount == 2 && unMatched.size() == 1) {
                index = unMatched.get(0);
                break;
            }
            k++;
        }
        return index;
    }

}

class PlayingTTT {
    public void settingBoard(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = " ";
            }
        }
    }
    public String[] choosingSymbol(boolean toss, Scanner sc, String[] symbolArray) {
        if (toss) {
            System.out.println("Enter a symbol");
            String symbol = sc.nextLine();
            if (symbol == "X") {
                symbolArray[0] = "O";
                symbolArray[1] = "X";

            } else {
                symbolArray[0] = "X";
                symbolArray[1] = "O";
            }
        } else {
            if (decidingToss()) {
                symbolArray[0] = "X";
                symbolArray[1] = "O";
            } else {
                symbolArray[0] = "O";
                symbolArray[1] = "X";
            }
        }
        return symbolArray;
    }
    public boolean decidingToss() {
        Random r = new Random();
        return r.nextBoolean();
    }
    public void displayBoard(String[][] board) {
        String str = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (j != 2) {
                    str += board[i][j] + "|";
                } else {
                    str += board[i][j];
                }
            }
            if (i != 2) {
                str += "\n-+-+-\n";
            }

        }
        System.out.println("\n" + str);
    }
    public boolean checkForWin(List<Integer> board) {
        if (checkRowsForWin(board) || checkColumnsForWin(board) || checkDiagonalsForWin(board)) {
            return true;
        }
        return false;
    }
    private boolean checkRowsForWin(List<Integer> board) {
        List<Integer> row0 = new ArrayList<>(Arrays.asList(1,2,3));
        List<Integer> row1 = new ArrayList<>(Arrays.asList(4,5,6));
        List<Integer> row2 = new ArrayList<>(Arrays.asList(7,8,9));
        if (board.containsAll(row0) || board.containsAll(row1) || board.containsAll(row2)) {
            return true;
        }
        return false;
    }
    private boolean checkColumnsForWin(List<Integer> board) {
        List<Integer> col0 = new ArrayList<>(Arrays.asList(1,4,7));
        List<Integer> col1 = new ArrayList<>(Arrays.asList(2,5,8));
        List<Integer> col2 = new ArrayList<>(Arrays.asList(3,6,9));
        if (board.containsAll(col0) || board.containsAll(col1) || board.containsAll(col2)) {
            return true;
        }
        return false;
    }
    private boolean checkDiagonalsForWin(List<Integer> board) {
        List<Integer> dig0 = new ArrayList<>(Arrays.asList(1,5,9));
        List<Integer> dig1 = new ArrayList<>(Arrays.asList(3,5,7));
        if (board.containsAll(dig0) || board.containsAll(dig1)) {
            return true;
        }
        return false;
    }
    public boolean checkForDraw(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == " ") {
                    return false;
                }
            }
        }
        return true;
    }
    public String[][] settingSymbol(String[][] board, String symbol, int index) {
        index -= 1;
        int x = (int) Math.floor(index / 3);
        int y = index % 3;
        board[x][y] = symbol;
        return board;
    }
}