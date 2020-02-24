package app;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.omg.Messaging.SyncScopeHelper;

public class Main {
	
	public static final int M_SIZE = 9;
	
	public static int matrix[][] = new int[M_SIZE][M_SIZE];
	public static int matrix2[][] = new int[M_SIZE][M_SIZE];
	public static String filePath = "assignment.txt";
	
	public static int prev , nextX, nextY, max = 0, startX, startY;
	public static int[][] neighbours = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
	
	public static int[][] diagonal = {{-1,-1}, {-1,1}, {1,1}, {1,-1}};
	
	public static int counter = 0;
	public static ArrayList<String> visited = new ArrayList<>();
	
	public static void main(String[] args) {
		loadMatrix();
		printMatrix(matrix);
		fillOneOff();
		printMatrix(matrix);
		findMax();
		fillVisitedMatrix();
		copyMatrix();
		upperDFS(nextX, nextY, max);
		printMatrix(matrix2);
	}
	
	
	public static boolean upperDFS(int x, int y, int prev){
		if (x < 0 || x >= M_SIZE || y < 0 || y >= M_SIZE) return false;
		if (max == 81)  return DFS(startX, startY, max+1);
		
		for (int i = 0; i < neighbours.length; i++){
			if (y + neighbours[i][1] < M_SIZE && y + neighbours[i][1] >= 0 &&
					x + neighbours[i][0] < M_SIZE && x + neighbours[i][0] >= 0 && 
							matrix[y + neighbours[i][1]][x + neighbours[i][0]] == 0){
				
				matrix[y + neighbours[i][1]][x + neighbours[i][0]] = prev + 1;
				
				//printMatrix(matrix);
				
				if (prev+1 < 81 && upperDFS(x + neighbours[i][0], y + neighbours[i][1], prev+1)) return true;
				if (prev+1 == 81) {
					copyMatrix();
					boolean test = DFS(startX, startY, max+1);
					if (test) return true;
				}
				
				matrix[y + neighbours[i][1]][x + neighbours[i][0]] = 0;
			}
		}
		
		return false;
	}
	
	
	public static boolean DFS(int x, int y, int prev){
		if (x < 0 || x >= M_SIZE || y < 0 || y >= M_SIZE) return false;
		boolean test = checkIfSolved(x, y);
		if (test) return DFS(nextX, nextY, matrix2[y][x]);
		if (!test && visited.contains((prev-2)+"") && !checkDiagonal(x, y)){
			return false;
		}
		for (int i = 0; i < neighbours.length; i++){
			if (y + neighbours[i][1] < M_SIZE && y + neighbours[i][1] >= 0 &&
					x + neighbours[i][0] < M_SIZE && x + neighbours[i][0] >= 0 && 
							matrix2[y + neighbours[i][1]][x + neighbours[i][0]] == 0){
				
				visited.add((prev-2)+"");
				matrix2[y + neighbours[i][1]][x + neighbours[i][0]] = prev - 2;
				
				//printMatrix(matrix2);
				
				if (visited.size() == max){
					return true;
				}
				test = DFS(x + neighbours[i][0], y + neighbours[i][1], matrix2[y][x]);
				if (test) return true;
				visited.remove((prev-2)+"");
				matrix2[y + neighbours[i][1]][x + neighbours[i][0]] = 0;
			}
		}
		return false;
	}
	
	public static void copyMatrix(){
		for (int i = 0; i < M_SIZE; i++){
			for (int j = 0; j < M_SIZE; j++){
				matrix2[i][j] = matrix[i][j];
			}
		}
	}
	
	public static boolean checkIfSolved(int x, int y){
		for (int i = 0; i < neighbours.length; i++){
			if (x + neighbours[i][0] >=0 && x + neighbours[i][0] < M_SIZE && 
					y + neighbours[i][1] >= 0 && y + neighbours[i][1] < M_SIZE && matrix2[y + neighbours[i][1]][x + neighbours[i][0]] == matrix2[y][x]-1){
				nextX = x + neighbours[i][0];
				nextY = y + neighbours[i][1];
				prev = matrix2[x][y];
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkDiagonal(int x, int y){
		for (int i = 0; i < diagonal.length; i++){
			if (x + diagonal[i][0] >=0 && x + diagonal[i][0] < M_SIZE && 
					y + diagonal[i][1] >= 0 && y + diagonal[i][1] < M_SIZE && matrix2[y + diagonal[i][1]][x + diagonal[i][0]] == matrix2[y][x]-2){
				return true;
			}
		}
		return false;
	}
	
	public static void fillVisitedMatrix(){
		for (int i = 0; i < M_SIZE; i++){
			for (int j = 0; j < M_SIZE; j++){
				if (matrix[i][j] != 0){
					visited.add(matrix[i][j]+"");
				}
			}
		}
	}
	
	public static void findMax(){
		for (int i = 0; i < M_SIZE; i++){
			for (int j = 0; j < M_SIZE; j++){
				if (matrix[i][j] > max){
					max = matrix[i][j];
					nextX = j;
					nextY = i;
					startX = j;
					startY = i;
				}
			}
		}
	}
	
	public static void fillOneOff(){
		for (int i = 0; i < M_SIZE; i++){
			for (int j = 0; j < M_SIZE; j++){
				
				if (matrix[i][j] == 0){
					
					if (j-1>=0 && j+1<M_SIZE && matrix[i][j-1] != 0 && matrix[i][j+1] != 0 && Math.abs(matrix[i][j-1] - matrix[i][j+1]) == 2){
						matrix[i][j] = matrix[i][j-1]<matrix[i][j+1]?matrix[i][j-1]+1:matrix[i][j-1]-1;
					}
					
					if (i-1>=0 && i+1<M_SIZE && matrix[i-1][j] != 0 && matrix[i+1][j] != 0 && Math.abs(matrix[i-1][j] - matrix[i+1][j]) == 2){
						matrix[i][j] = matrix[i-1][j]<matrix[i+1][j]?matrix[i-1][j]+1:matrix[i-1][j]-1;
					}
					
				}
				
			}
		}
	}
	
	public static void printMatrix(int matrix[][]){
		System.out.println("____________________________________________________________________________");
		for (int i = 0; i < M_SIZE; i++){
			for (int j = 0; j < M_SIZE; j++){
				System.out.print(matrix[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println("____________________________________________________________________________");
	}
	
	public static void loadMatrix(){
		Scanner s = null;
		try {
			s = new Scanner(new File(filePath));
			int i = 0;
			while (s.hasNextLine()){
				String str = s.nextLine();
				matrix[i++] = Arrays.asList(str.split(" ")).stream().mapToInt(Integer::parseInt).toArray();
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			s.close();
		}
	}

}
