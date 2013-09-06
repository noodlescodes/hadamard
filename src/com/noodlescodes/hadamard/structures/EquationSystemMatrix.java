package com.noodlescodes.hadamard.structures;

import java.util.ArrayList;

public class EquationSystemMatrix extends Matrix {

	private int[] rhs;
	private int[] bounds;

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
			for(int i = bounds.length - 1; i >= 0; i--) {
				System.out.print(current[i]);
			}
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
			System.out.println("");
		}

		return current;
	}

	private int[] multiply(int[][] m, int[] vec) {
		int[] resultvec = new int[vec.length];

		for(int i = 0; i < resultvec.length; i++) {
			resultvec[i] = 0;
		}

		for(int i = 0; i < vec.length; i++) {
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

	public ArrayList<EquationSystemMatrix> generateChildren() {
		ArrayList<EquationSystemMatrix> childrenArrayList = new ArrayList<EquationSystemMatrix>();

		int[] count = new int[bounds.length];
		for(int i = 0; i < count.length; i++) {
			count[i] = bounds[i];
		}

		while(count != null) {
			int[] resultVec = multiply(mat, count);
			if(vecEqual(resultVec, rhs)) {
				childrenArrayList.add(new EquationSystemMatrix(rows + 1, columns, rhs, bounds));
			}

			count = getNextMixedBase(count);
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
				str += " " + mat[i][j];
			}
			str += " | ";
			str += rhs[i] + "\n";
		}
		return str;
	}
}