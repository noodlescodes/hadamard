package com.noodlescodes.hadamard.structures;

public class Matrix {

	int[][] mat;
	int rows, columns;

	public Matrix(int size) {
		mat = new int[size][size];
		rows = size;
		columns = size;
		initialise();
	}

	public Matrix(int rows, int columns) {
		mat = new int[rows][columns];
		this.rows = rows;
		this.columns = columns;
		initialise();
	}

	private void initialise() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				mat[i][j] = 0;
			}
		}
	}

	public void setIJ(int i, int j, int v) {
		mat[i][j] = v;
	}

	public String toString() {
		String str = "";

		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns - 1; j++) {
				str += mat[i][j] + ", ";
			}
			str += mat[i][columns - 1] + "\n";
		}

		return str;
	}

	public int rowSum(int i) {
		int sum = 0;
		
		for(int j = 0; j < columns; j++) {
			sum += mat[i][j];
		}

		return sum;
	}

	public int columnSum(int j) {
		int sum = 0;
		
		for(int i = 0; i < rows; i++) {
			sum += mat[i][j];
		}

		return sum;
	}
}