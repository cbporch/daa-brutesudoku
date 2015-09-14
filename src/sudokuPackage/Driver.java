package sudokuPackage;

/*
 *  Project: Brute Force Sudoku Solver
 *  Class: Design and Analysis of Algorithms
 *  Professor Lobo
 *  Authors:	Christopher Porch <porchc0@students.rowan.edu>
 *  			Dan Boehmke <boehmked2@students.rowan.edu>
 *  			Brian Grillo <>
 *  version: 2015.9.11
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver {
	public static BufferedReader br;
	public static Timer tim;
	public static void main(String[] args) throws IOException {
		tim = new Timer();
		tim.start();
		br = new BufferedReader(new FileReader("src\\testInput.txt"));

		int w = 0, h = 0, size = 0;
		

		try {
			String line = skipComments(br.readLine());
			w = Integer.parseInt(line);
			line = skipComments(br.readLine());
			h = Integer.parseInt(line);
			size = w*h;
		} catch (Exception e) {
			System.out.println("Error reading from file");
		} // end try catch

		int sdk[][] = new int[size][size];
		int y;

		// Use regex to pull numbers out of the string read in by
		// BufferedReader br.readLine() and populate 2D array sdk[][]
		Pattern p = Pattern.compile("\\d+");
		for (int x = 0; x < (size); x++) {
			y = 0;
			String line = skipComments(br.readLine());
			Matcher m = p.matcher(line);

			while (m.find() && y < (size)) {
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
		System.out.println("List of six numbers: " + createNumbers(size));

		/*
		 * Somewhere in here should go code to check for zeros, and start to
		 * fill them in with potential numbers. I think maybe we should
		 * duplicate our 2D array each time and test on that one, so as not to
		 * mess up the original too badly while searching? That or somehow keep
		 * track of the indexes the zeros are in. -Chris
		 */

		// have not tested this block yet, might need to be inside a bigger
		// while loop
		// but it should test one row and one column at a time, so it only has
		// to
		// run (size) times instead of 2(size) times -Chris
		int tempRow[] = new int[size];
		int tempCol[] = new int[size];
		for (int i = 0; i < (size); i++) {
			for (int j = 0; j < (size); j++) {
				tempRow[j] = sdk[i][j]; // pulls numbers from each row
				tempCol[j] = sdk[j][i]; // pulls number from each column
			} // end for

			if (checkRowColumn(tempRow)) {
				// catch potential row
			} else {
				// fail, exit loop, try new numbers?
				i += (size);
			} // end if else

			if (checkRowColumn(tempCol)) {
				// catch potential column
			} else {
				// fail, exit loop, try new numbers?
				i += (size);
			} // end if else
			
			tim.stop();
			System.out.println("Total Runtime: " + tim.getDuration() + " milliseconds");
			
		} // end for

	}// end main
	public static int[] createNumbers(int size){
		int tempNums[] = new int[size];
		for(int i = 0; i < size; i++)
			tempNums[i] = 1;
		return tempNums;
	}
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
		// placeholder, needs to be written, idea is to break the 2D array into
		// separate
		// 1D array blocks, and use Dan's checkRowColumn to verify each has no
		// duplicates
		// block by block
	}// end checkBlocks
	
	public static String skipComments(String line) throws IOException{
		try{
			while(line.trim().substring(0, 1).equals("c")){
				// line is a comment, skip
				line = br.readLine();
			}// end while
		}catch(Exception e){
			System.out.println("Error reading from file");
		}
		return line;
	}
}// end Driver
