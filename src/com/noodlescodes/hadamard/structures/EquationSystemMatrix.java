package com.noodlescodes.hadamard.structures;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class EquationSystemMatrix extends Matrix {

	private int[] rhs;
	private int[] bounds;
	private static int[][] gramMat;
	private static int gramOrder;
	public static int numberSolutions = 0;

	public EquationSystemMatrix(int size) {
		super(size);
	}

	public EquationSystemMatrix(int rows, int columns) {
		super(rows, columns);
	}

	public EquationSystemMatrix(int rows, int columns, int[] r, int[] b) {
		super(rows, columns, r, b);
		rhs = r;
		bounds = b;
	}

	public EquationSystemMatrix(int rows, int columns, int[] r, int[] b, int[][] mat) {
		super(rows, columns, r, b, mat);
		rhs = r;
		bounds = b;
	}

	public EquationSystemMatrix(String file) {
		// File structure:
		// gram order
		// gram matrix for the next gram order lines
		// rows,columns
		// matrix
		// bounds
		// rhs
		// make the file IO work with labels so it's easier to create.

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String[] data;

		try {
			br = new BufferedReader(new FileReader(file));

			line = br.readLine();

			// get the gram order
			gramOrder = Integer.parseInt(line);

			gramMat = new int[gramOrder][gramOrder];

			int rowReadin = 0;
			// while loop reads in the gram matrix
			for(line = br.readLine(); rowReadin < gramOrder; line = br.readLine()) {
				data = line.split(cvsSplitBy);
				for(int j = 0; j < gramOrder; j++) {
					gramMat[rowReadin][j] = Integer.parseInt(data[j]);
				}
				rowReadin++;
			}

			// line = br.readLine(); //don't need this because it's written in by the for loop previously
			data = line.split(cvsSplitBy); // gets row and column data
			rows = Integer.parseInt(data[0]);
			columns = Integer.parseInt(data[1]);

			mat = new int[rows][columns];
			bounds = new int[columns];
			rhs = new int[rows];

			rowReadin = 0;
			// while loop reads in matrix
			for(line = br.readLine(); rowReadin < rows; line = br.readLine()) {
				data = line.split(cvsSplitBy);
				for(int i = 0; i < mat[rowReadin].length; i++) {
					mat[rowReadin][i] = Integer.parseInt(data[i]);
				}
				rowReadin++;
			}

			// get the bounds
			// line = br.readLine(); //don't need to do this because it is done in the for loop previously.
			data = line.split(cvsSplitBy);
			for(int i = 0; i < bounds.length; i++) {
				bounds[i] = Integer.parseInt(data[i]);
			}

			// get the rhs
			line = br.readLine();
			data = line.split(cvsSplitBy);
			for(int i = 0; i < rhs.length; i++) {
				rhs[i] = Integer.parseInt(data[i]);
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		catch(IOException e) {
			System.out.println("IO error.");
			e.printStackTrace();
		}
		finally {
			if(br != null) {
				try {
					br.close();
				}
				catch(IOException e) {
					System.out.println("Couldn't close BufferedReader.");
					e.printStackTrace();
				}
			}
		}
	}

	private int[] getNextMixedBase(int[] current) {

		boolean done = true;
		for(int i = 0; i < current.length; i++) {
			if(current[i] != 0) {
				done = false;
			}
		}
		if(done) {
			return null;
		}
		else {
			current[0]--;
			for(int i = 0; i < bounds.length; i++) {
				if(current[i] < 0 && i != bounds.length - 1) {
					if(i + 1 < bounds.length) {
						current[i + 1]--;
					}
					else if(current[i] < 0 && i == bounds.length - 1) {
						break;
					}
					current[i] = bounds[i];
				}
			}
		}

		return current;
	}

	protected int[] multiply(int[][] m, int[] vec) {
		int[] resultvec = new int[m.length];

		for(int i = 0; i < resultvec.length; i++) {
			resultvec[i] = 0;
		}

		for(int i = 0; i < m.length; i++) {
			for(int j = 0; j < vec.length; j++) {
				resultvec[i] += m[i][j] * vec[j];
			}
		}

		return resultvec;
	}

	private boolean vecEqual(int[] vecA, int[] vecB) {
		if(vecA.length != vecB.length) {
			return false;
		}

		for(int i = 0; i < vecA.length; i++) {
			if(vecA[i] != vecB[i]) {
				return false;
			}
		}

		return true;
	}

	private void printVec(int[] v) {
		System.out.print("(");
		for(int i = 0; i < v.length - 1; i++) {
			System.out.print(v[i] + ", ");
		}
		System.out.print(v[v.length - 1] + ")");
	}

	private int[] createChildBoundsVerbose(int[] solution, int[] bound) {

		int[] childBounds = new int[2 * bound.length];

		for(int i = 0; i < bounds.length; i++) {
			childBounds[2 * i] = solution[i];
			childBounds[2 * i + 1] = bound[i] - solution[i];
		}

		return childBounds;
	}

	private int[] createChildBounds(int[] verboseChildBounds) {
		int width = 0;
		for(int i = 0; i < verboseChildBounds.length; i++) {
			if(verboseChildBounds[i] != 0) {
				width++;
			}
		}

		int[] childBounds = new int[width];

		int counted = 0;
		for(int i = 0; i < verboseChildBounds.length; i++) {
			if(verboseChildBounds[i] != 0) {
				childBounds[counted] = verboseChildBounds[i];
				counted++;
			}
		}

		return childBounds;
	}

	private int[][] createChildMatrix(int[] childBounds, int[] bound, int[][] matrix) {
		int width = 0;
		for(int i = 0; i < childBounds.length; i++) {
			if(childBounds[i] != 0) {
				width++;
			}
		}

		int[][] childMatrix = new int[matrix.length + 1][width];

		int filled = 0;
		int column = 0;
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < childBounds.length / 2; j++) {
				if(childBounds[2 * j] != 0) {
					childMatrix[i][filled] = matrix[i][column];
					filled++;
				}
				if(childBounds[2 * j + 1] != 0) {
					childMatrix[i][filled] = matrix[i][column];
					filled++;
				}
				column++;
			}
			filled = 0;
			column = 0;
		}

		for(int i = 0; i < childBounds.length; i++) {
			if(childBounds[i] != 0) {
				childMatrix[childMatrix.length - 1][filled] = (int) Math.pow(-1, i);
				filled++;
			}
		}

		return childMatrix;
	}

	private int[] createChildRHS(int[][] mat, int[] gramRow, int[] bound, int length) {
		int[] childRHS = new int[length];

		for(int i = 0; i < length; i++) {
			childRHS[i] = gramRow[i];
		}

		for(int i = 0; i < length; i++) {
			for(int j = 0; j < mat[i].length; j++) {
				childRHS[i] += mat[i][j] * bound[j];
			}
			childRHS[i] = childRHS[i] >> 1;
		}

		return childRHS;
	}

	public ArrayList<EquationSystemMatrix> generateChildren() {
		if(rhs == null) {
			return new ArrayList<EquationSystemMatrix>();
		}
		ArrayList<EquationSystemMatrix> childrenArrayList = new ArrayList<EquationSystemMatrix>();

		int[] solution = new int[bounds.length];
		for(int i = 0; i < solution.length; i++) {
			solution[i] = bounds[i];
		}

		while(solution != null) {
			int[] resultVec = multiply(mat, solution);
			if(vecEqual(resultVec, rhs)) { // if we have a solution, then create a child for it
				int[] childBoundsTemp = createChildBoundsVerbose(solution, bounds);
				int[][] childMat = createChildMatrix(childBoundsTemp, bounds, mat);
				int[] childBounds = createChildBounds(childBoundsTemp);
				int[] childRHS;
				if(childMat.length != gramOrder) {
					childRHS = createChildRHS(childMat, gramMat[childMat.length], childBounds, childMat.length);
				}
				else {
					childRHS = null;
					numberSolutions++;
					System.out.println(new EquationSystemMatrix(childMat.length, childMat[0].length, childRHS, childBounds, childMat));
				}
				childrenArrayList.add(new EquationSystemMatrix(childMat.length, childMat[0].length, childRHS, childBounds, childMat));
			}
			solution = getNextMixedBase(solution);
		}

		return childrenArrayList;
	}

	public void setMatrix(int[][] m) {
		mat = m;
	}

	public void setRHS(int[] r) {
		rhs = r;
	}

	public void setBounds(int[] b) {
		bounds = b;
	}
	
	public int getGramOrder() {
		return gramOrder;
	}

	protected void initialise() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				mat[i][j] = 0;
			}
		}

		rhs = new int[rows];
		bounds = new int[columns];
		for(int i = 0; i < rows; i++) {
			rhs[i] = 0;
		}
		for(int i = 0; i < columns; i++) {
			bounds[i] = 0;
		}
	}

	public String toString() {
		String str = "";

		for(int i = 0; i < bounds.length; i++) {
			str += " " + bounds[i];
		}
		str += " |\n";
		int temp = str.length() + 1;
		for(int i = 0; i < temp; i++) {
			str += "-";
		}
		str += "\n";
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat[i].length; j++) {
				if(mat[i][j] == 1) {
					str += " 1";
				}
				else {
					str += " -";
				}
			}
			str += " | ";
			if(rhs != null) {
				str += rhs[i] + "\n";
			}
			else {
				str += "\n";
			}
		}
		return str;
	}
}