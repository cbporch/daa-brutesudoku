package sudokuPackage;

/*
 *  Project: Brute Force Sudoku Solver
 *  Class: Design and Analysis of Algorithms
 *  Professor Lobo
 *  Authors: 	Christopher Porch <porchc0@students.rowan.edu>
 *  		Dan Boehmke <boehmked2@students.rowan.edu>
 *  		Brian Grillo <>
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

		// checkRow Tester
		int[] trueArray = new int[] { 1, 4, 3, 2, 5, 6 };
		int[] falseArray = new int[] { 1, 2, 3, 1, 2, 3 };
		System.out.println("Should be true: " + checkRowColumn(trueArray));
		System.out.println("Should be false: " + checkRowColumn(falseArray));

		/*
		 * Somewhere in here should go code to check for zeros, and start to fill them in with
		 * potential numbers. I think maybe we should duplicate our 2D array each time and test on that 
		 * one, so as not to mess up the original too badly while searching? That or somehow keep
		 * track of the indexes the zeros are in.
		 * 															-Chris
		 */
		
		// have not tested this block yet, might need to be inside a bigger while loop 
		// but it should test one row and one column at a time, so it only has to
		// run (w * h) times instead of 2(w * h) times              -Chris
		int tempRow[] = new int[w*h];
		int tempCol[] = new int[w*h];
		for(int i = 0;i < (w*h);i++) {
			for(int j = 0;j < (w*h);j++) {
				tempRow[j] = sdk[i][j]; // pulls numbers from each row
				tempCol[j] = sdk[j][i]; // pulls number from each column
			}// end for
			
			if(checkRowColumn(tempRow)) {
				// catch potential row
			} else {
				// fail, exit loop, try new numbers?
				i += (w * h);
			}// end if else
		
			if(checkRowColumn(tempCol)) {
				// catch potential column
			} else {
				// fail, exit loop, try new numbers?
				i += (w * h);
			}// end if else
		}// end for
		
		
	}// end main

	public static boolean checkRowColumn(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			for (int j = 0; j < nums.length; j++) {
				if (i != j && nums[i] == nums[j]) {
					return false;
				}
			} // end j for
		} // end i for
		return true;
	}// end CheckRowColumn
	
	public static boolean checkBlocks(int[][] arr) {
		return false;
		// placeholder, needs to be written, idea is to break the 2D array into separate
		// 1D array blocks, and use Dan's checkRowColumn to verify each has no duplicates 
		// block by block
	}// end checkBlocks
}// end Driver
