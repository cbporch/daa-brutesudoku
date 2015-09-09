/*
 *  Project: Brute Force Sudoku Solver
 *  Class: Design and Analysis of Algorithms
 *  Professor Lobo
 *  Authors: Christopher Porch <porchc0@students.rowan.edu>
 *  version: 2015.9.9
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Driver {
	public static BufferedReader br;
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new FileReader("src\\testInput.txt"));
		
		int w = 0, h = 0;
		
		try{
			w = Integer.parseInt(br.readLine());
			h = Integer.parseInt(br.readLine());
		}catch(Exception e){
			System.out.println("Error reading from file");
		}
		System.out.println(w + " " + h);
		int sdk[][] = new int[w*h][w*h];
		
	}

}
