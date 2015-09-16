package sudokuPackage;

/*
 *  Project: Brute Force Sudoku Solver
 *  Class: Design and Analysis of Algorithms
 *  Professor Lobo
 *  Authors:	Christopher Porch <porchc0@students.rowan.edu>
 *  			Dan Boehmke <boehmked2@students.rowan.edu>
 *  			Brian Grillo <grillo88@students.rowan.edu>
 *  version: 2015.9.15
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
		}
		// System.out.println("Finished");
		if (!passed){
			System.out.println("No Solution");
		}
		tim.stop();
		System.out.println("Total Runtime: " + tim.getDuration() + " milliseconds");
	}// end main

	public static int[][] clonePuzzle(int[][] puzz) {
		int tempSDK[][] = new int[size][size];
		for (int x = 0; x < (size); x++) {
			for (int y = 0; y < (size); y++) {
				tempSDK[x][y] = puzz[x][y];
			}
		}
		return tempSDK;
	}

	public static int[] createNumbers(int size) {
		System.out.println("\n" + size + " zeroes\n");
		int tempNums[] = new int[size];
		for (int i = 0; i < size; i++)
			tempNums[i] = 1;
		return tempNums;
	}

	public static int[] incrementNumbers(int[] nums) {
		int tempNums[] = new int[nums.length];
		tempNums = nums;
		try{
			tempNums[nums.length - 1]++; // last int incremented
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Error, no zeros found in puzzle!\n");
		}

		for (int i = nums.length - 1; i > 0; i--) {
			if (tempNums[i] == (size + 1)) {
				tempNums[i] = 1;
				tempNums[i - 1]++;
			}
		}
		return tempNums;
	}

	public static void listNumbers(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			System.out.print(nums[i]);
		}
		System.out.println();
	}

	public static int[][] fillInNumbers(int[] nums, int[][] tempNums) {
		int place = 0;
		for (int x = 0; x < (size); x++) {
			for (int y = 0; y < (size); y++) {
				if (tempNums[x][y] == 0) {
					tempNums[x][y] = nums[place];
					place++;
				}
			}
		}
		return tempNums;
	}

	public static void listPuzzle(int[][] puzz) {
		for (int x = 0; x < (size); x++) {
			for (int y = 0; y < (size); y++) {
				System.out.print(puzz[x][y] + " ");

			}
			System.out.print("\n");
		}
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
		int x_offset = 0, y_offset = 0, point = 0;
		boolean pass = false;
		int temparray[] = new int[size];
		for (int i = 0; i < size; i++) {
			point = 0;
			for (int j = x_offset; j < (w + x_offset); j++) {
				for (int k = y_offset; k < h + y_offset; k++) {
					temparray[point] = arr[j][k];
					point++;
				}
			}
			pass = checkRowColumn(temparray);
			if (x_offset < size) {
				x_offset += w;
				if (x_offset == size) {
					x_offset = 0;
					y_offset += h;
					if (y_offset == size) {
						// checked all blocks
						return pass;
					}
				}
			}
		}
		return false;
	}// end checkBlocks

	public static boolean checkPuzzle(int[][] puzz) {
		boolean pass = false;
		do {
			int tempRow[] = new int[size];
			int tempCol[] = new int[size];
			for (int i = 0; i < (size); i++) {
				for (int j = 0; j < (size); j++) {
					tempRow[j] = puzz[i][j]; // pulls numbers from each row
					tempCol[j] = puzz[j][i]; // pulls numbers from each column
				} // end for

				if ((checkRowColumn(tempRow))&&(checkRowColumn(tempCol))) {
					pass = true;
				} else {
					// fail, exit loop, try new numbers?
					i += (size);
					pass = false;
					// System.out.println("Failed Row");
				} // end if else
			} // end for

			if (pass) {// if all rows/columns pass
				pass = checkBlocks(puzz);
				return pass;
			}
			// System.out.println("Testing");
		} while (pass); // end do while
		return pass;
	}

	public static String skipComments(String line) throws IOException {
		try {
			while (line.trim().substring(0, 1).equals("c")) {
				// line is a comment, skip
				line = br.readLine();
			} // end while
		} catch (Exception e) {
			System.out.println("Error reading from file");
		}
		return line;
	}
}// end Driver
