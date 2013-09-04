package com.noodlescodes.hadamard.structures;

public class EquationSystemMatrix extends Matrix {
	
	private int[] rhs;
	private int[] bounds;
	// private int[][] augmentedMatrix;

	public EquationSystemMatrix(int size) {
		super(size);
	}
	
	public EquationSystemMatrix(int rows, int columns) {
		super(rows, columns);
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
		str +="\n";
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
