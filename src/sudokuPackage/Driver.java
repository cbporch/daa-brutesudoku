package sudokuPackage;

/*
 *  Project: Brute Force Sudoku Solver
 *  Class: Design and Analysis of Algorithms
 *  Professor Lobo
 *  Authors:	Christopher Porch <porchc0@students.rowan.edu>
 *  			Dan Boehmke <boehmked2@students.rowan.edu>
 *  			Brian Grillo <grillo88@students.rowan.edu>
 *  version: 2015.9.21
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver {
	public static BufferedReader br;
	public static Timer tim;
	public static int w = 0, h = 0, size = 0;

	public static void main(String[] args) throws IOException {
		tim = new Timer();
		tim.start();
		br = new BufferedReader(new FileReader("src\\testInput.txt"));

		// int w = 0, h = 0, size = 0;

		try {
			String line = skipComments(br.readLine());
			w = Integer.parseInt(line);
			line = skipComments(br.readLine());
			h = Integer.parseInt(line);
			size = w * h;
		} catch (Exception e) {
			System.out.println("Error reading from file");
		} // end try catch

		int sdk[][] = new int[size][size];
		int zeroCounter = 0;

		// Use regex to pull numbers out of the string read in by
		// BufferedReader br.readLine() and populate 2D array sdk[][]
		// counts number of zeros in the base file
		Pattern p = Pattern.compile("\\d+");
		for (int x = 0; x < (size); x++) {
			int y = 0;
			String line = skipComments(br.readLine());
			Matcher m = p.matcher(line);

			while (m.find() && y < (size)) {
				sdk[x][y] = Integer.parseInt(m.group());
				System.out.print(sdk[x][y] + " ");
				if (sdk[x][y] == 0) {
					zeroCounter++;
				} // end if
				y++;
			} // end while
			System.out.println("");

		} // end for

		boolean passed = false;
		int tempSDK[][] = new int[size][size];
		int[] numArray = createNumbers(zeroCounter);
		do {
			tempSDK = clonePuzzle(sdk);
			tempSDK = fillInNumbers(numArray, tempSDK);
			passed = checkPuzzle(tempSDK);
			numArray = incrementNumbers(numArray);
		} while (!passed && numArray[0] <= size);
		
		if (passed) {
			listPuzzle(tempSDK);
			System.out.println("------------");
			listNumbers(numArray);
			System.out.println("------------");
		}// end if
		
		if (!passed){
			System.out.println("No Solution");
		}// end if
		tim.stop();
		System.out.println("Total Runtime: " + tim.getDuration() + " milliseconds");
	}// end main

	/*
	 * Returns a duplicate of a given 2D array puzzle matrix
	 */
	public static int[][] clonePuzzle(int[][] puzz) {
		int tempSDK[][] = new int[size][size];
		for (int x = 0; x < (size); x++) {
			for (int y = 0; y < (size); y++) {
				tempSDK[x][y] = puzz[x][y];
			}// end for
		}// end for
		return tempSDK;
	}// end clonePuzzle

	/*
	 * Creates and returns an array of ones of a given length to begin testing for a solution 
	 */
	public static int[] createNumbers(int size) {
		System.out.println("\n" + size + " zeroes\n");
		int tempNums[] = new int[size];
		for (int i = 0; i < size; i++) {
			tempNums[i] = 1;
		}// end for
		return tempNums;
	}// end createNumbers

	/*
	 * Increments the given array by one, while handling rollovers, returns the
	 * incremented array
	 */
	public static int[] incrementNumbers(int[] nums) {
		int tempNums[] = new int[nums.length];
		tempNums = nums;
		try {
			tempNums[nums.length - 1]++; // last int incremented
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error, no zeros found in puzzle!\n");
		}// end try-catch

		for (int i = nums.length - 1; i > 0; i--) {
			if (tempNums[i] == (size + 1)) {
				tempNums[i] = 1;
				tempNums[i - 1]++;
			}// end if
		}// end for
		return tempNums;
	}//end incrementNumbers

	/*
	 * Prints a given array as a string, used to output the
	 * specific numbers used in the solution
	 */
	public static void listNumbers(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			System.out.print(nums[i]);
		}// end for
		System.out.println();
	}// end listNumbers

	/*
	 * Given a 2D array, replaces all instances of zeros with the given solution
	 * numbers, and then returns that array
	 */
	public static int[][] fillInNumbers(int[] nums, int[][] tempNums) {
		int place = 0;
		for (int x = 0; x < (size); x++) {
			for (int y = 0; y < (size); y++) {
				if (tempNums[x][y] == 0) {
					tempNums[x][y] = nums[place];
					place++;
				}// end if
			}// end for
		}// end for
		return tempNums;
	}// end fillInNumbers

	/*
	 * Prints out a given puzzle, with similar formatting to the input file.
	 */
	public static void listPuzzle(int[][] puzz) {
		for (int x = 0; x < (size); x++) {
			for (int y = 0; y < (size); y++) {
				System.out.print(puzz[x][y] + " ");
			}// end for
			System.out.print("\n");
		}// end for
	}// end listPuzzle

	/*
	 * Checks a given array row/column for correctness, 
	 * returns true if the row/column is valid, false if not
	 */
	public static boolean checkRowColumn(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			for (int j = 0; j < nums.length; j++) {
				if (i != j && nums[i] == nums[j]) {
					return false;
				}// end if
			} // end j for
		} // end i for
		return true;
	}// end CheckRowColumn

	/*
	 * Breaks off individual blocks from a given puzzle, treats them as arrays,
	 * then sends them to checkRowColumn to verify. Returns a boolean, true if
	 * all the blocks are valid, false if not
	 */
	public static boolean checkBlocks(int[][] arr) {
		int x_offset = 0, y_offset = 0, point = 0;
		boolean pass = false;
		int temparray[] = new int[size];
		for (int i = 0; i < size; i++) {
			point = 0;
			for (int j = x_offset; j < (w + x_offset); j++) {
				for (int k = y_offset; k < h + y_offset; k++) {
					temparray[point] = arr[j][k];
					point++;
				}// end for
			}// end for
			pass = checkRowColumn(temparray);
			if (x_offset < size) {
				x_offset += w;
				if (x_offset == size) {
					x_offset = 0;
					y_offset += h;
					if (y_offset == size) {
						// checked all blocks
						return pass;
					}// end if
				}// end if
			}// end if
		}// end for
		return false;
	}// end checkBlocks

	/*
	 * Checks the given puzzle for correctness, using the checkRowColumn and 
	 * checkBlocks methods. returns true if the puzzle is a valid
	 * solution, false if not.
	 */
	public static boolean checkPuzzle(int[][] puzz) {
		boolean pass = false;
		do {
			int tempRow[] = new int[size];
			int tempCol[] = new int[size];
			for (int i = 0; i < (size); i++) {
				for (int j = 0; j < (size); j++) {
					tempCol[j] = puzz[j][i]; // pulls numbers from each column
				} // end for

				tempRow = puzz[i];
				
				if ((checkRowColumn(tempRow)) && (checkRowColumn(tempCol))) {
					pass = true;
				} else {
					// fail, exit loop
					i += (size);
					pass = false;
				} // end if else
			} // end for

			if (pass) {// if all rows/columns pass
				pass = checkBlocks(puzz);
				return pass;
			}// end if
		} while (pass); // end do while
		return pass;
	}// end checkPuzzle

	/*
	 * Method to skip comments in the given input file. If the given line
	 * begins with a 'c' the line is skipped until a line that does not start
	 * with a 'c' is reached, then returns that line.
	 */
	public static String skipComments(String line) throws IOException {
		try {
			while (line.trim().substring(0, 1).equals("c")) {
				// line is a comment, skip
				line = br.readLine();
			} // end while
		} catch (Exception e) {
			System.out.println("Error reading from file");
		}// end try-catch
		return line;
	}// end skipComments
}// end Driver
