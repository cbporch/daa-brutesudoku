package sudokuPackage;

/*
 *  Project: Brute Force Sudoku Solver
 *  Class: Design and Analysis of Algorithms
 *  Professor Lobo
 *  Authors: 	 Christopher Porch <porchc0@students.rowan.edu>
 *  			 Dan Boehmke <boehmked2@students.rowan.edu>
 *  version: 2015.9.9
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver {
	public static BufferedReader br;

	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new FileReader("src\\testInput.txt"));

		int w = 0, h = 0;

		try {
			w = Integer.parseInt(br.readLine());
			h = Integer.parseInt(br.readLine());
		} catch (Exception e) {
			System.out.println("Error reading from file");
		} // end try catch
		
		int sdk[][] = new int[w * h][w * h];
		int y;
		
		// Use regex to pull numbers out of the string read in by
		// BufferedReader br.readLine() and populate 2D array sdk[][]
		Pattern p = Pattern.compile("\\d+");
		for (int x = 0; x < (w * h); x++) {
			y = 0;
			Matcher m = p.matcher(br.readLine());
			
			while (m.find() && y < (w * h)) {
				sdk[x][y] = Integer.parseInt(m.group());
				System.out.print(sdk[x][y] + " ");
				y++;
			} // end while
			System.out.println("");
		} // end for
		
		//checkRow Tester
		int[] trueArray = new int[] {1,4,3,2,5,6};
		int[] falseArray = new int[] {1,2,3,1,2,3};
		System.out.println("Should be true: " + checkRowColumn(trueArray));
		System.out.println("Should be false: " + checkRowColumn(falseArray));
		
	}// end main
	
	// Methods for checking rows/columns/blocks will go here?
	// I think its best to make each thing a separate method - Dan
	// Or, they will just be a part of main
	
	public static boolean checkRowColumn(int[] nums)
	{
		for(int i = 0; i < nums.length; i++) 
		{
			for(int j = 0; j < nums.length; j++)
			{
				if(i!=j && nums[i] == nums[j])
				{
					return false;
				}
			}// end j for
		}// end i for
		return true;
	}// end CheckRowColumn

}// end Driver
