package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Numbrix {

	/*
	 * 49 0 51 0 53 0 75 0 81
		0 0 0 0 0 0 0 0 0
		37 0 0 0 0 0 0 0 79
		0 0 0 0 0 0 0 0 0
		31 0 0 0 0 0 0 0 69
		0 0 0 0 0 0 0 0 0
		29 0 0 0 0 0 0 0 65
		0 0 0 0 0 0 0 0 0
		27 0 23 0 9 0 7 0 5
	 * 
	 */
	
	final static String[] board1 = {
	        "00,00,00,00,00,00,00,00,00",
	        "00,00,46,45,00,55,74,00,00",
	        "00,38,00,00,43,00,00,78,00",
	        "00,35,00,00,00,00,00,71,00",
	        "00,00,33,00,00,00,59,00,00",
	        "00,17,00,00,00,00,00,67,00",
	        "00,18,00,00,11,00,00,64,00",
	        "00,00,24,21,00,01,02,00,00",
	        "00,00,00,00,00,00,00,00,00"};
	
	final static String[] board = {
	        "49,00,51,00,53,00,75,00,81",
	        "00,00,46,45,00,55,74,00,00",
	        "37,00,00,00,00,00,00,00,79",
	        "00,00,00,00,00,00,00,00,00",
	        "31,00,00,00,00,00,00,00,69",
	        "00,00,00,00,00,00,00,00,00",
	        "29,00,00,00,00,00,00,00,65",
	        "00,00,00,00,00,00,00,00,00",
	        "27,00,23,00,09,00,07,00,05"};
	 
	    final static int[][] moves = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
	 
	    static int[][] grid;
	    static int[] clues;
	    static int totalToFill;
	 
	    public static void main(String[] args) {
	        int nRows = board.length + 2;
	        int nCols = board[0].split(",").length + 2;
	        int startRow = 0, startCol = 0;
	 
	        grid = new int[nRows][nCols];
	        totalToFill = (nRows - 2) * (nCols - 2);
	        List<Integer> lst = new ArrayList<>();
	 
	        for (int r = 0; r < nRows; r++) {
	            Arrays.fill(grid[r], -1);
	 
	            if (r >= 1 && r < nRows - 1) {
	 
	                String[] row = board[r - 1].split(",");
	 
	                for (int c = 1; c < nCols - 1; c++) {
	                    int val = Integer.parseInt(row[c - 1]);
	                    if (val > 0)
	                        lst.add(val);
	                    if (val == 1) {
	                        startRow = r;
	                        startCol = c;
	                    }
	                    grid[r][c] = val;
	                }
	            }
	        }
	 
	        clues = lst.stream().sorted().mapToInt(i -> i).toArray();
	 
	        if (solve(startRow, startCol, 1, 0))
	            printResult();
	    }
	 
	    static boolean solve(int r, int c, int count, int nextClue) {
	        if (count > totalToFill)
	            return true;
	 
	        if (grid[r][c] != 0 && grid[r][c] != count)
	            return false;
	 
	        if (grid[r][c] == 0 && nextClue < clues.length)
	            if (clues[nextClue] == count)
	                return false;
	 
	        int back = grid[r][c];
	        if (back == count)
	            nextClue++;
	 
	        grid[r][c] = count;
	        for (int[] move : moves)
	            if (solve(r + move[1], c + move[0], count + 1, nextClue))
	                return true;
	 
	        grid[r][c] = back;
	        return false;
	    }
	 
	    static void printResult() {
	        for (int[] row : grid) {
	            for (int i : row) {
	                if (i == -1)
	                    continue;
	                System.out.printf("%2d ", i);
	            }
	            System.out.println();
	        }
	    }
	
}
